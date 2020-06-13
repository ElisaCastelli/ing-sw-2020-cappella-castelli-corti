package it.polimi.ingsw.server;


import it.polimi.ingsw.network.SendMessageToClient;
import it.polimi.ingsw.network.events.CloseConnectionFromServerEvent;
import it.polimi.ingsw.network.objects.ObjMessage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class represents the server
 */
public class EchoServer {

    /**
     * This attribute is the array of players who are going to play the game
     */
    private static ArrayList<ServerHandler> clientArray = new ArrayList<>();

    /**
     * This attribute is the array of players who have to wait to get in the game
     */
    private static ArrayList<ServerHandler> clientWaiting = new ArrayList<>();

    /**
     * This attribute is the number port of the server
     */
    private static int portNumber;

    /**
     * This attribute is the lock used for the client array
     */
    private final Object LOCKClientArray = new Object();

    /**
     * This attribute is the lock used for the client waiting array
     */
    private final Object LOCKWaitingArray = new Object();

    public EchoServer(int portNumber) {
        EchoServer.portNumber = portNumber;
    }

    /**
     * This method adds a client, who is in the clientWaiting array, in clientArray
     * @param indexClientWaiting index of the clientWaiting array
     */
    public void updateClientArray(int indexClientWaiting){
        synchronized (LOCKClientArray) {
            clientArray.add(clientWaiting.get(indexClientWaiting));
            int size = clientArray.size();
            clientArray.get(size - 1).setIndexClientArray(size - 1);
        }
    }

    /**
     * This method updates the clientWaiting indexes when a client disconnected
     * @param indexClientWaiting index of the clientWaiting array who disconnected
     */
    public void updateIndexClientWaiting(int indexClientWaiting){
        synchronized (LOCKWaitingArray) {
            for (int index = indexClientWaiting; index < clientWaiting.size(); index++) {
                clientWaiting.get(index).setIndexClientArray(index);
            }
        }
    }

    /**
     * This method updates the clientArray indexes when a client who is in the game disconnected
     * @param indexClient client index who disconnected
     */
    public void updateIndexClient(int indexClient){
        synchronized (LOCKClientArray) {
            for (int index = indexClient; index < clientArray.size(); index++) {
                clientArray.get(index).setIndexClientArray(index);
            }
        }
    }

    public ArrayList<ServerHandler> getClientArray() {
        return clientArray;
    }

    public ArrayList<ServerHandler> getClientWaiting() {
        return clientWaiting;
    }

    /**
     * This method sends a same message to all clients
     * @param objMessage object message to send
     */
    public void sendBroadCast(ObjMessage objMessage){
        for(ServerHandler serverHandler : clientArray){
            serverHandler.sendUpdate(objMessage);
        }
    }

    /**
     * This method sends a message to one client
     * @param objMessage object message to send
     * @param indexArrayClient index of the clientArray to send the message
     */
    public void send(ObjMessage objMessage, int indexArrayClient){
        clientArray.get(indexArrayClient).sendUpdate(objMessage);
    }

    /**
     * This method sends a waiting message to a client
     * @param objMessage object message to send
     * @param indexArrayClient index of the clientArray to send the message
     */
    public void sendWaiting(ObjMessage objMessage,int indexArrayClient){
        clientWaiting.get(indexArrayClient).sendUpdate(objMessage);
    }

    /**
     * This method closes all the clients
     */
    public void closeServerHandlers(){
        synchronized (LOCKClientArray) {
            if (clientWaiting.size() > 0) {
                for (ServerHandler serverHandler : clientWaiting) {
                    serverHandler.close();
                }
            } else {
                for (ServerHandler serverHandler : clientArray) {
                    serverHandler.close();
                }
            }
            clientWaiting.clear();
            clientArray.clear();
        }
    }

    /**
     * This method remove all the clients who are not in the clientArray
     */
    public void resetWaiting() {
        synchronized (LOCKWaitingArray) {
            int sizeWaiting = clientWaiting.size();
            int sizeInGame = clientArray.size();
            if (sizeWaiting > sizeInGame) {
                for (int i = sizeInGame; i < sizeWaiting; i++) {
                    clientWaiting.get(i).sendUpdate(new CloseConnectionFromServerEvent(true));
                }
            }
            clientWaiting.clear();
        }
    }

    /**
     * This method accepts the client and puts them in the clientWaiting array
     * @param serverSocket server socket of the client
     */
    public void acceptClientWaiting(ServerSocket serverSocket) {
        VirtualView virtualView = null;
        try {
            virtualView = new VirtualView(new SendMessageToClient(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            while(true) {
                Socket clientSocket;

                clientSocket = serverSocket.accept();
                System.out.println("Un client si è connesso" + clientSocket);

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                ServerHandler serverHandler = new ServerHandler(clientSocket, oos, ois, virtualView, clientWaiting.size());
                clientWaiting.add(serverHandler);

                serverHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    closeServerHandlers();
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is the launcher that starts the server
     * @param args arguments
     */
    public static void main(String[] args) {
        portNumber = 1234;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            e.printStackTrace();
        }

        EchoServer echoServer = new EchoServer(portNumber);
        echoServer.acceptClientWaiting(serverSocket);
    }

}

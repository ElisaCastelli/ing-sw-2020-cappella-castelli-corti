package it.polimi.ingsw.server;


import it.polimi.ingsw.network.SendMessageToClient;
import it.polimi.ingsw.network.events.CloseConnectionFromServerEvent;
import it.polimi.ingsw.network.objects.ObjMessage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class EchoServer {
    private static ArrayList<ServerHandler> clientArray = new ArrayList<>();
    private static ArrayList<ServerHandler> clientWaiting = new ArrayList<>();
    private static int portNumber;
    private final Object LOCKClientArray = new Object();
    private final Object LOCKWaitingArray = new Object();

    public EchoServer(int portNumber) {
        EchoServer.portNumber =portNumber;
    }

    ///magari prima di inserire il nome e l'età faccio un controllo che non ci siano extra player e nel caso li chiudo

    public void updateClientArray(int indexClientWaiting){
        synchronized (LOCKClientArray) {
            clientArray.add(clientWaiting.get(indexClientWaiting));
            int size = clientArray.size();
            clientArray.get(size - 1).setIndexClientArray(size - 1);
        }
    }
    public void updateIndexClientWaiting(int indexClientWaiting){
        synchronized (LOCKWaitingArray) {
            for (int index = indexClientWaiting; index < clientWaiting.size(); index++) {
                clientWaiting.get(index).setIndexClientArray(index);
            }
        }
    }
    public void updateIndexClient(int indexClientWaiting){
        synchronized (LOCKClientArray) {
            for (int index = indexClientWaiting; index < clientArray.size(); index++) {
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

    public void sendBroadCast(ObjMessage objMessage){
        for(ServerHandler serverHandler : clientArray){
            serverHandler.sendUpdate(objMessage);
        }
    }
    public void send(ObjMessage objMessage, int indexArrayClient){
        clientArray.get(indexArrayClient).sendUpdate(objMessage);
    }

    public void sendWaiting(ObjMessage objMessage,int indexArrayClient){
        clientWaiting.get(indexArrayClient).sendUpdate(objMessage);
    }

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


    //non lo considerate è per dopo se vogliamo fare la lobby, non è mai richiamato
    public void acceptClientWaiting(ServerSocket serverSocket) {
        VirtualView virtualView = null;
        try {
            virtualView = new VirtualView(new SendMessageToClient(this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            while(true) {
                Socket clientSocket = new Socket();

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

    public static void main(String[] args) {
        //inizializzazione fatta senza main
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

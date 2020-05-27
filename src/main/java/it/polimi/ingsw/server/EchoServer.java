package it.polimi.ingsw.server;


import it.polimi.ingsw.network.SendMessageToClient;
import it.polimi.ingsw.network.events.AskPlayerEvent;
import it.polimi.ingsw.network.events.StartGameEvent;
import it.polimi.ingsw.network.objects.ObjMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private static ArrayList<ServerHandler> clientArray = new ArrayList<>();
    private static ArrayList<ServerHandler> clientWaiting = new ArrayList<>();
    private static int portNumber;


    public EchoServer(int portNumber) {
        EchoServer.portNumber =portNumber;
    }

    public void updateClientArray(int indexClientWaiting){
        clientArray.add(clientWaiting.get(indexClientWaiting));
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

    public ArrayList<ServerHandler> getClientArray() {
        return clientArray;
    }

    //non lo considerate è per dopo se vogliamo fare la lobby, non è mai richiamato
    public void acceptClientWaiting(ServerSocket serverSocket) {
        VirtualView virtualView = null;
        try {
            virtualView = new VirtualView(new SendMessageToClient(this));
        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true){

            Socket clientSocket = new Socket();
            try{

                clientSocket = serverSocket.accept();
                System.out.println("Un client si è connesso" + clientSocket);

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                ServerHandler serverHandler = new ServerHandler(clientSocket, oos, ois, virtualView , clientWaiting.size());
                clientWaiting.add(serverHandler);

                serverHandler.start();

            }catch(Exception e){
                try {
                    clientSocket.close();
                    System.out.println("Error");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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

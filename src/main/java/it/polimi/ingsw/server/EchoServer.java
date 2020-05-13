package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskPlayerEvent;
import it.polimi.ingsw.network.events.StartGameEvent;
import it.polimi.ingsw.network.objects.ObjMessage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private static ArrayList<ServerHandler> clientArray = new ArrayList<>();
    private static boolean notGameStarted = false;
    private static boolean firstTime = true;
    private static boolean notGameFinished = false;
    private static int portNumber;
    private static VirtualView virtualView;
    private static int nPlayer=1;

    public EchoServer(int portNumber){
        EchoServer.portNumber =portNumber;
    }

    public static synchronized void acceptClient(ServerSocket serverSocket) throws IOException {

        Socket clientSocket = new Socket();
        try{
            clientSocket = serverSocket.accept();
            System.out.println("Un client si è connesso" + clientSocket);

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Assigning new thread for this client");

            ServerHandler serverHandler = new ServerHandler(clientSocket, oos, ois, virtualView);
            clientArray.add(serverHandler);

            // Invoking the start() method
            serverHandler.setIndexArrayDiClient(clientArray.size());
            serverHandler.start();

        }catch(Exception e){
            clientSocket.close();
            System.out.println("Errore");
        }
    }

    public static synchronized void sendBroadCast(ObjMessage objMessage){
        for(ServerHandler serverHandler : clientArray){
            serverHandler.sendUpdate(objMessage);
        }
    }

    public static synchronized void initializeGame(){
        if(!notGameStarted){
            initializePlayer();
            waitForPlayer();
            virtualView.startGame();
            sendBroadCast(new StartGameEvent(clientArray.size()));
            notGameStarted=true;
        }
    }

    public static synchronized void initializePlayer(){
        initializeClientArray();
        sendBroadCast(new AskPlayerEvent());
    }

    public static synchronized void initializeClientArray(){
        for(ServerHandler clientHandlers : clientArray){
            clientHandlers.setClientArray(clientArray);
        }
    }

    public static void waitForPlayer(){
        while(!virtualView.isReady()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        virtualView.setReady(false);
    }


    public static void main(String[] args) throws Exception{
        //inizializzazione fatta senza main
        portNumber=1234;
        virtualView= new VirtualView();

        ServerSocket serverSocket = new ServerSocket(portNumber);
        while(!notGameFinished) {

            if(clientArray.size()==0){
                acceptClient(serverSocket);
            }
            while (clientArray.size() != clientArray.get(0).getNPlayer()) {
                acceptClient(serverSocket);
            }
            nPlayer=clientArray.get(0).getNPlayer();
            initializeGame();
        }
    }
}

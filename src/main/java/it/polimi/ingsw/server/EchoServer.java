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
    private static ArrayList<ServerHandler> clientWaiting = new ArrayList<>();
    private static boolean notGameStarted = false;
    private static boolean notGameFinished = false;
    private static int portNumber;
    private static VirtualView virtualView;
    private static int nPlayer=0;

    public EchoServer(int portNumber){
        EchoServer.portNumber =portNumber;
    }

    public static void acceptClient(ServerSocket serverSocket) throws IOException {

        Socket clientSocket = new Socket();
        try{
            //si ferma qua ad aspettare un nuovo client
            clientSocket = serverSocket.accept();
            System.out.println("Un client si è connesso" + clientSocket);

            ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

            System.out.println("Assigning new thread for this client");

            ServerHandler serverHandler = new ServerHandler(clientSocket, oos, ois, virtualView);
            clientArray.add(serverHandler);

            // Invoking the start() method
            serverHandler.setIndexArrayDiClient(clientArray.size()-1);
            serverHandler.start();

        }catch(Exception e){
            clientSocket.close();
            System.out.println("Errore");
        }
    }

    public static void sendBroadCast(ObjMessage objMessage){
        for(ServerHandler serverHandler : clientArray){
            serverHandler.sendUpdate(objMessage);
        }
    }
    public static void send(ObjMessage objMessage,int indexArrayClient){
        clientArray.get(indexArrayClient).sendUpdate(objMessage);
    }



    public static void initializeGame(){
        if(!notGameStarted){
            initializePlayer();
            waitForPlayer();
            virtualView.startGame();
            sendBroadCast(new StartGameEvent(clientArray.size()));
            notGameStarted=true;
        }
    }

    public static void initializePlayer(){
        initializeClientArray();
        sendBroadCast(new AskPlayerEvent());
    }

    public static void initializeClientArray(){
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
    //non lo cacate è per dopo se vogliamo fare la lobby, non è mai richiamato
    public void clientWaiting(ServerSocket serverSocket) throws IOException {
        while(!virtualView.isReady()){

            Socket clientSocket = new Socket();
            try{
                //si ferma qua ad aspettare un nuovo client
                clientSocket = serverSocket.accept();
                System.out.println("Un client si è connesso" + clientSocket);

                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());

                System.out.println("Assigning new thread for this client");

                ServerHandler serverHandler = new ServerHandler(clientSocket, oos, ois, virtualView);
                clientWaiting.add(serverHandler);

                // Invoking the start() method
                serverHandler.setIndexArrayDiClient(clientWaiting.size()-1);
                serverHandler.start();

            }catch(Exception e){
                clientSocket.close();
                System.out.println("Errore");
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
                waitForPlayer();
                nPlayer=clientArray.get(0).getNPlayer();
            }
            while (clientArray.size() != nPlayer) {
                acceptClient(serverSocket);
            }
            initializeGame();
        }
    }
}

package it.polimi.ingsw.server;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.network.events.Ask3CardsEvent;
import it.polimi.ingsw.network.events.AskNPlayerEvent;
import it.polimi.ingsw.network.events.AskPlayerEvent;
import it.polimi.ingsw.network.events.StartGameEvent;
import it.polimi.ingsw.network.objects.ObjAck;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.ProxyGameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private static ArrayList<ServerHandler> clientArray = new ArrayList<>();
    private static boolean notGameStarted = false;
    private static boolean notGameFinished = false;
    private static int portNumber;
    private static VirtualView virtualView;
    private static int nPlayer=1;

    public EchoServer(int portNumber){
        EchoServer.portNumber =portNumber;
    }


    public static void acceptClient(ServerSocket serverSocket) throws IOException {

        Socket socket = new Socket();

        try{
            socket = serverSocket.accept();
            System.out.println("Un client si è connesso" + socket);

            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

            System.out.println("Assigning new thread for this client");

            ServerHandler serverHandler = new ServerHandler(socket, oos, ois, virtualView);
            clientArray.add(serverHandler);


            // Invoking the start() method
            serverHandler.setIndiceArrayDiClient(clientArray.size());
            serverHandler.start();

        }catch(Exception e){
                socket.close();
                System.out.println("Errore");
        }
    }

    public static void sendBroadCast(ObjMessage objMessage){
        for(ServerHandler serverHandler : clientArray){
            serverHandler.sendUpdate(objMessage);
        }
    }
    public static
    void initializeGame(){
        if(!notGameStarted){
            sendBroadCast(new AskPlayerEvent());
            //associazione dei client con l'array di giocatori e farà la sort
            notGameStarted=true;
            sendBroadCast(new StartGameEvent());
        }
    }

    public static void main(String[] args) throws Exception{
        //inizializzazione fatta senza main
        portNumber=1234;
        virtualView= new VirtualView();

        //Create server socket
        ServerSocket serverSocket = new ServerSocket(portNumber);
        while(!notGameFinished) {
            // running infinite loop for getting
            acceptClient(serverSocket);
            while (clientArray.size() != clientArray.get(0).getnPlayer()) {
                acceptClient(serverSocket);
            }
            initializeGame();
        }
    }



}

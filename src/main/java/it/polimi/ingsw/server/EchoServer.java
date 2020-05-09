package it.polimi.ingsw.server;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.network.events.Ask3CardsEvent;
import it.polimi.ingsw.network.events.AskNPlayerEvent;
import it.polimi.ingsw.network.events.StartGameEvent;
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
            serverHandler.start();

            if (clientArray.size()==1) {
                serverHandler.sendUpdate(new AskNPlayerEvent());
                while(serverHandler.getnPlayer()==0){

                }
                nPlayer=serverHandler.getnPlayer();
            }

        }catch(Exception e){
                socket.close();
                System.out.println("Errore");
            }
    }

    public static void startGame(){
        for(ServerHandler serverHandler: clientArray){
            serverHandler.setClientArray(clientArray);
            serverHandler.sendUpdate(new StartGameEvent());
        }
        clientArray.get(0).sendUpdate(new Ask3CardsEvent());
        notGameStarted=true;
    }

    public static void main(String[] args) throws Exception{
        //inizializzazione fatta senza main
        portNumber=1234;
        virtualView= new VirtualView();

        //Create server socket
        ServerSocket serverSocket = new ServerSocket(portNumber);
        while(true) {
            // running infinite loop for getting
            while (clientArray.size() != nPlayer) {
                acceptClient(serverSocket);
                notGameStarted=false;
            }
            if(!notGameStarted) {
                startGame();
            }
        }
    }



}

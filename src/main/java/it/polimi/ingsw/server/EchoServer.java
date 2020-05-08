package it.polimi.ingsw.server;

import com.sun.security.ntlm.Server;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.ProxyGameModel;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private static ArrayList<Thread> clientArray = new ArrayList<>();
    private static boolean gameStarted = false;
    private static int portNumber;
    private static VirtualView virtualView;
    private static int nPlayer;

    public EchoServer(int portNumber){
        EchoServer.portNumber =portNumber;
    }


    public void waitForClient(ServerSocket serverSocket) throws IOException {

        while( !gameStarted || clientArray.size() != 0 ){

            Socket socket = new Socket();

            try{
                socket = serverSocket.accept();
                System.out.println("Un client si è connesso"+socket);

                ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());

                System.out.println("Assigning new thread for this client");

                Thread serverHandler = new ServerHandler(socket, oos, ois, virtualView);
                clientArray.add(serverHandler);
                if(clientArray.size() == 1){
                    gameStarted = true;
                }

                // Invoking the start() method
                serverHandler.start();

            }catch (Exception e){
                socket.close();
                System.out.println("Errore");
            }
        }
    }
    public static void main(String[] args) throws Exception{
        //inizializzazione fatta senza main
        portNumber=1234;
        virtualView= new VirtualView();

        //Create server socket
        ServerSocket serverSocket = new ServerSocket(portNumber);

        // running infinite loop for getting

        // client request



    }

}

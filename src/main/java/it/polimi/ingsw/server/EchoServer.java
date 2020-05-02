package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private ArrayList<Client> clientArray = new ArrayList<>();

    private static int portNumber;

    public EchoServer(int portNumber){
        EchoServer.portNumber =portNumber;
    }
    public void clientRequest(){
        //Infinite loop for getting client request

    }

    public static void main(String[] args) throws Exception{

        //inizializzazione fatta senza main
        portNumber=1234;

        //Create server socket
        ServerSocket serverSocket = new ServerSocket(portNumber);
        // running infinite loop for getting
        // client request

        while(true){
            Socket socket = null;

            try{

                socket = serverSocket.accept();
                System.out.println("Un client si è connesso"+socket);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                Thread requestHandler = new RequestHandler(socket, inputStream, outputStream);


                // Invoking the start() method
                requestHandler.start();

            }catch (Exception e){
                socket.close();
                System.out.println("Errore");
            }
        }
    }
}

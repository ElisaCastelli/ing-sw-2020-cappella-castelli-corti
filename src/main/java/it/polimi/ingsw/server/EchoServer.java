package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

            Socket socket = new Socket();

            try{
                socket = serverSocket.accept();
                System.out.println("Un client si è connesso"+socket);

                ObjectOutputStream oos= new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream ois= new ObjectInputStream(socket.getInputStream());



                System.out.println("Assigning new thread for this client");

                Thread requestHandler = new RequestHandler(socket,oos,ois);


                // Invoking the start() method
                requestHandler.start();

            }catch (Exception e){
                socket.close();
                System.out.println("Errore");
            }
        }
    }
}

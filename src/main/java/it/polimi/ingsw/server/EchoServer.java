package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class EchoServer {
    private ArrayList<Client> clientArray = new ArrayList<>();
    private final String hostName;
    private static int portNumber=1234;

    public EchoServer(String hostName, int portNumber){
        this.hostName=hostName;
        this.portNumber=portNumber;
    }
    public void clientRequest(){
        //Infinite loop for getting client request

    }

    public static void  main(String[] args, int argc) throws Exception{

        //Create server socket
        ServerSocket serverSocket = new ServerSocket(portNumber);
        while(true){
            Socket socket=null;
            try{
                socket = serverSocket.accept();
                System.out.println("Un client si è connesso"+socket);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                Runnable requestHandler = new RequestHandler(socket, inputStream, outputStream);
                requestHandler.run();
            }catch (Exception e){
                System.out.println("Errore");
            }
        }
    }
}

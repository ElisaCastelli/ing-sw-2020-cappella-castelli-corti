package it.polimi.ingsw.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private static InetAddress ip;
    private static int portNumber;


    public Client(int portNumber) throws UnknownHostException {
        ip = InetAddress.getByName("localhost");
        Client.portNumber =portNumber;
    }

    public static void  main(String[] args) {

        //inizializzazione fatta senza main
        try {
            ip = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        portNumber = 1234;

        // establish the connection with server port portnumber
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ip, portNumber);
        } catch (IOException e) {
            System.out.println("connection refused");
        }
        if(clientSocket != null) {
            // obtaining input and out streams
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientHandler clientHandler = new ClientHandler(inputStream, outputStream, clientSocket);
            clientHandler.listening();
        }
    }
}

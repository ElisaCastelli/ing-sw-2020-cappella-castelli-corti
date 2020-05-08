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
        try {
            //inizializzazione fatta senza main
            ip = InetAddress.getByName("localhost");
            portNumber = 1234;

            // establish the connection with server port portnumber
            Socket clientSocket = new Socket(ip, portNumber);
            // obtaining input and out streams

            ObjectOutputStream outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            ClientHandler clientHandler = new ClientHandler(inputStream, outputStream, clientSocket, new View());
            clientHandler.listening();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

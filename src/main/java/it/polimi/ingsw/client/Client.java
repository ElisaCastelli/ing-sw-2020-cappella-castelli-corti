package it.polimi.ingsw.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static InetAddress ip;
    private static int portNumber;
    public Client(int portNumber) throws UnknownHostException {
        ip = InetAddress.getByName("localhost");
        this.portNumber=portNumber;
    }
    public static void  main(String[] args, int argc) throws IOException {
       Socket clientSocket = new Socket(ip,portNumber);
        DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
        DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
        Scanner input = new Scanner(System.in);
        while(!"exit".equals(input)){
            System.out.println(inputStream.readUTF());
            String toSend = input.nextLine();
            outputStream.writeUTF(toSend);
        }
        inputStream.close();
        outputStream.close();
        input.close();
        clientSocket.close();
    }
}

package it.polimi.ingsw.client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client2 {
    private static InetAddress ip;
    private static int portNumber;

    public Client2(int portNumber) throws UnknownHostException {
        ip = InetAddress.getByName("localhost");
        Client2.portNumber =portNumber;
    }
    public static void  main(String[] args) throws IOException {

        try{
            //inizializzazione fatta senza main
            ip = InetAddress.getByName("localhost");
            portNumber=1234;

            // establish the connection with server port portnumber
            Socket clientSocket = new Socket(ip,portNumber);
            // obtaining input and out streams
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            Scanner input = new Scanner(System.in);
            String toSend="";

            //riceve conferma di essere connesso
            System.out.println(inputStream.readUTF());

            while(!"exit".equals(toSend)){

                //send message to the server
                toSend = input.nextLine();
                outputStream.writeUTF(toSend);

                //receiving response by the server
                String received = inputStream.readUTF();
                System.out.println(received);

            }

            System.out.println("Closing this connection : " + clientSocket);
            inputStream.close();
            outputStream.close();
            input.close();
            clientSocket.close();
            System.out.println("Connection closed");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
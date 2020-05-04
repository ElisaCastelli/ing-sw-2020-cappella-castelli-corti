package it.polimi.ingsw.client;

import it.polimi.ingsw.ObjMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {
    private static InetAddress ip;
    private static int portNumber;

    public Client(int portNumber) throws UnknownHostException {
        ip = InetAddress.getByName("localhost");
        Client.portNumber =portNumber;
    }
    public static void  main(String[] args) {

        try{
            //inizializzazione fatta senza main
            ip = InetAddress.getByName("localhost");
            portNumber=1234;

            // establish the connection with server port portnumber
            Socket clientSocket = new Socket(ip,portNumber);
            // obtaining input and out streams

            ObjectOutputStream oos= new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream ois= new ObjectInputStream(clientSocket.getInputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            Scanner input = new Scanner(System.in);
            String toSend="";
            //riceve conferma di essere connesso

            System.out.println(ois.readObject());

            while(!"exit".equals(toSend)){

                //send message to the server
                toSend = input.nextLine();

                ObjMessage objMessageTosend= new ObjMessage(toSend, 56);
                oos.writeObject(objMessageTosend);

                //receiving response by the server

                ObjMessage objRecivied= (ObjMessage) ois.readObject();
                String received=objRecivied.getName();
                int age=objRecivied.getAge();
                System.out.println(received+" " + age);

            }

            System.out.println("Closing this connection : " + clientSocket);
            //inputStream.close();
            //outputStream.close();
            ois.close();
            oos.close();

            input.close();
            clientSocket.close();
            System.out.println("Connection closed");

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

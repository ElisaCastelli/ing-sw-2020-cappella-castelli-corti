package it.polimi.ingsw.server;

import it.polimi.ingsw.network.ObjMessage;

import java.io.*;
import java.net.Socket;

public class RequestHandler extends Thread{

    //private Scanner scanner = new Scanner(System.in);

    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;


    private Socket socket;

    public RequestHandler(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream){
        this.socket=socket;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
    }

    @Override
    public void run(){

        String out="Sei connesso";
        String in ="";

        try {
            outputStream.writeObject(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!"exit".equals(in)){
            // receive the answer from client
            try {
                ObjMessage messageRecieved= (ObjMessage) inputStream.readObject();

                in=messageRecieved.getName();
                System.out.println(in);

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                ObjMessage objMessageToSend= new ObjMessage("messaggio ricevuto",43);
                outputStream.writeObject(objMessageToSend);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Client " + this.socket + " sends exit...");
        System.out.println("Closing this connection.");
        try{
            inputStream.close();
            outputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection closed");
    }
}

package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler extends Thread{

    //private Scanner scanner = new Scanner(System.in);
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private Socket socket;

    public RequestHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream){
        this.socket=socket;
        this.inputStream=inputStream;
        this.outputStream=outputStream;
    }

    @Override
    public void run(){

        String out="Sei connesso";
        String in ="";

        try {
            outputStream.writeUTF(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!"exit".equals(in)){
            // receive the answer from client
            try {
                in = inputStream.readUTF();
                System.out.println(in);

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                outputStream.writeUTF("messaggio ricevuto");

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

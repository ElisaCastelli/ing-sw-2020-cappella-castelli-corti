package it.polimi.ingsw.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class RequestHandler implements Runnable{
    //private Scanner scanner = new Scanner(System.in);
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private Socket socket= null;

    public RequestHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream){
        this.socket=socket;
        this.inputStream=inputStream;
        this.outputStream=outputStream;
    }

    @Override
    public void run(){
        String out="Sei connesso";
        String in = null;
        try {
            in = inputStream.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(!"exit".equals(in)){
            try {
                outputStream.writeUTF(out);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    }
}

package it.polimi.ingsw.server;

import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorServer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread{

    //private Scanner scanner = new Scanner(System.in);
    private ArrayList<ServerHandler> clientArray;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final VirtualView virtualView;
    private Socket socket;
    private int nPlayer;


    public ServerHandler(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, VirtualView virtualView){
        this.socket=socket;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
        this.virtualView = virtualView;
        nPlayer=0;
        clientArray=null;
    }

    public void setClientArray(ArrayList<ServerHandler> clientArray){
        this.clientArray=clientArray;
    }
    public VirtualView getVirtualView() {
        return virtualView;
    }

    public ObjectOutputStream getOutputStream(){
        return outputStream;
    }


    public int getnPlayer(){
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    @Override
    public void run(){
        System.out.println("Sono in ascolto");
        listening();
    }

    public void listening(){
        while (true){
            ObjMessage message = null;
            try{
                message = (ObjMessage) inputStream.readObject();
                message.accept(new VisitorServer(this));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendUpdate(ObjMessage objMessage) {
        try {
            for(ServerHandler serverHandler: clientArray){
                serverHandler.getOutputStream().writeObject(objMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void close(){
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

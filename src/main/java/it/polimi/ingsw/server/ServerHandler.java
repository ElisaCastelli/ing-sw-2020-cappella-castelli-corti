package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskWantToPlayEvent;

import it.polimi.ingsw.network.events.CloseConnectionFromClientEvent;
import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorServer;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ServerHandler extends Thread{

    int indexClientArray;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final VirtualView virtualView;
    private Socket socket;
    private boolean closed= false;

    public ServerHandler(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, VirtualView virtualView, int indexClientArray){
        this.socket=socket;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
        this.virtualView = virtualView;
        this.indexClientArray=indexClientArray;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setIndexClientArray(int indexClientArray) {
        this.indexClientArray = indexClientArray;
    }

    public void sendHeartBeat(){
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!closed) {
                        System.out.println("sending HeartBeat");
                        sendUpdate(new ObjHeartBeat());
                    } else {
                        t.cancel();
                    }
                }catch ( Exception e){
                    e.printStackTrace();
                }
            }
        };
        t.scheduleAtFixedRate(tt, 50000, 60000);
    }

    @Override
    public void run(){
        sendUpdate(new AskWantToPlayEvent(indexClientArray));
        sendHeartBeat();
        listening();
    }

    public void listening(){
        try {
            while (!closed) {
                ObjMessage objMessage = null;

                try {
                    objMessage = (ObjMessage) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    closed = true;
                }
                if (objMessage instanceof CloseConnectionFromClientEvent)
                    closed = true;
                if (objMessage != null)
                    objMessage.accept(new VisitorServer(virtualView));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        virtualView.controlStillOpen(indexClientArray);
        close();
    }


    public void sendUpdate(ObjMessage objMessage) {
        objMessage.setClientIndex(indexClientArray);

        try {
            outputStream.writeObject(objMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("connection reset");
            e.printStackTrace();
        }
    }


    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package it.polimi.ingsw.server;

//import it.polimi.ingsw.network.HeartBeatServer;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.events.AskNPlayerEvent;
//import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.events.AskWantToPlay;

import it.polimi.ingsw.network.events.CloseConnectionFromClientEvent;
import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorServer;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class ServerHandler extends Thread{

    ///al posto del client array
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

    public void sendHeartBeat(){
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (!closed) {
                    System.out.println("sending HeartBeat");
                    sendUpdate(new ObjHeartBeat(System.currentTimeMillis()));
                }else{
                    t.cancel();
                }

            }
        };
        t.scheduleAtFixedRate(tt, 50000, 60000);
    }

    @Override
    public void run(){
        sendUpdate(new AskWantToPlay(indexClientArray));
        sendHeartBeat();
        listening();
    }

    public void listening(){
        while (!closed){
            ObjMessage message = null;
            try{
                message = (ObjMessage) inputStream.readObject();
                if(message instanceof CloseConnectionFromClientEvent)
                    closed=true;
                message.accept(new VisitorServer(virtualView));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        close();
    }


    public void sendUpdate(ObjMessage objMessage) {
        try {
            objMessage.setClientIndex(indexClientArray);
            outputStream.writeObject(objMessage);
            outputStream.reset();
        } catch (IOException e) {
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

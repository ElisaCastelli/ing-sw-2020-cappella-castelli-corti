package it.polimi.ingsw.server;

//import it.polimi.ingsw.network.HeartBeatServer;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.events.AskNPlayerEvent;
//import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.events.AskWantToPlay;

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




    public ServerHandler(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, VirtualView virtualView, int indexClientArray){
        this.socket=socket;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
        this.virtualView = virtualView;

        this.indexClientArray=indexClientArray;
    }

    public void sendHeartBeat(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("sending HeartBeat");
                sendUpdate(new ObjHeartBeat(indexClientArray, System.currentTimeMillis()));
            }
        }, 10000, 50000);
    }

    @Override
    public void run(){
        sendUpdate(new AskWantToPlay(indexClientArray));
        sendHeartBeat();
        listening();
    }

    public void listening(){
        while (true){
            ObjMessage message = null;
            try{
                message = (ObjMessage) inputStream.readObject();
                message.accept(new VisitorServer(virtualView));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

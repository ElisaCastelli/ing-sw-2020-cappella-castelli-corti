package it.polimi.ingsw.network;

import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.objects.ObjMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HeartBeatClient extends Thread {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Socket socket;
    private final int MAX_HEARTBEATS_MISSED=3;
    private boolean recivedHB=false;
    int missed_heartBeats=0;

    public HeartBeatClient(ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
    }
    public void waitHeartBeat(){
        while(!recivedHB){
            try{
                Thread.sleep(11000);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {


        while (missed_heartBeats<=MAX_HEARTBEATS_MISSED){
            listening();
            if(!recivedHB){
                //non ho ricevuto un messaggio dal server
                missed_heartBeats++;
            }
            //ho ricevuto un heartbeat e invio la risposta
            try {
                ObjHeartBeat objHeartBeat= new ObjHeartBeat();
                outputStream.writeObject(objHeartBeat);
            } catch (IOException e) {
                e.printStackTrace();
            }
            recivedHB=false;
            waitHeartBeat();

        }

    }
    public synchronized void listening(){
        long start_time = System.currentTimeMillis();
        long wait_time = 1000;
        long end_time = start_time + wait_time;
        while (System.currentTimeMillis()< end_time){
            ObjMessage objMessage = null;
            try{
                objMessage = (ObjMessage) inputStream.readObject();
                if(objMessage instanceof ObjHeartBeat){
                    System.out.println(((ObjHeartBeat) objMessage).getMessageHeartbeat());
                    recivedHB=true;
                    missed_heartBeats=0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("il server non sta rispondendo");
        close();
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
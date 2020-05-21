package it.polimi.ingsw.network;

import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.objects.ObjMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class HeartBeatServer extends Thread {
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private Socket socket;
    private final int MAX_HEARTBEATS_MISSED=3;
    private boolean recivedHB=false;
    int missed_heartBeats=0;



    public HeartBeatServer(ObjectOutputStream outputStream, ObjectInputStream inputStream, Socket socket) {
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.socket = socket;
    }
    public void waitNextHeartBeat(){
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        while (missed_heartBeats<=MAX_HEARTBEATS_MISSED){
            try {
                //manda un heartbeat
                ObjHeartBeat objHeartBeat= new ObjHeartBeat();
                outputStream.writeObject(objHeartBeat);
                //ascolta per tot secondi
                listening();
                //controlla se ha ricevuto risposta e incrementa nel caso negativo
                if(!recivedHB){
                    missed_heartBeats++;
                    System.out.println("incremento hb");
                }
                //altrimenti rimette a falso
                recivedHB=false;
                waitNextHeartBeat();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        System.out.println("il client non sta rispondendo");
        close();
    }


    public synchronized void listening(){
        long start_time = System.currentTimeMillis();
        long wait_time = 2000;
        long end_time = start_time + wait_time;

        while (System.currentTimeMillis()< end_time){
            ObjMessage objMessage=null;
            try{
                objMessage = (ObjMessage) inputStream.readObject();

                if(objMessage instanceof ObjHeartBeat) {
                    System.out.println(((ObjHeartBeat) objMessage).getMessageHeartbeat());
                    recivedHB=true;
                    missed_heartBeats=0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
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

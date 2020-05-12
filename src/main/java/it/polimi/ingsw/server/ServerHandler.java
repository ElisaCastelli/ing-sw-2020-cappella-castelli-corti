package it.polimi.ingsw.server;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.events.AskNPlayerEvent;
import it.polimi.ingsw.network.objects.ObjInitialize;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerHandler extends Thread{

    private ArrayList<ServerHandler> clientArray;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final VirtualView virtualView;
    private Socket socket;
    private User user;


    public ServerHandler(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, VirtualView virtualView){
        this.socket=socket;
        this.outputStream=outputStream;
        this.inputStream=inputStream;
        this.virtualView = virtualView;
        user=new User();
        clientArray=new ArrayList<>();
    }

    public String getUserName(){
        return user.getName();
    }
    public ArrayList<ServerHandler> getClientArray() {
        return clientArray;
    }

    public int getIndexClient(int indexPlayer){
        boolean found=false;
        int indexClient=0;
        if(indexPlayer==clientArray.size()){
            indexPlayer=0;
        }
        while(!found && indexClient < clientArray.size()){
            if(clientArray.get(indexClient).getIndexPlayer()==indexPlayer){
                found=true;
            }
            else {
                indexClient++;
            }
        }
        return indexClient;
    }

    public void setClientArray(ArrayList<ServerHandler> clientArray){
        this.clientArray=clientArray;
    }
    public void setClientName(String name){
        user.setName(name);
    }

    public void setIndexPlayer(int indexPlayer){
        user.setIndexPlayer(indexPlayer);
    }

    public int getIndexPlayer(){
        return user.getIndexPlayer();
    }

    public void setnPlayer(int nPlayer){
        user.setnPlayer(nPlayer);
    }

    public int getNPlayer(){
        return user.getnPlayer();
    }

    public VirtualView getVirtualView() {
        return virtualView;
    }

    public void setIndexArrayDiClient(int indiceArrayDiClient) {
        user.setIndexArrayDiClient(indiceArrayDiClient);
    }

    public User getUser(){
        return user;
    }

    public void setNameCard(String nameCard) {
        user.setNameCard(nameCard);
    }
    public ObjectOutputStream getOutputStream(){
        return outputStream;
    }

    public ObjInitialize gameData(){
        ArrayList<User> userArray= new ArrayList<>();
        for(ServerHandler client : clientArray){
            userArray.add(client.getUser());
        }
        return new ObjInitialize(userArray,getVirtualView().updateBoard());
    }

    public void waitForPlayer(){
        while(!virtualView.isReady()){
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        virtualView.setReady(false);
    }

    @Override
    public void run(){
        System.out.println("Sono in ascolto");
        //se sono il primo ad essermi connesso mando un messaggio al client per sapere il numero di giocatori
        if(user.getIndexArrayDiClient()==1){
            sendUpdate(new AskNPlayerEvent());
        }else{
            listening();
        }
        //poi mi metto in ascolto del cient
        listening();
    }

    public void listening(){
        while (true){
            ObjMessage message = null;
            try{
                message = (ObjMessage) inputStream.readObject();
                message.accept(new VisitorServer(this));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendUpdateBroadcast(ObjMessage objMessage) {
        try {
            for(int index=0; index<clientArray.size();index++){
                clientArray.get(index).getOutputStream().writeObject(objMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendUpdate(ObjMessage objMessage) {
        try {
            outputStream.writeObject(objMessage);
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

package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.AskWantToPlay;
import it.polimi.ingsw.network.events.CloseConnectionFromClientEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class SendMessageToServer {

    private final ClientHandler clientHandler;

    public SendMessageToServer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void sendNPlayer(int response) {
        ObjNumPlayer numPlayer = new ObjNumPlayer(response);
        clientHandler.sendMessage(numPlayer);
    }
    public void sendAckStartGame() {
        clientHandler.sendMessage(new AckStartGame());
    }

    public void sendAckPlayer() {
        clientHandler.sendMessage(new AckPlayer());
    }

    public void sendPlayer(String name, int age, int indexClient) {
        ObjPlayer objPlayer = new ObjPlayer(name,age);
        objPlayer.setClientIndex(indexClient);
        clientHandler.sendMessage(objPlayer);
    }

    public void send3card(ArrayList<Integer> cardTemp) {
        ObjTempCard objTempCard = new ObjTempCard(cardTemp);
        clientHandler.sendMessage(objTempCard);
    }

    public void sendCard(int choseCardIndex, int indexPlayer) {
        ObjCard objCard = new ObjCard(choseCardIndex, indexPlayer);
        System.out.println("Sending the card chose to the server");
        clientHandler.sendMessage(objCard);
    }

    public void sendWorker(ArrayList<Box> boxes, int indexPlayer) {
        ObjWorkers objWorkers = new ObjWorkers(boxes.get(0), boxes.get(1));
        clientHandler.sendMessage(objWorkers);
    }

    public void sendWorkerToMove(ObjWorkerToMove objWorkerToMove) {
        clientHandler.sendMessage(objWorkerToMove);
    }

    public void sendBlockBeforeMove(ObjBlockBeforeMove objBlockBeforeMove) {
        clientHandler.sendMessage(objBlockBeforeMove);
    }

    public void sendMoveWorker(ObjMove objMove) {
            clientHandler.sendMessage(objMove);
    }

    public void sendAckMove(AckMove ackMove) {
        clientHandler.sendMessage(ackMove);
    }

    public void sendBuildMove(ObjBlock objBlock) {
        if(objBlock.isDone()){
            clientHandler.sendMessage(new AckBlock());
        }else{
            clientHandler.sendMessage(objBlock);
        }
    }

    public void sendAckState() {
        clientHandler.sendMessage(new AckState());
    }

    public void sendPong(int indexClient, long timeStamp){
        ObjHeartBeat objHeartBeat =new ObjHeartBeat(indexClient, timeStamp);
        clientHandler.sendMessage(objHeartBeat);
    }

    public void sendAskWantToPlay(AskWantToPlay askWantToPlay) {
        clientHandler.sendMessage(askWantToPlay);
    }


    public void sendAckClosingConnection(int clientIndex) {
        clientHandler.sendMessage(new CloseConnectionFromClientEvent(clientIndex));
    }
}

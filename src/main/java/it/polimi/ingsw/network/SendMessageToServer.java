package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class SendMessageToServer {

    private final ClientHandler clientHandler;

    public SendMessageToServer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void sendNPlayer(int response) {
        ObjNumPlayer numPlayer= new ObjNumPlayer(response);
        clientHandler.sendMessage(numPlayer);
    }
    public void sendAckStartGame() {
        clientHandler.sendMessage(new AckStartGame());
    }

    public void sendAckPlayer() {
        clientHandler.sendMessage(new AckPlayer());
    }

    public void sendPlayer(String name, int age) {
        ObjPlayer objPlayer= new ObjPlayer(name,age);
        clientHandler.sendMessage(objPlayer);
    }

    public void send3card(ArrayList<Integer> cardTemp) {

        ObjTempCard objCard = new ObjTempCard(cardTemp);
        clientHandler.sendMessage(objCard);
    }

    public void sendCard(int scelta) {
        ObjCard objCard = new ObjCard(scelta);
        System.out.println("Sending the card chose to the server");
        clientHandler.sendMessage(objCard);
    }

    public void sendWorker(ArrayList<Box> boxes) {
        ObjWorkers objWorkers = new ObjWorkers(boxes.get(0), boxes.get(1));
        clientHandler.sendMessage(objWorkers);
    }

    public void sendWorkerToMove(ObjWorkerToMove objWorkerToMove) {
        clientHandler.sendMessage(objWorkerToMove);
    }

    public void sendMoveWorker(ObjMove objMove) {
        if(objMove.isDone()){
            clientHandler.sendMessage(new AckMove(objMove.getIndexWorkerToMove(), objMove.getRowStart(), objMove.getColumnStart()));
        }else{
            clientHandler.sendMessage(objMove);
        }
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
}

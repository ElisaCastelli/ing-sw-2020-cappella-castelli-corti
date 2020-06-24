package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.AskWantToPlayEvent;
import it.polimi.ingsw.network.events.CloseConnectionFromClientEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

/**
 * class to send messages to the clients
 */
public class SendMessageToServer {
    /**
     * Client handler
     */
    private final ClientHandler clientHandler;

    /**
     * constructor for the class
     *
     * @param clientHandler the class used to handle the connection client's side
     */
    public SendMessageToServer(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    /**
     * a method to send the response to join a game
     *
     * @param askWantToPlayEvent identify an object to ask if the client can join a game
     */

    public void sendAskWantToPlay(AskWantToPlayEvent askWantToPlayEvent) {
        clientHandler.sendMessage(askWantToPlayEvent);
    }

    /**
     * send the response for the number of players
     *
     * @param response the number of player chosen for the game
     */

    public void sendNPlayer(int response) {
        ObjNumPlayer numPlayer = new ObjNumPlayer(response);
        clientHandler.sendMessage(numPlayer);
    }

    /**
     * a method to send a message to notify the start of a game to the server
     */

    public void sendAckStartGame() {
        clientHandler.sendMessage(new AckStartGame());
    }

    /**
     * a method to send a message to notify that a user join a game
     */

    public void sendAckPlayer() {
        clientHandler.sendMessage(new AckPlayer());
    }

    /**
     * a method to send info of a player
     *
     * @param name        the name of the player
     * @param age         the age of a player
     * @param indexClient the index of the client connected
     */

    public void sendPlayer(String name, int age, int indexClient) {
        ObjPlayer objPlayer = new ObjPlayer(name, age);
        objPlayer.setClientIndex(indexClient);
        clientHandler.sendMessage(objPlayer);
    }

    /**
     * a method to send the cards selected for the game
     *
     * @param cardTemp the cards selected for the game
     */

    public void send3card(ArrayList<Integer> cardTemp) {
        ObjTempCard objTempCard = new ObjTempCard(cardTemp);
        clientHandler.sendMessage(objTempCard);
    }

    /**
     * a method to send the card chosen by a player for a game
     *
     * @param choseCardIndex index of the card in the array
     */

    public void sendCard(int choseCardIndex) {
        ObjCard objCard = new ObjCard(choseCardIndex);
        clientHandler.sendMessage(objCard);
    }

    /**
     * a method to send the position of a worker in the board
     *
     * @param boxes is a array of box to identify the position of a worker
     */


    public void sendWorker(ArrayList<Box> boxes) {
        ObjWorkers objWorkers = new ObjWorkers(boxes.get(0), boxes.get(1));
        clientHandler.sendMessage(objWorkers);
    }

    /**
     * a method to send the worker that a player want to move
     *
     * @param objWorkerToMove identify the worker to move
     */

    public void sendWorkerToMove(ObjWorkerToMove objWorkerToMove) {
        clientHandler.sendMessage(objWorkerToMove);
    }

    /**
     * a method to send the block that a player wat to build before the worker's move
     *
     * @param objBlockBeforeMove identify the block to build before the worker's move
     */

    public void sendBlockBeforeMove(ObjBlockBeforeMove objBlockBeforeMove) {
        clientHandler.sendMessage(objBlockBeforeMove);
    }

    /**
     * a method to send the info for the move of a worker
     *
     * @param objMove identify the info for the move of a worker
     */

    public void sendMoveWorker(ObjMove objMove) {
        clientHandler.sendMessage(objMove);
    }

    /**
     * a method to send the notification of a move
     *
     * @param ackMove identify an object to notify that a move was done
     */

    public void sendAckMove(AckMove ackMove) {
        clientHandler.sendMessage(ackMove);
    }

    /**
     * a method to send the info for a building move
     *
     * @param objBlock identify the info for a building move
     */

    public void sendBuildMove(ObjBlock objBlock) {
        if (objBlock.isDone()) {
            clientHandler.sendMessage(new AckBlock());
        } else {
            clientHandler.sendMessage(objBlock);
        }
    }

    /**
     * a method to send the response to the heartbeat received by the server
     *
     * @param indexClient identify the index of the client
     */

    public synchronized void sendPong(int indexClient) {
        ObjHeartBeat objHeartBeat = new ObjHeartBeat();
        objHeartBeat.setClientIndex(indexClient);
        clientHandler.sendMessage(objHeartBeat);
    }


    /**
     * a method to send the notification of the connection's closing
     *
     * @param indexClient identify the index of the client
     * @param beforeStart true if the game has not started yet
     */


    public void sendAckClosingConnection(int indexClient, boolean beforeStart) {
        CloseConnectionFromClientEvent closeConnectionFromClientEvent = new CloseConnectionFromClientEvent();
        closeConnectionFromClientEvent.setClientIndex(indexClient);
        closeConnectionFromClientEvent.setBeforeStart(beforeStart);
        clientHandler.sendMessage(closeConnectionFromClientEvent);
    }
}

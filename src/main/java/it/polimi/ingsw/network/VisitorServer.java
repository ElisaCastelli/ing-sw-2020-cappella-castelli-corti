package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.VirtualView;

//todo da rinominare la classe

/**
 * Visitor pattern server's side to invoke methods on the virtualView
 */

public class VisitorServer {
    /**
     * view of the MVC pattern
     */
    private final VirtualView virtualView;

    /**
     * class constructor
     *
     * @param virtualView the view of the MVC pattern
     */
    public VisitorServer(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    /**
     * method to establish a connect with the client
     *
     * @param askWantToPlayEvent is a message to start the communication with the client
     */

    public void visit(AskWantToPlayEvent askWantToPlayEvent) {
        virtualView.askWantToPlay(askWantToPlayEvent.getIndexClient());
    }

    /**
     * method to set the number of player in a game
     *
     * @param objNumPlayer is a message that contains the number of players chosen by a user
     */

    public void visit(ObjNumPlayer objNumPlayer) {
        virtualView.setNPlayers(objNumPlayer.getNPlayer());
    }

    /**
     * method to set the info of a player
     *
     * @param objPlayer is a message that contains the name and the age of a player
     */

    public void visit(ObjPlayer objPlayer) {
        System.out.println("Receiving player data");
        ///serverHandler.setClientName(objPlayer.getName());
        virtualView.addPlayer(objPlayer.getName(), objPlayer.getAge(), objPlayer.getClientIndex());
    }

    /**
     * method to receive a response from a client joining a game
     *
     * @param ackPlayer is a message that notify the server for a client joining a game
     */
    public void visit(AckPlayer ackPlayer) {
        try {
            virtualView.getCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to receive the response from a client about the starting of the game
     *
     * @param ackStartGame is a message that notify that a game has started
     */

    public void visit(AckStartGame ackStartGame) {
        virtualView.askState();
    }

    /**
     * method to receive the cards chosen for the game
     *
     * @param objTempCard is a message that contains the cards for a game
     */

    public void visit(ObjTempCard objTempCard) {
        virtualView.setTempCard(objTempCard.getCardsTemp());
    }

    /**
     * method to receive a card chosen by a player
     *
     * @param objCard is a message that contains a card chosen by a user
     */

    public void visit(ObjCard objCard) {
        try {
            virtualView.setCard(objCard.getCardChose());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method to receive the position of a worker
     *
     * @param objWorkers is a message that contains the position of a worker
     */

    public void visit(ObjWorkers objWorkers) {
        virtualView.initializeWorker(objWorkers.getBox1(), objWorkers.getBox2());
    }

    /**
     * method to receive the info of a worker to move in this turn
     *
     * @param objWorkerToMove is a message that contains the worker to move chosen by the user
     */

    public void visit(ObjWorkerToMove objWorkerToMove) {
        virtualView.setBoxReachable(objWorkerToMove.getRow(), objWorkerToMove.getColumn(), objWorkerToMove.getIndexWorkerToMove(), objWorkerToMove.isReady());
    }

    /**
     * method to receive a Block to build before the actual move of a worker
     *
     * @param objBlockBeforeMove is a message that contains the block that the user want to build before the move of the worker
     */

    public void visit(ObjBlockBeforeMove objBlockBeforeMove) {
        virtualView.buildBeforeMove(objBlockBeforeMove.getIndexWorker(), objBlockBeforeMove.getRowWorker(), objBlockBeforeMove.getColumnWorker(), objBlockBeforeMove.wantToBuild());
    }

    /**
     * method to receive the move the move of the worker selected by the user
     *
     * @param objMove is a message that contains the move of the worker selected by the user
     */

    public void visit(ObjMove objMove) {
        virtualView.move(objMove.getRowStart(), objMove.getColumnStart(), objMove.getRow(), objMove.getColumn(), objMove.getIndexWorkerToMove(), objMove.isFirstTime());
    }

    /**
     * method to receive a notification for the move done by a player
     *
     * @param ackMove is a message that contains the information to control if a player can build after the move
     */

    public void visit(AckMove ackMove) {
        virtualView.canBuild(ackMove.getClientIndex(), ackMove.getIndexWorker(), ackMove.getRowWorker(), ackMove.getColumnWorker());
    }

    /**
     * method to receive a block that a player wants to build
     *
     * @param objBlock is a message that contains the build move of a player
     */

    public void visit(ObjBlock objBlock) {
        virtualView.buildBlock(objBlock.getClientIndex(), objBlock.getIndexWorker(), objBlock.getRowWorker(), objBlock.getColumnWorker(), objBlock.getRowBlock(), objBlock.getColumnBlock(), objBlock.isFirstTime(), objBlock.isSpecialTurn(), objBlock.getPossibleBlock());
    }

    /**
     * method to receive a notification for the building move done by a player
     *
     * @param ackBlock is a message that contains the information to control if a player can move after building
     */

    public void visit(AckBlock ackBlock) {
        virtualView.canMove();
    }

    /**
     * method to control the connection with a client
     *
     * @param objHeartBeat is a message to test the connection's validity
     */

    public void visit(ObjHeartBeat objHeartBeat) {
        virtualView.printHeartBeat(objHeartBeat.getMessageHeartbeat(), objHeartBeat.getClientIndex());
    }

    /**
     * method to close the connection with a client
     *
     * @param closeConnectionClientEvent is a message to notify the connection's closing
     */

    public void visit(CloseConnectionFromClientEvent closeConnectionClientEvent) {
        virtualView.close(closeConnectionClientEvent.getClientIndex());
    }
}

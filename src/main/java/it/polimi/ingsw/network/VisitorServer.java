package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;

import it.polimi.ingsw.server.VirtualView;

public class VisitorServer {
    private VirtualView virtualView;

    public VisitorServer(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    public void visit(AskWantToPlay askWantToPlay){
        virtualView.askWantToPlay(askWantToPlay.getIndexClient());
    }

    public void visit(ObjNumPlayer objNumPlayer){
        virtualView.setNPlayers(objNumPlayer.getnPlayer());
    }



    public void visit(ObjPlayer objPlayer){
        System.out.println("Receiving player data");
        ///serverHandler.setClientName(objPlayer.getName());
        virtualView.addPlayer(objPlayer.getName(), objPlayer.getAge(), objPlayer.getClientIndex());
    }


    public void visit(AckStartGame ackStartGame) {
        virtualView.askState();
    }

    public void visit(ObjTempCard objTempCard){
        virtualView.setTempCard(objTempCard.getCardsTemp());
    }

    public void visit(ObjCard objCard) {
        try {
            virtualView.setCard(objCard.getIndexPlayer(), objCard.getCardChose());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visit(AckState ackState) {
        //todo probabilmente da togliere
        //serverHandler.getVirtualView().incCounterOpponent();
        //System.out.println("AckState thread number: "+ serverHandler.getName());
    }

    public void visit(AckPlayer ackPlayer) {
        try {
            virtualView.getCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visit (ObjWorkers objWorkers){
        virtualView.initializeWorker(objWorkers);
    }

    public void visit (ObjWorkerToMove objWorkerToMove){
        virtualView.setBoxReachable(objWorkerToMove);
    }

    public void visit(AckUpdateBoard ackUpdateBoard){
        ///serverHandler.getVirtualView().incCounterOpponent();
    }

    public void visit ( ObjBlockBeforeMove objBlockBeforeMove ){
        virtualView.buildBeforeMove(objBlockBeforeMove.getIndexWorker(), objBlockBeforeMove.getRowWorker(), objBlockBeforeMove.getColumnWorker(), objBlockBeforeMove.wantToBuild());
    }

    public void visit(ObjMove objMove){
        virtualView.move(objMove);
    }

    public void visit(AckMove ackMove){
        virtualView.canBuild(ackMove.getClientIndex(), ackMove.getIndexWorker(), ackMove.getRowWorker(), ackMove.getColumnWorker());
    }

    public void visit(ObjBlock objBlock){
        virtualView.buildBlock(objBlock.getClientIndex(), objBlock.getIndexWorker(), objBlock.getRowWorker(), objBlock.getColumnWorker(), objBlock.getRowBlock(), objBlock.getColumnBlock(), objBlock.isSpecialTurn(), objBlock.getPossibleBlock());
    }

    public void visit(AckBlock ackBlock){
        virtualView.canMove();
    }

    public void visit(ObjHeartBeat objHeartBeat){
        virtualView.printHeartBeat(objHeartBeat.getMessageHeartbeat(), objHeartBeat.getClientIndex(), objHeartBeat.getTimeStamp());
    }

    public void visit(CloseConnectionFromClientEvent closeConnectionClientEvent){
        virtualView.close(closeConnectionClientEvent.getIndexClient());
    }
}

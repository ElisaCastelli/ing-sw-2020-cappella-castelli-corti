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
        ///serverHandler.setIndexPlayer(serverHandler.getVirtualView().searchByName(serverHandler.getUserName()));
        ///serverHandler.sendUpdate(new ObjState(serverHandler.getIndexPlayer(),0));
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
    ///mi serve per controllare che a tutti sia arrivata la board aggiornata
    public void visit(AckUpdateBoard ackUpdateBoard){
        ///serverHandler.getVirtualView().incCounterOpponent();
    }

    public void visit(ObjMove objMove){
        virtualView.move(objMove);

        /*serverHandler.waitForPlayer();
        int indexPlayer = serverHandler.getIndexPlayer();
        if(serverHandler.getVirtualView().isReachable(objMove.getRow(), objMove.getColumn())) {

            AskMoveEvent askMoveEvent = serverHandler.getVirtualView().move(indexPlayer, objMove.getIndexWorkerToMove(), objMove.getRow(), objMove.getColumn());
            UpdateBoardEvent updateBoardEvent = serverHandler.getVirtualView().updateBoard();
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            ///check win/morto
            if(serverHandler.getVirtualView().checkWin(indexPlayer, objMove.getRowStart(), objMove.getColumnStart(), objMove.getIndexWorkerToMove())){
                ///sendupdate.hai vinto
                ///sendbroadcast senza io hai perso
            }else{
                if(askMoveEvent.isDone()){
                    //Controllo che può costruire e passo alla costruzione dell'edificio, altrimenti il giocatore perde
                    if(serverHandler.getVirtualView().canBuild(indexPlayer, askMoveEvent.getIndexWorker())){
                        updateBoardEvent = serverHandler.getVirtualView().updateBoard();
                        updateBoardEvent.setShowReachable(true);
                        serverHandler.sendUpdateBroadcast(updateBoardEvent);
                        serverHandler.waitForPlayer();
                        serverHandler.sendUpdateBroadcast(new AskBuildEvent(askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn(), true, false));
                    }
                    else{
                        //Giocatore morto X
                    }
                }else{
                    updateBoardEvent = serverHandler.getVirtualView().setBoxBuilding(indexPlayer, askMoveEvent.getIndexWorker());
                    updateBoardEvent.setShowReachable(true);
                    serverHandler.sendUpdateBroadcast(updateBoardEvent);
                    serverHandler.waitForPlayer();
                    serverHandler.sendUpdateBroadcast(askMoveEvent);
                }
            }
        }*/
    }

    public void visit(AckMove ackMove){
       /* serverHandler.waitForPlayer();

        int indexWorker = ackMove.getIndexWorker();
        int rowWorker = ackMove.getRowWorker();
        int columnWorker = ackMove.getColumnWorker();

        UpdateBoardEvent updateBoardEvent;
        updateBoardEvent = serverHandler.getVirtualView().updateBoard();
        updateBoardEvent.setShowReachable(true);
        serverHandler.sendUpdateBroadcast(updateBoardEvent);
        serverHandler.waitForPlayer();
        serverHandler.sendUpdateBroadcast(new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false));*/
    }

    public void visit(ObjBlock objBlock){
        /*int indexWorker = objBlock.getIndexWorker();
        int rowWorker = objBlock.getRowWorker();
        int columnWorker = objBlock.getColumnWorker();
        int rowBlock = objBlock.getRowBlock();
        int columnBlock = objBlock.getColumnBlock();
        UpdateBoardEvent updateBoardEvent;

        serverHandler.waitForPlayer();
        //Controllo se la casella data è una di quelle consigliate come raggiungibile, in caso negativo gli rimando askBuildEvent come se fosse la prima volta (Questo controllo serve più che altro per la CLI)
        if(serverHandler.getVirtualView().isReachable(rowBlock, columnBlock)){
            int indexPlayer = serverHandler.getIndexPlayer();
            AskBuildEvent askBuildEvent = serverHandler.getVirtualView().buildBlock(indexPlayer, indexWorker, rowWorker, columnWorker, rowBlock, columnBlock);
            updateBoardEvent = serverHandler.getVirtualView().updateBoard();
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            if(serverHandler.getVirtualView().checkWinAfterBuild()){
                //Il giocatore con Crono ha vinto
            }else
                if(askBuildEvent.isDone()){
                    //Ricontrollare se è giusto il passaggio al nuovo giocatore + stato per inizio turno
                    ObjState objState = serverHandler.getVirtualView().goPlayingNext();
                    int nextPlayer = serverHandler.getIndexClient(indexPlayer + 1);
                    if(serverHandler.getVirtualView().canMove(nextPlayer)){
                        serverHandler.sendUpdateBroadcast(objState);
                        serverHandler.waitForPlayer();
                        AskWorkerToMoveEvent askWorkerToMoveEvent = serverHandler.getVirtualView().getWorkersPos(serverHandler.getIndexNext(), true);
                        serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
                    }else {

                    }
                }else {
                    updateBoardEvent = serverHandler.getVirtualView().setBoxBuilding(indexPlayer, indexWorker);
                    updateBoardEvent.setShowReachable(true);
                    serverHandler.sendUpdateBroadcast(updateBoardEvent);
                    serverHandler.waitForPlayer();
                    serverHandler.sendUpdateBroadcast(askBuildEvent);
                }
        }else {
            updateBoardEvent = serverHandler.getVirtualView().updateBoard();
            updateBoardEvent.setShowReachable(true);
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, objBlock.isFirstTime(), false);
            askBuildEvent.setWrongBox(true);
            serverHandler.sendUpdateBroadcast(askBuildEvent);
        }*/
    }

    public void visit(AckBlock ackBlock){
        /*serverHandler.waitForPlayer();
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        int indexPlayer = serverHandler.getIndexPlayer();
        int nextPlayer = serverHandler.getIndexClient(indexPlayer + 1);
        if(serverHandler.getVirtualView().canMove(nextPlayer)){
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();
            AskWorkerToMoveEvent askWorkerToMoveEvent = serverHandler.getVirtualView().getWorkersPos(serverHandler.getIndexNext(), true);
            serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
        }else {

        }*/
    }

    public void visit(ObjHeartBeat objHeartBeat){
        ///virtualView.printHeartBeat();
    }



    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        ///serverHandler.close();
    }
}

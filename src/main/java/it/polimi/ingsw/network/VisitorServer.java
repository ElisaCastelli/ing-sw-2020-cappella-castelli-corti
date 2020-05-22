package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.ServerHandler;


import java.util.ArrayList;

public class VisitorServer {

    private ServerHandler serverHandler;

    public VisitorServer(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void visit(ObjNumPlayer objNumPlayer){
        int nPlayer=serverHandler.getVirtualView().setNPlayers(objNumPlayer.getnPlayer()).getnPlayer();
        serverHandler.setnPlayer(nPlayer);
        serverHandler.getVirtualView().setReady(true);
    }

    public void visit(ObjPlayer objPlayer){
        System.out.println("Receiving player data");
        serverHandler.setClientName(objPlayer.getName());
        serverHandler.getVirtualView().addPlayer(objPlayer.getName(), objPlayer.getAge());
    }


    public void visit(AckStartGame ackStartGame) {
        serverHandler.setIndexPlayer(serverHandler.getVirtualView().searchByName(serverHandler.getUserName()));
        serverHandler.sendUpdate(new ObjState(serverHandler.getIndexPlayer(),0));
    }

    public void visit(ObjTempCard objTempCard){
        System.out.println("Receiving Temp Card ");
        serverHandler.waitForPlayer();
        AskCard tempCard = serverHandler.getVirtualView().setTempCard(objTempCard.getCardsTemp());
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        serverHandler.sendUpdateBroadcast(objState);
        //ho aggiunto uesta riga e il corrispondente incremento degli ack sullo Ackstate
        serverHandler.waitForPlayer();
        serverHandler.sendUpdateBroadcast(tempCard);
    }

    public void visit(ObjCard objCard) throws Exception {
        System.out.println("Receiving objCard");
        serverHandler.waitForPlayer();
        int indexPlayer=serverHandler.getIndexPlayer();
        AskCard askcard = serverHandler.getVirtualView().setCard(indexPlayer, objCard.getCardChose());
        serverHandler.setNameCard(serverHandler.getVirtualView().getPlayerArray().get(indexPlayer).getGod().getName());
        if(askcard.getCardTemp().size() != 0){
            ObjState objState = serverHandler.getVirtualView().goPlayingNext();
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();
            serverHandler.sendUpdateBroadcast(askcard);
        }
        else{
            System.out.println("Sending board");
            ObjInitialize objInitialize = serverHandler.gameData();
            serverHandler.sendUpdateBroadcast(objInitialize);
            serverHandler.waitForPlayer();
            serverHandler.sendUpdateBroadcast(new AskInitializeWorker());
        }
    }

    public void visit(AckState ackState) {
        serverHandler.getVirtualView().incCounterOpponent();
        System.out.println("AckState thread number: "+ serverHandler.getName());
    }

    public void visit(AckPlayer ackPlayer) throws Exception {
        serverHandler.waitForPlayer();
        ArrayList<String> cards= serverHandler.getVirtualView().getCards();
        Ask3CardsEvent ask3Cards = new Ask3CardsEvent(cards);
        serverHandler.sendUpdateBroadcast(ask3Cards);
    }

    public void visit (ObjWorkers objWorkers){
        serverHandler.waitForPlayer();
        if(serverHandler.getVirtualView().initializeWorker(serverHandler.getIndexPlayer(), objWorkers.getBox1(), objWorkers.getBox2())){
            UpdateBoardEvent updateBoardEvent = serverHandler.getVirtualView().updateBoard();
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            ObjState objState = serverHandler.getVirtualView().goPlayingNext();
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();
            if(serverHandler.getIndexPlayer()+1 == serverHandler.getClientArray().size()){
                AskWorkerToMoveEvent askWorkerToMoveEvent = serverHandler.getVirtualView().getWorkersPos(serverHandler.getIndexNext(), true);
                serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
            }else{
                serverHandler.sendUpdateBroadcast(new AskInitializeWorker());
            }
        }
        else{
            serverHandler.sendUpdateBroadcast(new AskInitializeWorker());
        }
    }

    public void visit (ObjWorkerToMove objWorkerToMove){
        serverHandler.waitForPlayer();
        if(objWorkerToMove.isReady()){
            serverHandler.sendUpdateBroadcast(new AskMoveEvent(objWorkerToMove.getIndexWorkerToMove(), objWorkerToMove.getRow(), objWorkerToMove.getColumn(),true,false));
        }else{
            int indexPlayer = serverHandler.getIndexPlayer();
            UpdateBoardEvent updateBoardEvent = serverHandler.getVirtualView().setReachable(indexPlayer,objWorkerToMove.getIndexWorkerToMove());
            updateBoardEvent.setShowReachable(true);
            //mando la board a tutti così quello stronzo dopo mi dice se vuole cambiare pedina o fare una mossa
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            ///TODO lo mando in broadcast o solo a lui? perchè devo ricordarmi di ricontrollare gli ack che mi arrivano
            AskWorkerToMoveEvent askWorkerToMoveEvent = serverHandler.getVirtualView().getWorkersPos(indexPlayer,false);
            //Ho tolto l'indexWorker dal costruttore, poi ho fatto una set di quest'ultimo almeno ho sempre l'ultimo index che ho scelto
            askWorkerToMoveEvent.setIndexWorker(objWorkerToMove.getIndexWorkerToMove());
            serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
        }
    }
    ///mi serve per controllare che a tutti sia arrivata la board aggiornata
    public void visit(AckUpdateBoard ackUpdateBoard){
        serverHandler.getVirtualView().incCounterOpponent();
    }

    public void visit(ObjMove objMove){
        serverHandler.waitForPlayer();
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
                        serverHandler.sendUpdateBroadcast(new AskBuildEvent(askMoveEvent.getIndexWorker(), askMoveEvent.getRow1(), askMoveEvent.getColumn1(), true, false));
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
        }else {
            UpdateBoardEvent updateBoardEvent = serverHandler.getVirtualView().updateBoard();
            updateBoardEvent.setShowReachable(true);
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            AskMoveEvent askMoveEvent = new AskMoveEvent(objMove.getIndexWorkerToMove(), objMove.getRowStart(), objMove.getColumnStart(), true, false);
            askMoveEvent.setWrongBox(true);
            serverHandler.sendUpdateBroadcast(askMoveEvent);
        }
    }

    public void visit(AckMove ackMove){
        serverHandler.waitForPlayer();

        int indexWorker = ackMove.getIndexWorker();
        int rowWorker = ackMove.getRowWorker();
        int columnWorker = ackMove.getColumnWorker();

        UpdateBoardEvent updateBoardEvent;
        updateBoardEvent = serverHandler.getVirtualView().updateBoard();
        updateBoardEvent.setShowReachable(true);
        serverHandler.sendUpdateBroadcast(updateBoardEvent);
        serverHandler.waitForPlayer();
        serverHandler.sendUpdateBroadcast(new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false));
    }

    public void visit(ObjBlock objBlock){
        int indexWorker = objBlock.getIndexWorker();
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
                    //va richiamata la can move(player+1)
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
        }
    }

    public void visit(AckBlock ackBlock){
        serverHandler.waitForPlayer();
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        int indexPlayer = serverHandler.getIndexPlayer();
        int nextPlayer = serverHandler.getIndexClient(indexPlayer + 1);
        if(serverHandler.getVirtualView().canMove(nextPlayer)){
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();
            AskWorkerToMoveEvent askWorkerToMoveEvent = serverHandler.getVirtualView().getWorkersPos(serverHandler.getIndexNext(), true);
            serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
        }else {

        }
    }

    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }
}

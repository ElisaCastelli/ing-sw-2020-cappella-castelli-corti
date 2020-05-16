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
        System.out.println("Ricevuto dati giocatore");
        serverHandler.setClientName(objPlayer.getName());
        serverHandler.getVirtualView().addPlayer(objPlayer.getName(), objPlayer.getAge());
    }


    public void visit(AckStartGame ackStartGame) {
        serverHandler.setIndexPlayer(serverHandler.getVirtualView().searchByName(serverHandler.getUserName()));
        serverHandler.sendUpdate(new ObjState(serverHandler.getIndexPlayer(),0));
    }

    public void visit(ObjTempCard objTempCard){
        System.out.println("Imposto carte temporanee");
        serverHandler.waitForPlayer();
        AskCard tempCard = serverHandler.getVirtualView().setTempCard(objTempCard.getCardsTemp());
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        serverHandler.sendUpdateBroadcast(objState);
        //ho aggiunto uesta riga e il corrispondente incremento degli ack sullo Ackstate
        serverHandler.waitForPlayer();
        serverHandler.sendUpdateBroadcast(tempCard);
    }

    public void visit(ObjCard objCard) throws Exception {
        System.out.println("Ricevo objCard");
        serverHandler.waitForPlayer();
        int indexPlayer=serverHandler.getIndexPlayer();
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        AskCard askcard = serverHandler.getVirtualView().setCard(indexPlayer, objCard.getCardChose());
        serverHandler.sendUpdateBroadcast(objState);
        //ho aggiunto uesta riga e il corrispondente incremento degli ack sullo Ackstate
        serverHandler.waitForPlayer();
        serverHandler.setNameCard(serverHandler.getVirtualView().getPlayerArray().get(indexPlayer).getGod().getName());
        if(askcard.getCardTemp().size() != 0){
            serverHandler.sendUpdateBroadcast(askcard);
        }
        else{
            System.out.println("Invio board");
            ObjInitialize objInitialize= serverHandler.gameData();
            serverHandler.sendUpdateBroadcast(objInitialize);
        }
    }

    public void visit(AckState ackState) {
        serverHandler.getVirtualView().incCounterOpponent();
        System.out.println("stoca**o di "+ serverHandler.getName());
    }

    public void visit(AckPlayer ackPlayer) throws Exception {
        serverHandler.waitForPlayer();
        ArrayList<String> cards= serverHandler.getVirtualView().getCards();
        Ask3CardsEvent ask3Cards = new Ask3CardsEvent(cards);
        serverHandler.sendUpdateBroadcast(ask3Cards);
    }

    public void visit (ObjWorkers objWorkers){
        //serverHandler.waitForPlayer();
        ObjState objState = serverHandler.getVirtualView().goPlayingNext();
        serverHandler.getVirtualView().initializeWorker(serverHandler.getIndexPlayer(), objWorkers.getBox1(), objWorkers.getBox2());
        serverHandler.sendUpdateBroadcast(objState);
    }

    public void visit (ObjWokerToMove objWokerToMove){
        serverHandler.waitForPlayer();
        if(objWokerToMove.isReady()){
            serverHandler.sendUpdateBroadcast(new AskMoveEvent(objWokerToMove.getIndexWokerToMove(),objWokerToMove.getRow(),objWokerToMove.getColumn(),true,false));
        }else{
            int indexPlayer=serverHandler.getIndexPlayer();
            UpdateBoardEvent updateBoardEvent= serverHandler.getVirtualView().setReachable(indexPlayer,objWokerToMove.getIndexWokerToMove());
            updateBoardEvent.setShowReachable(true);
            //mando la board a tutti così quello stronzo dopo mi dice se vuole cambiare pedina o fare una mossa
            serverHandler.sendUpdateBroadcast(updateBoardEvent);
            serverHandler.waitForPlayer();
            ///TODO lo mando in broadcast o solo a lui? perchè devo ricordarmi di ricontrollare gli ack che mi arrivano
            AskWorkerToMoveEvent askWorkerToMoveEvent= serverHandler.getVirtualView().getWorkersPos(indexPlayer,false);
            serverHandler.sendUpdateBroadcast(askWorkerToMoveEvent);
        }


    }
    ///mi serve per controllare che a tutti sia arrivata la board aggiornata
    public void visit(AckUpdateBoard ackUpdateBoard){
        serverHandler.getVirtualView().incCounterOpponent();
    }

    public void visit(ObjMove objMove){
        serverHandler.waitForPlayer();
        int indexPlayer=serverHandler.getIndexPlayer();
        AskMoveEvent askMoveEvent=serverHandler.getVirtualView().move(indexPlayer,objMove.getIndexWokerToMove(),objMove.getRow(),objMove.getColumn());
        UpdateBoardEvent updateBoardEvent=serverHandler.getVirtualView().updateBoard();
        serverHandler.sendUpdateBroadcast(updateBoardEvent);
        serverHandler.waitForPlayer();
        ///check win/morto
        if(/*ha vinto*/ ){
            ///sendupdate.hai vinto
            ///sendbroadcast senza io hai perso
        }else{
            if(askMoveEvent.isDone()){
                serverHandler.sendUpdateBroadcast(new BuildEvent());
            }else{
                serverHandler.sendUpdateBroadcast(askMoveEvent);
            }
        }

    }

    ///Greta Rules
    public void visit(AckMove ackMove){

    }



    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }

}

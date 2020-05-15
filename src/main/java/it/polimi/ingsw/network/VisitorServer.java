package it.polimi.ingsw.network;

import it.polimi.ingsw.network.ack.AckPlayer;
import it.polimi.ingsw.network.ack.AckStartGame;
import it.polimi.ingsw.network.ack.AckState;
import it.polimi.ingsw.network.ack.NackState;
import it.polimi.ingsw.network.events.Ask3CardsEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.CloseConnectionClientEvent;
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
    public void visit(NackState nackState){

    }

    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }

    //COMMENTO IL VECCHIO METODO DI OBJCARD
    /*public void visit(ObjCard objCard) throws Exception {
        int indexPlayer=serverHandler.getIndexPlayer();

        if(objCard.getCardsTemp().size()>0 && objCard.getCardChose()==-1){
            System.out.println("Imposto carte temporanee");
            AskCard tempCard = serverHandler.getVirtualView().setTempCard(objCard.getCardsTemp());
            ObjState objState = serverHandler.getVirtualView().goPlayingNext();
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();

            //int indexRec= serverHandler.getIndexClient((indexPlayer+1));
            //ServerHandler receiver= serverHandler.getClientArray().get(indexRec);
            serverHandler.sendUpdateBroadcast(tempCard);
        }else{
            System.out.println("Carta scelta");

            AskCard askcard= serverHandler.getVirtualView().setCard(indexPlayer, objCard.getCardChose());
            serverHandler.setNameCard(serverHandler.getVirtualView().getPlayerArray().get(indexPlayer).getGod().getName());
            ObjState objState = serverHandler.getVirtualView().goPlayingNext();
            serverHandler.sendUpdateBroadcast(objState);
            serverHandler.waitForPlayer();


            if(askcard.getCardTemp().size() != 0){
                //int indexRec= serverHandler.getIndexClient(indexPlayer+1);
                //ServerHandler receiver= serverHandler.getClientArray().get(indexRec);
                serverHandler.sendUpdateBroadcast(askcard);
            }
            else{
                ObjInitialize objInitialize= serverHandler.gameData();
                serverHandler.sendUpdateBroadcast(objInitialize);
            }
        }
    }*/
}

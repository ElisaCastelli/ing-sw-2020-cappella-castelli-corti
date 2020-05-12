package it.polimi.ingsw.network;

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
    }

    public void visit(ObjPlayer objPlayer){
        System.out.println("Ricevuto dati giocatore");
        serverHandler.setClientName(objPlayer.getName());
        serverHandler.getVirtualView().addPlayer(objPlayer.getName(), objPlayer.getAge());

    }


    public synchronized void visit(AckStartGame ackStartGame) {
        serverHandler.setIndexPlayer(serverHandler.getVirtualView().searchByName(serverHandler.getUserName()));
        serverHandler.sendUpdate(new ObjState(serverHandler.getIndexPlayer(),0));
    }

    public synchronized void visit(ObjCard objCard) throws Exception {
        int indexPlayer=serverHandler.getIndexPlayer();
        if(objCard.getCardsTemp().size()>0){
            System.out.println("Imposto carte temporanee");
            AskCard tempCard= serverHandler.getVirtualView().setTempCard(objCard.getCardsTemp());

            int indexRec= serverHandler.getIndexClient((indexPlayer+1));
            ServerHandler receiver= serverHandler.getClientArray().get(indexRec);
            receiver.sendUpdate(tempCard);
        }else{
            System.out.println("Carta scelta");

            AskCard askcard= serverHandler.getVirtualView().setCard(indexPlayer, objCard.getCardChose());
            serverHandler.setNameCard(serverHandler.getVirtualView().getPlayerArray().get(indexPlayer).getGod().getName());
            if(askcard.getCardTemp()!=null){
                int indexRec= serverHandler.getIndexClient(indexPlayer+1);
                ServerHandler receiver= serverHandler.getClientArray().get(indexRec);
                receiver.sendUpdate(askcard);
            }
            else{
                ObjInitialize objInitialize= serverHandler.gameData();
                serverHandler.sendUpdateBroadcast(objInitialize);
            }
        }
    }
    public void visit(ObjState objstate){

    }

    public void visit(AckState ackState) throws Exception {
        serverHandler.getVirtualView().incCounter();
        serverHandler.waitForPlayer();
        ArrayList<String> cards= serverHandler.getVirtualView().getCards();
        Ask3CardsEvent askCards = new Ask3CardsEvent(cards);
        serverHandler.sendUpdate(askCards);
    }

    public void visit(NackState nackState){

    }

    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }
}

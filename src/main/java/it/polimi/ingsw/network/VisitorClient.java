package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.ObjAck;
import it.polimi.ingsw.network.objects.ObjCard;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjPlayer;
import it.polimi.ingsw.server.model.god.God;

import java.util.ArrayList;

public class VisitorClient {
    private final ClientHandler clientHandler;

    public VisitorClient(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void visit(ObjNumPlayer objNumPlayer){
        //clientHandler.getView().setNPlayer(objNumPlayer.getnPlayer());
    }
    public void visit(ObjCard objCard) throws Exception {
        clientHandler.getView();
    }

    public void visit(ObjPlayer objPlayer){
        clientHandler.getView();
    }

    public void visit(ObjAck objAck){

    }
    public void visit(AskNPlayerEvent askNPlayerEvent){
        int nPlayer=clientHandler.getView().askNPlayer();
        ObjNumPlayer objNumPlayer= new ObjNumPlayer(nPlayer);
        clientHandler.sendMessage(objNumPlayer);
    }

    public void visit(StartGameEvent start){
        //clientHandler.getView().startGame();
        clientHandler.sendMessage(new ObjAck());
        System.out.println("Visitor client - start game");
    }

    public void visit(AskPlayerEvent askPlayerEvent){
        String name= clientHandler.getView().askName();
        int age=clientHandler.getView().askAge();
        ObjPlayer objPlayer= new ObjPlayer(name,age);
        clientHandler.sendMessage(objPlayer);
    }

    public void visit(Ask3CardsEvent ask3CardsEvent){
        ArrayList<God> cardTemp= clientHandler.getView().ask3Card();
        ObjCard objCard= new ObjCard();
        objCard.setCardsTemp(cardTemp);
        clientHandler.sendMessage(objCard);
    }

}

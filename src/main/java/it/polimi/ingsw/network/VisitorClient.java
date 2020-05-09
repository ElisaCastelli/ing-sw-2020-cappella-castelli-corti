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
    private boolean isPlaying;


    public VisitorClient(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void visit(ObjNumPlayer objNumPlayer){
        //clientHandler.getView().setNPlayer(objNumPlayer.getnPlayer());
        System.out.println("ask n player lato client ");
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
        //int nPlayer=clientHandler.getView().askNPlayer();
        //ObjNumPlayer objNumPlayer= new ObjNumPlayer(nPlayer);
        //clientHandler.sendMessage(objNumPlayer);
        ObjNumPlayer objNumPlayer= new ObjNumPlayer(2);
        clientHandler.sendMessage(objNumPlayer);
        System.out.println("ask n player lato client ");
    }

    public void visit(StartGameEvent start){
        //clientHandler.getView().startGame();
        //clientHandler.sendMessage(new ObjAck());
        System.out.println("Visitor client - start game");
    }

    public void visit(AskPlayerEvent askPlayerEvent){
        //String name= clientHandler.getView().askName();
        //int age=clientHandler.getView().askAge();
        //ObjPlayer objPlayer= new ObjPlayer(name,age);
        //clientHandler.sendMessage(objPlayer);
        ObjPlayer objPlayer= new ObjPlayer("paolo",23);
        clientHandler.sendMessage(objPlayer);
        System.out.println("ask player lato client ");
    }

    public void visit(Ask3CardsEvent ask3CardsEvent){
        //ArrayList<God> cardTemp= clientHandler.getView().ask3Card();
        //ObjCard objCard= new ObjCard();
        //objCard.setCardsTemp(cardTemp);
        //clientHandler.sendMessage(objCard);
        System.out.println("ask 3 card lato client ");
    }

}

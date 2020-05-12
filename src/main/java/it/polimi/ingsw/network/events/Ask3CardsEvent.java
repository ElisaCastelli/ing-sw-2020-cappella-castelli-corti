package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

public class Ask3CardsEvent extends Event {
    ArrayList<String> cardArray= new ArrayList<>();
    PlayerState state;
    public Ask3CardsEvent(ArrayList<String> cardArray){
        this.cardArray=cardArray;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState(){
        return state;
    }

    public ArrayList<String> getCardArray(){
        return cardArray;
    }

    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) { visitorClient.visit(this);
    }
}

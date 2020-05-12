package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

import java.util.ArrayList;

public class AskCard extends Event{

    ArrayList<String> cardTemp;

    public AskCard(ArrayList<String> cardTemp){
        this.cardTemp=cardTemp;
    }

    public ArrayList<String> getCardTemp() {
        return cardTemp;
    }

    public void setCardTemp(ArrayList<String> cardTemp) {
        this.cardTemp = cardTemp;
    }

    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) { visitorClient.visit(this);
    }
}

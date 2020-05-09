package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.god.God;

import java.util.ArrayList;

public class ObjCard extends ObjMessage {

    private ArrayList<God> cards;
    private ArrayList<God> cardsTemp;
    private int cardChose;
    private int player;

    public ArrayList<God> getCards() {
        return cards;
    }
    public void setCards(ArrayList<God> cards) {
        this.cards = cards;
    }

    public ArrayList<God> getCardsTemp() {
        return cardsTemp;
    }

    public void setCardsTemp(ArrayList<God> cardsTemp) {
        this.cardsTemp = cardsTemp;
    }

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getCardChose() {
        return cardChose;
    }
    public void setCardChose(int cardChose) {
        this.cardChose = cardChose;
    }
    public int getPlayer() {
        return player;
    }

    public ObjCard(){
        super();
    }
    public ObjCard(ArrayList<God> cards, int cardChose) {
        this.cards = cards;
        this.cardChose = cardChose;
    }

    @Override
    public void accept(VisitorServer visitor) {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        try {
            visitorClient.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

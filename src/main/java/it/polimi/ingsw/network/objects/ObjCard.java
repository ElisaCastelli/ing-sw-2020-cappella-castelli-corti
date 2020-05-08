package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

import java.util.ArrayList;

public class ObjCard extends ObjMessage {

    private ArrayList<String> cards;
    private int cardChose;
    private int player;

    public ArrayList<String> getCards() {
        return cards;
    }
    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
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
    public ObjCard(ArrayList<String> cards, int cardChose) {
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

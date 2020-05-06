package it.polimi.ingsw.network;

import java.util.ArrayList;

public class ObjCard extends ObjMessage {

    private ArrayList<String> cards;
    private String cardChose;

    public ArrayList<String> getCards() {
        return cards;
    }
    public void setCards(ArrayList<String> cards) {
        this.cards = cards;
    }
    public String getCardChose() {
        return cardChose;
    }
    public void setCardChose(String cardChose) {
        this.cardChose = cardChose;
    }

    public ObjCard(){
        super();
    }
    public ObjCard(ArrayList<String> cards, String cardChose) {
        this.cards = cards;
        this.cardChose = cardChose;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitCard(this);
    }
}

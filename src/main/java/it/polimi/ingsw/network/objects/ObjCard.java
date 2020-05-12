package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.god.God;

import java.util.ArrayList;

public class ObjCard extends ObjMessage {

    private ArrayList<Integer> cardsTemp;
    private int cardChoose;

    public ObjCard(ArrayList<Integer> cardsTemp){
        this.cardsTemp=cardsTemp;
        cardChoose=5;
    }
    public ObjCard(int cardChoose){
        this.cardChoose=cardChoose;
        cardsTemp=null;
    }


    public ArrayList<Integer> getCardsTemp() {
        return cardsTemp;
    }
    public void setCardsTemp(ArrayList<Integer> cardsTemp) {
        this.cardsTemp = cardsTemp;
    }
    public int getCardChose() {
        return cardChoose;
    }
    public void setCardChose(int cardChose) {
        this.cardChoose = cardChose;
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

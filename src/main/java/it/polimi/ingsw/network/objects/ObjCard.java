package it.polimi.ingsw.network.objects;


import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;


public class ObjCard extends ObjMessage {

    private static final long serialVersionUID = -8393284750293847561L;

    private int cardChoose;

    public ObjCard(int cardChoose){
        this.cardChoose=cardChoose;
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
        throw new UnsupportedOperationException();
    }
}

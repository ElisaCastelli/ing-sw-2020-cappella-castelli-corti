package it.polimi.ingsw.network.objects;


import it.polimi.ingsw.network.VisitorClient;
        import it.polimi.ingsw.network.VisitorServer;


public class ObjCard extends ObjMessage {

    private static final long serialVersionUID = -8393284750293847561L;

    private int cardChoose;
    private int indexPlayer;

    public ObjCard(int cardChoose, int indexPlayer) {
        this.cardChoose = cardChoose;
        this.indexPlayer = indexPlayer;
    }

    public int getCardChose() {
        return cardChoose;
    }
    public void setCardChose(int cardChose) {
        this.cardChoose = cardChose;
    }

    public int getCardChoose() {
        return cardChoose;
    }

    public void setCardChoose(int cardChoose) {
        this.cardChoose = cardChoose;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
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

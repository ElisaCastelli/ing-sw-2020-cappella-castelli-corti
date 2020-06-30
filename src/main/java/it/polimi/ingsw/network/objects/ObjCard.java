package it.polimi.ingsw.network.objects;


import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message card
 */
public class ObjCard extends ObjMessage {

    private static final long serialVersionUID = -8393284750293847561L;
    /**
     * id of the card
     */
    private final int cardChoose;

    /**
     * constructor of the class
     *
     * @param cardChoose  int for the chosen card
     */
    public ObjCard(int cardChoose) {
        this.cardChoose = cardChoose;
    }

    /**
     * Card chosen getter
     *
     * @return id of the card
     */
    public int getCardChose() {
        return cardChoose;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitor the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorMessageFromClient visitor) {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */

    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        throw new UnsupportedOperationException();
    }
}

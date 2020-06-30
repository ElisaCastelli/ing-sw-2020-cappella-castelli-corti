package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

import java.util.ArrayList;

/**
 * message cards for the game's initialization
 */
public class ObjTempCard extends ObjMessage {

    private static final long serialVersionUID = -3829471843029385138L;

    /**
     * array of card's id
     */
    private final ArrayList<Integer> cardsTemp;

    /**
     * constructor of the class
     *
     * @param cardsTemp array of cards for the game
     */
    public ObjTempCard(ArrayList<Integer> cardsTemp) {
        this.cardsTemp = cardsTemp;
    }

    /**
     * Temp card getter
     *
     * @return array of card's id
     */
    public ArrayList<Integer> getCardsTemp() {
        return cardsTemp;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        visitorMessageFromClient.visit(this);
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
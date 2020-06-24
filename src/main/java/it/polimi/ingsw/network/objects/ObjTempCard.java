package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

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
     * @param visitorServer the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorServer visitorServer) {
        visitorServer.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}
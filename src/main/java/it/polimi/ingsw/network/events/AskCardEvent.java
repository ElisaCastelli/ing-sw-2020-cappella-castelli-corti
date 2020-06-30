package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

import java.util.ArrayList;

/**
 * message ask card event
 */

public class AskCardEvent extends Event {

    private static final long serialVersionUID = 984771837456293048L;

    final ArrayList<String> cardTemp;

    /**
     * constructor of the class
     *
     * @param cardTemp array of chosen cards
     */

    public AskCardEvent(ArrayList<String> cardTemp) {
        this.cardTemp = cardTemp;
    }

    /**
     * Card temp getter
     * @return array of cards
     */
    public ArrayList<String> getCardTemp() {
        return cardTemp;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        visitorMessageFromServer.visit(this);
    }
}

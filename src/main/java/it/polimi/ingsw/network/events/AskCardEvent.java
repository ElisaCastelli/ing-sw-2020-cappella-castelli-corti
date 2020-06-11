package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

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

    public ArrayList<String> getCardTemp() {
        return cardTemp;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

/**
 * message ask n cards event
 */

public class AskNCardsEvent extends Event {
    /**
     * array of all cards
     */
    final ArrayList<String> cardArray;
    /**
     * State of the player
     */
    PlayerState state;

    /**
     * constructor of the class
     *
     * @param cardArray array of all cards
     */

    public AskNCardsEvent(ArrayList<String> cardArray) {
        this.cardArray = cardArray;
    }

    /**
     * Array of cards getter
     *
     * @return array of cards
     */
    public ArrayList<String> getCardArray() {
        return cardArray;
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

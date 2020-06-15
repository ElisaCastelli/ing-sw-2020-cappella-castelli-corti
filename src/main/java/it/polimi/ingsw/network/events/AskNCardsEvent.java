package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

/**
 * message ask n cards event
 */

public class AskNCardsEvent extends Event {
    final ArrayList<String> cardArray;
    PlayerState state;

    /**
     * constructor of the class
     *
     * @param cardArray array of all cards
     */

    public AskNCardsEvent(ArrayList<String> cardArray) {
        this.cardArray = cardArray;
    }

    public void setState(PlayerState state) {
        this.state = state;
    }

    public PlayerState getState() {
        return state;
    }

    public ArrayList<String> getCardArray() {
        return cardArray;
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
package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message start event
 */
public class StartGameEvent extends Event {
    /**
     * number of players
     */
    private final int nPlayer;

    /**
     * constructor of class
     *
     * @param nPlayer number of players
     */
    public StartGameEvent(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    /**
     * Number of player getter
     *
     * @return number of player
     */
    public int getNPlayer() {
        return nPlayer;
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

package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message start event
 */
public class StartGameEvent extends Event {

    private final int nPlayer;

    /**
     * constructor of class
     *
     * @param nPlayer number of players
     */
    public StartGameEvent(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public int getNPlayer() {
        return nPlayer;
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

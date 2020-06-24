package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message closing event
 */

public class CloseConnectionFromServerEvent extends Event {
    /**
     * boolean for the reason of the disconnection
     */
    private final boolean gameNotAvailable;

    /**
     * constructor for the class
     *
     * @param gameNotAvailable boolean for the reason of the disconnection
     */
    public CloseConnectionFromServerEvent(boolean gameNotAvailable) {
        this.gameNotAvailable = gameNotAvailable;
    }

    /**
     * Game not available getter
     *
     * @return true if there isn't a game available
     */
    public boolean isGameNotAvailable() {
        return gameNotAvailable;
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

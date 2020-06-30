package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

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

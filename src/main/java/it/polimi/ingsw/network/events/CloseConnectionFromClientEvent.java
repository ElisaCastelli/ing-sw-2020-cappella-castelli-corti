package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message closing event
 */
public class CloseConnectionFromClientEvent extends Event {

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

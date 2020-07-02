package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message ask player event
 */
public class AskPlayerEvent extends Event {
    /**
     * boolean false if there is another player with the same name
     */
    boolean firstTime;

    /**
     * Constructor of the class
     * @param firstTime false if there is another player with the same name
     */
    public AskPlayerEvent(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * First time getter
     * @return false if there is another player with the same name
     */
    public boolean isFirstTime() {
        return firstTime;
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

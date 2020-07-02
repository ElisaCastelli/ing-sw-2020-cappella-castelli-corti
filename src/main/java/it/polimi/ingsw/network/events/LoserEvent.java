package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

import java.util.ArrayList;

/**
 * message to the player who has lost
 */
public class LoserEvent extends Event {

    private final ArrayList<User> userArray;

    /**
     * Constructor of the class
     * @param userArray user array
     */
    public LoserEvent(ArrayList<User> userArray) {
        this.userArray = userArray;
    }

    /**
     * User array getter
     * @return user array
     */
    public ArrayList<User> getUserArray() {
        return userArray;
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

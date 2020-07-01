package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.server.model.gameComponents.Board;

import java.util.ArrayList;

/**
 * message losing event
 */
public class WhoHasLostEvent extends Event {

    /**
     * array of users
     */
    private final ArrayList<User> userArray;
    /**
     * game's board
     */
    final Board board;
    /**
     * boolean to show the reachable's box in a board
     */
    final boolean showReachable;

    public WhoHasLostEvent(ArrayList<User> userArray, Board board, boolean showReachable) {
        this.userArray = userArray;
        this.board = board;
        this.showReachable = showReachable;
    }

    public ArrayList<User> getUserArray() {
        return userArray;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isShowReachable() {
        return showReachable;
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

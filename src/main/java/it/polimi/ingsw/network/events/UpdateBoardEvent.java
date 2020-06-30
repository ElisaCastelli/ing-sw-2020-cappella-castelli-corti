package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.server.model.gameComponents.Board;

import java.util.ArrayList;

/**
 * message update of the game's data
 */
public class UpdateBoardEvent extends Event {

    private static final long serialVersionUID = 1038573990901284957L;
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

    /**
     * constructor of the class
     *
     * @param userArray     array of users
     * @param board         game's board
     * @param showReachable boolean to show the reachable's box in a board
     */
    public UpdateBoardEvent(ArrayList<User> userArray, Board board, boolean showReachable) {
        this.userArray = userArray;
        this.board = board;
        this.showReachable = showReachable;
    }

    /**
     * Board getter
     *
     * @return board of the game
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Array of user getter
     *
     * @return array of users
     */
    public ArrayList<User> getUserArray() {
        return userArray;
    }

    /**
     * Show reachable getter
     *
     * @return true if he reachable hash to be seen
     */
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

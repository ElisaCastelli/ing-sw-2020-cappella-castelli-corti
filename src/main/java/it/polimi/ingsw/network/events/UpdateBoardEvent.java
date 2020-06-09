package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.server.model.gameComponents.Board;

import java.util.ArrayList;

/**
 * message update of the game's data
 */
public class UpdateBoardEvent extends ObjMessage {

    private static final long serialVersionUID = 1038573990901284957L;

    private final ArrayList<User> userArray;
    final Board board;
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

    public Board getBoard() {
        return board;
    }

    public ArrayList<User> getUserArray() {
        return userArray;
    }

    public boolean isShowReachable() {
        return showReachable;
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

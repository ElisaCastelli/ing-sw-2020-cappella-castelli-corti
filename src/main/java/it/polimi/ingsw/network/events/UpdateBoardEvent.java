package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class UpdateBoardEvent extends ObjMessage {

    private static final long serialVersionUID = 1038573990901284957L;

    private ArrayList<User> userArray;
    Board board;
    boolean showReachable;

    public UpdateBoardEvent(ArrayList<User> userArray, Board board, boolean showReachable){
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

    public void setUserArray(ArrayList<User> userArray) {
        this.userArray = userArray;
    }

    public boolean isShowReachable() {
        return showReachable;
    }

    public void setShowReachable(boolean showReachable) {
        this.showReachable = showReachable;
    }

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

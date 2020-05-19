package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class ObjInitialize extends ObjMessage{

    private static final long serialVersionUID = -7294721194857349563L;

    private ArrayList<User> userArray;
    private Board board;

    public ObjInitialize(ArrayList<User> userArray,Board board){
        this.userArray=userArray;
        this.board=board;
    }

    public ArrayList<User> getUserArray() {
        return userArray;
    }

    public void setUserArray(ArrayList<User> userArray) {
        this.userArray = userArray;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
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

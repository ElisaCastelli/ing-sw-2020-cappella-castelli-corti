package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjWokerToMove extends ObjMessage {

    int indexWokerToMove;
    int row;
    int column;
    boolean isReady;

    public ObjWokerToMove(int indexWokerToMove, int row, int column, boolean isReady) {
        this.indexWokerToMove = indexWokerToMove;
        this.row = row;
        this.column = column;
        this.isReady=isReady;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getIndexWokerToMove() {
        return indexWokerToMove;
    }

    public boolean isReady() {
        return isReady;
    }

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        visitorServer.visit(this);
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

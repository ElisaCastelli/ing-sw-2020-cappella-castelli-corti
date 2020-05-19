package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjMove extends ObjMessage {

    private static final long serialVersionUID = -6284934856022193045L;

    int indexWokerToMove;
    int row;
    int column;
    boolean isDone;

    public ObjMove(int indexWokerToMove, int row, int column, boolean isDone) {
        this.indexWokerToMove = indexWokerToMove;
        this.row = row;
        this.column = column;
        this.isDone = isDone;
    }

    public ObjMove(boolean isDone) {
        this.isDone = isDone;
    }

    public int getIndexWokerToMove() {
        return indexWokerToMove;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setIndexWokerToMove(int indexWokerToMove) {
        this.indexWokerToMove = indexWokerToMove;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setDone(boolean done) {
        isDone = done;
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

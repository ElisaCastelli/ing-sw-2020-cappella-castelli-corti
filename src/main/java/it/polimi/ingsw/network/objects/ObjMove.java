package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjMove extends ObjMessage {

    private static final long serialVersionUID = -6284934856022193045L;

    int indexWorkerToMove;
    int rowStart;
    int columnStart;
    int row; //row where I wanna move
    int column; //column where I wanna move
    boolean isDone;

    public ObjMove(int indexWorkerToMove, int rowStart, int columnStart, int row, int column, boolean isDone) {
        this.indexWorkerToMove = indexWorkerToMove;
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.row = row;
        this.column = column;
        this.isDone = isDone;
    }

    public ObjMove(boolean isDone) {
        this.isDone = isDone;
    }

    public int getIndexWorkerToMove() {
        return indexWorkerToMove;
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getColumnStart() {
        return columnStart;
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

    public void setIndexWorkerToMove(int indexWorkerToMove) {
        this.indexWorkerToMove = indexWorkerToMove;
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

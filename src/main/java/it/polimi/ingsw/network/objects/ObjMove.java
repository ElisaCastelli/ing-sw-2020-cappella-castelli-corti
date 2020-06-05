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

    public ObjMove(int indexWorkerToMove, int rowStart, int columnStart, int row, int column, boolean isDone) {
        this.indexWorkerToMove = indexWorkerToMove;
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.row = row;
        this.column = column;
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

    public void setIndexWorkerToMove(int indexWorkerToMove) {
        this.indexWorkerToMove = indexWorkerToMove;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
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

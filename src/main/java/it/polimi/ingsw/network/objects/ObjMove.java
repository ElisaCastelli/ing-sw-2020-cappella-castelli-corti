package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message move worker
 */
public class ObjMove extends ObjMessage {

    private static final long serialVersionUID = -6284934856022193045L;

    final int indexWorkerToMove;
    final int rowStart;
    final int columnStart;
    int row; //row where I wanna move
    int column; //column where I wanna move
    boolean firstTime;

    /**
     * constructor of the class
     *
     * @param indexWorkerToMove index of the worker
     * @param rowStart          row start of the worker
     * @param columnStart       column start of the worker
     * @param row               final row of the worker
     * @param column            final column of the worker
     * @param isDone            boolean for the choice of the player
     */
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

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorServer visitorServer) {
        visitorServer.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

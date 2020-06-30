package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message move worker
 */
public class ObjMove extends ObjMessage {

    private static final long serialVersionUID = -6284934856022193045L;
    /**
     * index of the worker to move
     */
    final int indexWorkerToMove;
    /**
     * row start of the worker
     */
    final int rowStart;
    /**
     * column start of the worker
     */
    final int columnStart;
    /**
     * final row of the worker
     */
    int row;
    /**
     * final column of the worker
     */
    int column;
    /**
     * boolean for the choice of the player
     */
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

    /**
     * Index of the worker getter
     *
     * @return index of the worker
     */
    public int getIndexWorkerToMove() {
        return indexWorkerToMove;
    }

    /**
     * Row start getter
     *
     * @return row of the starter box
     */
    public int getRowStart() {
        return rowStart;
    }

    /**
     * Column start getter
     *
     * @return column of the starter box
     */
    public int getColumnStart() {
        return columnStart;
    }

    /**
     * Row getter
     *
     * @return row of the starter box
     */
    public int getRow() {
        return row;
    }

    /**
     * Column getter
     *
     * @return column of the box
     */
    public int getColumn() {
        return column;
    }

    /**
     * Row setter
     *
     * @param row of the starter box
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Column setter
     *
     * @param column of the box
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * First time setter
     *
     * @param firstTime true if is the first time
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * First time getter
     *
     * @return true if is the first time
     */
    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        visitorMessageFromClient.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */

    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        throw new UnsupportedOperationException();
    }
}

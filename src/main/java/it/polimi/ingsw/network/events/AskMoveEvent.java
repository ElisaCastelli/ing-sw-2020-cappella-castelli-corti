package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message ask move event
 */

public class AskMoveEvent extends Event {

    private static final long serialVersionUID = 6294837110495348563L;
    /**
     * row of he starter box of the worker
     */
    int rowStart = -1;
    /**
     * column of he starter box of the worker
     */
    int columnStart = -1;
    /**
     * row of the worker
     */
    final int row;
    /**
     * column of the worker
     */
    final int column;
    /**
     * index of the worker
     */
    final int indexWorker;
    /**
     * boolean for the first of the first ask
     */
    boolean firstTime;
    /**
     * boolean if the player selected the wrong box
     */
    boolean wrongBox = false;
    /**
     * boolean for decision of the player
     */
    final boolean isDone;

    /**
     * constructor of the class
     *
     * @param indexWorker index of the worker
     * @param row         row of the worker
     * @param column      column of the worker
     * @param firstTime   boolean for the first of the first ask
     * @param isDone      boolean for decision of the player
     */

    public AskMoveEvent(int indexWorker, int row, int column, boolean firstTime, boolean isDone) {
        this.row = row;
        this.column = column;
        this.indexWorker = indexWorker;
        this.firstTime = firstTime;
        this.isDone = isDone;
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
     * Row start setter
     *
     * @param rowStart row of the starter box
     */
    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
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
     * Column start setter
     *
     * @param columnStart column of the starter box
     */
    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
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
     * Index of the worker getter
     *
     * @return index of the worker
     */
    public int getIndexWorker() {
        return indexWorker;
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
     * First time setter
     *
     * @param firstTime true if is the first time
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * Is wrong getter
     *
     * @return true if is wrong
     */
    public boolean isWrongBox() {
        return wrongBox;
    }

    /**
     * Wrong box setter
     *
     * @param wrongBox true if is wrong
     */
    public void setWrongBox(boolean wrongBox) {
        this.wrongBox = wrongBox;
    }

    /**
     * Is done getter
     *
     * @return true if done
     */
    public boolean isDone() {
        return isDone;
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

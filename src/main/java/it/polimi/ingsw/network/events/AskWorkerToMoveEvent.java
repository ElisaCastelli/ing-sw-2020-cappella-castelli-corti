package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message ask worker event
 */

public class AskWorkerToMoveEvent extends Event {

    private static final long serialVersionUID = 7283937558231029548L;
    /**
     * row first worker
     */
    int row1;
    /**
     * column first worker
     */
    int column1;
    /**
     * row second worker
     */
    int row2;
    /**
     * column second worker
     */
    int column2;
    /**
     * index of the worker
     */
    int indexWorker;
    /**
     * boolean first ask
     */
    boolean firstAsk;
    /**
     * boolean if it can move
     */
    boolean canMove;

    /**
     * empty constructor of the class
     */
    public AskWorkerToMoveEvent() {

    }

    /**
     * constructor of the class
     *
     * @param row1     row first worker
     * @param column1  column first worker
     * @param row2     row second worker
     * @param column2  column second worker
     * @param firstAsk boolean first ask
     * @param canMove  boolean if it can move
     */
    public AskWorkerToMoveEvent(int row1, int column1, int row2, int column2, boolean firstAsk, boolean canMove) {
        this.row1 = row1;
        this.column1 = column1;
        this.row2 = row2;
        this.column2 = column2;
        indexWorker = -1;
        this.firstAsk = firstAsk;
        this.canMove = canMove;
    }

    /**
     * First ask getter
     *
     * @return boolean first ask
     */
    public boolean isFirstAsk() {
        return firstAsk;
    }

    /**
     * Row getter
     *
     * @return row of the box
     */
    public int getRow1() {
        return row1;
    }

    /**
     * Column getter
     *
     * @return column of the box
     */
    public int getColumn1() {
        return column1;
    }

    /**
     * Row getter
     *
     * @return row of the box
     */
    public int getRow2() {
        return row2;
    }

    /**
     * Column getter
     *
     * @return column of the box
     */

    public int getColumn2() {
        return column2;
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
     * Index of the worker setter
     *
     * @param indexWorker index of the worker
     */
    public void setIndexWorker(int indexWorker) {
        this.indexWorker = indexWorker;
    }

    /**
     * Can Move getter
     *
     * @return true if it can move
     */
    public boolean isCanMove() {
        return canMove;
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

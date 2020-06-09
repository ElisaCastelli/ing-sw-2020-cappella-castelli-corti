package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message ask worker event
 */

public class AskWorkerToMoveEvent extends Event {

    private static final long serialVersionUID = 7283937558231029548L;

    int row1;
    int column1;
    int row2;
    int column2;
    int indexWorker;
    boolean firstAsk;

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
     */
    public AskWorkerToMoveEvent(int row1, int column1, int row2, int column2, boolean firstAsk) {
        this.row1 = row1;
        this.column1 = column1;
        this.row2 = row2;
        this.column2 = column2;
        indexWorker = -1;
        this.firstAsk = firstAsk;
    }

    public boolean isFirstAsk() {
        return firstAsk;
    }

    public int getRow1() {
        return row1;
    }

    public int getColumn1() {
        return column1;
    }

    public int getRow2() {
        return row2;
    }

    public int getColumn2() {
        return column2;
    }

    public int getIndexWorker() {
        return indexWorker;
    }

    public void setIndexWorker(int indexWorker) {
        this.indexWorker = indexWorker;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

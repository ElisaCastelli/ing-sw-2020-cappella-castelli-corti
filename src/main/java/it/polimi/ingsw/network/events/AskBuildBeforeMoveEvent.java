package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message ask build before move event
 */
public class AskBuildBeforeMoveEvent extends Event {
    /**
     * index of te worker
     */
    final int indexWorker;
    /**
     * row of the worker
     */
    final int rowWorker;
    /**
     * column of the worker
     */
    final int columnWorker;

    /**
     * constructor of the class
     *
     * @param indexWorker  index of te worker
     * @param rowWorker    row of the worker
     * @param columnWorker column of the worker
     */
    public AskBuildBeforeMoveEvent(int indexWorker, int rowWorker, int columnWorker) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
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
     * Row of the worker getter
     *
     * @return row of the worker
     */

    public int getRowWorker() {
        return rowWorker;
    }

    /**
     * Column of the worker getter
     *
     * @return column of the worker
     */

    public int getColumnWorker() {
        return columnWorker;
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

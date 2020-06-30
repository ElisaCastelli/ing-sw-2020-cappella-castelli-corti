package it.polimi.ingsw.network.ack;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message move's ack
 */
public class AckMove extends ObjMessage {

    private static final long serialVersionUID = 20398492L;
    /**
     * index of the worker
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
     * @param indexWorker  index of the worker
     * @param rowWorker    row of the worker
     * @param columnWorker column of the worker
     */
    public AckMove(int indexWorker, int rowWorker, int columnWorker) {
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

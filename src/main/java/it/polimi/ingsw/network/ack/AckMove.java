package it.polimi.ingsw.network.ack;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message move's ack
 */
public class AckMove extends ObjMessage {

    private static final long serialVersionUID = 20398492L;

    final int indexWorker;
    final int rowWorker;
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

    public int getIndexWorker() {
        return indexWorker;
    }

    public int getRowWorker() {
        return rowWorker;
    }

    public int getColumnWorker() {
        return columnWorker;
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

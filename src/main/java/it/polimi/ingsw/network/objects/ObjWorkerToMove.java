package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message worker to move
 */
public class ObjWorkerToMove extends ObjMessage {

    private static final long serialVersionUID = -5927482148957834053L;

    final int indexWorkerToMove;
    final int row;
    final int column;
    final boolean isReady;

    /**
     * constructor of the class
     *
     * @param indexWorkerToMove index of the worker
     * @param row               row of the worker
     * @param column            column of the worker
     * @param isReady           boolean if a player is ready to move
     */
    public ObjWorkerToMove(int indexWorkerToMove, int row, int column, boolean isReady) {
        this.indexWorkerToMove = indexWorkerToMove;
        this.row = row;
        this.column = column;
        this.isReady = isReady;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public int getIndexWorkerToMove() {
        return indexWorkerToMove;
    }

    public boolean isReady() {
        return isReady;
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

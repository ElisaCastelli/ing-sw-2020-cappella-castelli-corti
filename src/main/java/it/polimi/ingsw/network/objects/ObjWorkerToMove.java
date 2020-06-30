package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message worker to move
 */
public class ObjWorkerToMove extends ObjMessage {

    private static final long serialVersionUID = -5927482148957834053L;
    /**
     * index of the worker
     */
    final int indexWorkerToMove;
    /**
     * row of the worker
     */
    final int row;
    /**
     * column of the worker
     */
    final int column;
    /**
     * boolean if a player is ready to move
     */
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
    public int getIndexWorkerToMove() {
        return indexWorkerToMove;
    }

    /**
     * Is ready getter
     *
     * @return true if ready to move
     */
    public boolean isReady() {
        return isReady;
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

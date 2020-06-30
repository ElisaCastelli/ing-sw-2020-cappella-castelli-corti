package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message block before move
 */
public class ObjBlockBeforeMove extends ObjMessage {
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
     * boolean choice of the player
     */
    boolean wantToBuild;

    /**
     * constructor of the class
     *
     * @param indexWorker  index of the worker
     * @param rowWorker    row of the worker
     * @param columnWorker column of the worker
     * @param wantToBuild  boolean choice of the player
     */

    public ObjBlockBeforeMove(int indexWorker, int rowWorker, int columnWorker, boolean wantToBuild) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
        this.wantToBuild = wantToBuild;
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
     * Want to build getter
     *
     * @return true if he want to build
     */
    public boolean wantToBuild() {
        return wantToBuild;
    }

    /**
     * Want to build setter
     *
     * @param wantToBuild boolean choice of the player
     */

    public void setWantToBuild(boolean wantToBuild) {
        this.wantToBuild = wantToBuild;
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

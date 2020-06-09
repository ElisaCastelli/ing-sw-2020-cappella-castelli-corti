package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message block before move
 */
public class ObjBlockBeforeMove extends ObjMessage {

    final int indexWorker;
    final int rowWorker;
    final int columnWorker;
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

    public int getIndexWorker() {
        return indexWorker;
    }

    public int getRowWorker() {
        return rowWorker;
    }

    public int getColumnWorker() {
        return columnWorker;
    }

    public boolean wantToBuild() {
        return wantToBuild;
    }

    public void setWantToBuild(boolean wantToBuild) {
        this.wantToBuild = wantToBuild;
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

package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjBlockBeforeMove extends ObjMessage {

    int indexWorker;
    int rowWorker;
    int columnWorker;
    boolean wantToBuild;

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

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        visitorServer.visit(this);
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

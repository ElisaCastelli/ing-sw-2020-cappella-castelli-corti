package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjWorkerToMove extends ObjMessage {

    private static final long serialVersionUID = -5927482148957834053L;

    int indexWorkerToMove;
    int row;
    int column;
    boolean isReady;

    public ObjWorkerToMove(int indexWorkerToMove, int row, int column, boolean isReady) {
        this.indexWorkerToMove = indexWorkerToMove;
        this.row = row;
        this.column = column;
        this.isReady=isReady;
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

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        visitorServer.visit(this);
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjBlock extends ObjMessage {

    int indexWorker;
    int rowWorker;
    int columnWorker;
    int rowBlock;
    int columnBlock;
    boolean done;

    public ObjBlock(int indexWorker, int rowWorker, int columnWorker) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
    }

    public ObjBlock(boolean done) {
        this.done = done;
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

    public int getRowBlock() {
        return rowBlock;
    }

    public int getColumnBlock() {
        return columnBlock;
    }

    public boolean isDone() {
        return done;
    }

    public void setRowBlock(int rowBlock) {
        this.rowBlock = rowBlock;
    }

    public void setColumnBlock(int columnBlock) {
        this.columnBlock = columnBlock;
    }

    public void setDone(boolean done) {
        this.done = done;
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

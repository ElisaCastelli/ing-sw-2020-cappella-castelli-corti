package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjBlock extends ObjMessage {

    int indexWorker;
    int rowWorker;
    int columnWorker;
    int rowBlock;
    int columnBlock;
    int inputPossibleBlock;
    boolean firstTime;
    boolean done;
    boolean isSpecialTurn;


    public ObjBlock(int indexWorker, int rowWorker, int columnWorker, boolean firstTime, boolean isSpecialTurn) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
        this.firstTime = firstTime;
        this.isSpecialTurn= isSpecialTurn;
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

    public int getPossibleBlock() {
        return inputPossibleBlock;
    }

    public void setPossibleBlock(int inputPossibleBlock) {
        this.inputPossibleBlock = inputPossibleBlock;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setRowBlock(int rowBlock) {
        this.rowBlock = rowBlock;
    }

    public void setColumnBlock(int columnBlock) {
        this.columnBlock = columnBlock;
    }

    public boolean isSpecialTurn() {
        return isSpecialTurn;
    }

    public void setSpecialTurn(boolean specialTurn) {
        isSpecialTurn = specialTurn;
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

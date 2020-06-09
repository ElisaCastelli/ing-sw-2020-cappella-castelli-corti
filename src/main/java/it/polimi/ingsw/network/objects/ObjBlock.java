package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message block to build
 */
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

    /**
     * constructor of the class
     *
     * @param indexWorker   index of a worker
     * @param rowWorker     row  of a worker
     * @param columnWorker  column of the worker
     * @param firstTime     boolean for the first time
     * @param isSpecialTurn boolean for a special turn
     */
    public ObjBlock(int indexWorker, int rowWorker, int columnWorker, boolean firstTime, boolean isSpecialTurn) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
        this.firstTime = firstTime;
        this.isSpecialTurn = isSpecialTurn;
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

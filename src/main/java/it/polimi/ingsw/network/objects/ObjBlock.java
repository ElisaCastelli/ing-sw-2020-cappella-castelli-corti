package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message block to build
 */
public class ObjBlock extends ObjMessage {
    /**
     * index of a worker
     */
    int indexWorker;
    /**
     * row  of a worker
     */
    int rowWorker;
    /**
     * column of the worker
     */
    int columnWorker;
    /**
     * row  of a block
     */
    int rowBlock;
    /**
     * column of the block
     */
    int columnBlock;
    /**
     * id of the block
     */
    int inputPossibleBlock;
    /**
     * boolean for the first time
     */
    boolean firstTime;
    /**
     * boolean if the player chose the block
     */
    boolean done;
    /**
     * boolean for a special turn
     */
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
     * Row of the block getter
     *
     * @return row of the block
     */
    public int getRowBlock() {
        return rowBlock;
    }

    /**
     * Column of the block getter
     *
     * @return Column of the block
     */
    public int getColumnBlock() {
        return columnBlock;
    }

    /**
     * Row of the block setter
     *
     * @param rowBlock of the block
     */
    public void setRowBlock(int rowBlock) {
        this.rowBlock = rowBlock;
    }

    /**
     * Column of the block setter
     *
     * @param columnBlock of the block
     */

    public void setColumnBlock(int columnBlock) {
        this.columnBlock = columnBlock;
    }

    /**
     * Possible block getter
     *
     * @return id of the block
     */
    public int getPossibleBlock() {
        return inputPossibleBlock;
    }

    /**
     * Possible block setter
     *
     * @param inputPossibleBlock id of the block
     */
    public void setPossibleBlock(int inputPossibleBlock) {
        this.inputPossibleBlock = inputPossibleBlock;
    }

    /**
     * First time getter
     *
     * @return true if is the first time
     */

    public boolean isFirstTime() {
        return firstTime;
    }

    /**
     * Is done getter
     *
     * @return true if done
     */
    public boolean isDone() {
        return done;
    }

    /**
     * Done setter
     *
     * @param done true if done
     */
    public void setDone(boolean done) {
        this.done = done;
    }

    /**
     * Is Special turn getter
     *
     * @return true if is the special turn
     */

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

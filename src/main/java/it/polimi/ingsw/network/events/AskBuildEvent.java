package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message ask build event
 */

public class AskBuildEvent extends Event {

    private static final long serialVersionUID = 5035948712944284350L;
    /**
     * index of the worker
     */
    int indexWorker;
    /**
     * row of the worker
     */
    int rowWorker;
    /**
     * column of the worker
     */
    int columnWorker;
    /**
     * boolean for the first ask
     */
    boolean firstTime;
    /**
     * boolean if the player selected the wrong box
     */
    boolean wrongBox;
    /**
     * boolean for the decision of the player
     */
    final boolean Done;
    /**
     * boolean if is a special turn for the player
     */
    boolean specialTurn;

    /**
     * constructor of the class
     *
     * @param indexWorker  index of the worker
     * @param rowWorker    row of the worker
     * @param columnWorker column of the worker
     * @param firstTime    boolean for the first ask
     * @param Done         boolean for the decision of the player
     * @param specialTurn  boolean if is a special turn for the player
     */

    public AskBuildEvent(int indexWorker, int rowWorker, int columnWorker, boolean firstTime, boolean Done, boolean specialTurn) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
        this.firstTime = firstTime;
        wrongBox = false;
        this.Done = Done;
        this.specialTurn = specialTurn;
    }

    /**
     * Second constructor of the class
     *
     * @param Done boolean for the decision of the player
     */
    public AskBuildEvent(boolean Done) {
        this.Done = Done;
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
     * Index of the worker getter
     *
     * @return index of the worker
     */
    public int getIndexWorker() {
        return indexWorker;
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
     * First time setter
     *
     * @param firstTime true if is the first time
     */
    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    /**
     * Wrong box setter
     *
     * @param wrongBox true if is wrong
     */
    public void setWrongBox(boolean wrongBox) {
        this.wrongBox = wrongBox;
    }

    /**
     * Is wrong getter
     *
     * @return true if is wrong
     */
    public boolean isWrongBox() {
        return wrongBox;
    }

    /**
     * Is done getter
     *
     * @return true if done
     */
    public boolean isDone() {
        return Done;
    }

    /**
     * Is special turn getter
     *
     * @return true if is the special turn
     */
    public boolean isSpecialTurn() {
        return specialTurn;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

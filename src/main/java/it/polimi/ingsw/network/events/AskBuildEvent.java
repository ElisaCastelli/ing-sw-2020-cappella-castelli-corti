package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message ask build event
 */

public class AskBuildEvent extends ObjMessage {

    private static final long serialVersionUID = 5035948712944284350L;

    int indexWorker;
    int rowWorker;
    int columnWorker;
    boolean firstTime;
    boolean wrongBox;
    final boolean Done;
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

    public AskBuildEvent(boolean Done) {
        this.Done = Done;
    }

    public int getRowWorker() {
        return rowWorker;
    }

    public int getColumnWorker() {
        return columnWorker;
    }

    public int getIndexWorker() {
        return indexWorker;
    }

    public boolean isFirstTime() {
        return firstTime;
    }

    public void setFirstTime(boolean firstTime) {
        this.firstTime = firstTime;
    }

    public void setWrongBox(boolean wrongBox) {
        this.wrongBox = wrongBox;
    }

    public boolean isWrongBox() {
        return wrongBox;
    }

    public boolean isDone() {
        return Done;
    }

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

package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskMoveEvent extends ObjMessage {

    private static final long serialVersionUID = 6294837110495348563L;

    int row1;
    int column1;
    int indexWorker;
    boolean firstTime;
    boolean isDone;

    public AskMoveEvent(int indexWorker, int row1, int column1, boolean firstTime, boolean isDone) {
        this.row1 = row1;
        this.column1 = column1;
        this.indexWorker = indexWorker;
        this.firstTime = firstTime;
        this.isDone = isDone;

    }

    public int getRow1() {
        return row1;
    }

    public int getColumn1() {
        return column1;
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

    public boolean isDone() {
        return isDone;
    }

    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

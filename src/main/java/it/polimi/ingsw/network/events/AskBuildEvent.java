package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskBuildEvent extends ObjMessage {

    int indexWorker;
    int rowWorker;
    int columnWorker;
    boolean firstTime;
    boolean wrongBox;
    boolean Done;

    public AskBuildEvent(int indexWorker, int rowWorker, int columnWorker, boolean firstTime, boolean Done) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
        this.firstTime = firstTime;
        wrongBox = false;
        this.Done = Done;
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

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

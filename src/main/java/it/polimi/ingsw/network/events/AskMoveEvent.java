package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskMoveEvent extends ObjMessage {

    int row1;
    int column1;
    int indexWoker;
    boolean firstTime;
    boolean isDone;

    public AskMoveEvent(int indexWoker, int row1, int column1,boolean firstTime, boolean isDone) {
        this.row1 = row1;
        this.column1 = column1;
        this.indexWoker = indexWoker;
        this.firstTime=firstTime;
        this.isDone=isDone;

    }

    public AskMoveEvent(boolean isDone) {
        this.isDone = isDone;
    }

    public int getRow1() {
        return row1;
    }

    public int getColumn1() {
        return column1;
    }

    public int getIndexWoker() {
        return indexWoker;
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
    public void accept(VisitorServer visitorServer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

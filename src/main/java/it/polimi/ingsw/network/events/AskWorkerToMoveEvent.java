package it.polimi.ingsw.network.events;
import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
public class AskWorkerToMoveEvent extends Event {
    int row1;
    int column1;
    int indexFirstWoker;
    int row2;
    int column2;
    int indexSecondWoker;
    boolean firstAsk;
    public AskWorkerToMoveEvent(int row1, int column1, int indexFirstWoker, int row2, int column2, int indexSecondWoker, boolean firstAsk) {
        this.row1 = row1;
        this.column1 = column1;
        this.indexFirstWoker = indexFirstWoker;
        this.row2 = row2;
        this.column2 = column2;
        this.indexSecondWoker = indexSecondWoker;
        this.firstAsk = firstAsk;
    }
    public boolean isFirstAsk() {
        return firstAsk;
    }
    public int getRow1() {
        return row1;
    }
    public int getColumn1() {
        return column1;
    }
    public int getRow2() {
        return row2;
    }
    public int getColumn2() {
        return column2;
    }
    public int getIndexFirstWoker() {
        return indexFirstWoker;
    }
    public int getIndexSecondWoker() {
        return indexSecondWoker;
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
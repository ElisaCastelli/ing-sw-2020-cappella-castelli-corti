package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskMoveEvent extends ObjMessage {

    private static final long serialVersionUID = 6294837110495348563L;

    int rowStart=-1;
    int columnStart=-1;
    int row;
    int column;
    int indexWorker;
    boolean firstTime;
    boolean wrongBox=false;
    boolean isDone;

    public AskMoveEvent(int indexWorker, int row, int column, boolean firstTime, boolean isDone) {
        this.row = row;
        this.column = column;
        this.indexWorker = indexWorker;
        this.firstTime = firstTime;
        this.isDone = isDone;
    }
    public int getRowStart() {
        return rowStart;
    }

    public void setRowStart(int rowStart) {
        this.rowStart = rowStart;
    }

    public int getColumnStart() {
        return columnStart;
    }

    public void setColumnStart(int columnStart) {
        this.columnStart = columnStart;
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
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

    public boolean isWrongBox() {
        return wrongBox;
    }

    public void setWrongBox(boolean wrongBox) {
        this.wrongBox = wrongBox;
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

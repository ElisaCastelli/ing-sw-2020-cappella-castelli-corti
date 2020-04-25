package it.polimi.ingsw.model;

public class Move {
    String godName;
    Worker worker;
    Box boxStart;
    Box boxReached;
    boolean moveUp;

    public String getGodName() {
        return godName;
    }

    public void setGodName(String godName) {
        this.godName = godName;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public Box getBoxReached() {
        return boxReached;
    }

    public void setBoxReached(Box boxReached) {
        this.boxReached = boxReached;
    }

    public Box getBoxStart() {
        return boxStart;
    }

    public void setBoxStart(Box boxStart) {
        this.boxStart = boxStart;
    }

    public boolean isMoveup() {
        return moveUp;
    }

    public void setMoveup(boolean moveup) {
        this.moveUp = moveup;
    }
    public Box getDirection(){
        Box temp=worker.getActualBox();
        int newColumn=boxReached.getColumn();
        int newRow=boxReached.getRow();
        if ( boxReached.getColumn() - boxStart.getColumn() == 1 ) { //va a destra
            newColumn = newColumn + 1;
        }
        else if ( boxStart.getColumn() - boxReached.getColumn() == 1 ) {//va a sinistra
            newColumn = newColumn - 1;
        }

        if ( boxReached.getRow() - boxStart.getRow() == 1 ) {//va sotto
            newRow = newRow + 1;
        }
        else if ( boxStart.getRow() - boxReached.getRow() == 1 ) {//va sopra
            newRow = newRow - 1;
        }
        temp.setRow(newRow);
        temp.setColumn(newColumn);
        return temp;
    }
}

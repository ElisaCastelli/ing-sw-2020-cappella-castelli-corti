package it.polimi.ingsw;

public class Worker {
    private int workerId;
    private int height;
    private Box actualBox;
    private Board board;



    public Worker(int workerId, Board board){
        this.workerId=workerId;
        this.board=board;
    }

    public Worker(int workerId, Box actualBox) {
        this.workerId = workerId;
        height=0;
        this.actualBox=actualBox;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }

    public int getWorkerId() {
        return workerId;
    }

    public Box getActualBox() {
        return actualBox;
    }

    public void setActualBox(Box actualBox) {
        this.actualBox = actualBox;
    }

}

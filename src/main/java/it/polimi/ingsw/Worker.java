package it.polimi.ingsw;

public class Worker {
    private int workerId;
    private int height;
    private Box actualBox;
    private Board board;

    public Worker(){
        workerId=0;
        height=0;
        actualBox=null;
        board=null;
    }
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
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }
    public void setBoard(Board board) {
        this.board = board;
    }
    public Box getActualBox() {
        return actualBox;
    }
    public void setActualBox(Box actualBox) {
        this.actualBox = actualBox;
    }
    public boolean initializePos(int row, int column){
        Box requestedBox;
        if(this.board.isEmpty(row,column)){
            requestedBox=this.board.getBox(row,column);
            requestedBox.setWorker(this);
            actualBox=requestedBox;
            System.out.println("the box is now occupied by this worker");
            return true;
        }else {
            System.out.println("the box is occupied");
            return false;
        }
    }

}

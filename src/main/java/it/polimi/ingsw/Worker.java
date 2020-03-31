package it.polimi.ingsw;

public class Worker {
    private int workerId;
    private int height;
    private Box actualBox;
    private String gamerName;

    public Worker(){
        workerId=0;
        height=0;
        actualBox=null;
        gamerName="";
    }

    public Worker(int workerId, String gamerName) {
        this.workerId = workerId;
        height=0;
        actualBox=null;
        this.gamerName=gamerName;
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

    public Box getActualBox() {
        return actualBox;
    }
    public void setActualBox(Box actualBox) {
        this.actualBox = actualBox;
    }

    public String getGamerName() {
        return gamerName;
    }

    public boolean initializePos(Box requestedBox){
        if(requestedBox.notWorker()){
            requestedBox.setWorker(this);
            actualBox=requestedBox;
            System.out.println("the box is now occupied by this worker");
            return true;
        }else {
            System.out.println("the box is occupied");
            return false;
        }
    }

    public void clear(){
        workerId=0;
        height=0;
        actualBox=null;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "workerId=" + workerId +
                '}';
    }
}

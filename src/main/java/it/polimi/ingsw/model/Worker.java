package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Box;

public class Worker {
    private int workerId;
    private int height;
    private Box actualBox;

    public Worker(int workerId) {
        this.workerId = workerId;
        height=0;
        actualBox=null;
    }

    /**
     *
     * @return the level of the worker in a building
     */
    public int getHeight() {
        return height;
    }

    /**
     *
     * @param height the level of the worker in a building
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     *
     * @return the number of the worker associated with the player
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     *
     * @param workerId can be 1 or 2 depends on the gamer's decision
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    /**
     *
     * @return the box in which it is set the worker
     */
    public Box getActualBox() {
        return actualBox;
    }

    /**
     *
     * @param actualBox is a box in which it is set the worker
     */
    public void setActualBox(Box actualBox) {
        this.actualBox = actualBox;
    }

    /**
     *
     * @param requestedBox is the box where the player wants to put the worker
     * @return a boolean that is true if the worker is set in the requested position
     */
    public boolean initializePos( Box requestedBox ){
        if( requestedBox.notWorker () ){
            requestedBox.setWorker( this );
            actualBox=requestedBox ;
            System.out.println( "the box is now occupied by this worker" );
            return true;
        }else {
            System.out.println( "the box is occupied" );
            return false;
        }
    }

    /**
     *is a method to clear and reinitialize the worker
     */

    public void clear(){
        workerId=0;
        height=0;
        actualBox=null;
    }

    /**
     *
     * @return the toString of the worker
     */
    @Override
    public String toString() {
        return "Worker{" +
                "workerId=" + workerId +
                '}';
    }
}
package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.Game;

/**
 * This class is the Worker associated to the player during a match
 */

public class Worker {
    /**
     * this parameter is to identify the worker of the player
     */
    private int workerId;
    /**
     * this parameter is to identify the worker's height in the box in which it is
     */
    private int height;
    /**
     * this parameter is to identify the worker?s position in the Board
     */
    private Box actualBox;
    /**
     * this parameter is to identify the color of the player
     */
    private final Game.COLOR color;

    /**
     *
     * @param workerId this parameter is to identify the worker of the player
     * @param color this parameter is to identify the color of the player
     */
    public Worker(int workerId, Game.COLOR color) {
        this.workerId = workerId;
        height=0;
        actualBox=null;
        this.color = color;
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
     * @return the color associated with the player
     */
    public Game.COLOR getColor() {
        return color;
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
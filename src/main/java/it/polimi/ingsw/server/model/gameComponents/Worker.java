package it.polimi.ingsw.server.model.gameComponents;

import java.io.Serializable;

/**
 * This class is the Worker associated to the player during a match
 */

public class Worker implements Serializable {

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
    private int indexPlayer;
    /**
     * this parameter is to identify the color of the player
     */
    private int indexClient;

    /**
     * The constructor of the class
     *
     * @param workerId this parameter is to identify the worker of the player
     */
    public Worker(int workerId) {
        this.workerId = workerId;
        height = 0;
        actualBox = null;
    }

    /**
     * This method is recall to get the height of the worker based on the building
     *
     * @return the level of the worker in a building
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method is recall to set the height of the worker based on the building
     *
     * @param height the level of the worker in a building
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * This method is recall to get the worker id
     *
     * @return the number of the worker associated with the player
     */
    public int getWorkerId() {
        return workerId;
    }

    /**
     * This method is recall to set the worker id
     *
     * @param workerId can be 1 or 2 depends on the gamer's decision
     */
    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    /**
     * This method is recall to get the box where the worker is positioned in the board
     *
     * @return the box in which it is set the worker
     */
    public Box getActualBox() {
        return actualBox;
    }

    /**
     * This method is recall to set the box where the worker is positioned in the board
     *
     * @param actualBox is a box in which it is set the worker
     */
    public void setActualBox(Box actualBox) {
        this.actualBox = actualBox;
    }

    /**
     * This method is recall to get the index of the player
     *
     * @return this method return the index associated with the player
     */
    public int getIndexPlayer() {
        return indexPlayer;
    }

    /**
     * This method is recall to set the index of the player
     *
     * @param indexPlayer index of the player
     */
    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    /**
     * This method is recall to get the index of the client
     *
     * @return this method return the index associated with the client
     */
    public int getIndexClient() {
        return indexClient;
    }

    /**
     * This method is recall to set the index of the client
     *
     * @param indexClient index of the player
     */
    public void setIndexClient(int indexClient) {
        this.indexClient = indexClient;
    }

    /**
     * This method is recall to initialize the position of a worker in the Board
     *
     * @param requestedBox is the box where the player wants to put the worker
     * @param board        game's board
     * @return a boolean that is true if the worker is set in the requested position
     */
    public boolean initializePos(Box requestedBox, Board board) {
        if (board.getBox(requestedBox.getRow(), requestedBox.getColumn()).notWorker()) {
            board.getBox(requestedBox.getRow(), requestedBox.getColumn()).setWorker(this);
            actualBox = board.getBox(requestedBox.getRow(), requestedBox.getColumn());
            return true;
        } else {
            return false;
        }
    }

    /**
     * It is a method to clear and reinitialize the worker
     */
    public void clear() {
        workerId = 0;
        height = 0;
        actualBox = null;
    }

    /**
     * To sting method
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

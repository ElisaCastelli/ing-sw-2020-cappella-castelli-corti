package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.playerState.PlayerStateManager;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the player who is playing the game
 */
public class Player implements Serializable {
    /**
     * This is the name of the player
     */
    private String name;

    /**
     * Player's age
     */
    private int age;

    /**
     * This is the god card drawn by the player
     */
    private God myGod;
    /**
     * This is an integer associated with the player
     */
    private int indexPlayer;
    /**
     * This array of Workers contains two workers for each player
     */
    private final Worker[] myWorkers;
    /**
     * This is an integer associated with the client
     */
    private int indexClient;
    /**
     * This attribute indicates the max heartbeats that a client can miss before the server closes the client
     */
    private final int MAX_HEARTBEATS_MISSED = 4;
    /**
     * This attribute counts the heartbeats that a client misses
     */
    private int missed_heartbeat = 0;
    /**
     * Timer
     */
    private final Timer timer;
    /**
     * Timer task activated for the heartbeat
     */
    private final TimerTask timerTask;
    /**
     * Object used as a lock
     */
    private final Object LOCK = new Object();

    /**
     * This is the playerStateManager that indicates the player state
     */
    final PlayerStateManager gamerManager;

    /**
     * Constructor of the class
     *
     * @param indexClient index of the client
     * @param timer       Timer set for heartbeat
     * @param timerTask   Timer task set for heartbeat
     */

    public Player(int indexClient, Timer timer, TimerTask timerTask) {
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1);
        myWorkers[1] = new Worker(2);
        myWorkers[0].setIndexClient(indexClient);
        myWorkers[1].setIndexClient(indexClient);
        myGod = new BasicGod();
        gamerManager = new PlayerStateManager(myGod);
        this.indexClient = indexClient;
        this.indexPlayer = indexClient;
        this.timer = timer;
        this.timerTask = timerTask;
    }

    /**
     * God setter
     *
     * @param god God for a player
     */
    public void setGod(God god) {
        myGod = god;
        gamerManager.setMyGod(myGod);
    }

    /**
     * God getter
     *
     * @return God for a player
     */

    public God getGod() {
        return myGod;
    }

    /**
     * Name getter
     *
     * @return name of the player
     */

    public String getName() {
        return name;
    }

    /**
     * Name setter
     *
     * @param name name of the player
     */

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter age
     *
     * @param age age of the player
     */

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter age of the player
     *
     * @return age of the player
     */

    public int getAge() {
        return age;
    }

    /**
     * This method sets own indexPlayer to the two player workers
     *
     * @param indexPlayer number that identifies each player
     */
    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
        myWorkers[0].setIndexPlayer(indexPlayer);
        myWorkers[1].setIndexPlayer(indexPlayer);
    }

    /**
     * Index of the player Getter
     *
     * @return index of the player
     */

    public int getIndexPlayer() {
        return indexPlayer;
    }

    /**
     * Index of the client Getter
     *
     * @return index of the client
     */

    public int getIndexClient() {
        return indexClient;
    }

    /**
     * Index of the client setter
     *
     * @param indexClient index of the client
     */
    public void setIndexClient(int indexClient) {
        this.indexClient = indexClient;
        myWorkers[0].setIndexClient(indexClient);
        myWorkers[1].setIndexClient(indexClient);
    }

    /**
     * Getter if is playing the player
     *
     * @return true if is playing
     */

    public boolean isPlaying() {
        return gamerManager.isPlaying();
    }

    /**
     * Box of the worker getter
     *
     * @param indexWorker index of the worker
     * @return a box of the board
     */

    public Box getWorkerBox(int indexWorker) {
        return myWorkers[indexWorker].getActualBox();
    }

    /**
     * Boxes of the workers of the player
     *
     * @return array of box
     */

    public ArrayList<Box> getWorkersBox() {
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(0, myWorkers[0].getActualBox());
        boxes.add(1, myWorkers[1].getActualBox());
        return boxes;
    }

    /**
     * Missed heartbeat getter
     *
     * @return integer of how many missed heartbeat
     */

    public int getMissed_heartbeat() {
        return missed_heartbeat;
    }

    /**
     * Missed Heartbeat setter
     *
     * @param missed_heartbeat missed heartbeats
     */

    public void setMissed_heartbeat(int missed_heartbeat) {
        this.missed_heartbeat = missed_heartbeat;
    }

    /**
     * Timer getter
     *
     * @return Timer
     */

    public Timer getTimer() {
        return timer;
    }

    /**
     * Timer Task getter
     *
     * @return Timer task
     */

    public TimerTask getTimerTask() {
        return timerTask;
    }

    /**
     * This method sets the first position of a worker
     *
     * @param box1 is the number of the worker i want to set
     * @param box2 is the box where i want to set the worker
     * @param board game's board
     * @return true if initialization is successful, else false
     */
    public boolean initializeWorker(Box box1, Box box2, Board board) {
        boolean initializeW1 = myWorkers[0].initializePos(box1, board);
        boolean initializeW2 = myWorkers[1].initializePos(box2, board);

        if (initializeW1 ^ initializeW2) {
            if (!initializeW1) {
                board.getBox(box2.getRow(), box2.getColumn()).clearWorker();
            } else {
                board.getBox(box1.getRow(), box1.getColumn()).clearWorker();
            }
        }
        return initializeW1 && initializeW2;
    }

    /**
     * This method set the player to playing
     */

    public void goPlay() {
        gamerManager.goPlaying();
    }

    /**
     * This method set the player to waiting
     */
    public void goWaiting() {
        gamerManager.goWaiting();
    }

    /**
     * This method set the player to win
     */

    public void goWin() {
        gamerManager.goWin();
    }

    /**
     * This method control if is the winner
     *
     * @return true if is the winner
     */

    public boolean amITheWinner() {
        return gamerManager.amITheWinner();
    }

    /**
     * This method control if is dead
     *
     * @return true if is dead
     */
    public boolean amIDead() {
        return gamerManager.amIDead();
    }

    /**
     * This method set the player to dead
     */
    public void goDead() {
        gamerManager.goDead();
    }

    /**
     * This method eliminates the workers from the board
     */
    public void clearWorkers() {
        for (Worker worker : myWorkers) {
            worker.getActualBox().clearWorker();
        }
    }

    /**
     * This method calls the setPossibleMove in the playerStateManager
     *
     * @param indexWorker index of the worker which is getting the possible moves
     *
     * @return true if the worker can move
     */
    public boolean setPossibleMove(int indexWorker) {
        //gamerManager.setMyGod(myGod);
        return gamerManager.setPossibleMove(myWorkers[indexWorker]);
    }

    /**
     * This method calls the setPossibleBuild in the playerStateManager
     *
     * @param indexWorker index of the worker which is getting the possible builds
     * @return true if is reachable
     */
    public boolean setPossibleBuild(int indexWorker) {
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        return myWorkers[indexWorker].getActualBox().checkPossible();
    }

    /**
     * This method calls the moveWorker in the playerStateManager
     *
     * @param indexWorker index of the worker which is going to be moved
     * @param pos         box where the worker is going to be moved
     * @return false if the player can do another move because of god ability, otherwise returns true
     */
    public boolean playWorker(int indexWorker, Box pos) {
        return gamerManager.moveWorker(myWorkers[indexWorker], pos);
    }

    /**
     * This method calls the moveBlock in the playerStateManager
     *
     * @param pos box where the player wants to build a block
     * @return false if the player can do another move because of god ability, otherwise returns true
     */
    public boolean playBlock(Box pos) {
        return gamerManager.moveBlock(pos);
    }

    /**
     * This method calls the checkWin in the playerStateManager and if the player wins, sets his state in Win
     *
     * @param startedBox starter box of the worker
     * @param finalBox   box where the worker moved
     * @return true if the player wins, otherwise returns false
     */
    public boolean checkWin(Box startedBox, Box finalBox) {
        boolean win = gamerManager.checkWin(finalBox, startedBox);
        if (win) {
            goWin();
        }
        return win;
    }

    /**
     * This method checks if at least one of the two workers can move
     *
     * @return false if no workers can move, otherwise returns true
     */
    public boolean checkWorkers() {
        myWorkers[0].getActualBox().clearBoxesNextTo();
        boolean firstWorker = gamerManager.setPossibleMove(myWorkers[0]);
        myWorkers[0].getActualBox().clearBoxesNextTo();
        myWorkers[1].getActualBox().clearBoxesNextTo();
        boolean secondWorker = gamerManager.setPossibleMove(myWorkers[1]);
        myWorkers[1].getActualBox().clearBoxesNextTo();
        return (firstWorker || secondWorker);
    }

    /**
     * This method checks if a worker can move
     *
     * @param indexWorker index of the worker that is going to be checked
     * @return false if the worker cannot move, otherwise returns true
     */
    public boolean checkWorker(int indexWorker) {
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        boolean worker = gamerManager.setPossibleMove(myWorkers[indexWorker]);
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        return worker;
    }

    /**
     * This method checks if a worker can build
     *
     * @param indexWorker index of the worker that is going to be checked
     * @return false if the worker cannot move, otherwise returns true
     */
    public boolean checkBuilding(int indexWorker) {
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        myWorkers[indexWorker].getActualBox().setReachable(false);
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        return myWorkers[indexWorker].getActualBox().checkPossible();
    }

    /**
     * This method checks if the player have the ability to build before the worker move
     *
     * @return true if has this god ability, otherwise returns false
     */
    public boolean canBuildBeforeWorkerMove() {
        return gamerManager.canBuildBeforeWorkerMove();
    }

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     */
    public void controlHeartBeat() {
        setMissed_heartbeat(0);
    }

    /**
     * This method counts how many heartbeats a client misses
     *
     * @return true if the client misses the maximum number of heartbeats that could miss, otherwise returns false
     */
    public boolean incrementMissedHeartBeat() {
        synchronized (LOCK) {
            int mh = getMissed_heartbeat();
            mh++;
            setMissed_heartbeat(mh);
            return getMissed_heartbeat() != MAX_HEARTBEATS_MISSED;
        }
    }

    /**
     * This method sets the index of the possible block that a player can build
     *
     * @param indexPossibleBlock index of the possible block
     */
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        gamerManager.setIndexPossibleBlock(indexPossibleBlock);
    }
}

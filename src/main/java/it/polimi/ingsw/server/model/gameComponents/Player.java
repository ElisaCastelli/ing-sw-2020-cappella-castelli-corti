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
    private final Timer timer;
    private final TimerTask timerTask;
    private final Object LOCK = new Object();

    /**
     * This is the playerStateManager that indicates the player state
     */
    final PlayerStateManager gamerManager;


    public Player(int indexClient, Timer timer, TimerTask timerTask) {
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1);
        myWorkers[1] = new Worker(2);
        myGod = new BasicGod();
        gamerManager = new PlayerStateManager(myGod);
        this.indexClient = indexClient;
        this.indexPlayer = indexClient;
        this.timer = timer;
        this.timerTask = timerTask;
    }
    //todo ma perchè funziona se non si setta il god sul playerstatemanager...sarà sempre un basic god
    public void setGod(God god){
        myGod = god;
        gamerManager.setMyGod(myGod);
    }

    public God getGod(){
        return myGod;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    /**
     * This method sets own indexPlayer to the two player workers
     * @param indexPlayer number that identifies each player
     */
    public void setIndexPlayer(int indexPlayer){
        this.indexPlayer = indexPlayer;
        myWorkers[0].setIndexPlayer(indexPlayer);
        myWorkers[1].setIndexPlayer(indexPlayer);
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public int getIndexClient() {
        return indexClient;
    }

    public void setIndexClient(int indexClient) {
        this.indexClient = indexClient;
    }

    public boolean isPlaying(){
        return gamerManager.isPlaying();
    }

    public Box getWorkerBox(int indexWorker){
        return myWorkers[indexWorker].getActualBox();
    }

    public ArrayList<Box> getWorkersBox(){
        ArrayList<Box> boxes = new ArrayList<>();
        boxes.add(0, myWorkers[0].getActualBox());
        boxes.add(1, myWorkers[1].getActualBox());
        return boxes;
    }

    public int getMissed_heartbeat() {
        return missed_heartbeat;
    }

    public void setMissed_heartbeat(int missed_heartbeat) {
        this.missed_heartbeat = missed_heartbeat;
    }

    public Timer getTimer() {
        return timer;
    }

    public TimerTask getTimerTask() {
        return timerTask;
    }

    /**
     * This method sets the first position of a worker
     * @param box1 is the number of the worker i want to set
     * @param box2 is the box where i want to set the worker
     * @return true if initialization is successful, else false
     */
    public boolean initializeWorker( Box box1, Box box2, Board board){
        boolean initializeW1 = myWorkers[0].initializePos(box1, board);
        boolean initializeW2 = myWorkers[1].initializePos(box2, board);

        if(initializeW1 ^ initializeW2){
            if(!initializeW1){
                board.getBox(box2.getRow(),box2.getColumn()).clearWorker();
            }else {
                board.getBox(box1.getRow(),box1.getColumn()).clearWorker();
            }
        }
        return initializeW1 && initializeW2;
    }

    public void goPlay(){
        gamerManager.goPlaying();
    }

    public void goWaiting(){
        gamerManager.goWaiting();
    }

    public void goWin(){
        gamerManager.goWin();
    }

    public boolean amITheWinner(){return gamerManager.amITheWinner();}

    public boolean amIDead(){
        return gamerManager.amIDead();
    }

    public void goDead(){
        gamerManager.goDead();
    }

    /**
     * This method eliminates the workers from the board
     */
    public void clearWorkers(){
        for(Worker worker : myWorkers){
            worker.getActualBox().clearWorker();
        }
    }

    /**
     * This method calls the setPossibleMove in the playerStateManager
     * @param indexWorker index of the worker which is getting the possible moves
     */
    public boolean setPossibleMove( int indexWorker ){
        //gamerManager.setMyGod(myGod);
        return gamerManager.setPossibleMove(myWorkers[indexWorker]);
    }

    /**
     * This method calls the setPossibleBuild in the playerStateManager
     * @param indexWorker index of the worker which is getting the possible builds
     */
    public void setPossibleBuild( int indexWorker ){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
    }

    /**
     * This method calls the moveWorker in the playerStateManager
     * @param indexWorker index of the worker which is going to be moved
     * @param pos box where the worker is going to be moved
     * @return false if the player can do another move because of god ability, otherwise returns true
     */
    public boolean playWorker(int indexWorker, Box pos){
        return gamerManager.moveWorker(myWorkers[indexWorker], pos);
    }

    /**
     * This method calls the moveBlock in the playerStateManager
     * @param pos box where the player wants to build a block
     * @return false if the player can do another move because of god ability, otherwise returns true
     */
    public boolean playBlock(Box pos){
        return gamerManager.moveBlock( pos );
    }

    /**
     * This method calls the checkWin in the playerStateManager and if the player wins, sets his state in Win
     * @param startedBox starter box of the worker
     * @param finalBox box where the worker moved
     * @return true if the player wins, otherwise returns false
     */
    public boolean checkWin(Box startedBox, Box finalBox) {
        boolean win = gamerManager.checkWin(finalBox, startedBox);
        if(win){
            goWin();
        }
        return win;
    }

    /**
     * This method checks if at least one of the two workers can move
      * @return false if no workers can move, otherwise returns true
     */
    public boolean checkWorkers(){
        gamerManager.setPossibleMove(myWorkers[0]);
        boolean firstWorker = myWorkers[0].getActualBox().checkPossible();
        myWorkers[0].getActualBox().clearBoxesNextTo();
        gamerManager.setPossibleMove(myWorkers[1]);
        boolean secondWorker = myWorkers[1].getActualBox().checkPossible();
        myWorkers[1].getActualBox().clearBoxesNextTo();
        return (firstWorker || secondWorker);
    }

    /**
     * This method checks if a worker can move
     * @param indexWorker index of the worker that is going to be checked
     * @return false if the worker cannot move, otherwise returns true
     */
    public boolean checkWorker(int indexWorker){
        gamerManager.setPossibleMove(myWorkers[indexWorker]);
        boolean worker = myWorkers[indexWorker].getActualBox().checkPossible();
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        return worker;
    }

    /**
     * This method checks if a worker can build
     * @param indexWorker index of the worker that is going to be checked
     * @return false if the worker cannot move, otherwise returns true
     */
    public boolean checkBuilding(int indexWorker){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        return myWorkers[indexWorker].getActualBox().checkPossible();
    }

    /**
     * This method checks if the player have the ability to build before the worker move
     * @return true if has this god ability, otherwise returns false
     */
    public boolean canBuildBeforeWorkerMove(){
        return gamerManager.canBuildBeforeWorkerMove();
    }

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     */
    public void controlHeartBeat(){
        System.out.println(missed_heartbeat+"riazzero"+ indexPlayer);
        setMissed_heartbeat(0);
    }

    /**
     * This method counts how many heartbeats a client misses
     * @return true if the client misses the maximum number of heartbeats that could miss, otherwise returns false
     */
    public boolean incrementMissedHeartBeat(){
        synchronized (LOCK) {
            int mh = getMissed_heartbeat();
            mh++;
            System.out.println(mh + "client" + indexClient );
            setMissed_heartbeat(mh);
            return getMissed_heartbeat() != MAX_HEARTBEATS_MISSED;
        }
    }

    /**
     * This method sets the index of the possible block that a player can build
     * @param indexPossibleBlock index of the possible block
     */
    public void setIndexPossibleBlock( int indexPossibleBlock){
        gamerManager.setIndexPossibleBlock(indexPossibleBlock);
    }
}

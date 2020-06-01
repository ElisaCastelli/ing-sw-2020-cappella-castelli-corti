package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.playerState.PlayerState;
import it.polimi.ingsw.server.model.playerState.PlayerStateManager;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Timer;


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
    private final int indexClient;

    private final int MAX_HEARTBEATS_MISSED=5;
    private int missed_heartbeat = 0;
    private final Timer timer;
    private final Object LOCK = new Object();


    final PlayerStateManager gamerManager;


    public Player(int indexClient, Timer timer) {
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1);
        myWorkers[1] = new Worker(2);
        myGod = new BasicGod();
        gamerManager = new PlayerStateManager(myGod);
        this.indexClient = indexClient;
        this.timer = timer;
    }

    public void setGod(God god){
        myGod = god;
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
    public void setIndexPlayer(int indexPlayer){
        this.indexPlayer=indexPlayer;
        myWorkers[0].setIndexPlayer(indexPlayer);
        myWorkers[1].setIndexPlayer(indexPlayer);
    }
    public int getIndexPlayer() {
        return indexPlayer;
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

    public int getIndexClient() {
        return indexClient;
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

    public void clearWorkers(){
        for(Worker worker : myWorkers){
            worker.getActualBox().clearWorker();
        }
    }

    public void setPossibleMove( int indexWorker ){
        gamerManager.setMyGod(myGod);
        gamerManager.setPossibleMove(myWorkers[indexWorker]);
    }

    public void setPossibleBuild( int indexWorker ){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
    }

    public boolean playWorker(int indexWorker, Box pos){
        return gamerManager.moveWorker(myWorkers[indexWorker], pos);
    }

    public boolean playBlock(Box pos){
        return gamerManager.moveBlock( pos );
    }

    public boolean checkWin(Box startedBox, Box finalBox) {
        boolean win = gamerManager.checkWin(finalBox, startedBox);
        if(win){
            goWin();
        }
        return win;
    }

    //controlla che entrambe le pedine possono muoversi
    public boolean checkWorkers(){
        gamerManager.setPossibleMove(myWorkers[0]);
        boolean firstWorker = myWorkers[0].getActualBox().checkPossible();
        myWorkers[0].getActualBox().clearBoxesNextTo();
        gamerManager.setPossibleMove(myWorkers[1]);
        boolean secondWorker = myWorkers[1].getActualBox().checkPossible();
        myWorkers[1].getActualBox().clearBoxesNextTo();
        return (firstWorker || secondWorker);
    }

    public boolean checkWorker(int indexWorker){
        gamerManager.setPossibleMove(myWorkers[indexWorker]);
        boolean worker = myWorkers[indexWorker].getActualBox().checkPossible();
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        return worker;
    }

    //Controlla che si può costruire, ho tolto la clear delle box almeno posso fare un update diretta
    public boolean checkBuilding(int indexWorker){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        return myWorkers[indexWorker].getActualBox().checkPossible();
    }

    public boolean canBuildBeforeWorkerMove(){
        return gamerManager.canBuildBeforeWorkerMove();
    }

    public void controlHeartBeat(long timeStamp){
        System.out.println(missed_heartbeat+"riazzero"+ indexPlayer);
        setMissed_heartbeat(0);
    }

    public boolean incrementMissedHeartBeat(){
        synchronized (LOCK) {
            int mh = getMissed_heartbeat();
            mh++;
            System.out.println(mh + "player" + indexClient );
            setMissed_heartbeat(mh);
            if (getMissed_heartbeat() == MAX_HEARTBEATS_MISSED) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void setIndexPossibleBlock( int indexPossibleBlock){
        gamerManager.setIndexPossibleBlock(indexPossibleBlock);
    }
}

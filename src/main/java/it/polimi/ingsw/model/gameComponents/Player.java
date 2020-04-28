package it.polimi.ingsw.model.gameComponents;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.playerState.PlayerStateManager;
import it.polimi.ingsw.model.god.BasicGod;
import it.polimi.ingsw.model.god.God;


public class Player {
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
     * This is a String in Hexadecimal to identify the color associated with the player and his workers
     */
    private final Game.COLOR color;

    /**
     * This array of Workers contains two workers for each player
     */
    private final Worker[] myWorkers;

    final PlayerStateManager gamerManager;

    /**
     * Constructor with name and board as parameters
     * @param age the age of a player
     * @param name the name of a player
     */
    public Player(String name, int age, Game.COLOR color){
        this.name = name;
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1, color);
        myWorkers[1] = new Worker(2,color);
        this.age = age;
        myGod = new BasicGod();
        gamerManager = new PlayerStateManager(myGod);
        this.color = color;
    }

    public void setGod(God god){
        myGod = god;
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

    public Box getWorkerBox(int indexWorker){
        return myWorkers[indexWorker].getActualBox();
    }

    /**
     * This method sets the first position of a worker
     * @param index is the number of the worker i want to set
     * @param requestedBox is the box where i want to set the worker
     * @return true if initialization is successful, else false
     */
    public boolean initializeWorker( int index , Box requestedBox){
        return myWorkers[index].initializePos(requestedBox);
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

    public void goDead(){
        gamerManager.goDead();
    }

    public void setPossibleMove( int indexWorker ){
         gamerManager.setPossibleMove(myWorkers[indexWorker]);
    }

    public void setPossibleBuild( int indexWorker ){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
    }

    public boolean playWorker(int indexWorker, Box pos){
        boolean movedWorker=false;
        setPossibleMove(indexWorker);
        if(pos.isReachable()) {
            movedWorker = gamerManager.moveWorker(myWorkers[indexWorker], pos);
        }
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        return  movedWorker;
    }

    public boolean playBlock( int indexWorker, Box pos){
        boolean movedBlock=false;
        if(myWorkers[indexWorker].getActualBox().isNext(pos)){
            movedBlock = gamerManager.moveBlock( pos );
        }
        return movedBlock;
    }

    public boolean checkWin(Box startedBox, Box finalBox) { //index già giusto
        // posizione di partenza e posizione di arrivo
        return gamerManager.checkWin(startedBox, finalBox);
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

    public boolean checkBuilding(int indexWorker){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        boolean canBuild = myWorkers[indexWorker].getActualBox().checkPossible();
        myWorkers[indexWorker].getActualBox().clearBoxesNextTo();
        return canBuild;
    }


}

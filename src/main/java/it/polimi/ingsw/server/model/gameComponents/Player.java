package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.playerState.PlayerState;
import it.polimi.ingsw.server.model.playerState.PlayerStateManager;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;

import java.io.Serializable;
import java.util.ArrayList;


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
     * This is a String in Hexadecimal to identify the color associated with the player and his workers
     */
    private int indexPlayer;

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
    public Player(String name, int age){
        this.name = name;
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1);
        myWorkers[1] = new Worker(2);
        this.age = age;
        myGod = new BasicGod();
        gamerManager = new PlayerStateManager(myGod);
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
        boxes.add(0,myWorkers[0].getActualBox());
        boxes.add(1,myWorkers[1].getActualBox());
        return boxes;
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
        return gamerManager.moveWorker(myWorkers[indexWorker], pos);
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
        if(!firstWorker && !secondWorker){
            gamerManager.goDead();
        }
        return (firstWorker || secondWorker);
    }

    //Controlla che si può costruire, ho tolto la clear delle box almeno posso fare un update diretta
    public boolean checkBuilding(int indexWorker){
        gamerManager.setPossibleBuild(myWorkers[indexWorker]);
        boolean canBuild=myWorkers[indexWorker].getActualBox().checkPossible();
        if(!canBuild){
            gamerManager.goDead();
        }
        return canBuild;
    }


}

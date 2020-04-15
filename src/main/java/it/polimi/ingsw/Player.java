package it.polimi.ingsw;

import it.polimi.ingsw.gamerstate.GamerStateManager;
import it.polimi.ingsw.god.God;


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
     * This array of Workers contains two workers for each player
     */
    private Worker[] myWorkers;

    GamerStateManager gamerManager;

    /**
     * Constructor with name and board as parameters
     * @param age
     * @param name
     */
    Player(String name, int age){
        this.name = name;
        myWorkers = new Worker[2];
        myWorkers[0] = new Worker(1,name);
        myWorkers[1] = new Worker(2,name);
        this.age = age;
        //todo mygod da ricontrollare perchè sempre null
        myGod = null;
        gamerManager = new GamerStateManager(myGod);
    }

    public void setGod(God god){
        myGod = god;
        //TODO swtch in base al nome decidere decorator
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
     * This method changes the attributes of a gamer with attributes of another, and the other way around
     * @param player2
     */
    public void swap(Player player2){
        Player newPlayer = new Player( player2.name, player2.age);
        player2.setName( this.name );
        player2.setAge( this.age );
        this.setName( newPlayer.name );
        this.setAge( newPlayer.age );
    }

    public void print(){
        System.out.println(name+ " " +age);
    }

    /**
     * This method sets the first position of a worker
     * @param index is the number of the worker i want to set
     * @param requestedBox is the box where i want to set the worker
     * @return true if initialization is successful, else false
     */
    public boolean initializeWorker( int index , Box requestedBox){
        if(myWorkers[index - 1].initializePos(requestedBox)){
            return true;
        }
        return false;
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
        boolean movedWorker = false;
        movedWorker = gamerManager.moveWorker(myWorkers [ indexWorker ] , pos);
        return  movedWorker;
    }

    public boolean playBlock( Box pos){
        boolean movedBlock = false;
        movedBlock = gamerManager.moveBlock( pos );
        return movedBlock;
    }

//salgo al terzo
    public boolean checkWin(Box startedBox, Box finalBox) { //index già giusto
        // posizione di partenza e posizione di arrivo
        return gamerManager.checkWin(startedBox, finalBox);
    }

    public boolean checkWorkers(){
        gamerManager.setPossibleMove(myWorkers[0]);
        boolean firstWorker = myWorkers[0].getActualBox().checkPossible();
        myWorkers[0].getActualBox().clearBoxesNextTo();
        gamerManager.setPossibleMove(myWorkers[1]);
        boolean secondWorker = myWorkers[1].getActualBox().checkPossible();
        myWorkers[1].getActualBox().clearBoxesNextTo();
        return (firstWorker || secondWorker);
    }

    /*public static void main( String[] args )
    {
        Board b= new Board();
        Gamer g= new Gamer("Io",b);
        Box box= new Box(0,1,1);
        if(g.initializeWorker(1,box)==true){
            System.out.println("ok");
        }
        else{
            System.out.println("mannaggia la miseria");
        }

    }*/
}

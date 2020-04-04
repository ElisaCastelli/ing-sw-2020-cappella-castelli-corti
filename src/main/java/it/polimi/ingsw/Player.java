package it.polimi.ingsw;

import it.polimi.ingsw.gamerstate.GamerStateManager;
import it.polimi.ingsw.god.God;
import it.polimi.ingsw.god.Gods;

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
    private Worker myWorkers[];

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

    public God getMyGod() {
        return myGod;
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
        if( myWorkers[ index - 1 ].initializePos( requestedBox ) == true ){
            return true;
        }
        return false;
    }

    public void goPlay(){
        gamerManager.goPlaying();
    }

    public boolean checkPossibleMove( Box actualBox , Board boardToControl ){
        return gamerManager.checkPossibleMove(actualBox, boardToControl);
    }

    /**
     *
     * @param finalBox
     * @param boardToControl
     * @return 0 if you can't build otherwise it will return the index of the worker nearby
     */
    public int checkPossibleBuild( Box finalBox, Board boardToControl ){
        return gamerManager.checkPossibleBuild(finalBox,boardToControl,name);
    }

    //0 mossa niente 1 mossa ok 2 mosse strane
    public int playWorker(int indexWorker, Board board, int row, int column){
        int movedWorker = 0;
        gamerManager.goPlaying();
        movedWorker = gamerManager.moveWorker(myWorkers [ indexWorker ] , board.getBox( row , column ) , myGod.getGodName());
        return  movedWorker;
    }

    public int playBlock( Board board, int row, int column){
        int movedBlock = 0;
        int indexWorker = checkPossibleBuild(board.getBox(row, column), board);
        movedBlock = gamerManager.moveBlock( myWorkers [ indexWorker ] , board.getBox( row , column ) , myGod.getGodName() );
        return movedBlock;
    }

//salgo al terzo
    public boolean checkWin(int indexWorkerMoved, Board board, int row, int column) { //index già giusto
        // posizione di partenza e posizione di arrivo
        return gamerManager.checkWin(board.getBox(row, column), myWorkers[indexWorkerMoved].getActualBox(), myGod.getGodName());
    }

    public boolean checkWorkers( Board boardToControl){
        return gamerManager.checkWorkers(myWorkers[0].getActualBox(), myWorkers[1].getActualBox(), boardToControl);
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

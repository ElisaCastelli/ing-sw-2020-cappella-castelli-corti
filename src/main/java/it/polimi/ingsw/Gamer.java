package it.polimi.ingsw;

import it.polimi.ingsw.gamerstate.GamerStateManager;
import it.polimi.ingsw.god.God;
import java.util.Scanner;

/**
 * This class represents the gamer
 */
public class Gamer {
    /**
     * This is the name of the player
     */
    private String name;
    /**
     * This id the god card drawn by the player
     */
    private God myCard;
    /**
     * This array of Workers contains two workers for each player
     */
    private Worker myWorkers[];
    private int age;
    private Board board;
    GamerStateManager gamerManager = new GamerStateManager();

    /**
     * Constructor with name and board as parameters
     * @param age
     * @param name
     * @param board
     */
    Gamer(String name, int age, Board board){
        this.name=name;
        myWorkers= new Worker[2];
        myWorkers[0]= new Worker();
        myWorkers[1]= new Worker();
        this.board=board;
        this.age=age;
        myCard=null;
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
    public void setBoard(Board board) {
        this.board = board;
    }

    public God getMyCard() {
        return myCard;
    }
    public void setMyCard(God myCard) {
        this.myCard = myCard;
    }
    public int getAge() {
        return age;
    }

    /**
     * This method changes the attributes of a gamer with attributes of another, and the other way around
     * @param gamer2
     */
    public void swap(Gamer gamer2){
        Gamer newGamer=new Gamer(gamer2.name,gamer2.age, gamer2.board);
        gamer2.setName(this.name);
        gamer2.setAge(this.age);
        gamer2.setBoard(this.board);
        this.setName(newGamer.name);
        this.setAge(newGamer.age);
        this.setBoard(newGamer.board);
    }

    public void print(){
        System.out.println(name+ " "+ age);
    }
    /**
     * This method sets the first position of a worker
     * @param index is the number of the worker i want to set
     * @param requestedBox is the box where i want to set the worker
     * @return true if initialization is successful, else false
     */
    public boolean initializeWorker(int index, Box requestedBox){
        if(myWorkers[index-1].initializePos(requestedBox)==true){
            myWorkers[index-1].setWorkerId(index);
            return true;
        }
        return false;
    }

    /**
     * this method implements a single turn for a gamerr. the turn consist of two parts:
     * the movement of the pawn and the construction of a building
     */
    void move(){
        boolean workerMoved=false;
        boolean built=false;
        while(!workerMoved){

            //la richiesta del numero di pedina e dello spazio dove voglio muovermi verrà fatta graficamente
            System.out.println("Pedina da muovere [1] 0 [2]:");
            Scanner worker = new Scanner(System.in);
            int w = Integer.parseInt(worker.nextLine());
            System.out.println("Riga dove voglio muovermi:");
            Scanner row = new Scanner(System.in);
            int r = Integer.parseInt(row.nextLine());
            System.out.println("Colonna dove voglio muovermi:");
            Scanner col = new Scanner(System.in);
            int c = Integer.parseInt(row.nextLine());
            //TO DO:MOVEWORKER BOOLEAN
            /*workerMoved=*/myCard.moveWorker(myWorkers[w],board.getBox(r,c));
        }
        while(!built){
            //moveBlock(block, box);
        }
        gamerManager.move();
        gamerManager.move();
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

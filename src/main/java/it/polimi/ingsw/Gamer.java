package it.polimi.ingsw;

import it.polimi.ingsw.gamerstate.GamerStateManager;
import it.polimi.ingsw.god.God;
import it.polimi.ingsw.god.Gods;

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
     * Gamer's age
     */
    private int age;
    /**
     * This id the god card drawn by the player
     */
    private God myGod;
    /**
     * This array of Workers contains two workers for each player
     */
    private Worker myWorkers[];
    GamerStateManager gamerManager = new GamerStateManager();


    /**
     * Constructor with name and board as parameters
     * @param age
     * @param name
     */
    Gamer(String name, int age){
        this.name=name;
        myWorkers= new Worker[2];
        /*myWorkers[0]= new Worker();
        myWorkers[1]= new Worker();*/
        this.age=age;
        myGod=null;
    }
    public void setGod(String nameGod){
        myGod=new Gods(nameGod);
        //TO DO swtch in base al nome decidere decorator
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
     * This method changes the attributes of a gamer with attributes of another, and the other way around
     * @param gamer2
     */
    public void swap(Gamer gamer2){
        Gamer newGamer=new Gamer(gamer2.name,gamer2.age/*, gamer2.board*/);
        gamer2.setName(this.name);
        gamer2.setAge(this.age);
        //gamer2.setBoard(this.board);
        this.setName(newGamer.name);
        this.setAge(newGamer.age);
        //this.setBoard(newGamer.board);
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

    public int checkPossibleBuild(Box finalBox){
       int indexWorker=3;
       return indexWorker;
    }

    public boolean playWorker(int indexWorker, Box finalBox){
        boolean movedWorker=false;
        movedWorker=myGod.moveWorker(myWorkers[indexWorker],finalBox,myGod.getGodName());
        if(movedWorker==true){
            return true;
        }
        return false;
    }

    public boolean playBlock( Box finalBox){
        boolean movedBlock=false;
        int indexWorker=checkPossibleBuild(finalBox);
        movedBlock=myGod.moveBlock(myWorkers[indexWorker], finalBox,myGod.getGodName());
        if(movedBlock==true){
            return true;
        }
        return false;

    }

    public boolean checkWin(Box oldBox, int indexWorkerMoved){ //index già giusto
        if(myGod.checkWin(oldBox, myWorkers[indexWorkerMoved].getActualBox(),myGod.getGodName())==true){
            //gamerManager.moveToWin();
            return true;
        }
        return false;
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

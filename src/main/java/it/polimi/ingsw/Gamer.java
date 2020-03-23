package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;

public class Gamer {
    private String name;
    private boolean alive;
    private God myCard;
    private Worker myWorkers[];

    Gamer(String name){
        this.name=name;
        myWorkers= new Worker[2];
        myWorkers[0]= new Worker();
        myWorkers[1]= new Worker();
        alive=true;
        myCard=null;
    }
    public String getName() {
        return name;
    }
    public  void setName(String name) {
        this.name = name;
    }
    public God getMyCard() {
        return myCard;
    }
    public void setMyCard(God myCard) {
        this.myCard = myCard;
    }
    public boolean isAlive() {
        return alive;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setWorker(int index, Board b){
        myWorkers[index].setBoard(b);
        myWorkers[index].setWorkerId(index+1);
    }
    void move(){
        /*boolean workerMoved=false;
        boolean built=false;
        Box b=new Box();
        while(!workerMoved){
            b.clear();
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
            b.setRow(r);
            b.setColumn(c);

            //myCard.moveWorker(myWorkers[w],b);
        }
        while(!built){
            b.clear();


            //moveBlock(block, box);
        }*/
        System.out.println("Muove giocatore "+name);
    }


}

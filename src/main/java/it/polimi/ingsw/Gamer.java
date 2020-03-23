package it.polimi.ingsw;

public class Gamer {
    private static String name;
    private boolean alive;
    //Worker myWorkers[];

    Gamer(String name){
        this.name=name;
        //myWorkers= new Worker[2];
        //myworkers[1]=new Worker(1);
        //myWorkers[2]=new Worker(2);
        alive=true;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
/*void move(Worker w){

    }*/

}

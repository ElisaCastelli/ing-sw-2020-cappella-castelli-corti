package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This is God concrete class which implements the basic moves of the workers and of the build
 */
public class Gods implements God {
    /**
     * This attribute is the name of the God
     */
    private String godName;

    /**
     * This constructor instantiates a God with the given godName
     * @param godName Name of the God to be instantiated
     */
    /*public Gods(){
        godName="";
    }*/
    public Gods (String godName) {
        this.godName=godName;
    }

    public String getGodName() {return godName;}
    public void setGodName(String godName) {this.godName = godName;}

    /**
     * This method implements the basic worker move: a chosen worker moves to a new position that must be unoccupied
     * (not containing a worker or a dome) and must be next to the worker.
     * The worker may move up a maximum of one level higher, move down any number of levels lower or move along the
     * same level.
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveWorker (Worker worker, Box pos, String godName) {
        Box boxWorker=worker.getActualBox();
        int heightWorker=worker.getHeight();
        int counterPos=pos.getCounter();
        //i puntatori sono giusti, pos puntatore alla box della board, boxWorker puntatore alla posizionne inziale tramite worker
        if (boxWorker.reachable(pos) && pos.notWorker() && counterPos!=4) {

            if (upDownOrStayAtTheSameLevel(counterPos,heightWorker)==1) {
                    worker.setHeight(heightWorker+1);
                    worker.setActualBox(pos);
                    return true;
            }
            else if (upDownOrStayAtTheSameLevel(counterPos,heightWorker)==2) {
                    worker.setHeight(counterPos);
                    worker.setActualBox(pos);
                    return true;
            }
            else if (upDownOrStayAtTheSameLevel(counterPos,heightWorker)==3) {
                    worker.setActualBox(pos);
                    return true;
            }
            //se si prova un caso in cui non entra in nessuna delle tre condizioni significa che la mossa non è valida
            //in quanto sale di troppi livelli (upDownOrStayAtTheSameLevel==4)
            //checkWin();
        }
        return false;
    }

    /**
     * This method implements the basic block move: a chosen worker builds a block or a dome in a position next to him
     * onto a level of any height and this position must be unoccupied (not containing a worker or a dome)
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    @Override
    public boolean moveBlock(Worker worker, Box pos, String godName) {
        Box boxWorker=worker.getActualBox();
        int counterPos=pos.getCounter();

        if (boxWorker.reachable(pos) && pos.notWorker() && counterPos!=4) {
            pos.build();
            return true;
        }
        return false;
    }

    /**
     * This method implements the basic check win: a player wins if his worker moves up on top of level 3
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @param godName The God name card
     * @return False if the player doesn't win; true if the player wins
     */
    @Override
    public boolean checkWin(Box initialPos, Box finalBox, String godName) {
        return false;
    }

    /**
     * This method controls where the worker moves: the first case, the worker moves up a maximum of one level
     * higher; the second case, the worker moves down any number of levels lower; the third case, the worker moves along
     * the same level; the fourth case, the worker moves up too much, so the move is not available.
     * @param counterBuilding Counter of the building
     * @param counterWorker Counter of the worker
     * @return int representing the case in which the player is
     */
    public int upDownOrStayAtTheSameLevel(int counterBuilding, int counterWorker) {
        if (counterBuilding-counterWorker==1) {
            return 1;
        }
        else if (counterWorker-counterBuilding>=1) {
            return 2;
        }
        else if (counterWorker==counterBuilding) {
            return 3;
        }
        return 4;
    }
}

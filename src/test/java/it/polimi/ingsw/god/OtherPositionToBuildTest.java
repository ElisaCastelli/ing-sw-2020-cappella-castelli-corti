package it.polimi.ingsw.god;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtherPositionToBuildTest {

    @Test
    void setPossibleBuild() {
        //DEMETRA
        God god=new OtherPositionToBuild(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();
        board.setBoxesNext();
        worker.initializePos(board.getBox(0,1));
        worker2.initializePos(board.getBox(1,1));
        worker3.initializePos(board.getBox(3,3));





    }

    @Test
    void moveBlock() {



    }
}
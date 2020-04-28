package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.gameComponents.Board;
import it.polimi.ingsw.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OpponentBlockTest {


    @Test
    void moveWorker() {
        //ATENA
        God god=new OpponentBlock(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();

        worker.initializePos(board.getBox(0,1));
        worker2.initializePos(board.getBox(1,1));
        worker3.initializePos(board.getBox(3,3));
        //costruisco un livello
        board.getBox(0,2).build();
        assertEquals(1,board.getBox(0,2).getCounter());

        //MOSSA BASE
        god.setPossibleMove(worker3);
        worker3.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker3,board.getBox(2,4)));

        //MOSSA SPECIALE
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker,board.getBox(0,2)));
        assertEquals(1,worker.getActualBox().getCounter());

        god.setPossibleMove(worker2);
        worker2.getActualBox().clearBoxesNextTo();

        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker,board.getBox(1,3)));
        assertEquals(0,worker.getActualBox().getCounter());
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();

        //costruisco due volte
        board.getBox(0,3).build();
        board.getBox(0,3).build();
        assertEquals(2,board.getBox(0,3).getCounter());
        assertTrue(god.moveWorker(worker,board.getBox(0,2)));
        assertTrue(god.moveWorker(worker,board.getBox(0,3)));
        assertEquals(2,worker.getActualBox().getCounter());



    }

}
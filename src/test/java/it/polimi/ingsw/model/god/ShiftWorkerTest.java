package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ShiftWorkerTest {

    @Test
    void setPossibleMove() {
        God god=new ShiftWorker(new SwitchWorker(new BasicGod()));
        Worker worker=new Worker(1, Game.COLOR.BLU);
        Worker worker2=new Worker(2,Game.COLOR.BLU);
        Worker worker3=new Worker(3,Game.COLOR.BLU);
        Board board = new Board();

        worker.initializePos(board.getBox(1,1));
        worker2.initializePos(board.getBox(0,0));
        worker3.initializePos(board.getBox(3,3));

        //NON MI DEVE FAR SPOSTARE IN ALTO
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        //TUTTE POSSIBILI DOVE NON NULL
        god.setPossibleMove(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        //TUTTE POSSIBILI
        god.setPossibleMove(worker3);
        worker3.getActualBox().clearBoxesNextTo();


    }

    @Test
    void moveWorker() {
        //MINOTAURO
        God god=new ShiftWorker(new SwitchWorker(new BasicGod()));
        Worker worker=new Worker(1,Game.COLOR.BLU);
        Worker worker2=new Worker(2,Game.COLOR.BLU);
        Worker worker3=new Worker(3,Game.COLOR.BLU);
        Board board = new Board();
        board.setBoxesNext();
        worker.initializePos(board.getBox(1,1));
        worker2.initializePos(board.getBox(0,0));
        worker3.initializePos(board.getBox(3,3));
        //MOSSA BASE
        assertTrue(god.moveWorker(worker3,board.getBox(2,2)));
        assertEquals(3,board.getBox(2,2).getWorker().getWorkerId());
        assertNull(board.getBox(3,3).getWorker());
        assertTrue(board.getBox(3, 3).notWorker());
        assertNotNull(board.getBox(2,2).getWorker());
        assertFalse(board.getBox(2, 2).notWorker());

        //controllo che il worker non può fare la mossa verso il worker1 ora che il worker3 si è mossa
        god.setPossibleMove(worker2);
        worker2.getActualBox().clearBoxesNextTo();

        //MOSSA SPECIALE
        assertTrue(god.moveWorker(worker,board.getBox(2,2)));
        assertFalse(board.getBox(0,0).notWorker());
        assertTrue(board.getBox(1,1).notWorker());
        assertFalse(board.getBox(2,2).notWorker());
        assertFalse(board.getBox(3,3).notWorker());
        assertEquals(2,board.getBox(0,0).getWorker().getWorkerId());
        assertEquals(1,board.getBox(2,2).getWorker().getWorkerId());
        assertEquals(3,board.getBox(3,3).getWorker().getWorkerId());
    }

}
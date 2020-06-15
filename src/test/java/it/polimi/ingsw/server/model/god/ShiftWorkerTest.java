package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ShiftWorkerTest {

    private God god = new ShiftWorker(new SwitchWorker(new BasicGod()));

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("ShiftWorker");
        effects.add("SwitchWorker");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Minotaur");
        assertEquals("Minotaur", god.getName());
        assertEquals("ShiftWorker", god.getEffects().get(0));
        assertEquals("SwitchWorker", god.getEffects().get(1));
        assertEquals("BasicGod", god.getEffects().get(2));
    }

    @Test
    void setPossibleMove() {
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();

        worker.initializePos(board.getBox(1,1),board);
        worker2.initializePos(board.getBox(0,0),board);
        worker3.initializePos(board.getBox(3,3),board);

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
        Worker worker=new Worker(1);
        worker.setIndexPlayer(0);
        Worker worker2=new Worker(2);
        worker2.setIndexPlayer(1);
        Worker worker3=new Worker(3);
        worker3.setIndexPlayer(1);
        Board board = new Board();
        Board board2 = new Board();

        worker.initializePos(board.getBox(1,1),board);
        worker2.initializePos(board.getBox(0,0),board);
        worker3.initializePos(board.getBox(3,3),board);
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
        assertTrue(god.moveWorker(worker, board.getBox(2,2)));
        assertFalse(board.getBox(0,0).notWorker());
        assertTrue(board.getBox(1,1).notWorker());
        assertFalse(board.getBox(2,2).notWorker());
        assertFalse(board.getBox(3,3).notWorker());
        assertEquals(2,board.getBox(0,0).getWorker().getWorkerId());
        assertEquals(1,board.getBox(2,2).getWorker().getWorkerId());
        assertEquals(3,board.getBox(3,3).getWorker().getWorkerId());
        assertFalse(god.checkWin(board.getBox(1,1), worker.getActualBox()));
        god.setPossibleBuild(worker);
        assertTrue(god.moveBlock(board.getBox(1,1)));

        worker.initializePos(board2.getBox(2,2),board2);
        worker.setIndexPlayer(0);
        worker2.initializePos(board2.getBox(3,1),board2);
        worker2.setIndexPlayer(1);
        worker3.initializePos(board2.getBox(2,1),board2);
        worker3.setIndexPlayer(1);

        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board2.getBox(3,1)));
        board2.getBox(2,2).clearBoxesNextTo();
        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board2.getBox(2,1)));
        board2.getBox(3,1).clearBoxesNextTo();
        assertTrue(god.moveWorker(worker2, board2.getBox(3,1)));
        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board2.getBox(3,1)));
        board2.getBox(2,1).clearBoxesNextTo();
        assertTrue(god.moveWorker(worker3, board2.getBox(2,2)));
        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board2.getBox(2,2)));
        board2.getBox(3,1).clearBoxesNextTo();
        worker2.initializePos(board2.getBox(2,1),board2);
        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board2.getBox(2,1)));
        board2.getBox(2,2).clearBoxesNextTo();
    }

}
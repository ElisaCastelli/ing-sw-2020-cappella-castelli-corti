package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker = new Worker(1);
    Board board = new Board();

    @Test
    void initializePos() {
        assertTrue(worker.initializePos(board.getBox(1, 2), board));

        System.out.println(board.getBox(1, 2).getWorker().toString());
        board.getBox(1, 2).clearWorker();
        assertTrue(worker.initializePos(board.getBox(1, 2), board));
        assertFalse(worker.initializePos(board.getBox(1, 2), board));

    }

    @Test
    void clear() {
        assertTrue(worker.initializePos(board.getBox(3,2 ), board));
        System.out.println(board.getBox(3,2 ).getWorker().toString());
        worker.clear();
        System.out.println(worker.toString());
        assertEquals(0, worker.getWorkerId());
        assertEquals(0, worker.getHeight());
        assertNull(worker.getActualBox());
        System.out.println(board.getBox(3,2 ).getWorker().toString());


    }

}
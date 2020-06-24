package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker;
    Board board;
    @BeforeEach
    public void init() {
        worker =new Worker(1);
        board =new Board();
    }

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

    @Test
    void getHeight() {
        assertEquals(0, worker.getHeight());
    }

    @Test
    void setHeight() {
        worker.setHeight(1);
        assertEquals(1,worker.getHeight() );
    }

    @Test
    void getWorkerId() {
        assertEquals(1,worker.getWorkerId());
    }

    @Test
    void setWorkerId() {
        worker.setWorkerId(2);
        assertEquals(2, worker.getWorkerId());
    }

    @Test
    void getActualBox() {
        worker.setActualBox(board.getBox(0,0));
        assertEquals(board.getBox(0,0), worker.getActualBox());
    }

    @Test
    void setActualBox() {
        worker.setActualBox(board.getBox(0,0));
    }

    @Test
    void getIndexPlayer() {
        assertEquals(0,worker.getIndexPlayer());
    }

    @Test
    void setIndexPlayer() {
        worker.setIndexPlayer(1);
        assertEquals(1,worker.getIndexPlayer());
    }

    @Test
    void getIndexClient() {
        assertEquals(0, worker.getIndexClient());
    }

    @Test
    void setIndexClient() {
        worker.setIndexClient(1);
        assertEquals(1,worker.getIndexClient());
    }

    @Test
    void testToString() {
        assertEquals("Worker{workerId=1}",worker.toString() );
    }
}
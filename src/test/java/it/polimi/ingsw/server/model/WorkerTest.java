package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker = new Worker(1);
    Board board= new Board();

    @Test
    void initializePos() {
        Box box = new Box(1, 2);
        assertEquals(true, worker.initializePos(box,board));
        System.out.println(box.getWorker().toString());
        box.clearWorker();
        assertEquals(true, worker.initializePos(box,board));
        assertEquals(false, worker.initializePos(box,board));

    }

   @Test
    void clear() {
        Box box = new Box(3, 2);
        assertEquals(true, worker.initializePos(box,board));
        System.out.println(box.getWorker().toString());
        worker.clear();
        System.out.println(worker.toString());
        assertEquals(0,worker.getWorkerId());
        assertEquals(0,worker.getHeight());
        assertEquals(null,worker.getActualBox());
        System.out.println(box.getWorker().toString());


    }

}
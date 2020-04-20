package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class WorkerTest {
    Worker worker = new Worker(1);


    @Test
    void initializePos() {
        Box box = new Box(1, 2);
        assertEquals(true, worker.initializePos(box));
        System.out.println(box.getWorker().toString());
        box.clearWorker();
        assertEquals(true, worker.initializePos(box));
        assertEquals(false, worker.initializePos(box));

    }

   @Test
    void clear() {
        Box box = new Box(3, 2);
        assertEquals(true, worker.initializePos(box));
        System.out.println(box.getWorker().toString());
        worker.clear();
        System.out.println(worker.toString());
        assertEquals(0,worker.getWorkerId());
        assertEquals(0,worker.getHeight());
        assertEquals(null,worker.getActualBox());
        System.out.println(box.getWorker().toString());


    }

}
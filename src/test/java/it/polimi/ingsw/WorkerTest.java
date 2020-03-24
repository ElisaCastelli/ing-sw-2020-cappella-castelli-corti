package it.polimi.ingsw;

import junit.framework.TestCase;

public class WorkerTest extends TestCase {
    Board boardTest=new Board();
    Worker workerTest1= new Worker(1,boardTest);
    Worker workerTest2=new Worker(2,boardTest);


    public void testGetHeight() {
        workerTest1.setHeight(0);
        assertEquals(0,workerTest1.getHeight());
        workerTest1.setHeight(1);
        assertEquals(1,workerTest1.getHeight());
        workerTest1.setHeight(2);
        assertEquals(2,workerTest1.getHeight());
        workerTest1.setHeight(3);
        assertEquals(3,workerTest1.getHeight());
    }

    public void testGetWorkerId() {
        workerTest1.setWorkerId(1);
        assertEquals(1,workerTest1.getWorkerId());
    }

    public void testGetActualBox() {
        workerTest1.setWorkerId(2);
        assertEquals(2,workerTest1.getWorkerId());
    }

    public void testInitializePos() {
        assertTrue(workerTest1.initializePos(1, 1));
        assertTrue(workerTest2.initializePos(1,2));
        assertFalse(workerTest1.initializePos(1,2));
    }
}
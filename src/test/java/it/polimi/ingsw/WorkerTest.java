package it.polimi.ingsw;

import junit.framework.TestCase;

public class WorkerTest extends TestCase {
    Worker workerTest= new Worker();

    public void testGetHeight() {
        workerTest.setHeight(0);
        assertEquals(0,workerTest.getHeight());
        workerTest.setHeight(1);
        assertEquals(1,workerTest.getHeight());
        workerTest.setHeight(2);
        assertEquals(2,workerTest.getHeight());
        workerTest.setHeight(3);
        assertEquals(3,workerTest.getHeight());
    }

    public void testGetWorkerId() {
        workerTest.setWorkerId(1);
        assertEquals(1,workerTest.getWorkerId());
    }

    public void testGetActualBox() {
        workerTest.setWorkerId(2);
        assertEquals(2,workerTest.getWorkerId());
    }

    public void testInitializePos() {
    }
}
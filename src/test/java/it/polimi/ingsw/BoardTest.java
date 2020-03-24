package it.polimi.ingsw;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
    Board boardTest=new Board();


    public void testBuild() {
        assertNotNull(boardTest.getMatrix());
    }

    public void testClear() {
        Worker workerTest=new Worker(1,boardTest.getBox(4,3));
        boardTest.getBox(1,1).setWorker(workerTest);
        boardTest.build(2,3);
        boardTest.build(2,3);
        boardTest.clear();
        assertTrue(boardTest.getBox(1,1).notWorker());
        assertEquals(0,boardTest.getBox(4,3).getCounter());
        boardTest.build(0,0);
        workerTest.setWorkerId(2);
        boardTest.getBox(0,0).setWorker(workerTest);
        boardTest.clear();
        assertTrue(boardTest.getBox(0,0).notWorker());
        assertEquals(0,boardTest.getBox(0,0).getCounter());

    }

    public void testIsEmpty() {
    }

    public void testTestIsEmpty() {
    }

    public void testGetBox() {
    }
}
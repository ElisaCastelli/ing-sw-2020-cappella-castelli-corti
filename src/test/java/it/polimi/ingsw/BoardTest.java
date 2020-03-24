package it.polimi.ingsw;

import junit.framework.TestCase;

public class BoardTest extends TestCase {
    Board boardTest=new Board();


    public void testBuild() {
        assertNotNull(boardTest.getMatrix());
    }

    public void testClear() {
        Worker workerTest=new Worker(1,boardTest);
        boardTest.getBox(1,1).setWorker(workerTest);
        boardTest.build(2,3);
        boardTest.build(2,3);
        boardTest.clear();
        assertTrue(boardTest.getBox(1,1).notWorker());
        assertEquals(0,boardTest.getBox(2,3).getCounter());

    }

    public void testIsEmpty() {
    }

    public void testTestIsEmpty() {
    }

    public void testGetBox() {
    }
}
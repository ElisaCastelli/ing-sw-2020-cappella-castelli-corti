package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player pTest = new Player("a",10,Game.COLOR.BLU);
    Board boardTest = new Board();
    @Test
    void setName() {
        pTest.setName("b");
        assertEquals("b",pTest.getName());
    }

    @Test
    void setAge() {
        pTest.setAge(21);
        assertEquals(21,pTest.getAge());
    }

    /*@Test
    void getWorkerBox() {
        Worker w = new Worker(1,Game.COLOR.BLU);
        pTest.initializeWorker(1,boardTest.getBox(0,0));
        assertEquals(boardTest.getBox(0,0),pTest.getWorkerBox(1));
    }*/


   /* @Test
    void initializeWorker() {
        int index=0;
        Box b = boardTest.getBox(0,0);
        pTest.initializeWorker(index,b);
        assertEquals(b,pTest.getWorkerBox(index));
    }*/

    /*@Test
    void setPossibleMove() {
        boardTest.clear();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        pTest.setPossibleMove(1);
        for(int index=0; index<8; index++){
            assertTrue(pTest.getWorkerBox(1).getBoxesNextTo().get(index).isReachable());
        }
    }*/

    /*@Test
    void setPossibleBuild() {
        boardTest.clear();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();

        pTest.setPossibleBuild(1);
        assertFalse(pTest.getWorkerBox(1).getBoxesNextTo().get(0).isReachable());
    }*/

   /* @Test
    void playWorker() {
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        pTest.playWorker(1, boardTest.getBox(0,0));
        assertNotEquals(null, boardTest.getBox(0,0).getWorker());
        assertNull(boardTest.getBox(1, 1).getWorker());

        assertFalse(pTest.playWorker(1, boardTest.getBox(3, 3)));
    }

    @Test
    void playBlock() {
        int counter= boardTest.getBox(1,2).getCounter();
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(1, boardTest.getBox(1,1));
        pTest.playBlock(1,boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());
        pTest.playBlock(1,boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());
        assertFalse(pTest.playBlock(1, boardTest.getBox(4,4)));
        pTest.playBlock(1,boardTest.getBox(2,2));
        assertEquals(1, boardTest.getBox(2,2).getCounter());
    }

    @Test
    void checkWin() {
        boardTest.clear();
        pTest.goPlay();
        boardTest.getBox(2,2).build();
        boardTest.getBox(2,2).build();
        boardTest.getBox(2,2).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        pTest.playWorker(1,boardTest.getBox(2,2));
        assertTrue(pTest.checkWin(boardTest.getBox(1, 1), boardTest.getBox(2, 2)));
    }

    @Test
    void checkWorkers() {
        boardTest.clear();
        pTest.initializeWorker(0, boardTest.getBox(0,0));
        pTest.initializeWorker(1, boardTest.getBox(1,0));
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,0).build();
        boardTest.getBox(2,0).build();
        assertFalse(pTest.checkWorkers());
    }

    @Test
    void checkBuilding() {
        pTest.initializeWorker(0,boardTest.getBox(0,0));
        pTest.initializeWorker(1, boardTest.getBox(3,3));
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        assertFalse(pTest.checkBuilding(0));
        assertTrue(pTest.checkBuilding(1));
    }*/
}
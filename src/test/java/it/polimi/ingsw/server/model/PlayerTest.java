package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player pTest = new Player(1, new Timer(), new TimerTask() {
        @Override
        public void run() {
            System.out.println("timer task");
        }
    });
    Board boardTest = new Board();

    @Test
    void setName() {
        pTest.setName("b");
        assertEquals("b", pTest.getName());
    }

    @Test
    void setAge() {
        pTest.setAge(21);
        assertEquals(21, pTest.getAge());
    }

    @Test
    void getWorkerBox() {
        Worker w = new Worker(1);
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 1,1 ), boardTest);
        assertEquals(boardTest.getBox(0, 0), pTest.getWorkerBox(0));
    }


    @Test
    void initializeWorker() {
        int index=0;
        Box b = boardTest.getBox(0,0);
        Box c = boardTest.getBox(1,1);
        pTest.initializeWorker(b,c, boardTest);
        assertEquals(b,pTest.getWorkerBox(index));
    }

    @Test
    void setPossibleMove() {
        boardTest.clear();
        pTest.initializeWorker(boardTest.getBox(1,1), boardTest.getBox(3, 3), boardTest);
        pTest.setPossibleMove(1);
        for(int index=0; index<8; index++){
            assertTrue(pTest.getWorkerBox(1).getBoxesNextTo().get(index).isReachable());
        }
    }

    @Test
    void setPossibleBuild() {
        boardTest.clear();
        pTest.initializeWorker(boardTest.getBox(0, 1), boardTest.getBox( 1,1 ), boardTest);
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();

        pTest.setPossibleBuild(1);
        assertFalse(pTest.getWorkerBox(1).getBoxesNextTo().get(0).isReachable());
    }

   @Test
    void playWorker() {
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(boardTest.getBox(2, 0), boardTest.getBox( 1,1 ), boardTest);
        pTest.playWorker(1, boardTest.getBox(0,0));
        assertNotEquals(null, boardTest.getBox(0,0).getWorker());
        assertNull(boardTest.getBox(1, 1).getWorker());

        assertTrue(pTest.playWorker(1, boardTest.getBox(3, 3)));
    }

    @Test
    void playBlock() {
        int counter= boardTest.getBox(1,2).getCounter();
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 1,1 ), boardTest);
        pTest.playBlock(boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());
        pTest.playBlock(boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());
        assertTrue(pTest.playBlock( boardTest.getBox(4,4)));
        pTest.playBlock(boardTest.getBox(2,2));
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
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 1,1 ), boardTest);
        pTest.playWorker(1,boardTest.getBox(2,2));
        assertTrue(pTest.checkWin(boardTest.getBox(1, 1), boardTest.getBox(2, 2)));
    }

    @Test
    void checkWorkers() {
        boardTest.clear();
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 1,0 ), boardTest);
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
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 3,3 ), boardTest);
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
    }
}
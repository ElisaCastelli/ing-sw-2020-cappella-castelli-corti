package it.polimi.ingsw.server.model.gameComponents;

import it.polimi.ingsw.server.model.god.BasicGod;
import org.junit.jupiter.api.BeforeEach;
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
    Player player = new Player(0, new Timer(), new TimerTask() {
        @Override
        public void run() {
            System.out.println("timer task");
        }
    });
    Board boardTest = new Board();

    @BeforeEach
    void init(){
        pTest.setIndexPlayer(0);
        player.setIndexPlayer(1);
        pTest.setGod(new BasicGod());
        player.setGod(new BasicGod());
        pTest.goPlay();
        player.goPlay();
    }

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
    void setIndexPlayer(){
        pTest.setIndexPlayer(0);
        assertEquals(0, pTest.getIndexPlayer());
        pTest.setIndexClient(2);
        assertEquals(2, pTest.getIndexClient());
        assertTrue(pTest.isPlaying());
        pTest.goWaiting();
    }

    @Test
    void getWorkerBox() {
        pTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox( 1,1 ), boardTest);
        assertEquals(boardTest.getBox(0, 0), pTest.getWorkerBox(0));
    }


    @Test
    void initializeWorker() {
        Box b = boardTest.getBox(0,0);
        Box c = boardTest.getBox(1,1);
        assertTrue(pTest.initializeWorker(b, c, boardTest));
        assertEquals(b, pTest.getWorkersBox().get(0));
        assertEquals(c, pTest.getWorkersBox().get(1));

        Box a = boardTest.getBox(3,3);
        Box d = boardTest.getBox(2, 1);
        assertFalse(player.initializeWorker(b, a, boardTest));
        assertFalse(player.initializeWorker(d, c, boardTest));
    }

    @Test
    void setPossibleMove() {
        boardTest.clear();
        pTest.setGod(new BasicGod());
        assertNotNull(pTest.getGod());
        pTest.goPlay();
        pTest.initializeWorker(boardTest.getBox(1,1), boardTest.getBox(3, 3), boardTest);
        pTest.setPossibleMove(0);
        for(int index = 0; index < 8; index++){
            assertTrue(pTest.getWorkerBox(0).getBoxesNextTo().get(index).isReachable());
        }
    }

    @Test
    void setPossibleBuild() {
        boardTest.clear();
        pTest.goPlay();
        pTest.setGod(new BasicGod());
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
        assertTrue(pTest.amITheWinner());
    }

    @Test
    void checkWorkers() {
        boardTest.clear();
        pTest.goPlay();
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
        pTest.checkWorker(0);
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
        pTest.goPlay();
        assertFalse(pTest.checkBuilding(0));
        assertTrue(pTest.checkBuilding(1));
        pTest.setIndexPossibleBlock(0);
        assertFalse(pTest.canBuildBeforeWorkerMove());
        pTest.clearWorkers();
        assertNull(boardTest.getBox(0,0).getWorker());
        assertNull(boardTest.getBox(3,3).getWorker());
        pTest.goDead();
        assertTrue(pTest.amIDead());
    }
}
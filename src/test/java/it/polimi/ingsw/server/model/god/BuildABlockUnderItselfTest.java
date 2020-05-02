package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildABlockUnderItselfTest {

    @Test
    void setPossibleBuild() {
        God god = new BuildABlockUnderItself(new BasicGod());
        Worker myWorker = new Worker(1, Game.COLOR.BLU);
        Board board = new Board();

        myWorker.initializePos(board.getBox(4,1));
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(4,0).isReachable());
        assertTrue(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());

        assertTrue(god.moveBlock(board.getBox(4,1)));
        assertEquals(1, myWorker.getHeight());

        myWorker.getActualBox().clearBoxesNextTo();
        board.getBox(4,0).clearBoxesNextTo();
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(4,0).isReachable());
        assertTrue(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());

        assertTrue(god.moveBlock(board.getBox(4,1)));
        assertEquals(2, myWorker.getHeight());

        myWorker.getActualBox().clearBoxesNextTo();
        board.getBox(4,0).clearBoxesNextTo();
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(4,0).isReachable());
        assertTrue(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());

        assertTrue(god.moveBlock(board.getBox(4,1)));
        assertEquals(3, myWorker.getHeight());

        myWorker.getActualBox().clearBoxesNextTo();
        board.getBox(4,0).clearBoxesNextTo();
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(4,0).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());
    }

    @Test
    void moveBlock() {
        God god = new BuildABlockUnderItself(new BasicGod());
        Worker myWorker = new Worker(1,Game.COLOR.BLU);
        Board board = new Board();

        myWorker.initializePos(board.getBox(0,0));

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(1, myWorker.getHeight());
        assertEquals(1,board.getBox(0,0).getCounter());

        myWorker.initializePos(board.getBox(0,0));

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(2, myWorker.getHeight());
        assertEquals(2,board.getBox(0,0).getCounter());

        myWorker.initializePos(board.getBox(0,0));

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(3, myWorker.getHeight());
        assertEquals(3,board.getBox(0,0).getCounter());

        god.setPossibleBuild(myWorker);

        assertFalse(board.getBox(0,0).isReachable());

        //mossa normale
        god.moveBlock(board.getBox(0,1));
        assertEquals(1, board.getBox(0,1).getCounter());
    }
}
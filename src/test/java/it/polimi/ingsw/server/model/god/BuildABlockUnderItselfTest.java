package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildABlockUnderItselfTest {

    private God god = new BuildABlockUnderItself(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("BuildABlockUnderItself");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Zeus");
        assertEquals("Zeus", god.getName());
        assertEquals("BuildABlockUnderItself", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleBuild() {
        Worker myWorker = new Worker(1);
        Board board = new Board();

        myWorker.initializePos(board.getBox(4,1),board);
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
        Worker myWorker = new Worker(1);
        Board board = new Board();

        myWorker.initializePos(board.getBox(0,0),board);

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(1, myWorker.getHeight());
        assertEquals(1,board.getBox(0,0).getCounter());

        myWorker.initializePos(board.getBox(0,0),board);

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(2, myWorker.getHeight());
        assertEquals(2,board.getBox(0,0).getCounter());

        myWorker.initializePos(board.getBox(0,0),board);

        assertTrue(god.moveBlock(board.getBox(0,0)));
        assertEquals(3, myWorker.getHeight());
        assertEquals(3,board.getBox(0,0).getCounter());

        god.setPossibleBuild(myWorker);

        assertFalse(board.getBox(0,0).isReachable());

        //mossa normale
        board.getBox(0, 1).build();
        board.getBox(0, 1).build();
        board.getBox(0, 1).build();
        god.moveBlock(board.getBox(0,1));
        assertEquals(4, board.getBox(0,1).getCounter());
    }

    @Test
    void moveWorker() {
        Worker myWorker = new Worker(1);
        Board board = new Board();

        myWorker.initializePos(board.getBox(0,0),board);
        god.setPossibleMove(myWorker);
        assertTrue(god.moveWorker(myWorker, board.getBox(0,1)));
        assertFalse(god.checkWin(board.getBox(0,0), myWorker.getActualBox()));
    }
}
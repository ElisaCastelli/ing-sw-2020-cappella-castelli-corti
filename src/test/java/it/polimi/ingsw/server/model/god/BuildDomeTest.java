package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildDomeTest {

    private God god;
    private Worker worker;
    private Board board;

    @BeforeEach
    void init(){
        god = new BuildDome(new BasicGod());
        ArrayList<String> effects = new ArrayList<>();
        effects.add("BuildDome");
        effects.add("BasicGod");
        god.setEffect(effects);
        worker = new Worker(1);
        board = new Board();
        god.setName("Atlas");

        worker.initializePos(board.getBox(1,1),board);

        board.getBox(0,2).build();

        board.getBox(0,3).build();
        board.getBox(0,3).build();

        board.getBox(1,1).build();
        board.getBox(1,1).build();
        board.getBox(1,1).build();

        board.getBox(1,3).build();
        board.getBox(1,3).build();
        board.getBox(1,3).build();
        board.getBox(1,3).build();
    }

    @Test
    void setPossibleBuild(){
        assertEquals("Atlas", god.getName());
        assertEquals("BuildDome", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));

        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board.getBox(1,2)));
        assertFalse(god.checkWin(board.getBox(1,1), worker.getActualBox()));
        board.getBox(1,1).clearBoxesNextTo();

        god.setPossibleBuild(worker);

        assertTrue(board.getBox(0,1).isReachable());
        assertEquals("Base", board.getBox(0, 1).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(0, 1).getPossibleBlock().get(1).toString());
        assertTrue(board.getBox(0,2).isReachable());
        assertEquals("Middle", board.getBox(0, 2).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(0, 2).getPossibleBlock().get(1).toString());
        assertTrue(board.getBox(0,3).isReachable());
        assertEquals("Top", board.getBox(0, 3).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(0, 3).getPossibleBlock().get(1).toString());
        assertTrue(board.getBox(1,1).isReachable());
        assertEquals("Dome", board.getBox(1, 1).getPossibleBlock().get(0).toString());
        assertFalse(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(2,1).isReachable());
        assertEquals("Base", board.getBox(2, 1).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(2, 1).getPossibleBlock().get(1).toString());
        assertTrue(board.getBox(2,2).isReachable());
        assertEquals("Base", board.getBox(2, 2).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(2, 2).getPossibleBlock().get(1).toString());
        assertTrue(board.getBox(2,3).isReachable());
        assertEquals("Base", board.getBox(2, 3).getPossibleBlock().get(0).toString());
        assertEquals("Dome", board.getBox(2, 3).getPossibleBlock().get(1).toString());
    }

    @Test
    void moveBlock() {
        god.setPossibleMove(worker);
        assertTrue(god.moveWorker(worker, board.getBox(1,2)));
        assertFalse(god.checkWin(board.getBox(1,1), worker.getActualBox()));
        board.getBox(1,1).clearBoxesNextTo();

        god.setIndexPossibleBlock(1);
        god.moveBlock(board.getBox(0,1));
        assertEquals(4, board.getBox(0,1).getCounter());

        god.setIndexPossibleBlock(0);
        god.moveBlock(board.getBox(1,1));
        assertEquals(4, board.getBox(1,1).getCounter());

        board.getBox(0,3).build();
        god.setIndexPossibleBlock(1);
        god.moveBlock(board.getBox(0,3));
        assertEquals(4, board.getBox(0,3).getCounter());
    }
}
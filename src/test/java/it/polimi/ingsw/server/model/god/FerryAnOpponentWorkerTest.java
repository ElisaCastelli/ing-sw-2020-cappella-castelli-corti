package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class FerryAnOpponentWorkerTest {

    private God god = new FerryAnOpponentWorker(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("FerryAnOpponentWorker");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Charon");
        assertEquals("Charon", god.getName());
        assertEquals("FerryAnOpponentWorker", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleMove() {
        Board board = new Board();
        Worker myWorker = new Worker(1);
        myWorker.setIndexPlayer(0);
        Worker oppWorker = new Worker(2);
        oppWorker.setIndexPlayer(1);
        Worker othWorker = new Worker(3);

        myWorker.initializePos(board.getBox(1,3),board);
        oppWorker.initializePos(board.getBox(0,4),board);
        othWorker.initializePos(board.getBox(3,1),board);

        //Controllo su oppWorker
        god.setPossibleMove(oppWorker);

        assertTrue(board.getBox(0,3).isReachable());
        assertFalse(board.getBox(1,3).isReachable());
        assertTrue(board.getBox(1,4).isReachable());

        oppWorker.getActualBox().clearBoxesNextTo();

        //Controllo su myWorker
        board.getBox(2,2).build();
        board.getBox(2,2).build();
        god.setPossibleMove(myWorker);

        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(0,3).isReachable());
        assertTrue(board.getBox(0,4).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,4).isReachable());
        assertFalse(board.getBox(2,2).isReachable());
        assertTrue(board.getBox(2,3).isReachable());
        assertTrue(board.getBox(2,4).isReachable());

        god.moveWorker(myWorker, board.getBox(0,4));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);

        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(0,3).isReachable());
        assertTrue(board.getBox(0,4).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,4).isReachable());
        assertFalse(board.getBox(2,2).isReachable());
        assertTrue(board.getBox(2,3).isReachable());
        assertTrue(board.getBox(2,4).isReachable());

        god.moveWorker(myWorker, board.getBox(1,2));
        myWorker.getActualBox().clearBoxesNextTo();

        //Controllo su othWorker
        god.setPossibleMove(othWorker);

        assertTrue(board.getBox(2,0).isReachable());
        assertTrue(board.getBox(2,1).isReachable());
        assertTrue(board.getBox(2,2).isReachable());
        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(4,0).isReachable());
        assertTrue(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());

        othWorker.getActualBox().clearBoxesNextTo();
        board.getBox(4,0).build();
        board.getBox(4,0).build();
        board.getBox(4,0).build();
        board.getBox(4,0).build();
        god.setPossibleMove(othWorker);

        assertTrue(board.getBox(2,0).isReachable());
        assertTrue(board.getBox(2,1).isReachable());
        assertFalse(board.getBox(2,2).isReachable());
        assertTrue(board.getBox(3,0).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertFalse(board.getBox(4,0).isReachable());
        assertTrue(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,2).isReachable());

        othWorker.getActualBox().clearBoxesNextTo();
    }

    @Test
    void moveWorker() {
        Board board = new Board();
        Board board2 = new Board();
        Worker myWorker = new Worker(1);
        Worker oppWorker = new Worker(2);
        Worker othWorker = new Worker(3);
        Worker worker = new Worker(4);

        myWorker.initializePos(board.getBox(1,3),board);
        oppWorker.initializePos(board.getBox(0,4),board);
        othWorker.initializePos(board.getBox(3,1),board);
        worker.initializePos(board.getBox(2,2), board);

        board.getBox(2,2).build();
        board.getBox(2,2).build();

        assertFalse(god.moveWorker(myWorker, board.getBox(0,4)));
        assertNotNull((board.getBox(2,2).getWorker()));
        assertEquals(2, board.getBox(2,2).getWorker().getWorkerId());
        assertEquals(2, board.getBox(2,2).getWorker().getHeight());
        assertNull(board.getBox(0,4).getWorker());

        assertTrue(god.moveWorker(myWorker, board.getBox(1,2)));
        assertNotNull((board.getBox(1,2).getWorker()));
        assertEquals(1, board.getBox(1,2).getWorker().getWorkerId());
        assertEquals(0, board.getBox(1,2).getWorker().getHeight());
        assertNull(board.getBox(1,3).getWorker());

        //Mossa normale
        assertTrue(god.moveWorker(othWorker, board.getBox(3,2)));
        assertNotNull((board.getBox(3,2).getWorker()));
        assertEquals(3, board.getBox(3,2).getWorker().getWorkerId());
        assertEquals(0, board.getBox(3,2).getWorker().getHeight());
        assertNull(board.getBox(3,1).getWorker());
        assertFalse(god.checkWin(board.getBox(3,1),othWorker.getActualBox()));
        god.setPossibleBuild(othWorker);
        assertTrue(god.moveBlock(board.getBox(3,1)));

        assertFalse(god.moveWorker(worker, board.getBox(1,2)));
        assertTrue(god.moveWorker(worker, board.getBox(1,2)));

        myWorker.initializePos(board2.getBox(2,2),board2);
        myWorker.setIndexPlayer(0);
        oppWorker.initializePos(board2.getBox(1,1),board2);
        oppWorker.setIndexPlayer(1);

        god.setPossibleMove(myWorker);
        assertFalse(god.moveWorker(myWorker, board2.getBox(1,1)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertTrue(god.moveWorker(myWorker, board2.getBox(2,3)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertFalse(god.moveWorker(myWorker, board2.getBox(3,3)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertTrue(god.moveWorker(myWorker, board2.getBox(1,2)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertFalse(god.moveWorker(myWorker, board2.getBox(1,3)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertTrue(god.moveWorker(myWorker,board2.getBox(2,3)));
        myWorker.getActualBox().clearBoxesNextTo();
        othWorker.initializePos(board2.getBox(2,2), board2);
        othWorker.setIndexPlayer(2);
        god.setPossibleMove(myWorker);
        assertFalse(god.moveWorker(myWorker,board2.getBox(2,2)));
        myWorker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(myWorker);
        assertTrue(god.moveWorker(myWorker, board2.getBox(3,3)));
        myWorker.getActualBox().clearBoxesNextTo();
        worker.initializePos(board2.getBox(4,4), board2);
        worker.setIndexPlayer(2);
        god.setPossibleMove(myWorker);
        assertFalse(god.moveWorker(myWorker,board2.getBox(4,4)));
        myWorker.getActualBox().clearBoxesNextTo();
    }
}
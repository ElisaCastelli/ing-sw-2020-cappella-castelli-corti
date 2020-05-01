package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FerryAnOpponentWorkerTest {

    @Test
    void setPossibleMove() {
        God god = new FerryAnOpponentWorker(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1, Game.COLOR.BLU);
        Worker oppWorker = new Worker(2,Game.COLOR.BLU);
        Worker othWorker = new Worker(3,Game.COLOR.BLU);

        myWorker.initializePos(board.getBox(1,3));
        oppWorker.initializePos(board.getBox(0,4));
        othWorker.initializePos(board.getBox(3,1));

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
        God god = new FerryAnOpponentWorker(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1,Game.COLOR.BLU);
        Worker oppWorker = new Worker(2,Game.COLOR.BLU);
        Worker othWorker = new Worker(3,Game.COLOR.BLU);

        myWorker.initializePos(board.getBox(1,3));
        oppWorker.initializePos(board.getBox(0,4));
        othWorker.initializePos(board.getBox(3,1));

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
    }
}
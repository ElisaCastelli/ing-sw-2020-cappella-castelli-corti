package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildNotAlongThePerimeterTest {

    @Test
    void setPossibleBuild() {
        God god = new BuildNotAlongThePerimeter(new BasicGod());
        Worker myWorker = new Worker(1);
        Worker opponentWorker = new Worker(2);
        Board board = new Board();

        myWorker.initializePos(board.getBox(1,1));
        opponentWorker.initializePos(board.getBox(1,2));

        board.getBox(2,1).build();
        board.getBox(2,1).build();
        board.getBox(2,1).build();
        board.getBox(2,1).build();

        //Controllo e prima costruzione
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(0,0).isReachable());
        assertTrue(board.getBox(0,1).isReachable());
        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(1,0).isReachable());
        assertFalse(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(2,0).isReachable());
        assertFalse(board.getBox(2,1).isReachable());
        assertTrue(board.getBox(2,2).isReachable());

        assertFalse(god.moveBlock(board.getBox(2,2)));
        myWorker.getActualBox().clearBoxesNextTo();

        //Secondo controllo e mossa speciale
        god.setPossibleBuild(myWorker);

        assertFalse(board.getBox(0,0).isReachable());
        assertFalse(board.getBox(0,1).isReachable());
        assertFalse(board.getBox(0,2).isReachable());
        assertFalse(board.getBox(1,0).isReachable());
        assertFalse(board.getBox(1,2).isReachable());
        assertFalse(board.getBox(2,0).isReachable());
        assertFalse(board.getBox(2,1).isReachable());
        assertTrue(board.getBox(2,2).isReachable());

        assertTrue(god.moveBlock(board.getBox(2,2)));
        myWorker.getActualBox().clearBoxesNextTo();

        //Provo un'altra set e dovrebbe darmi le reachable come se fosse la prima mossa
        god.setPossibleBuild(myWorker);

        assertTrue(board.getBox(0,0).isReachable());
        assertTrue(board.getBox(0,1).isReachable());
        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(1,0).isReachable());
        assertFalse(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(2,0).isReachable());
        assertFalse(board.getBox(2,1).isReachable());
        assertTrue(board.getBox(2,2).isReachable());

        //Metto un altro worker in (4,4) e riapplico i due controlli e mosse
        Worker opponentWorker2 = new Worker(3);
        opponentWorker2.initializePos(board.getBox(4,4));

        god.setPossibleBuild(opponentWorker2);

        assertTrue(board.getBox(3,3).isReachable());
        assertTrue(board.getBox(3,4).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        assertFalse(god.moveBlock(board.getBox(3,3)));
        opponentWorker2.getActualBox().clearBoxesNextTo();

        god.setPossibleBuild(opponentWorker2);

        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(3,4).isReachable());
        assertFalse(board.getBox(4,3).isReachable());

        assertTrue(god.moveBlock(board.getBox(3,3)));
    }
}
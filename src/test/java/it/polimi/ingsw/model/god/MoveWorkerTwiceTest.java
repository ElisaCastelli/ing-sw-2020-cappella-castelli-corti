package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.gameComponents.Board;
import it.polimi.ingsw.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveWorkerTwiceTest {

    @Test
    void setPossibleMove() {
        God god = new MoveWorkerTwice(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1);
        Worker worker2=new Worker(2);

        myWorker.initializePos(board.getBox(0,1));
        worker2.initializePos(board.getBox(1,1));

        board.getBox(0,0).build();
        board.getBox(0,0).build();

        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(0,0).isReachable());
        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(1,0).isReachable());
        assertFalse(board.getBox(1,1).isReachable());
        assertTrue(board.getBox(1,2).isReachable());

        myWorker.getActualBox().clearBoxesNextTo();
        assertFalse(god.moveWorker(myWorker, board.getBox(0,2)));

        //Seconda mossa speciale, non stesso posto di partenza
        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(0,1).isReachable()); //Posto di partenza
        assertTrue(board.getBox(0,3).isReachable());
        assertFalse(board.getBox(1,1).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,3).isReachable());

        myWorker.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(myWorker, board.getBox(0,3)));

        //Terza mossa consecutiva: deve pensare che sia la prima mossa
        god.setPossibleMove(myWorker);

        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(0,4).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,3).isReachable());
        assertTrue(board.getBox(1,4).isReachable());
    }

    @Test
    void moveWorker() {
        God god = new MoveWorkerTwice(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1);

        myWorker.initializePos(board.getBox(0,1));

        //Prima mossa normale
        assertFalse(god.moveWorker(myWorker, board.getBox(1,0)));
        //Seconda mossa speciale
        assertTrue(god.moveWorker(myWorker, board.getBox(2,0)));
        //Terza mossa consecutiva. è come se fosse la prima
        assertFalse(god.moveWorker(myWorker, board.getBox(3,0)));
    }
}
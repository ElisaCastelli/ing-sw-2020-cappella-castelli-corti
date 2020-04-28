package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.gameComponents.Board;
import it.polimi.ingsw.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveInfinityTimesAlongThePerimeterTest {

    @Test
    void moveWorker() {
        God god = new MoveInfinityTimesAlongThePerimeter(new BasicGod());
        Worker workerToMove = new Worker(1);
        Board board = new Board();

        //Mossa normale
        workerToMove.initializePos(board.getBox(3,3));
        assertTrue(god.moveWorker(workerToMove,board.getBox(2,3)));

        //Mossa speciale
        assertFalse(god.moveWorker(workerToMove,board.getBox(3,4)));
        assertFalse(god.moveWorker(workerToMove,board.getBox(4,4)));
        assertFalse(god.moveWorker(workerToMove,board.getBox(4,3)));
        assertTrue(god.moveWorker(workerToMove,board.getBox(3,3)));
    }
}
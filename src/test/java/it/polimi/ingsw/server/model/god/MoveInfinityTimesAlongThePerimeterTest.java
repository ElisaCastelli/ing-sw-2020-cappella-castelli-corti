package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveInfinityTimesAlongThePerimeterTest {

    private God god = new MoveInfinityTimesAlongThePerimeter(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("MoveInfinityTimesAlongThePerimeter");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Triton");
        assertEquals("Triton", god.getName());
        assertEquals("MoveInfinityTimesAlongThePerimeter", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void moveWorker() {
        Worker workerToMove = new Worker(1);
        Board board = new Board();

        //Mossa normale
        workerToMove.initializePos(board.getBox(3,3),board);
        god.setPossibleMove(workerToMove);
        assertTrue(god.moveWorker(workerToMove,board.getBox(2,3)));
        assertFalse(god.checkWin(board.getBox(3,3), workerToMove.getActualBox()));
        board.getBox(3,3).clearBoxesNextTo();
        god.setPossibleBuild(workerToMove);
        assertTrue(god.moveBlock(board.getBox(2,2)));

        //Mossa speciale
        assertFalse(god.moveWorker(workerToMove,board.getBox(3,4)));
        assertFalse(god.moveWorker(workerToMove,board.getBox(4,4)));
        assertFalse(god.moveWorker(workerToMove,board.getBox(4,3)));
        assertTrue(god.moveWorker(workerToMove,board.getBox(3,3)));
    }
}
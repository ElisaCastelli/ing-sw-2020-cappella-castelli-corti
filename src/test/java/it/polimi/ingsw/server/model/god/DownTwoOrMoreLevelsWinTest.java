package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DownTwoOrMoreLevelsWinTest {

    private God god = new DownTwoOrMoreLevelsWin(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("DownTwoOrMoreLevelsWin");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Pan");
        assertEquals("Pan", god.getName());
        assertEquals("DownTwoOrMoreLevelsWin", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void checkWin() {
        Board board = new Board();
        Worker worker = new Worker(1);

        worker.initializePos(board.getBox(0,0), board);
        god.setPossibleMove(worker);
        god.moveWorker(worker, board.getBox(1,1));
        board.getBox(0,0).clearBoxesNextTo();
        god.setPossibleBuild(worker);
        god.moveBlock(board.getBox(0,1));

        board.getBox(3,3).build();
        board.getBox(3,3).build();
        board.getBox(3,3).build();

        board.getBox(2,2).build();

        board.getBox(2,3).build();
        board.getBox(2,3).build();

        assertFalse(god.checkWin(board.getBox(3,3), board.getBox(2,3))); //Scendo di un livello da 3 a 2
        assertTrue(god.checkWin(board.getBox(3,3), board.getBox(2,2))); //Scendo di due livelli da 3 a 1
        assertTrue(god.checkWin(board.getBox(3,3), board.getBox(3,2))); //Scendo di tre livelli da 3 a 0
        assertFalse(god.checkWin(board.getBox(3,2), board.getBox(2,2))); //Salgo di un livello da 0 a 1
        assertTrue(god.checkWin(board.getBox(2,3), board.getBox(3,3))); //Salgo di un livello da 2 a 3
    }
}
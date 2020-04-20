package it.polimi.ingsw.god;

import it.polimi.ingsw.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DownTwoOrMoreLevelsWinTest {

    @Test
    void checkWin() {
        God god = new DownTwoOrMoreLevelsWin(new BasicGod());
        Board board = new Board();

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
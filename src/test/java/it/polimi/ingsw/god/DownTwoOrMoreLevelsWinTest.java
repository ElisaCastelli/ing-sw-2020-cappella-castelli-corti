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


    }
}
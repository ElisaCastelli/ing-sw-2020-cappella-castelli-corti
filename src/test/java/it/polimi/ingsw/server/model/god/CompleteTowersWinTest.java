package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CompleteTowersWinTest {

    @Test
    void checkWin() {
        God athena = new OpponentBlock(new BasicGod());
        God apollo = new SwitchWorker(new BasicGod());
        God artemis = new MoveWorkerTwice(new BasicGod());
        God minotaur = new ShiftWorker(new SwitchWorker(new BasicGod()));
        God demeter = new OtherPositionToBuild(new BasicGod());
        God hephaestus = new BuildInTheSamePosition(new BasicGod());
        God pan = new DownTwoOrMoreLevelsWin(new BasicGod());
        God prometheus = new BuildBeforeWorkerMove(new BasicGod());
        God cronus = new CompleteTowersWin(new BasicGod());
        God hestia = new BuildNotAlongThePerimeter(new BasicGod());
        God triton = new MoveInfinityTimesAlongThePerimeter(new BasicGod());
        God zeus = new BuildABlockUnderItself(new BasicGod());
        God charon = new FerryAnOpponentWorker(new BasicGod());
        Board board = new Board();
        board.getBox(0,0);

        board.getBox(0,0).build();
        board.getBox(0,0).build();
        board.getBox(0,0).build();
        athena.moveBlock(board.getBox(0,0));
        assertFalse(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        apollo.moveBlock(board.getBox(0,1));
        board.getBox(0,1).build();
        board.getBox(0,1).build();
        apollo.moveBlock(board.getBox(0,1));
        assertFalse(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(0,2).build();
        artemis.moveBlock((board.getBox(0,2)));
        board.getBox(0,2).build();
        artemis.moveBlock(board.getBox(0,2));
        assertFalse(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(0,3).build();
        board.getBox(0,3).build();
        minotaur.moveBlock(board.getBox(0,3));
        minotaur.moveBlock(board.getBox(0,3));
        assertFalse(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        assertFalse(demeter.moveBlock(board.getBox(0,4)));
        assertTrue(demeter.moveBlock(board.getBox(1,4)));
        board.getBox(0,4).build();
        board.getBox(0,4).build();
        assertFalse(demeter.moveBlock(board.getBox(0,4)));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));
        board.getBox(1,4).build();
        board.getBox(1,4).build();
        assertTrue(demeter.moveBlock(board.getBox(1,4)));

        assertFalse(hephaestus.moveBlock(board.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(board.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(board.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(board.getBox(1,3)));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        pan.moveBlock(board.getBox(1,2));
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        pan.moveBlock(board.getBox(1,2));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(1,1).build();
        cronus.moveBlock(board.getBox(1,1));
        board.getBox(1,1).build();
        cronus.moveBlock(board.getBox(1,1));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(1,0).build();
        board.getBox(1,0).build();
        triton.moveBlock(board.getBox(1,0));
        triton.moveBlock(board.getBox(1,0));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        zeus.moveBlock(board.getBox(2,0));
        board.getBox(2,0).build();
        board.getBox(2,0).build();
        zeus.moveBlock(board.getBox(2,0));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(2,1).build();
        charon.moveBlock(board.getBox(2,1));
        board.getBox(2,1).build();
        charon.moveBlock(board.getBox(2,1));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        assertFalse(hestia.moveBlock(board.getBox(2,2)));
        assertTrue(hestia.moveBlock(board.getBox(2,2)));
        assertFalse(hestia.moveBlock(board.getBox(2,2)));
        assertTrue(hestia.moveBlock(board.getBox(2,2)));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        board.getBox(2,4).build();
        board.getBox(2,4).build();
        board.getBox(2,4).build();
        assertFalse(hestia.moveBlock(board.getBox(2,4)));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));

        assertTrue(hestia.moveBlock(board.getBox(2,3)));
        assertTrue(prometheus.moveBlock(board.getBox(2,3)));
        Worker proWorker = new Worker(1, Game.COLOR.BLU);
        proWorker.initializePos(board.getBox(4,0));
        assertTrue(prometheus.moveWorker(proWorker, board.getBox(4,1)));
        assertTrue(prometheus.moveBlock(board.getBox(2,3)));
        assertTrue(prometheus.moveWorker(proWorker, board.getBox(4,2)));
        assertTrue(prometheus.moveBlock(board.getBox(2,3)));
        assertTrue(cronus.checkWin(board.getBox(4,4), board.getBox(4,3)));
    }
}
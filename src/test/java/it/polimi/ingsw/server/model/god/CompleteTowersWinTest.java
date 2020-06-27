package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CompleteTowersWinTest {

    private God cronus = new CompleteTowersWin(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("CompleteTowersWin");
        effects.add("BasicGod");
        cronus.setEffect(effects);
        cronus.setName("Cronus");
        assertEquals("Cronus", cronus.getName());
        assertEquals("CompleteTowersWin", cronus.getEffects().get(0));
        assertEquals("BasicGod", cronus.getEffects().get(1));
    }

    @Test
    void checkWin(){
        God athena = new OpponentBlock(new BasicGod());
        God apollo = new SwitchWorker(new BasicGod());
        God artemis = new MoveWorkerTwice(new BasicGod());
        God minotaur = new ShiftWorker(new SwitchWorker(new BasicGod()));
        God demeter = new OtherPositionToBuild(new BasicGod());
        God hephaestus = new BuildInTheSamePosition(new BasicGod());
        God pan = new DownTwoOrMoreLevelsWin(new BasicGod());
        God prometheus = new BuildBeforeWorkerMove(new BasicGod());
        God hestia = new BuildNotAlongThePerimeter(new BasicGod());
        God triton = new MoveInfinityTimesAlongThePerimeter(new BasicGod());
        God zeus = new BuildABlockUnderItself(new BasicGod());
        God charon = new FerryAnOpponentWorker(new BasicGod());
        Board boardGame = new Board();

        boardGame.getBox(0,0).build();
        boardGame.getBox(0,0).build();
        boardGame.getBox(0,0).build();
        athena.moveBlock(boardGame.getBox(0,0));
        assertFalse(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        apollo.moveBlock(boardGame.getBox(0,1));
        boardGame.getBox(0,1).build();
        boardGame.getBox(0,1).build();
        apollo.moveBlock(boardGame.getBox(0,1));
        assertFalse(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(0,2).build();
        artemis.moveBlock((boardGame.getBox(0,2)));
        boardGame.getBox(0,2).build();
        artemis.moveBlock(boardGame.getBox(0,2));
        assertFalse(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(0,3).build();
        boardGame.getBox(0,3).build();
        minotaur.moveBlock(boardGame.getBox(0,3));
        minotaur.moveBlock(boardGame.getBox(0,3));
        assertFalse(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        assertFalse(demeter.moveBlock(boardGame.getBox(0,4)));
        assertTrue(demeter.moveBlock(boardGame.getBox(1,4)));
        boardGame.getBox(0,4).build();
        boardGame.getBox(0,4).build();
        assertFalse(demeter.moveBlock(boardGame.getBox(0,4)));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));
        boardGame.getBox(1,4).build();
        boardGame.getBox(1,4).build();
        assertTrue(demeter.moveBlock(boardGame.getBox(1,4)));

        assertFalse(hephaestus.moveBlock(boardGame.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(boardGame.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(boardGame.getBox(1,3)));
        assertTrue(hephaestus.moveBlock(boardGame.getBox(1,3)));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        pan.moveBlock(boardGame.getBox(1,2));
        boardGame.getBox(1,2).build();
        boardGame.getBox(1,2).build();
        pan.moveBlock(boardGame.getBox(1,2));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(1,1).build();
        cronus.moveBlock(boardGame.getBox(1,1));
        boardGame.getBox(1,1).build();
        cronus.moveBlock(boardGame.getBox(1,1));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(1,0).build();
        boardGame.getBox(1,0).build();
        triton.moveBlock(boardGame.getBox(1,0));
        triton.moveBlock(boardGame.getBox(1,0));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        zeus.moveBlock(boardGame.getBox(2,0));
        boardGame.getBox(2,0).build();
        boardGame.getBox(2,0).build();
        zeus.moveBlock(boardGame.getBox(2,0));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(2,1).build();
        charon.moveBlock(boardGame.getBox(2,1));
        boardGame.getBox(2,1).build();
        charon.moveBlock(boardGame.getBox(2,1));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        assertFalse(hestia.moveBlock(boardGame.getBox(2,2)));
        assertTrue(hestia.moveBlock(boardGame.getBox(2,2)));
        assertFalse(hestia.moveBlock(boardGame.getBox(2,2)));
        assertTrue(hestia.moveBlock(boardGame.getBox(2,2)));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        boardGame.getBox(2,4).build();
        boardGame.getBox(2,4).build();
        boardGame.getBox(2,4).build();
        assertFalse(hestia.moveBlock(boardGame.getBox(2,4)));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        assertTrue(hestia.moveBlock(boardGame.getBox(2,3)));
        assertTrue(prometheus.moveBlock(boardGame.getBox(2,3)));
        Worker proWorker = new Worker(1);
        proWorker.initializePos(boardGame.getBox(4,0),boardGame);
        assertTrue(prometheus.moveWorker(proWorker, boardGame.getBox(4,1)));
        assertTrue(prometheus.moveBlock(boardGame.getBox(2,3)));
        assertTrue(prometheus.moveWorker(proWorker, boardGame.getBox(4,2)));
        assertTrue(prometheus.moveBlock(boardGame.getBox(2,3)));
        assertTrue(cronus.checkWin(boardGame.getBox(4,4), boardGame.getBox(4,3)));

        cronus.setPossibleMove(proWorker);
        assertTrue(cronus.moveWorker(proWorker, boardGame.getBox(4,3)));
        boardGame.getBox(4,2).clearBoxesNextTo();
        cronus.setPossibleBuild(proWorker);
    }
}
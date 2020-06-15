package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class NotMoveUpTest {

    private God god = new NotMoveUp(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("NotMoveUp");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("God");
        assertEquals("God", god.getName());
        assertEquals("NotMoveUp", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleMove() {
        God opponentGod = new OpponentBlock(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1);
        myWorker.setIndexPlayer(0);
        Worker opponentWorker = new Worker(2);
        myWorker.setIndexPlayer(1);

        myWorker.initializePos(board.getBox(4,2), board);

        board.getBox(3,1).build();
        opponentWorker.setActualBox(board.getBox(3,1));
        opponentWorker.setHeight(board.getBox(3,1).getCounter());
        board.getBox(3,1).setWorker(opponentWorker);

        board.getBox(3,2).build();

        board.getBox(3,3).build();

        board.getBox(4,1).build();
        board.getBox(4,1).build();
        //Set normale: Atena non si è ancora mossa
        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena si alza di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(4,1));
        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertFalse(board.getBox(3,2).isReachable());
        assertFalse(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena scende di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(3,1));
        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());
        board.getBox(4,2).clearBoxesNextTo();

        //Prova con gli effetti del Minotauro
        God myGod2 = new NotMoveUp(new ShiftWorker(new SwitchWorker(new BasicGod())));
        Worker opponentWorker2 = new Worker (3);
        opponentWorker2.initializePos(board.getBox(4,3),board);

        //Set normale: Atena non si è ancora mossa
        myGod2.setPossibleMove(myWorker);

        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena si alza di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(4,1));
        myGod2.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertFalse(board.getBox(3,2).isReachable());
        assertFalse(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena scende di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(3,1));
        myGod2.setPossibleMove(myWorker);

        assertTrue(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());
        board.getBox(4,2).clearBoxesNextTo();
    }

    @Test
    void moveWorker() {
        Board board = new Board();
        Worker myWorker = new Worker(1);

        myWorker.initializePos(board.getBox(0,0), board);

        assertTrue(god.moveWorker(myWorker, board.getBox(1,1)));
        assertFalse(god.checkWin(board.getBox(0,0), myWorker.getActualBox()));
        god.setPossibleBuild(myWorker);
        assertTrue(god.moveBlock(board.getBox(1,2)));
    }
}
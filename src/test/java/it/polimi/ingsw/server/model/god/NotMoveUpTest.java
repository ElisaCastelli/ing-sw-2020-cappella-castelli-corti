package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotMoveUpTest {

    @Test
    void setPossibleMove() {
        God myGod = new NotMoveUp(new BasicGod());
        God opponentGod = new OpponentBlock(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1);
        Worker opponentWorker = new Worker(2);

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
        myGod.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertTrue(board.getBox(3,2).isReachable());
        assertTrue(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena si alza di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(4,1));
        myGod.setPossibleMove(myWorker);

        assertFalse(board.getBox(3,1).isReachable());
        assertFalse(board.getBox(3,2).isReachable());
        assertFalse(board.getBox(3,3).isReachable());
        assertFalse(board.getBox(4,1).isReachable());
        assertTrue(board.getBox(4,3).isReachable());

        //Atena scende di un livello
        board.getBox(4,2).clearBoxesNextTo();
        opponentGod.moveWorker(opponentWorker, board.getBox(3,1));
        myGod.setPossibleMove(myWorker);

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
        //Forse non serve fare questo test
    }
}
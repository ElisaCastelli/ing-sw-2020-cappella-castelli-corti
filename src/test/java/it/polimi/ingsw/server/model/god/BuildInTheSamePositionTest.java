package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuildInTheSamePositionTest {

    @Test
    void setPossibleBuild() {
        God god=new BuildInTheSamePosition(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1, Game.COLOR.BLU);
        //Controllo e costruisco livello 1 e 2
        myWorker.initializePos(board.getBox(2, 4));
        god.setPossibleBuild(myWorker);

        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(0).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(1).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(2));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(3).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(4));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(5).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(6).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(7));
        assertFalse(god.moveBlock(board.getBox(1,4)));

        board.getBox(2,4).clearBoxesNextTo();

        god.setPossibleBuild(myWorker);

        assertFalse(myWorker.getActualBox().getBoxesNextTo().get(0).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(1).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(2));
        assertFalse(myWorker.getActualBox().getBoxesNextTo().get(3).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(4));
        assertFalse(myWorker.getActualBox().getBoxesNextTo().get(5).isReachable());
        assertFalse(myWorker.getActualBox().getBoxesNextTo().get(6).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(7));
        assertTrue(god.moveBlock(board.getBox(1,4)));

        board.getBox(2,4).clearBoxesNextTo();

        //Controllo e costruisco solo livello 3, perchè il 4 non può
        god.setPossibleBuild(myWorker);

        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(0).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(1).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(2));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(3).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(4));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(5).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(6).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(7));
        assertTrue(god.moveBlock(board.getBox(1,4)));

        board.getBox(2,4).clearBoxesNextTo();

        //Controllo e costruisco livello 4 come prima mossa
        god.setPossibleBuild(myWorker);
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(0).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(1).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(2));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(3).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(4));
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(5).isReachable());
        assertTrue(myWorker.getActualBox().getBoxesNextTo().get(6).isReachable());
        assertNull(myWorker.getActualBox().getBoxesNextTo().get(7));
        assertEquals(3, myWorker.getActualBox().getBoxesNextTo().get(1).getCounter());
        assertTrue(god.moveBlock(board.getBox(1,4)));
        assertEquals(4, myWorker.getActualBox().getBoxesNextTo().get(1).getCounter());
    }

    @Test
    void moveBlock() {
        God god=new BuildInTheSamePosition(new BasicGod());
        Board board = new Board();
        Worker myWorker = new Worker(1,Game.COLOR.BLU);

        //Costruisco livello 1 e 2
        myWorker.initializePos(board.getBox(3, 1));

        assertFalse(god.moveBlock(board.getBox(2,2)));

        assertTrue(god.moveBlock(board.getBox(2,2)));

        //Costruisco livello 2 e 3
        board.getBox(4,1).build();
        god.setPossibleBuild(myWorker);

        assertFalse(god.moveBlock(board.getBox(4,1)));

        board.getBox(3,1).clearBoxesNextTo();
        god.setPossibleBuild(myWorker);

        assertTrue(god.moveBlock(board.getBox(4,1)));

        board.getBox(3,1).clearBoxesNextTo();

        //Costruisco livello 3 come prima mossa
        assertTrue(god.moveBlock(board.getBox(2,2)));
    }
}
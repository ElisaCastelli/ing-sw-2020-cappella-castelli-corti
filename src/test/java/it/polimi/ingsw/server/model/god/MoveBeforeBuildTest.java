package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoveBeforeBuildTest {

    @Test
    void moveBlock() {
        God god = new MoveBeforeBuild(new BasicGod());
        Worker myWorker = new Worker(1);
        Board board = new Board();

        myWorker.initializePos(board.getBox(2,2),board);
        //Prima mossa costruzione
        assertTrue(god.moveBlock(board.getBox(0,0)));

        //Prima mossa worker, poi costruzione
        assertTrue(god.moveWorker(myWorker, board.getBox(2,3)));
        assertTrue(god.moveBlock(board.getBox(2,4)));

        God god2 = new MoveBeforeBuild(new BuildInTheSamePosition(new BasicGod()));
        //Prima mossa worker, poi costruzione con mossa speciale di costruire due volte nella stessa casella
        assertTrue(god2.moveWorker(myWorker, board.getBox(2,3)));
        assertFalse(god2.moveBlock(board.getBox(2,4)));
        assertTrue(god2.moveBlock(board.getBox(2,4)));
    }
}
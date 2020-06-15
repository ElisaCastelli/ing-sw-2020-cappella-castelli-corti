package it.polimi.ingsw.server.model.god;


import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MoveWorkerTwiceTest {

    private God god = new MoveWorkerTwice(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("MoveWorkerTwice");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Artemis");
        assertEquals("Artemis", god.getName());
        assertEquals("MoveWorkerTwice", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleMove() {
        Board board = new Board();
        Worker myWorker = new Worker(1);
        Worker worker2=new Worker(2);
        assertFalse(god.canBuildBeforeWorkerMove());

        myWorker.initializePos(board.getBox(0,1),board);
        worker2.initializePos(board.getBox(1,1),board);

        board.getBox(0,0).build();
        board.getBox(0,0).build();

        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(0,0).isReachable());
        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(1,0).isReachable());
        assertFalse(board.getBox(1,1).isReachable());
        assertTrue(board.getBox(1,2).isReachable());

        myWorker.getActualBox().clearBoxesNextTo();
        assertFalse(god.moveWorker(myWorker, board.getBox(0,2)));

        //Seconda mossa speciale, non stesso posto di partenza
        god.setPossibleMove(myWorker);

        assertFalse(board.getBox(0,1).isReachable()); //Posto di partenza
        assertTrue(board.getBox(0,3).isReachable());
        assertFalse(board.getBox(1,1).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,3).isReachable());

        myWorker.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(myWorker, board.getBox(0,3)));

        //Terza mossa consecutiva: deve pensare che sia la prima mossa
        god.setPossibleMove(myWorker);

        assertTrue(board.getBox(0,2).isReachable());
        assertTrue(board.getBox(0,4).isReachable());
        assertTrue(board.getBox(1,2).isReachable());
        assertTrue(board.getBox(1,3).isReachable());
        assertTrue(board.getBox(1,4).isReachable());
    }

    @Test
    void moveWorker() {
        Board board = new Board();
        Worker myWorker = new Worker(1);

        myWorker.initializePos(board.getBox(0,1),board);

        //Prima mossa normale
        assertFalse(god.moveWorker(myWorker, board.getBox(1,0)));
        assertFalse(god.checkWin(board.getBox(0,1), myWorker.getActualBox()));
        //Seconda mossa speciale
        assertTrue(god.moveWorker(myWorker, board.getBox(2,0)));
        //Terza mossa consecutiva. è come se fosse la prima
        assertFalse(god.moveWorker(myWorker, board.getBox(3,0)));

        god.setPossibleBuild(myWorker);
        assertTrue(god.moveBlock(board.getBox(3,1)));
    }
}
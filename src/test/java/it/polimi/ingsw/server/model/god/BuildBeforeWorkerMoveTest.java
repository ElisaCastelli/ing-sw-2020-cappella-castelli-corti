package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BuildBeforeWorkerMoveTest {

    private God god = new BuildBeforeWorkerMove(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("BuildBeforeWorkerMove");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Prometheus");
        assertEquals("Prometheus", god.getName());
        assertEquals("BuildBeforeWorkerMove", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleMove() {
        Worker worker = new Worker(1);
        Worker worker2 = new Worker(2);
        Worker worker3 = new Worker(3);
        Board board = new Board();

        worker.initializePos(board.getBox(0,1),board);
        worker2.initializePos(board.getBox(1,2),board);
        worker3.initializePos(board.getBox(3,3),board);
        assertTrue(god.canBuildBeforeWorkerMove());

        //build-move-build
        System.out.println("build-move-build");
        god.setPossibleBuild(worker3);
        worker3.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveBlock(board.getBox(2,3)));
        System.out.println("build in 2,3");
        System.out.println("counter:"+board.getBox(2,3).getCounter());

        god.setPossibleMove(worker3);
        worker3.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker3,board.getBox(2,4)));
        System.out.println("can build everywhere");
        god.setPossibleBuild(worker3);
        worker3.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveBlock(board.getBox(2,3)));


        //move-build
        System.out.println("move-build");

        god.setPossibleMove(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker2,board.getBox(0,2)));
        assertFalse(god.checkWin(board.getBox(1,2), worker2.getActualBox()));
        System.out.println("moving in 0,2 with high"+board.getBox(0,2).getCounter());
        System.out.println("can build everywhere");
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveBlock(board.getBox(1,3)));
    }

    @Test
    void moveBlock() {
        Worker worker = new Worker(1);
        Worker worker2 = new Worker(2);
        Worker worker3 = new Worker(3);
        Board board = new Board();

        worker.initializePos(board.getBox(0,1),board);
        worker2.initializePos(board.getBox(1,2),board);
        worker3.initializePos(board.getBox(3,3),board);

        System.out.println("build-move-build");
        System.out.println("build in 2,3");

        assertTrue(god.moveBlock(board.getBox(2,3)));
        System.out.println("counter:"+board.getBox(2,3).getCounter());

        assertTrue(god.moveWorker(worker3,board.getBox(2,4)));
        System.out.println("moving in 2,4 with high"+board.getBox(2,4).getCounter());

        System.out.println("can build everywhere");
        System.out.println("build in 2,3");
        assertTrue(god.moveBlock(board.getBox(2,3)));

        System.out.println("move-build");

        assertTrue(god.moveWorker(worker2,board.getBox(0,2)));
        System.out.println("moving in 0,2 with high"+board.getBox(0,2).getCounter());
        System.out.println("can build everywhere");
        System.out.println("build in 1,3");
        assertTrue(god.moveBlock(board.getBox(1,3)));

        System.out.println("build-move-build");
        System.out.println("build in 1,1");
        assertTrue(god.moveBlock(board.getBox(1,1)));
        System.out.println("counter:"+board.getBox(1,1).getCounter());

        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        assertTrue(god.moveWorker(worker,board.getBox(1,0)));
        System.out.println("moving in 1,0 with high"+board.getBox(2,4).getCounter());

        System.out.println("can build everywhere");
        System.out.println("build in 2,1");
        assertTrue(god.moveBlock(board.getBox(2,1)));
        assertEquals(1,board.getBox(2,1).getCounter());

        System.out.println("move-build");
        assertTrue(god.moveWorker(worker2,board.getBox(1,1)));
        System.out.println("moving in 1,1 with high"+board.getBox(1,1).getCounter());
        assertEquals(1,board.getBox(1,1).getCounter());
        System.out.println("can build everywhere");
        System.out.println("build in 2,1");
        board.getBox(2,1).build();
        board.getBox(2,1).build();
        assertTrue(god.moveBlock(board.getBox(2,1)));
        assertEquals(4,board.getBox(2,1).getCounter());
        assertEquals(2,board.getBox(1,1).getWorker().getWorkerId());
    }
}
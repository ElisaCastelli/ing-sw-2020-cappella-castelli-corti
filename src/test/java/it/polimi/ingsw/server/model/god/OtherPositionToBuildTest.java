package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OtherPositionToBuildTest {

    @Test
    void setPossibleBuild() {
        //DEMETRA
        God god=new OtherPositionToBuild(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();

        worker.initializePos(board.getBox(0,1),board);
        worker2.initializePos(board.getBox(1,1),board);
        worker3.initializePos(board.getBox(3,3),board);

        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker3);
        worker3.getActualBox().clearBoxesNextTo();

        board.getBox(0,0).build();
        board.getBox(0,0).build();
        board.getBox(0,0).build();
        board.getBox(0,0).build();
        assertEquals(4,board.getBox(0,0).getCounter());

        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();

        //second move but not in the same position
        assertFalse(god.moveBlock(board.getBox(1,2)));
        System.out.println("build in 1,2 with counter-:"+ board.getBox(1,2).getCounter());
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        System.out.println("il worker 2");
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        System.out.println("now in 1,2 i can't build");
    }

    @Test
    void moveBlock() {
        God god=new OtherPositionToBuild(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();
        worker.initializePos(board.getBox(0,1),board);
        worker2.initializePos(board.getBox(1,1),board);
        worker3.initializePos(board.getBox(3,3),board);

        //second move
        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(1,board.getBox(1,2).getCounter());
        System.out.println("build in 1,2 with counter-:"+ board.getBox(1,2).getCounter());
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        System.out.println("worker 2");
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        System.out.println("now in 1,2 i can't build");

        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(1,board.getBox(1,2).getCounter());

        assertTrue(god.moveBlock(board.getBox(0,2)));
        assertEquals(1,board.getBox(0,2).getCounter());

        //TERZA MOSSA PER VEDERE COSA SUCCEDE=>MI FA RIFARE LA MOSSA IN 1,2 PERCHE' HA RESETTATO FIRSTTIME
        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(2,board.getBox(1,2).getCounter());


    }
}
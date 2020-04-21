package it.polimi.ingsw.god;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Worker;
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

        worker.initializePos(board.getBox(0,1));
        worker2.initializePos(board.getBox(1,1));
        worker3.initializePos(board.getBox(3,3));

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

        //SECONDA MOSSA NON DOVE PRIMA
        assertFalse(god.moveBlock(board.getBox(1,2)));
        System.out.println("ho costruito in 1,2 la-:"+ board.getBox(1,2).getCounter());
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        System.out.println("il worker 2");
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        System.out.println("ora nella 1,2 non posso costruire");
    }

    @Test
    void moveBlock() {
        God god=new OtherPositionToBuild(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();
        worker.initializePos(board.getBox(0,1));
        worker2.initializePos(board.getBox(1,1));
        worker3.initializePos(board.getBox(3,3));

        //SECONDA MOSSA
        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(1,board.getBox(1,2).getCounter());
        System.out.println("ho costruito in 1,2 la-:"+ board.getBox(1,2).getCounter());
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        System.out.println("il worker 2");
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        System.out.println("ora nella 1,2 non posso costruire");

        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(1,board.getBox(1,2).getCounter());

        assertTrue(god.moveBlock(board.getBox(0,2)));
        assertEquals(1,board.getBox(0,2).getCounter());

        //TERZA MOSSA PER VEDERE COSA SUCCEDE=>MI FA RIFARE LA MOSSA IN 1,2 PERCHE' HA RESETTATO FIRSTTIME
        assertFalse(god.moveBlock(board.getBox(1,2)));
        assertEquals(2,board.getBox(1,2).getCounter());


    }
}
package it.polimi.ingsw.model.god;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Worker;

//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BasicGodTest {

    @Test
    void setPossibleMove() {
        God BGTest = new BasicGod();
        Worker workerToMove = new Worker(1);
        Board board = new Board();

        board.getBox(0,2 ).build();
        workerToMove.setActualBox(board.getBox(0,2));
        workerToMove.setHeight(board.getBox(0,2).getCounter());
        board.getBox(0,2).setWorker(workerToMove);

        board.getBox(0,3).build();
        Worker opponentWorker = new Worker(2);
        opponentWorker.setActualBox(board.getBox(0,3));
        opponentWorker.setHeight(board.getBox(0,3).getCounter());
        board.getBox(0,3).setWorker(opponentWorker);

        board.getBox(1,1).build();
        board.getBox(1,1).build();

        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();

        board.getBox(1,3).build();
        board.getBox(1,3).build();
        board.getBox(1,3).build();

        BGTest.setPossibleMove( workerToMove );
        board.getBox(0,2).clearBoxesNextTo();

        System.out.println("Altro esempio");

        board.getBox(2,2).build();
        board.getBox(2,2).build();
        Worker worker = new Worker(2);
        worker.setActualBox(board.getBox(2,2));
        worker.setHeight(board.getBox(2,2).getCounter());
        board.getBox(2,2).setWorker(worker);

        board.getBox(2,3).build();

        Worker opponentWorker2 = new Worker(2);
        opponentWorker2.setActualBox(board.getBox(3,1));
        opponentWorker2.setHeight(board.getBox(3,1).getCounter());
        board.getBox(3,1).setWorker(opponentWorker2);

        BGTest.setPossibleMove( worker );
    }

    @Test
    void setPossibleBuild() {
        God BGTest = new BasicGod();
        Worker myWorker = new Worker(1);
        Board board = new Board();

        board.getBox(0,1).build();

        board.getBox(0,2).build();
        board.getBox(0,2).build();

        board.getBox(1,0).build();
        board.getBox(1,0).build();
        board.getBox(1,0).build();

        myWorker.setActualBox(board.getBox(1,1));
        myWorker.setHeight(board.getBox(1,1).getCounter());
        board.getBox(1,1).setWorker(myWorker);

        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();
        board.getBox(1,2).build();

        Worker opponentWorker = new Worker(2);
        board.getBox(2,0).build();
        board.getBox(2,0).build();
        board.getBox(2,0).build();
        opponentWorker.setActualBox(board.getBox(2,0));
        opponentWorker.setHeight(board.getBox(2,0).getCounter());
        board.getBox(2,0).setWorker(opponentWorker);

        board.getBox(2,1).build();
        Worker opponentWorker2 = new Worker(2);
        opponentWorker2.setActualBox(board.getBox(2,1));
        opponentWorker2.setHeight(board.getBox(2,1).getCounter());
        board.getBox(2,1).setWorker(opponentWorker2);

        board.getBox(2,2).build();
        board.getBox(2,2).build();
        Worker opponentWorker3 = new Worker(2);
        opponentWorker3.setActualBox(board.getBox(2,2));
        opponentWorker3.setHeight(board.getBox(2,2).getCounter());
        board.getBox(2,2).setWorker(opponentWorker3);

        BGTest.setPossibleBuild( myWorker );
        board.getBox(1,1).clearBoxesNextTo();

        System.out.println("Altro esempio");

        board.getBox(3,0).build();
        board.getBox(3,0).build();

        board.getBox(3,1).build();

        BGTest.setPossibleBuild( opponentWorker );
    }

    @Test
    void moveWorker() {
        God BGTest = new BasicGod();
        Worker myWorker = new Worker(1);
        Board board = new Board();

        board.getBox(1,2).build();

        board.getBox(2,2).build();
        board.getBox(2,2).build();
        myWorker.setActualBox(board.getBox(2,2));
        myWorker.setHeight(board.getBox(2,2).getCounter());
        board.getBox(2,2).setWorker(myWorker);
        //Scendo di due livelli
        BGTest.moveWorker( myWorker, board.getBox(1,1));

        assertEquals(1,board.getBox(1,1).getWorker().getWorkerId());
        assertFalse(board.getBox(1, 1).notWorker());
        assertEquals(0, myWorker.getHeight());
        assertEquals(board.getBox(1,1), myWorker.getActualBox());
        assertNull(board.getBox(2,2).getWorker());
        assertTrue(board.getBox(2, 2).notWorker());

        //Salgo di un livello dal nuovo punto di partenza (1,1)
        BGTest.moveWorker( myWorker, board.getBox(1,2));

        assertEquals(1,board.getBox(1,2).getWorker().getWorkerId());
        assertFalse(board.getBox(1, 2).notWorker());
        assertEquals(1, myWorker.getHeight());
        assertEquals(board.getBox(1,2), myWorker.getActualBox());
        assertNull(board.getBox(1,1).getWorker());
        assertTrue(board.getBox(1, 1).notWorker());
    }

    @Test
    void moveBlock() {
        God BGTest = new BasicGod();
        Board board = new Board();

        BGTest.moveBlock(board.getBox(1,2));

        assertEquals(1, board.getBox(1,2).getCounter());
    }

    @Test
    void checkWin() {
        God BGTest = new BasicGod();
        Board board = new Board();

        board.getBox(3,2).build();

        board.getBox(3,1).build();
        board.getBox(3,1).build();

        assertFalse( BGTest.checkWin(board.getBox(3,2), board.getBox(3,1)) );

        board.getBox(2,2).build();
        board.getBox(2,2).build();
        board.getBox(2,2).build();

        assertTrue( BGTest.checkWin(board.getBox(3,1), board.getBox(2,2)) );
    }
}
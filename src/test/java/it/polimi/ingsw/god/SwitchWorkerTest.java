package it.polimi.ingsw.god;

import it.polimi.ingsw.Board;
import it.polimi.ingsw.Box;
import it.polimi.ingsw.Player;
import it.polimi.ingsw.Worker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SwitchWorkerTest {

    @Test
    void setPossibleMove() {
        God god=new SwitchWorker(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Board board = new Board();
        board.setBoxesNext();
        worker.initializePos(board.getBox(1,1));
        worker2.initializePos(board.getBox(0,1));

        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleMove(worker2);
        worker2.getActualBox().clearBoxesNextTo();
    }

    @Test
    void setPossibleBuild() {
        God god=new SwitchWorker(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Board board = new Board();
        board.setBoxesNext();
        worker.initializePos(board.getBox(1,1));
        worker2.initializePos(board.getBox(0,1));

        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
    }

    @Test
    void moveWorker() {
        God god=new SwitchWorker(new BasicGod());
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
        Board board = new Board();
        board.setBoxesNext();
        worker.initializePos(board.getBox(1,1));
        worker2.initializePos(board.getBox(0,1));
        worker3.initializePos(board.getBox(3,3));
        //MOSSA NORMALE VERSO UN'ALTRA CASELLA
        god.moveWorker(worker3,board.getBox(2,3));
        assertEquals(3,board.getBox(2,3).getWorker().getWorkerId());
        assertNull(board.getBox(3,3).getWorker());
        assertEquals(true,board.getBox(3,3).notWorker());
        //god.setPossibleMove(worker3);
        //worker3.getActualBox().clearBoxesNextTo();

        //SWITCH
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.moveWorker(worker,board.getBox(0,1));
        assertEquals(1,board.getBox(0,1).getWorker().getWorkerId());
        assertEquals(2,board.getBox(1,1).getWorker().getWorkerId());
        assertEquals(false,board.getBox(0,1).notWorker());
        assertEquals(false,board.getBox(1,1).notWorker());
        //CONTROLLO CHE NON POSSO COSTRUIRE INTORNO PER VEDERE SE HA FATTO CASINO CHE GLI WORKER
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker3);
        worker3.getActualBox().clearBoxesNextTo();
    }

}
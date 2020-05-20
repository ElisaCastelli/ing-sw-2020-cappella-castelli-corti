package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.god.SwitchWorker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwitchWorkerTest {

    @Test
    void setPossibleMove() {
        God god=new SwitchWorker(new BasicGod());
        Worker worker=new Worker(1,Game.COLOR.BLU);
        Worker worker2=new Worker(2,Game.COLOR.ORANGE);
        Board board = new Board();

        worker.initializePos(board.getBox(1,1),board);
        worker2.initializePos(board.getBox(0,1),board);

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
        Worker worker=new Worker(1,Game.COLOR.BLU);
        Worker worker2=new Worker(2,Game.COLOR.ORANGE);
        Board board = new Board();

        worker.initializePos(board.getBox(1,1),board);
        worker2.initializePos(board.getBox(0,1),board);

        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
    }

    @Test
    void moveWorker() {
        God god=new SwitchWorker(new BasicGod());
        Worker worker=new Worker(1,Game.COLOR.BLU);
        Worker worker2=new Worker(2,Game.COLOR.ORANGE);
        Worker worker3=new Worker(3,Game.COLOR.RED);
        Board board = new Board();

        worker.initializePos(board.getBox(1,1),board);
        worker2.initializePos(board.getBox(0,1),board);
        worker3.initializePos(board.getBox(3,3),board);
        //MOSSA NORMALE VERSO UN'ALTRA CASELLA
        god.moveWorker(worker3,board.getBox(2,3));
        assertEquals(3,board.getBox(2,3).getWorker().getWorkerId());
        assertNull(board.getBox(3,3).getWorker());
        assertTrue(board.getBox(3, 3).notWorker());
        //god.setPossibleMove(worker3);
        //worker3.getActualBox().clearBoxesNextTo();

        //SWITCH
        god.setPossibleMove(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.moveWorker(worker,board.getBox(0,1));
        assertEquals(1,board.getBox(0,1).getWorker().getWorkerId());
        assertEquals(2,board.getBox(1,1).getWorker().getWorkerId());
        assertFalse(board.getBox(0, 1).notWorker());
        assertFalse(board.getBox(1, 1).notWorker());

        //CONTROLLO CHE NON POSSO COSTRUIRE INTORNO PER VEDERE SE HA FATTO CASINO CHE GLI WORKER
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker3);
        worker3.getActualBox().clearBoxesNextTo();
    }
}
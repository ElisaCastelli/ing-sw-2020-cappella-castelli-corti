package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SwitchWorkerTest {

    private God god = new SwitchWorker(new BasicGod());

    @BeforeEach
    void init(){
        ArrayList<String> effects = new ArrayList<>();
        effects.add("SwitchWorker");
        effects.add("BasicGod");
        god.setEffect(effects);
        god.setName("Apollo");
        assertEquals("Apollo", god.getName());
        assertEquals("SwitchWorker", god.getEffects().get(0));
        assertEquals("BasicGod", god.getEffects().get(1));
    }

    @Test
    void setPossibleMove() {
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);

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
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);


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
        Worker worker=new Worker(1);
        Worker worker2=new Worker(2);
        Worker worker3=new Worker(3);
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

        //CONTROLLO CHE NON POSSO COSTRUIRE INTORNO PER VEDERE SE HA FATTO CASINO CON GLI WORKER
        god.setPossibleBuild(worker);
        worker.getActualBox().clearBoxesNextTo();
        god.setPossibleBuild(worker2);
        worker2.getActualBox().clearBoxesNextTo();
        board.getBox(4,4).build();
        board.getBox(4,4).build();
        board.getBox(4,4).build();
        god.setPossibleBuild(worker3);
        assertTrue(god.moveBlock(board.getBox(4,4)));
        worker3.getActualBox().clearBoxesNextTo();
    }
}
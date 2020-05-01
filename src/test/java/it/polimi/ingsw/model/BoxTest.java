package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameComponents.Board;
import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.gameComponents.Worker;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxTest {
    final Board boardTest = new Board();
    final Box box = boardTest.getBox(0,0);

    @Test
    void getRow() {
        assertEquals(0,box.getRow());
    }

    @Test
    void getColumn() {
        assertEquals(0, box.getColumn());
    }

    @Test
    void getWorker() {
        Worker w = new Worker(1, Game.COLOR.BLU);
        box.setWorker(w);
        assertEquals(w,box.getWorker());
    }


    @Test
    void clear() {
        box.clear();
        assertEquals(0, box.getCounter());
        assertNull(box.getWorker());
    }

    @Test
    void clearWorker() {
        box.clearWorker();
        assertNull(box.getWorker());
    }

    @Test
    void build() {
        int counter= box.getCounter();
        counter++;
        box.build();
        assertEquals(counter, box.getCounter());
    }

    @Test
    void setReachable() {
        box.setReachable(true);
        assertTrue(box.isReachable());
        box.setReachable(false);
        assertFalse(box.isReachable());
    }

    @Test
    void setWorker() {
        Worker w= new Worker(1,Game.COLOR.BLU);
        box.setWorker(w);
        assertEquals(w,box.getWorker());
    }

    @Test
    void notWorker() {
        boolean worker= box.notWorker();
        if(box.getWorker()!=null){
            assertFalse(worker);
        }else{
            assertTrue(worker);
        }
    }

    @Test
    void isEmpty() {
        boolean empty= box.isEmpty();
        if(empty){
            assertEquals(0, box.getCounter());
            assertNull(box.getWorker());
        }else{
            assertNotEquals(0, box.getCounter());
            assertNotEquals(null,box.getWorker());
        }
    }
    @Test
    void clearBoxesNextTo() {
        box.clearBoxesNextTo();
        for(int index=0;index<8;index++){
            if(box.getBoxesNextTo().get(index)!=null){
                assertFalse(box.getBoxesNextTo().get(index).isReachable());
            }
        }
    }

    @Test
    void checkPossible() {
        box.clearBoxesNextTo();
        box.getBoxesNextTo().get(7).setReachable(true);
        assertTrue(box.checkPossible());
        box.clearBoxesNextTo();
        if(box.getBoxesNextTo().get(0)!=null){
            box.getBoxesNextTo().get(0).setReachable(true);
        }
        assertFalse(box.checkPossible());
    }
}
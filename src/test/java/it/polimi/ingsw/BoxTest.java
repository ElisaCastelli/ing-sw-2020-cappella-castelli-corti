package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        Worker w = new Worker(1);
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
        assertEquals(true, box.isReachable());
        box.setReachable(false);
        assertEquals(false, box.isReachable());
    }

    @Test
    void setWorker() {
        Worker w= new Worker(1);
        box.setWorker(w);
        assertEquals(w,box.getWorker());
    }

    @Test
    void notWorker() {
        boolean worker= box.notWorker();
        if(box.getWorker()!=null){
            assertEquals(false, worker);
        }else{
            assertEquals(true,worker);
        }
    }

    @Test
    void isEmpty() {
        boolean empty= box.isEmpty();
        if(empty){
            assertEquals(0, box.getCounter());
            assertEquals(null,box.getWorker());
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
                assertEquals(false,box.getBoxesNextTo().get(index).isReachable());
            }
        }
    }

    @Test
    void checkPossible() {
        box.clearBoxesNextTo();
        box.getBoxesNextTo().get(7).setReachable(true);
        assertEquals(true,box.checkPossible());
        box.clearBoxesNextTo();
        if(box.getBoxesNextTo().get(0)!=null){
            box.getBoxesNextTo().get(0).setReachable(true);
        }
        assertEquals(false,box.checkPossible());
    }
}
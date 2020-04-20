package it.polimi.ingsw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    Player pTest = new Player("a",10);
    Board boardTest = new Board();
    @Test
    void setName() {
        pTest.setName("b");
        assertEquals("b",pTest.getName());
    }

    @Test
    void setAge() {
        pTest.setAge(21);
        assertEquals(21,pTest.getAge());
    }

    @Test
    void getWorkerBox() {
        Worker w = new Worker(1);
        pTest.initializeWorker(1,boardTest.getBox(0,0));
        assertEquals(boardTest.getBox(0,0),pTest.getWorkerBox(1));
    }

    @Test
    void swap() {
        Player p2Test= new Player("b",12);
        String name1= pTest.getName();
        String name2 = p2Test.getName();
        int age1= pTest.getAge();
        int age2 = p2Test.getAge();
        pTest.swap(p2Test);
        assertEquals(name2, pTest.getName());
        assertEquals(name1, p2Test.getName());
        assertEquals(age2, pTest.getAge());
        assertEquals(age1, p2Test.getAge());
    }

    @Test
    void initializeWorker() {
        int index=0;
        Box b = boardTest.getBox(0,0);
        pTest.initializeWorker(index,b);
        assertEquals(b,pTest.getWorkerBox(index));
    }

    @Test
    void setPossibleMove() {
        boardTest.clear();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        pTest.setPossibleMove(1);
        for(int index=0; index<8; index++){
            assertEquals(true,pTest.getWorkerBox(1).getBoxesNextTo().get(index).isReachable());
        }
    }

    @Test
    void setPossibleBuild() {
        boardTest.clear();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();
        boardTest.getBox(0,0).build();

        pTest.setPossibleBuild(1);
        assertEquals(false,pTest.getWorkerBox(1).getBoxesNextTo().get(0).isReachable());
    }

    @Test
    void playWorker() {
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(1,boardTest.getBox(1,1));

        pTest.playWorker(1, boardTest.getBox(0,0));
        assertNotEquals(null, boardTest.getBox(0,0).getWorker());
        assertEquals(null, boardTest.getBox(1,1).getWorker());

        //assertEquals(false, pTest.playWorker(1, boardTest.getBox(3,3)));
    }

    @Test
    void playBlock() {
        int counter= boardTest.getBox(1,2).getCounter();
        boardTest.clear();
        pTest.goPlay();
        pTest.initializeWorker(1, boardTest.getBox(1,1));
        pTest.playBlock(boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());
        pTest.playBlock(boardTest.getBox(1,2));
        counter++;
        assertEquals((counter), boardTest.getBox(1,2).getCounter());

    }

    @Test
    void checkWin() {
        boardTest.clear();
        pTest.goPlay();
        boardTest.getBox(2,2).build();
        boardTest.getBox(2,2).build();
        boardTest.getBox(2,2).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        pTest.initializeWorker(1,boardTest.getBox(1,1));
        pTest.playWorker(1,boardTest.getBox(2,2));
        assertEquals(true, pTest.checkWin(boardTest.getBox(1,1), boardTest.getBox(2,2)));
    }

    @Test
    void checkWorkers() {
        boardTest.clear();
        pTest.initializeWorker(0, boardTest.getBox(0,0));
        pTest.initializeWorker(1, boardTest.getBox(1,0));
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,0).build();
        boardTest.getBox(2,0).build();
        //assertEquals(false,pTest.checkWorkers());
    }

    @Test
    void checkBuilding() {
    }
}
package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameComponents.Board;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game gameTest = new Game();
    Board boardTest= new Board();

    GameTest() throws Exception {
    }

    @Test
    void sortGamers() {
        gameTest.addPlayer("a", 22);
        gameTest.addPlayer("b", 15);
        gameTest.addPlayer("c", 42);
        gameTest.sortGamers();
        assertEquals(15, gameTest.getPlayer(0).getAge() );
        assertEquals(22, gameTest.getPlayer(1).getAge() );
        assertEquals(42, gameTest.getPlayer(2).getAge() );
    }

    @Test
    void setNPlayers() {
        gameTest.setNPlayers(3);
        assertEquals(3,gameTest.getnPlayers());
    }

    @Test
    void addPlayer() {
        int n= gameTest.getPlayers().size();
        gameTest.addPlayer("a",11);
        n++;
        assertEquals(n,gameTest.getPlayers().size());
    }

    @Test
    void initializeWorker() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.addPlayer("b",12);
        gameTest.initializeWorker(0,1,boardTest.getBox(0,0));
        assertNotEquals(null,boardTest.getBox(0,0).getWorker());
        gameTest.initializeWorker(0,2,boardTest.getBox(0,1));
        assertNotEquals(null,boardTest.getBox(0,1).getWorker());
        gameTest.initializeWorker(1,1,boardTest.getBox(2,0));
        assertNotEquals(null,boardTest.getBox(2,0).getWorker());
        assertFalse(gameTest.initializeWorker(1,2,boardTest.getBox(0,0)));
    }

    @Test
    void startTurn() {
    }

    @Test
    void canMove() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.getPlayer(0).initializeWorker(0,boardTest.getBox(0,0));
        gameTest.getPlayer(0).initializeWorker(1,boardTest.getBox(1,0));
        assertTrue(gameTest.canMove(0));
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,1).build();
        boardTest.getBox(2,0).build();
        boardTest.getBox(2,0).build();
        assertFalse(gameTest.canMove(0));
        gameTest.addPlayer("b",28);
        gameTest.getPlayer(1).initializeWorker(0,boardTest.getBox(2,3));
        gameTest.getPlayer(1).initializeWorker(1,boardTest.getBox(4,1));
        assertTrue(gameTest.canMove(1));
    }

    @Test
    void setBoxReachable() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.getPlayer(0).initializeWorker(0,boardTest.getBox(0,0));
        gameTest.getPlayer(0).initializeWorker(1,boardTest.getBox(1,0));
        gameTest.setBoxReachable(0,1);
        assertTrue(boardTest.getBox(0,1).isReachable());
        assertFalse(boardTest.getBox(1,0).isReachable());
    }

    @Test
    void movePlayer() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.addPlayer("b",25);
        gameTest.sortGamers();
        gameTest.getPlayer(0).initializeWorker(0,boardTest.getBox(0,0));
        gameTest.getPlayer(0).initializeWorker(1,boardTest.getBox(1,0));
        gameTest.getPlayer(1).initializeWorker(0,boardTest.getBox(2,2));
        gameTest.getPlayer(1).initializeWorker(1,boardTest.getBox(4,2));

    }

    @Test
    void canBuild() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.getPlayer(0).initializeWorker(0,boardTest.getBox(0,0));
        assertTrue(gameTest.canBuild(0,1));
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(0,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,1).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        boardTest.getBox(1,0).build();
        assertFalse(gameTest.canBuild(0,1));
    }

    @Test
    void setBoxBuilding() {
        boardTest.clear();
        gameTest.addPlayer("a",22);
        gameTest.getPlayer(0).initializeWorker(0,boardTest.getBox(0,0));
        gameTest.getPlayer(0).initializeWorker(1,boardTest.getBox(1,0));
        gameTest.setBoxBuilding(0,1);
        assertTrue(boardTest.getBox(0,1).isReachable());
        assertFalse(boardTest.getBox(1,0).isReachable());
    }

    @Test
    void buildBlock() {
    }

    @Test
    void checkWin() {
    }

    @Test
    void setWinningPlayer() {
    }

    @Test
    void setDeadPlayer() {
    }

}
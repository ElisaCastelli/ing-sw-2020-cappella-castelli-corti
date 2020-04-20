package it.polimi.ingsw;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game gameTest = new Game();
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
    void chooseCard() {

    }

    @Test
    void initializeWorker() {
        Board boardTest= new Board();
        gameTest.addPlayer("a",22);
        gameTest.initializeWorker(0,1,boardTest.getBox(0,0));
        assertNotEquals(null,boardTest.getBox(0,0).getWorker());
    }

    @Test
    void startTurn() {
    }

    @Test
    void canMove() {
    }

    @Test
    void setBoxReachable() {
    }

    @Test
    void movePlayer() {

    }

    @Test
    void canBuild() {
    }

    @Test
    void setBoxBuilding() {
    }

    @Test
    void buildBlock() {
    }

    @Test
    void finishTurn() {
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
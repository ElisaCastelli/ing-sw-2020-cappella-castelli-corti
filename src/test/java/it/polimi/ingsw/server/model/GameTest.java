package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.server.model.gameComponents.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;

import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.gameState.GoingState;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.BuildBeforeWorkerMove;
import it.polimi.ingsw.server.model.god.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GameTest {
    Game gameTest = new Game();
    Board boardTest = new Board();

    @BeforeEach
    public void init() {
        gameTest.setNPlayers(3);
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer(1, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer(2, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.addPlayer("b", 15, 1);
        gameTest.addPlayer("c", 42, 2);
    }

    @Test
    void sortGamers() {
        gameTest.sortGamers();
        assertEquals(15, gameTest.getPlayerArray().get(0).getAge());
        assertEquals(22, gameTest.getPlayerArray().get(1).getAge());
        assertEquals(42, gameTest.getPlayerArray().get(2).getAge());
    }

    @Test
    void setNPlayers() {
        gameTest.setNPlayers(3);
        assertEquals(3, gameTest.getNPlayers());
    }

    @Test
    void addPlayer() {
        int n = gameTest.getPlayerArray().size();
        gameTest.addPlayer(3, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("v", 11, 3);
        n++;
        assertEquals(n, gameTest.getPlayerArray().size());
    }

    @Test
    void initializeWorker() {
        boardTest.clear();
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 0));
        assertNotEquals(null, gameTest.getBoard().getBox(0, 0).getWorker());
        assertNotEquals(null, gameTest.getBoard().getBox(1, 0).getWorker());
        gameTest.initializeWorker(boardTest.getBox(1, 1), boardTest.getBox(2, 0));
        assertNotEquals(null, gameTest.getBoard().getBox(2, 0).getWorker());
        assertFalse(gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(4, 4)));
    }

    @Test
    void canMove() {
        boardTest.clear();
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(2, 3), gameTest.getBoard().getBox(4, 1), gameTest.getBoard());
        gameTest.getPlayerArray().get(1).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());

        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(2, 1).build();
        gameTest.getBoard().getBox(2, 1).build();
        gameTest.getBoard().getBox(2, 0).build();
        gameTest.getBoard().getBox(2, 0).build();
        assertTrue(gameTest.canMove());
    }

    @Test
    void setBoxReachable() {
        boardTest.clear();
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxReachable(1);
        assertTrue(gameTest.getBoard().getBox(0, 1).isReachable());
        assertFalse(gameTest.getBoard().getBox(1, 0).isReachable());
    }

    @Test
    void movePlayer() {
        boardTest.clear();
        gameTest.sortGamers();
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 0), boardTest);
        gameTest.getPlayerArray().get(1).initializeWorker(boardTest.getBox(2, 2), boardTest.getBox(4, 2), boardTest);
        assertTrue(gameTest.movePlayer(1, 1, 1));

    }

    @Test
    void canBuild() {
        boardTest.clear();
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(4, 4), gameTest.getBoard());
        assertTrue(gameTest.canBuild(1));
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(0, 1).build();
        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(1, 1).build();
        gameTest.getBoard().getBox(1, 0).build();
        gameTest.getBoard().getBox(1, 0).build();
        gameTest.getBoard().getBox(1, 0).build();
        gameTest.getBoard().getBox(1, 0).build();
        assertFalse(gameTest.canBuild(1));
    }

    @Test
    void setBoxBuilding() {
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxBuilding(1);
        assertTrue(gameTest.getBoard().getBox(0, 1).isReachable());
        assertFalse(gameTest.getBoard().getBox(1, 0).isReachable());
    }

    @Test
    void buildBlock() {
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxBuilding(1);
        assertTrue(gameTest.buildBlock(1, 1, 1));
    }

    @Test
    void checkWin() {
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        assertTrue(gameTest.movePlayer(1, 1, 1));
        assertFalse(gameTest.checkWin(0, 0, 1));
    }


    @Test
    void someoneWithYourName() {
        boolean exists1 = gameTest.someoneWithYourName("s");
        assertFalse(exists1);
        boolean exists2 = gameTest.someoneWithYourName("a");
        assertTrue(exists2);
    }

    @Test
    void goPlayingNext() {
        gameTest.startGame();
        assertTrue(gameTest.getPlayerArray().get(0).isPlaying());
        gameTest.goPlayingNext();
        assertTrue(gameTest.getPlayerArray().get(1).isPlaying());
    }

    @Test
    void getRowWorker() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        assertEquals(0, gameTest.getRowWorker(1));
        assertEquals(1, gameTest.getRowWorker(2));
    }

    @Test
    void getColumnWorker() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        assertEquals(0, gameTest.getColumnWorker(1));
        assertEquals(2, gameTest.getColumnWorker(2));
    }

    @Test
    void getNPlayers() {
        assertNotEquals(0, gameTest.getNPlayers());

        assertEquals(3, gameTest.getNPlayers());
    }

    @Test
    void checkAckPlayer() {
        assertTrue(gameTest.checkAckPlayer());
    }

    @Test
    void removeExtraPlayer() {
        assertEquals(3, gameTest.getNPlayers());
        gameTest.addPlayer(3, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        assertEquals(4, gameTest.getPlayerArray().size());
        gameTest.removeExtraPlayer();
        assertEquals(3, gameTest.getPlayerArray().size());
    }

    @Test
    void remove() {
        assertEquals(3, gameTest.getNPlayers());
        gameTest.addPlayer(3, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        assertEquals(4, gameTest.getPlayerArray().size());
        gameTest.remove(2);
        assertEquals(3, gameTest.getPlayerArray().size());

    }

    @Test
    void updateIndexInArray() {
        assertEquals(3, gameTest.getNPlayers());
        gameTest.addPlayer(3, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("d", 76, 3);
        assertEquals(4, gameTest.getPlayerArray().size());
        gameTest.remove(2);
        assertEquals(3, gameTest.getPlayerArray().size());
        int indexPlayer = gameTest.searchByName("d");
        assertEquals(indexPlayer, gameTest.getPlayerArray().get(2).getIndexPlayer());

    }

    @Test
    void startGame() {
        gameTest.startGame();
        assertTrue(gameTest.getPlayerArray().get(0).isPlaying());
        assertFalse(gameTest.getPlayerArray().get(1).isPlaying());
        assertFalse(gameTest.getPlayerArray().get(2).isPlaying());
    }

    @Test
    void askState() {
        assertFalse(gameTest.askState());
        gameTest.setNPlayers(5);
        assertTrue(gameTest.askState());
    }

    @Test
    void getPlayerArray() {
        assertNotNull(gameTest.getPlayerArray());
        assertEquals(3, gameTest.getPlayerArray().size());
    }

    @Test
    void searchByName() {
        int indexPlayer1 = gameTest.searchByName("a");
        assertEquals(1, indexPlayer1);
        int indexPlayer2 = gameTest.searchByName("b");
        assertEquals(0, indexPlayer2);
        int indexPlayer3 = gameTest.searchByName("c");
        assertEquals(2, indexPlayer3);
    }

    @Test
    void searchByClientIndex() {
        int indexPlayer1 = gameTest.searchByClientIndex(0);
        assertEquals(1, indexPlayer1);
        int indexPlayer2 = gameTest.searchByClientIndex(1);
        assertEquals(0, indexPlayer2);
        int indexPlayer3 = gameTest.searchByClientIndex(2);
        assertEquals(2, indexPlayer3);

    }

    @Test
    void searchByPlayerIndex() {
        int indexClient1 = gameTest.searchByPlayerIndex(0);
        assertEquals(1, indexClient1);
        int indexClient2 = gameTest.searchByPlayerIndex(1);
        assertEquals(0, indexClient2);
        int indexClient3 = gameTest.searchByPlayerIndex(2);
        assertEquals(2, indexClient3);
    }

    @Test
    void fakePlaying() {

    }

    @Test
    void getBoard() {
        assertNotNull(gameTest.getBoard());
    }

    @Test
    void gameData() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        gameTest.setBoxReachable(1);
        UpdateBoardEvent updateBoardEvent = gameTest.gameData(true);
        assertNotNull(updateBoardEvent.getBoard());
        assertNotNull(updateBoardEvent.getUserArray());
        assertEquals("a", updateBoardEvent.getUserArray().get(1).getName());
        assertNotNull(updateBoardEvent.getBoard().getBox(0, 0).getWorker());
        assertEquals(1, updateBoardEvent.getCurrentClientPlaying());
    }

    @Test
    void chooseTempCard() {
        gameTest.startGame();
        gameTest.loadCards();
        ArrayList<Integer> indexCards = new ArrayList<>();
        indexCards.add(2);
        indexCards.add(3);
        indexCards.add(6);
        int indexClientNext = gameTest.chooseTempCard(indexCards);
        assertEquals(0,indexClientNext);
        assertEquals(3,gameTest.getTempCard().size());

    }

    @Test
    void chooseCard() {
        gameTest.startGame();
        gameTest.loadCards();
        ArrayList<Integer> indexCards = new ArrayList<>();
        indexCards.add(2);
        indexCards.add(3);
        indexCards.add(6);
        int indexClientNext = gameTest.chooseTempCard(indexCards);
        int indexClientNext2 = gameTest.chooseCard(2);
        assertEquals(2,gameTest.getTempCard().size());
        assertEquals(2,indexClientNext2);
    }

    @Test
    void loadCards() {
        gameTest.startGame();
        gameTest.loadCards();
    }


    @Test
    void getCards() {
        gameTest.startGame();
        ArrayList<String> cardsGod = gameTest.getCards();
        assertEquals(14, cardsGod.size());
    }

    @Test
    void getTempCard() {
        gameTest.startGame();
        ArrayList<String> tempCardsGod = gameTest.getTempCard();
        assertEquals(0, tempCardsGod.size());
        //da finire
    }

    @Test
    void whoIsPlaying() {
        gameTest.startGame();
        assertEquals(0, gameTest.whoIsPlaying());
        gameTest.goPlayingNext();
        assertEquals(1, gameTest.whoIsPlaying());
        gameTest.goPlayingNext();
        assertEquals(2, gameTest.whoIsPlaying());
        gameTest.goPlayingNext();
        assertEquals(0, gameTest.whoIsPlaying());
    }

    @Test
    void getWorkersPos() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        ArrayList<Box> workersBox = gameTest.getWorkersPos(0);
        assertEquals(0, workersBox.get(0).getRow());
        assertEquals(0, workersBox.get(0).getColumn());
        assertEquals(1, workersBox.get(1).getRow());
        assertEquals(2, workersBox.get(1).getColumn());
    }

    @Test
    void isReachable() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        assertFalse(gameTest.isReachable(0, 0));
    }

    @Test
    void getState() {
        GameState current = gameTest.getState();
        assertNull(current);
        gameTest.startGame();
        assertTrue(gameTest.getState() instanceof GoingState);
    }

    @Test
    void canBuildBeforeWorkerMove() {
        gameTest.startGame();
        assertFalse(gameTest.canBuildBeforeWorkerMove());
        gameTest.getPlayerArray().get(0).setGod(new BuildBeforeWorkerMove(new BasicGod()));
        assertTrue(gameTest.canBuildBeforeWorkerMove());

    }

    @Test
    void canMoveSpecialTurn() {
        gameTest.startGame();
        assertFalse(gameTest.canBuildBeforeWorkerMove());

    }

    @Test
    void thereIsAWinner() {

    }

    @Test
    void setIndexPossibleBlock() {
        gameTest.startGame();
        gameTest.setIndexPossibleBlock(0);
        gameTest.getPlayerArray().get(0).playBlock(gameTest.getBoard().getBox(0,0));
        assertEquals(1, gameTest.getBoard().getBox(0,0).getBuilding().getArrayOfBlocks().size());
    }

    @Test
    void checkWinAfterBuild() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        gameTest.buildBlock(1,0,0);
        assertFalse(gameTest.checkWinAfterBuild());
    }

    @Test
    void getWinner() {
        gameTest.startGame();
        assertEquals(-1, gameTest.getWinner());
    }

    @Test
    void setDeadPlayer() {

    }

    @Test
    void controlHeartBeat() {
        boolean connected0 = gameTest.incrementHeartBeat(0);
        boolean connected1 = gameTest.incrementHeartBeat(1);
        boolean connected2 = gameTest.incrementHeartBeat(2);
        assertEquals(1, gameTest.getPlayerArray().get(0).getMissed_heartbeat());
        assertEquals(1, gameTest.getPlayerArray().get(1).getMissed_heartbeat());
        assertEquals(1, gameTest.getPlayerArray().get(2).getMissed_heartbeat());
        gameTest.controlHeartBeat(0);
        gameTest.controlHeartBeat(1);
        gameTest.controlHeartBeat(2);
        gameTest.controlHeartBeat(3);
        assertEquals(0, gameTest.getPlayerArray().get(0).getMissed_heartbeat());
        assertEquals(0, gameTest.getPlayerArray().get(1).getMissed_heartbeat());
        assertEquals(0, gameTest.getPlayerArray().get(2).getMissed_heartbeat());
    }

    @Test
    void incrementHeartBeat() {
        boolean connected0 = gameTest.incrementHeartBeat(0);
        boolean connected1 = gameTest.incrementHeartBeat(1);
        boolean connected2 = gameTest.incrementHeartBeat(2);
        boolean connected3 = gameTest.incrementHeartBeat(3);
        assertTrue(connected0);
        assertTrue(connected1);
        assertTrue(connected2);
    }

    @Test
    void reset() {
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 2));
        gameTest.reset();
        assertEquals(0, gameTest.getNPlayers());
        assertEquals(0, gameTest.getPlayerArray().size());
        assertEquals(0, gameTest.getTempCard().size());
        assertNull(boardTest.getBox(0, 0).getWorker());
    }
}
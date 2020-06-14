package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.parse.CardCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class GoingStateTest {


    static GameStateManager gameStateManager;
    static Board board = new Board();

    @BeforeAll
    public static void setup() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> playersDead = new ArrayList<>();

        CardCreator cardCreator = new CardCreator();
        ArrayList<God> cards = cardCreator.parseCard();

        players.add(new Player(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timertask");
            }
        }));
        players.add(new Player(1, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timertask");
            }
        }));
        players.add(new Player(2, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timertask");
            }
        }));

        players.get(0).setIndexClient(0);
        players.get(1).setIndexClient(1);
        players.get(2).setIndexClient(2);
        players.get(0).setName("a");
        players.get(1).setName("b");
        players.get(2).setName("c");
        players.get(0).goPlay();
        players.get(1).goPlay();
        players.get(2).goPlay();
        players.get(0).setGod(cards.get(8));
        players.get(1).setGod(cards.get(3));
        players.get(2).setGod(cards.get(2));

        players.get(0).initializeWorker(board.getBox(0, 0), board.getBox(1, 1), board);
        players.get(1).initializeWorker(board.getBox(2, 2), board.getBox(3, 3), board);
        players.get(2).initializeWorker(board.getBox(0, 4), board.getBox(4, 4), board);
        gameStateManager = new GameStateManager(players, playersDead);
        gameStateManager.start();

    }

    @Test
    void canBuildBeforeWorkerMove() {
        assertTrue(gameStateManager.canBuildBeforeWorkerMove(0));
        assertFalse(gameStateManager.canBuildBeforeWorkerMove(1));
    }

    @Test
    void canMove() {
        assertTrue(gameStateManager.canMove(0));
        assertTrue(gameStateManager.canMove(1));
    }

    @Test
    void canMoveSpecialTurn() {
        assertTrue(gameStateManager.canMoveSpecialTurn(0, 1));
    }


    @Test
    void setBoxReachable() {
        board.clearReachable();
        gameStateManager.setBoxReachable(0, 1);
        assertTrue(board.getBox(0, 1).isReachable());
        assertTrue(board.getBox(1, 0).isReachable());
        board.clearReachable();
        gameStateManager.setBoxReachable(1, 2);
        assertTrue(board.getBox(2, 3).isReachable());
        assertTrue(board.getBox(2, 4).isReachable());
        assertTrue(board.getBox(3, 2).isReachable());
        assertTrue(board.getBox(3, 4).isReachable());
        assertTrue(board.getBox(4, 2).isReachable());
        assertTrue(board.getBox(4, 3).isReachable());
        assertTrue(board.getBox(4, 4).isReachable());
        board.clearReachable();
    }

    @Test
    void movePlayer() {
        gameStateManager.movePlayer(0, 1, 0, 1, board);
        assertNull(board.getBox(0, 0).getWorker());
        assertNotNull(board.getBox(0, 1).getWorker());
        gameStateManager.movePlayer(0, 1, 0, 0, board);

    }

    @Test
    void canBuild() {
        assertTrue(gameStateManager.canBuild(0, 1));
        assertTrue(gameStateManager.canBuild(1, 2));
    }

    @Test
    void setBoxBuilding() {
        gameStateManager.setBoxBuilding(1, 2);
        assertFalse(board.getBox(2, 2).isReachable());
        assertTrue(board.getBox(2, 3).isReachable());
        assertTrue(board.getBox(2, 4).isReachable());
        assertTrue(board.getBox(3, 2).isReachable());
        assertTrue(board.getBox(3, 4).isReachable());
        assertTrue(board.getBox(4, 2).isReachable());
        assertTrue(board.getBox(4, 3).isReachable());
        assertFalse(board.getBox(4, 4).isReachable());
        board.clearReachable();
    }

    @Test
    void buildBlock() {
        gameStateManager.buildBlock(0, 1, 0, 1, board);
        assertEquals(1, board.getBox(0, 1).getCounter());
        gameStateManager.buildBlock(0, 1, 0, 1, board);
        assertEquals(2, board.getBox(0, 1).getCounter());
        gameStateManager.buildBlock(0, 1, 0, 1, board);
        assertEquals(3, board.getBox(0, 1).getCounter());
    }

    @Test
    void setIndexPossibleBlock() {
        gameStateManager.start();
        gameStateManager.setIndexPossibleBlock(1, 1);
        gameStateManager.buildBlock(1, 2, 2, 3, board);
        assertEquals(4, board.getBox(2, 3).getCounter());
    }

    @Test
    void checkWin() {
        gameStateManager.movePlayer(0, 1, 0, 1, board);
        assertEquals(3, board.getBox(0, 1).getWorker().getHeight());
        gameStateManager.buildBlock(0, 1, 0, 0, board);
        assertEquals(1, board.getBox(0, 0).getCounter());
        gameStateManager.buildBlock(0, 1, 0, 0, board);
        assertEquals(2, board.getBox(0, 0).getCounter());
        assertTrue(gameStateManager.checkWin(0, board.getBox(0, 0), 1));
        assertEquals(0, gameStateManager.getWinner());

    }

    @Test
    void checkWinAfterBuild() {
        assertFalse(gameStateManager.checkWinAfterBuild());
    }

    @Test
    void setDeadPlayer() {
        gameStateManager.setDeadPlayer(2);
        assertEquals(2, gameStateManager.getCurrentState().getPlayersDead().get(0).getIndexPlayer());
    }
}
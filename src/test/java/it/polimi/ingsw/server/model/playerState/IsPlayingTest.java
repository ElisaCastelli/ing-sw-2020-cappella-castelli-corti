package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.parse.CardCreator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class IsPlayingTest {

    static PlayerStateManager playerStateManager;
    static  ArrayList<God> gods;
    static Board board;
    static Player player;

    @BeforeAll
    static void setUp() {
        board= new Board();
        CardCreator parser= new CardCreator();
        gods=parser.parseCard();
        playerStateManager= new PlayerStateManager(gods.get(8));
        player= new Player(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timertask");
            }
        });
        player.setGod(gods.get(1));
        player.initializeWorker(board.getBox(0,0),board.getBox(3,3),board);
    }

    @Test
    void setMyGod() {
        playerStateManager.setMyGod(gods.get(1));
        playerStateManager.goPlaying();
        assertTrue(playerStateManager.isPlaying());
        assertEquals(gods.get(1),player.getGod());
    }

    @Test
    void moveWorker() {
        playerStateManager.setMyGod(gods.get(1));
        playerStateManager.goPlaying();
        playerStateManager.moveWorker(player.getWorkerBox(1).getWorker(),board.getBox(1,1));
        assertTrue(board.getBox(1,1).getWorker()!=null);
        playerStateManager.goWaiting();
        assertTrue(playerStateManager.getCurrentState() instanceof IsWaiting);
        assertFalse(playerStateManager.moveWorker(player.getWorkerBox(1).getWorker(),board.getBox(2,2)));
    }

    @Test
    void moveBlock() {
        playerStateManager.goPlaying();
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        playerStateManager.moveWorker(player.getWorkerBox(0).getWorker(),board.getBox(1,1));
        playerStateManager.setPossibleBuild(player.getWorkerBox(0).getWorker());
        assertTrue(playerStateManager.moveBlock(board.getBox(1,2)));
        assertEquals(board.getBox(1,2).getCounter(),1);
        assertTrue(playerStateManager.moveBlock(board.getBox(1,2)));
        assertEquals(board.getBox(1,2).getCounter(),2);
        playerStateManager.goWaiting();
        assertFalse(playerStateManager.moveBlock(board.getBox(2,2)));
    }

    @Test
    void checkWin() {
        playerStateManager.goPlaying();
        playerStateManager.setMyGod(gods.get(3));
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        playerStateManager.moveWorker(player.getWorkerBox(1).getWorker(),board.getBox(1,0));
        playerStateManager.setPossibleBuild(player.getWorkerBox(0).getWorker());
        playerStateManager.moveBlock(board.getBox(1,1));
        playerStateManager.moveBlock(board.getBox(1,1));
        playerStateManager.moveBlock(board.getBox(1,1));
        playerStateManager.moveBlock(board.getBox(2,1));
        playerStateManager.moveBlock(board.getBox(2,1));
        playerStateManager.moveBlock(board.getBox(2,0));
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        playerStateManager.moveWorker(player.getWorkerBox(0).getWorker(),board.getBox(2,0));
        playerStateManager.goWaiting();
        playerStateManager.goPlaying();
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        playerStateManager.moveWorker(player.getWorkerBox(0).getWorker(),board.getBox(2,1));
        playerStateManager.goWaiting();
        playerStateManager.goPlaying();
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        playerStateManager.moveWorker(player.getWorkerBox(0).getWorker(),board.getBox(1,1));
        assertTrue(playerStateManager.checkWin(board.getBox(1,1),board.getBox(2,1)));
    }

    @Test
    void canBuildBeforeWorkerMove() {
        playerStateManager.goPlaying();
        playerStateManager.setMyGod(gods.get(8));
        assertTrue(playerStateManager.canBuildBeforeWorkerMove());
        playerStateManager.setMyGod(gods.get(1));
        assertFalse(playerStateManager.canBuildBeforeWorkerMove());
    }

    @Test
    void setPossibleMove() {
        playerStateManager.goPlaying();
        playerStateManager.setPossibleMove(player.getWorkerBox(0).getWorker());
        assertTrue(board.getBox(1,1).isReachable());
        assertFalse(board.getBox(0,4).isReachable());
    }

    @Test
    void setPossibleBuild() {
        playerStateManager.goPlaying();
        playerStateManager.setPossibleBuild(player.getWorkerBox(1).getWorker());
        assertTrue(playerStateManager.moveBlock(board.getBox(1,0)));
        assertEquals(board.getBox(1,0).getCounter(),1);
    }

    @Test
    void setIndexPossibleBlock() {
        /*playerStateManager.goPlaying();
        playerStateManager.setIndexPossibleBlock(1);
        playerStateManager.moveBlock(board.getBox(0,1));
        assertEquals(1,board.getBox(0,1).getCounter());*/
    }
}
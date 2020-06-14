package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Player;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class EndStateTest {
    static GameStateManager gameStateManager;

    @BeforeAll
    public static void setup() {
        ArrayList<Player> players = new ArrayList<>();
        ArrayList<Player> playersDead = new ArrayList<>();
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
        gameStateManager = new GameStateManager(players, playersDead);

    }

    @Test
    void setWinner() {
        int winner = 1;
        gameStateManager.goEnd(winner);
        assertTrue(gameStateManager.getCurrentState() instanceof EndState);
    }

    @Test
    void getWinner() {
        assertEquals(1, gameStateManager.getWinner());
    }
}
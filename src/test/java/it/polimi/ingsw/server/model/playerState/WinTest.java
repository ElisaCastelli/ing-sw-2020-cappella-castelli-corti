package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class WinTest {

    static PlayerStateManager playerStateManager;

    @BeforeAll
    static void setUp() {
        playerStateManager= new PlayerStateManager(new BasicGod());
    }

    @Test
    void amITheWinner() {
        playerStateManager.goPlaying();
        playerStateManager.goWin();
        assertTrue(playerStateManager.getCurrentState() instanceof Win);
        assertTrue(playerStateManager.amITheWinner());
    }
}
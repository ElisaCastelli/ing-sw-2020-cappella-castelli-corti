package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.BasicGod;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeadTest {

    static PlayerStateManager playerStateManager;
    static PlayerStateManager playerStateManager2;

    @BeforeAll
    static void setUp() {
        playerStateManager= new PlayerStateManager(new BasicGod());
        playerStateManager2= new PlayerStateManager(new BasicGod());
    }

    @Test
    void amIDead() {
        playerStateManager.goPlaying();
        playerStateManager.goDead();
        assertTrue(playerStateManager.getCurrentState() instanceof Dead);
        assertTrue(playerStateManager.amIDead());
        playerStateManager2.goDead();
        assertTrue(playerStateManager2.getCurrentState() instanceof Dead);
        assertTrue(playerStateManager2.amIDead());
    }
}
package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import org.junit.jupiter.api.Test;


import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game gameTest = new Game();
    Board boardTest = new Board();

    GameTest() throws Exception {
    }

    @Test
    void sortGamers() {
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
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 11, 0);
        n++;
        assertEquals(n, gameTest.getPlayerArray().size());
    }

    @Test
    void initializeWorker() {
        boardTest.clear();

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
        gameTest.addPlayer("a", 22, 0);
        gameTest.addPlayer("b", 12, 1);
        gameTest.startGame();
        gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 0));
        assertNotEquals(null, gameTest.getBoard().getBox(0, 0).getWorker());
        assertNotEquals(null, gameTest.getBoard().getBox(1, 0).getWorker());
        gameTest.initializeWorker(boardTest.getBox(1, 1), boardTest.getBox(2, 0));
        assertNotEquals(null, gameTest.getBoard().getBox(2, 0).getWorker());
        assertFalse(gameTest.initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(4, 4)));
    }

    @Test
    void startTurn() {
    }

    @Test
    void canMove() {
        boardTest.clear();
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.startGame();
        gameTest.addPlayer(1, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("b", 28, 1);
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

        assertFalse(gameTest.canMove());

    }

    @Test
    void setBoxReachable() {
        boardTest.clear();
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxReachable(1);
        assertTrue(gameTest.getBoard().getBox(0, 1).isReachable());
        assertFalse(gameTest.getBoard().getBox(1, 0).isReachable());
    }

    @Test
    void movePlayer() {
        boardTest.clear();
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
        gameTest.addPlayer("a", 22, 0);
        gameTest.addPlayer("b", 25, 1);
        gameTest.sortGamers();
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(boardTest.getBox(0, 0), boardTest.getBox(1, 0), boardTest);
        gameTest.getPlayerArray().get(1).initializeWorker(boardTest.getBox(2, 2), boardTest.getBox(4, 2), boardTest);
        assertTrue(gameTest.movePlayer(1,1,1));

    }

    @Test
    void canBuild() {
        boardTest.clear();
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
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
        assertTrue(gameTest.canBuild(1));
    }

    @Test
    void setBoxBuilding() {
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxBuilding(1);
        assertTrue(gameTest.getBoard().getBox(0, 1).isReachable());
        assertFalse(gameTest.getBoard().getBox(1, 0).isReachable());
    }

    @Test
    void buildBlock() {
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        gameTest.setBoxBuilding(1);
        assertTrue(gameTest.buildBlock(1,1,1));
    }

    @Test
    void checkWin() {
        gameTest.addPlayer(0, new Timer(), new TimerTask() {
            @Override
            public void run() {
                System.out.println("timer task");
            }
        });
        gameTest.addPlayer("a", 22, 0);
        gameTest.startGame();
        gameTest.getPlayerArray().get(0).initializeWorker(gameTest.getBoard().getBox(0, 0), gameTest.getBoard().getBox(1, 0), gameTest.getBoard());
        assertTrue(gameTest.movePlayer(1,1,1));
        assertFalse(gameTest.checkWin(0,0,1));
    }

}
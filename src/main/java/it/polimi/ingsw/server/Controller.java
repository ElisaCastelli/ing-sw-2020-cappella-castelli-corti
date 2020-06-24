package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Timer;

/**
 * This class is the controller of MVC pattern: it controls all the game flow.
 */
public class Controller {
    private ProxyGameModel gameModel;

    public Controller(ProxyGameModel gameModel) {
        this.gameModel = gameModel;
    }

    /**
     * This method sets the number of players
     *
     * @param nPlayers number of players
     */
    public void setNPlayers(int nPlayers) {
        gameModel.setNPlayers(nPlayers);
    }

    /**
     * This method controls if nPlayer is set
     *
     * @return true if nPlayer is set, otherwise returns false
     */
    public boolean controlSetNPlayer() {
        int nPlayers = gameModel.getNPlayers();
        return nPlayers != 0;
    }

    /**
     * This method starts the timer of the new player and adds the new player in the array in Game class
     *
     * @param indexPlayer player index
     */
    public void addPlayer(int indexPlayer) {
        Timer timer = new Timer();
        gameModel.addPlayerProxy(indexPlayer, timer);
    }

    /**
     * This method adds the player and sets all the information
     *
     * @param name        player name
     * @param age         player age
     * @param indexClient client index of the player
     */
    public void addPlayer(String name, int age, int indexClient) {
        boolean addCompleted = gameModel.addPlayer(name, age, indexClient);
        if (addCompleted) {
            if (gameModel.checkAckPlayer()) {
                gameModel.notifyAddPlayer();
            }
        } else {
            gameModel.notifyNewAddPlayer(indexClient);
        }
    }

    /**
     * This method removes all the players that cannot play because out of game player size
     */
    public void removeExtraPlayer() {
        gameModel.removeExtraPlayer();
    }

    /**
     * This method removes a player if he is in a game
     *
     * @param indexClient client index that has to be removed
     */
    public void removePlayer(int indexClient) {
        gameModel.remove(indexClient);
    }

    /**
     * This method puts in isPlaying the first player who has to play and in goingState the game
     */
    public void startGame() {
        gameModel.startGame();
    }

    /**
     * This method waits that all the players are in the game
     */
    public void askState() {
        boolean startCompleted = gameModel.askState();
        if (startCompleted) {
            for (int indexClient = 0; indexClient < gameModel.getNPlayers(); indexClient++) {
                int indexPlayer = gameModel.searchByClientIndex(indexClient);
                gameModel.notifyAskState(indexClient, indexPlayer);
            }
        }
    }

    /**
     * This method memorizes the two or three cards chosen by the first player
     *
     * @param threeCard two or three cards chosen by the first player
     */
    public void setTempCard(ArrayList<Integer> threeCard) {
        int clientIndex = gameModel.chooseTempCard(threeCard);
        gameModel.notifyTempCard(clientIndex);
    }

    /**
     * This method memorizes the card chosen by the player
     *
     * @param godCard card chosen by the player
     */
    public void setCard(int godCard) {
        int clientIndex = gameModel.chooseCard(godCard);
        gameModel.notifyTempCard(clientIndex);
    }

    /**
     * This method sets two workers in two positions chosen by the player
     *
     * @param box1 first worker box
     * @param box2 second worker box
     */
    public void initializeWorker(Box box1, Box box2) {
        boolean init = gameModel.initializeWorker(box1, box2);
        if (init) {
            gameModel.goPlayingNext();
            gameModel.notifyAddWorker();
        } else {
            gameModel.notifyWorkersNotInitialized();
        }
    }

    /**
     * This method tells if a player can build before the worker move
     *
     * @param row               row of the box where the worker is
     * @param column            column of the box where the worker is
     * @param indexWorkerToMove worker index that the player wants to move
     */
    public void canBuildBeforeWorkerMove(int row, int column, int indexWorkerToMove) {
        boolean canBuildBeforeWorkerMove = gameModel.canBuildBeforeWorkerMove();
        if (canBuildBeforeWorkerMove) {
            gameModel.notifySpecialTurn(row, column, indexWorkerToMove);
        } else {
            gameModel.notifyBasicTurn(indexWorkerToMove, row, column);
        }
    }

    /**
     * This method checks if at least one of the two workers can move: if the workers can moves, the player can start his turn, otherwise he loses
     */
    public void canMove() {
        int loserClient = gameModel.whoIsPlaying();
        boolean goAhead = gameModel.canMove();
        if (goAhead) {
            gameModel.notifyStartTurn();
        } else {
            gameModel.notifyLoser(loserClient);
            int winnerClient = gameModel.getWinner();
            if (winnerClient != -1) {
                gameModel.notifyWin(winnerClient);
            } else {
                gameModel.notifyWhoHasLost(loserClient);
            }
        }
    }

    /**
     * This method checks if the worker can move: if it is, the player can start his special turn and build, otherwise he loses
     *
     * @param indexWorker  worker index that the player has to move
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canMoveSpecialTurn(int indexWorker, int rowWorker, int columnWorker) {
        int loserClient = gameModel.whoIsPlaying();
        boolean goAhead = gameModel.canMoveSpecialTurn(indexWorker);
        if (goAhead) {
            gameModel.setBoxReachable(indexWorker);
            gameModel.notifyUpdateBoard(true);
            gameModel.notifyBasicTurn(indexWorker, rowWorker, columnWorker);
        } else {
            gameModel.notifyLoser(loserClient);
            int winnerClient = gameModel.getWinner();
            if (winnerClient != -1) {
                gameModel.notifyWin(winnerClient);
            } else {
                gameModel.notifyWhoHasLost(loserClient);
            }
        }
    }

    /**
     * This method sets all the boxes that a worker can reach
     *
     * @param indexWorker worker index that the player wants to move
     * @param secondMove  true if it is a second worker move because of a God ability, otherwise it is false
     */
    public void setBoxReachable(int indexWorker, boolean secondMove) {
        boolean oneReachable = gameModel.setBoxReachable(indexWorker);
        if (oneReachable) {
            gameModel.notifySetReachable(indexWorker, secondMove);
        } else {
            gameModel.notifyNotReachable(indexWorker, secondMove);
        }
    }

    /**
     * This method moves a worker from a box to another
     *
     * @param rowStart          row of the box where the worker is
     * @param columnStart       column of the box where the worker is
     * @param row               row of the box where the player wants to reach
     * @param column            column of the box where the player wants to reach
     * @param indexWorkerToMove worker index that the player moves
     */
    public void movePlayer(int rowStart, int columnStart, int row, int column, int indexWorkerToMove) {
        boolean moved = gameModel.movePlayer(indexWorkerToMove, row, column);
        AskMoveEvent askMoveEvent;
        if (moved) {
            askMoveEvent = new AskMoveEvent(indexWorkerToMove, row, column, false, true);
        } else {
            if (gameModel.getRowWorker(indexWorkerToMove) == rowStart && gameModel.getColumnWorker(indexWorkerToMove) == columnStart) {
                askMoveEvent = new AskMoveEvent(indexWorkerToMove, rowStart, columnStart, true, false);
            } else {
                askMoveEvent = new AskMoveEvent(indexWorkerToMove, row, column, false, false);
            }
        }

        askMoveEvent.setRowStart(rowStart);
        askMoveEvent.setColumnStart(columnStart);
        int clientIndex = gameModel.searchByPlayerIndex(gameModel.whoIsPlaying());
        gameModel.notifyMovedWorker(askMoveEvent, clientIndex);
    }

    /**
     * This method checks if a player won: if he does, notifies the win, otherwise continues his turn
     *
     * @param askMoveEvent object with all the information about the worker move
     * @param indexClient  client index who is playing
     */
    public void checkWin(AskMoveEvent askMoveEvent, int indexClient) {
        boolean winCondition = gameModel.checkWin(askMoveEvent.getRowStart(), askMoveEvent.getColumnStart(), askMoveEvent.getIndexWorker());
        if (winCondition) {
            gameModel.notifyWin(indexClient);
        } else {
            gameModel.notifyContinueMove(askMoveEvent);
        }
    }

    /**
     * This method checks if the worker can build: if he cannot, the player loses, receives a lose notification and the other players receive a notification about who lost.
     * If the worker can build, the player can continue his turn with the building
     *
     * @param indexClient  client index who is playing
     * @param indexWorker  worker index that has to build
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canBuild(int indexClient, int indexWorker, int rowWorker, int columnWorker) {
        boolean goAhead = gameModel.canBuild(indexWorker);
        if (!goAhead) {
            gameModel.notifyLoser(indexClient);
            int winnerClient = gameModel.getWinner();
            if (winnerClient != -1) {
                gameModel.notifyWin(winnerClient);
            } else {
                gameModel.notifyWhoHasLost(indexClient);
            }
        } else {
            gameModel.notifyCanBuild(indexWorker, rowWorker, columnWorker);
        }
    }

    /**
     * This method tells if a player can build before the worker move: if he can, he receives a notification about this possibility, otherwise he has to do a normal turn
     *
     * @param indexWorker  worker index that has to build or hat to be moved
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canBuildSpecialTurn(int indexWorker, int rowWorker, int columnWorker) {
        boolean specialCondition = gameModel.canBuildBeforeWorkerMove();
        if (specialCondition) {
            gameModel.notifyAskBuildBeforeMove(indexWorker, rowWorker, columnWorker);
        } else {
            gameModel.notifyBasicTurn(indexWorker, rowWorker, columnWorker);
        }
    }

    /**
     * This method sets all the boxes that a worker can reach with the building
     *
     * @param indexWorker worker index that has to build
     */
    public void setBoxBuilding(int indexWorker) {
        gameModel.setBoxBuilding(indexWorker);
        gameModel.notifySetBuilding();
    }

    /**
     * This method builds a block in a box
     *
     * @param indexClient        client index who is playing
     * @param indexWorker        worker index that builds
     * @param rowWorker          row of the box where the player wants to build
     * @param columnWorker       column of the box where the player wants to build
     * @param row                row of the box where the player wants to build
     * @param column             column of the box where the player wants to build
     * @param isSpecialTurn      true if the player built before the worker move, otherwise is false
     * @param indexPossibleBlock index of the block that the player wants to build
     */
    public void buildBlock(int indexClient, int indexWorker, int rowWorker, int columnWorker, int row, int column, boolean isSpecialTurn, int indexPossibleBlock) {
        gameModel.setIndexPossibleBlock(indexPossibleBlock);
        boolean built = gameModel.buildBlock(indexWorker, row, column);
        AskBuildEvent askBuildEvent;
        if (built) {
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, true, isSpecialTurn);
        } else {
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, false, isSpecialTurn);
        }

        gameModel.notifyBuildBlock(askBuildEvent, indexClient);
    }

    /**
     * This method checks if someone could win if there are complete towers on the board
     *
     * @param askBuildEvent object with all the information about the building move
     */
    public void checkWinAfterBuild(AskBuildEvent askBuildEvent) {
        boolean winCondition = gameModel.checkWinAfterBuild();
        if (winCondition) {
            int winnerClient = gameModel.getWinner();
            gameModel.notifyWin(winnerClient);
        } else {
            gameModel.notifyContinueBuild(askBuildEvent);
        }
    }

    /**
     * This method is recall when a player meant to be dead
     *
     * @param indexPlayer the index of the player that is meant to be dead
     */

    public void setDeadPlayer(int indexPlayer) {
        gameModel.setDeadPlayer(indexPlayer);
    }

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     *
     * @param indexClient client index who sends the heartbeat response
     */
    public void heartBeat(int indexClient) {
        gameModel.controlHeartBeat(indexClient);
    }
}

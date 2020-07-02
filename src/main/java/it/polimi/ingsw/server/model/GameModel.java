package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This interface is composed by all the game methods that the controller can call
 */
public interface GameModel {
    /**
     * Board getter
     *
     * @return Board
     */

    Board getBoard();

    /**
     * Column getter
     *
     * @param indexWorker index of the worker
     * @return integer of the column
     */
    int getColumnWorker(int indexWorker);

    /**
     * Row getter
     *
     * @param indexWorker index of the worker
     * @return integer of the
     */
    int getRowWorker(int indexWorker);

    /**
     * Player array getter
     *
     * @return array of player
     */
    ArrayList<Player> getPlayerArray();

    /**
     * Number of players getter
     *
     * @return number of players
     */
    int getNPlayers();

    /**
     * Number of players setter
     *
     * @param nPlayers number of players
     */
    void setNPlayers(int nPlayers);

    /**
     * This method puts in isPlaying the first player who has to play and in goingState the game
     */
    void startGame();

    /**
     * Method to update index client in the players array
     * @param indexClient client dead
     */
    void updateIndexClient(int indexClient);

    /**
     * This method instances the player
     *
     * @param indexClient client index
     * @param timer       start the timer
     * @param timerTask   timer that sends heartbeats
     */
    void addPlayer(int indexClient, Timer timer, TimerTask timerTask);

    /**
     * This method adds the player and sets all the information
     *
     * @param name        player name
     * @param age         player age
     * @param indexClient client index of the player
     * @return false if the player is not added because has a similar name with another player, otherwise returns true
     */
    boolean addPlayer(String name, int age, int indexClient);

    /**
     * This method checks if all the players are been added
     *
     * @return true if all the players are been added
     */
    boolean checkAckPlayer();

    /**
     * This method removes all the players that cannot play because out of game player size
     */
    void removeExtraPlayer();

    /**
     * This method removes a player if he is in a game
     *
     * @param indexPlayer player index that has to be removed
     */
    void remove(int indexPlayer);

    /**
     * This method counts how many players has been added
     *
     * @return true if there are all the players in the game, otherwise returns false
     */
    boolean askState();

    /**
     * This method searches the player that has a determinate name
     *
     * @param name player name that you are looking for
     * @return index of the array where the player is
     */
    int searchByName(String name);

    /**
     * This method searches the player that has a determinate client index
     *
     * @param indexClient client index that you are looking for
     * @return index of the array where the player is
     */
    int searchByClientIndex(int indexClient);

    /**
     * This method searches the player that has a determinate player index
     *
     * @param playerIndex player index that you are looking for
     * @return client index of the player
     */
    int searchByPlayerIndex(int playerIndex);

    /**
     * This method gets all the cards of the game
     *
     * @return all the cards of the game
     */
    ArrayList<String> getCards();

    /**
     * This method puts some player information and the game board in a object that is going to send to the clients
     *
     * @param reach it tells if the client has to print the reachable boxes
     * @return object message that has to be sent to the clients
     */
    UpdateBoardEvent gameData(boolean reach);

    /**
     * This method gets the temporary cards chosen by the first player
     *
     * @return two or three cards chosen by the first player
     */
    ArrayList<String> getTempCard();

    /**
     * This method adds the chosen cards in tempCard array
     *
     * @param tempCard two or three cards that the first player chooses
     * @return client index of the player that has to play
     */
    int chooseTempCard(ArrayList<Integer> tempCard);

    /**
     * This method assigns the chosen card to the player
     *
     * @param godCard index of the card chosen by the player
     * @return client index of the player that has to play
     */
    int chooseCard(int godCard);

    /**
     * This method tells which player is playing
     *
     * @return player index of the player who is playing
     */
    int whoIsPlaying();

    /**
     * This method puts in isPlaying state the player who has to play next
     */
    void goPlayingNext();

    /**
     * This method sets two workers in two positions chosen by the player
     *
     * @param box1 first worker box
     * @param box2 second worker box
     * @return false if the player chooses a box already occupied
     */
    boolean initializeWorker(Box box1, Box box2);

    /**
     * State getter
     *
     * @return game state
     */
    GameState getState();

    /**
     * Position of the worker
     *
     * @param indexPlayer index of the worker
     * @return array of box
     */
    ArrayList<Box> getWorkersPos(int indexPlayer);

    /**
     * This method is used to control if a box is reachable
     *
     * @param row    row of the worker
     * @param column column of the worker
     * @return true if reachable
     */
    boolean isReachable(int row, int column);

    /**
     * This method checks if at least one of the two workers can move
     *
     * @return true if a worker can move, otherwise returns false
     */
    boolean canMove();

    /**
     * This method checks if the worker can move
     *
     * @param indexWorker worker index that the player has to move
     * @return true if the worker can move, otherwise returns false
     */
    boolean canMoveSpecialTurn(int indexWorker);

    /**
     * This method tells if a player can build before the worker move
     *
     * @return true if the player can build, otherwise returns false
     */
    boolean canBuildBeforeWorkerMove();

    /**
     * This method sets all the boxes that a worker can reach
     *
     * @param indexWorker worker index that the player wants to move
     * @return true if is reachable
     */
    boolean setBoxReachable(int indexWorker);

    /**
     * This method moves a worker from a box to another
     *
     * @param indexWorker worker index that the player moves
     * @param row         row of the box where the player wants to reach
     * @param column      column of the box where the player wants to reach
     * @return false if the player can do another move, otherwise returns true
     */
    boolean movePlayer(int indexWorker, int row, int column);

    /**
     * This method checks if the worker can build
     *
     * @param indexWorker worker index that has to build
     * @return true if the worker can build, otherwise returns false
     */
    boolean canBuild(int indexWorker);

    /**
     * This method sets all the boxes that a worker can reach with the building
     *
     * @param indexWorker worker index that has to build
     * @return true if is reachable
     */
    boolean setBoxBuilding(int indexWorker);

    /**
     * This method builds a block in a box
     *
     * @param indexWorker worker index that builds
     * @param row         row of the box where the player wants to build
     * @param column      column of the box where the player wants to build
     * @return false if the player can do another move, otherwise returns true
     */
    boolean buildBlock(int indexWorker, int row, int column);

    /**
     * This method sets the index of the possible block that the player wants to build
     *
     * @param indexPossibleBlock possible block index
     */
    void setIndexPossibleBlock(int indexPossibleBlock);

    /**
     * This method checks if a player won
     *
     * @param rowStart    row of the starting box
     * @param columnStart column of the starting box
     * @param indexWorker worker index that the players moved
     * @return true if the players won, otherwise returns false
     */
    boolean checkWin(int rowStart, int columnStart, int indexWorker);

    /**
     * This method checks if someone could win if there are complete towers on the board
     *
     * @return true if a player won, otherwise returns false
     */
    boolean checkWinAfterBuild();

    /**
     * Winner getter
     *
     * @return integer of the winner
     */
    int getWinner();

    /**
     * Player dead setter
     *
     * @param indexPlayer index of the player
     */
    void setDeadPlayer(int indexPlayer);

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     *
     * @param indexClient client index who sends the heartbeat response
     */
    void controlHeartBeat(int indexClient);

    /**
     * This method counts how many heartbeats a client misses
     *
     * @param indexPlayer player index who sends the heartbeat response
     * @return true if the client misses the maximum number of heartbeats that could miss, otherwise returns false
     */
    boolean incrementHeartBeat(int indexPlayer);

    /**
     * This method resets all the class so if a game ends, the players can start a new game
     */
    void reset();
}

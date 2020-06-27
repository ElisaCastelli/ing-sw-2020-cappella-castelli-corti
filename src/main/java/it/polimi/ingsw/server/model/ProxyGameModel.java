package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.Observer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class is composed by all the GameModel and Subject methods
 */
public class ProxyGameModel implements GameModel, Subject {

    private GameModel gameModel;
    private final Object LOCK = new Object();
    private Observer observer;

    /**
     * Constructor of the class
     */
    public ProxyGameModel() {
        this.gameModel = new Game();
    }

    /**
     * This method is used set the observer
     *
     * @param observer observer of the class
     */

    @Override
    public void subscribeObserver(Observer observer) {
        this.observer = observer;
    }

    /**
     * Board getter
     *
     * @return board
     */
    @Override
    public Board getBoard() {
        return gameModel.getBoard();
    }

    /**
     * Column of the worker
     *
     * @param indexWorker index of the worker
     * @return integer of the column
     */
    @Override
    public int getColumnWorker(int indexWorker) {
        return gameModel.getColumnWorker(indexWorker);
    }

    /**
     * Row of the worker
     *
     * @param indexWorker index of the worker
     * @return integer of the row
     */
    @Override
    public int getRowWorker(int indexWorker) {
        return gameModel.getRowWorker(indexWorker);
    }

    /**
     * Players array getter
     *
     * @return array of player
     */
    @Override
    public ArrayList<Player> getPlayerArray() {
        return gameModel.getPlayerArray();
    }

    /**
     * N players getter
     *
     * @return number of players
     */
    @Override
    public int getNPlayers() {
        return gameModel.getNPlayers();
    }

    /**
     * N player setter
     *
     * @param nPlayers number of players
     */
    @Override
    public void setNPlayers(int nPlayers) {
        gameModel.setNPlayers(nPlayers);
    }

    /**
     * This method starts the timer of the new player and adds the new player in the array in Game class
     *
     * @param indexClient client index of the player
     * @param timer       starting timer
     */
    public void addPlayerProxy(int indexClient, Timer timer) {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    int indexPlayer = searchByClientIndex(indexClient);
                    if (indexPlayer != -1) {
                        boolean connected = gameModel.incrementHeartBeat(indexPlayer);
                        if (!connected) {
                            observer.updateUnreachableClient(indexClient);
                        }
                    } else {
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
        };

        synchronized (LOCK) {
            gameModel.addPlayer(indexClient, timer, timerTask);
            incrementHeartBeats(timer, timerTask);
        }
    }

    /**
     * This method instances the player
     *
     * @param indexClient client index
     * @param timer       start the timer
     * @param timerTask   timer that sends heartbeats
     */
    @Override
    public void addPlayer(int indexClient, Timer timer, TimerTask timerTask) {

    }

    /**
     * This method overrides gameModel method
     *
     * @param indexPlayer player index who sends the heartbeat response
     * @return always false
     */
    @Override
    public boolean incrementHeartBeat(int indexPlayer) {
        return false;
    }

    /**
     * This method adds the player and sets all the information
     *
     * @param name        player name
     * @param age         player age
     * @param indexClient client index of the player
     * @return false if the player is not added because has a similar name with another player, otherwise returns true
     */
    @Override
    public boolean addPlayer(String name, int age, int indexClient) {
        return gameModel.addPlayer(name, age, indexClient);
    }

    /**
     * This method removes all the players that cannot play because out of game player size
     */
    @Override
    public void removeExtraPlayer() {
        gameModel.removeExtraPlayer();
    }

    /**
     * This method removes a player if he is in a game
     *
     * @param indexClient client index that has to be removed
     */
    @Override
    public void remove(int indexClient) {
        int indexPlayer = searchByClientIndex(indexClient);
        gameModel.remove(indexPlayer);
        int sizePlayerArray = gameModel.getPlayerArray().size();
        if (sizePlayerArray != 0) {
            if (getNPlayers() == 0 && indexClient == 0) {
                observer.updateControlSetNPlayer();
            }
            //il gioco è già iniziato e ci sono tutti
            if (getNPlayers() != 0 && sizePlayerArray == getNPlayers() - 1) {
                reset();
                observer.closeGame();
            }
        } else {
            observer.reset();
        }
    }

    /**
     * This method counts how many players has been added
     *
     * @return true if there are all the players in the game, otherwise returns false
     */
    @Override
    public boolean askState() {
        return gameModel.askState();
    }

    /**
     * This method searches the player that has a determinate name
     *
     * @param name player name that you are looking for
     * @return index of the array where the player is
     */
    @Override
    public int searchByName(String name) {
        return gameModel.searchByName(name);
    }

    /**
     * This method searches the player that has a determinate client index
     *
     * @param indexClient client index that you are looking for
     * @return index of the array where the player is
     */
    @Override
    public int searchByClientIndex(int indexClient) {
        return gameModel.searchByClientIndex(indexClient);
    }

    /**
     * This method searches the player that has a determinate player index
     *
     * @param playerIndex player index that you are looking for
     * @return client index of the player
     */
    @Override
    public int searchByPlayerIndex(int playerIndex) {
        return gameModel.searchByPlayerIndex(playerIndex);
    }

    /**
     * This method gets all the cards of the game
     *
     * @return all the cards of the game
     */
    @Override
    public ArrayList<String> getCards(){
        return gameModel.getCards();
    }

    /**
     * This method puts some player information and the game board in a object that is going to send to the clients
     *
     * @param reach it tells if the client has to print the reachable boxes
     * @return object message that has to be sent to the clients
     */
    @Override
    public UpdateBoardEvent gameData(boolean reach) {
        return gameModel.gameData(reach);
    }

    /**
     * This method gets the temporary cards chosen by the first player
     *
     * @return two or three cards chosen by the first player
     */
    @Override
    public ArrayList<String> getTempCard() {
        return gameModel.getTempCard();
    }

    /**
     * This method adds the chosen cards in tempCard array
     *
     * @param tempCard two or three cards that the first player chooses
     * @return client index of the player that has to play
     */
    @Override
    public int chooseTempCard(ArrayList<Integer> tempCard) {
        return gameModel.chooseTempCard(tempCard);
    }

    /**
     * This method assigns the chosen card to the player
     *
     * @param godCard index of the card chosen by the player
     * @return client index of the player that has to play
     */
    @Override
    public int chooseCard(int godCard) {
        return gameModel.chooseCard(godCard);
    }

    /**
     * This method puts in isPlaying state the player who has to play next
     */
    @Override
    public void goPlayingNext() {
        gameModel.goPlayingNext();
    }

    /**
     * This method tells which player is playing
     *
     * @return player index of the player who is playing
     */
    @Override
    public int whoIsPlaying() {
        return gameModel.whoIsPlaying();
    }

    /**
     * This method puts in isPlaying the first player who has to play and in goingState the game
     */
    @Override
    public void startGame() {
        gameModel.startGame();
    }

    /**
     * This method sets two workers in two positions chosen by the player
     *
     * @param box1 first worker box
     * @param box2 second worker box
     * @return false if the player chooses a box already occupied
     */
    @Override
    public boolean initializeWorker(Box box1, Box box2) {
        return gameModel.initializeWorker(box1, box2);
    }

    /**
     * This method is used to control if is reachable
     *
     * @param row    integer of the row
     * @param column integer of the column
     * @return true if is reachable
     */
    @Override
    public boolean isReachable(int row, int column) {
        return gameModel.isReachable(row, column);
    }

    /**
     * This method tells if a player can build before the worker move
     *
     * @return true if the player can build, otherwise returns false
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        return gameModel.canBuildBeforeWorkerMove();
    }

    /**
     * This method checks if at least one of the two workers can move
     *
     * @return true if a worker can move, otherwise returns false
     */
    @Override
    public boolean canMove() {
        return gameModel.canMove();
    }

    /**
     * This method checks if the worker can move
     *
     * @param indexWorker worker index that the player has to move
     * @return true if the worker can move, otherwise returns false
     */
    @Override
    public boolean canMoveSpecialTurn(int indexWorker) {
        return gameModel.canMoveSpecialTurn(indexWorker);
    }

    /**
     * This method notifies to the observers that they have to update the board
     *
     * @param reach true if the client has to print the reachable boxes, otherwise is false
     */
    @Override
    public void notifyUpdateBoard(boolean reach) {
        observer.updateBoard(reach);
    }

    /**
     * This method sets all the boxes that a worker can reach
     *
     * @param indexWorker worker index that the player wants to move
     */
    @Override
    public boolean setBoxReachable(int indexWorker) {
        return gameModel.setBoxReachable(indexWorker);
    }

    /**
     * Position of the worker getter
     *
     * @param indexPlayer index of the player
     * @return array of box
     */

    @Override
    public ArrayList<Box> getWorkersPos(int indexPlayer) {
        return gameModel.getWorkersPos(indexPlayer);
    }

    /**
     * This method moves a worker from a box to another
     *
     * @param indexWorker worker index that the player moves
     * @param row         row of the box where the player wants to reach
     * @param column      column of the box where the player wants to reach
     * @return false if the player can do another move, otherwise returns true
     */
    @Override
    public boolean movePlayer(int indexWorker, int row, int column) {
        return gameModel.movePlayer(indexWorker, row, column);
    }

    /**
     * This method checks if the worker can build
     *
     * @param indexWorker worker index that has to build
     * @return true if the worker can build, otherwise returns false
     */
    @Override
    public boolean canBuild(int indexWorker) {
        return gameModel.canBuild(indexWorker);
    }

    /**
     * This method notifies to the observers that the player can build
     *
     * @param indexWorker  worker index that builds
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    @Override
    public void notifyCanBuild(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateCanBuild(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method sets all the boxes that a worker can reach with the building
     *
     * @param indexWorker worker index that has to build
     * @return true if is reachable
     */
    @Override
    public boolean setBoxBuilding(int indexWorker) {
        return gameModel.setBoxBuilding(indexWorker);
    }

    /**
     * This method builds a block in a box
     *
     * @param indexWorker worker index that builds
     * @param row         row of the box where the player wants to build
     * @param column      column of the box where the player wants to build
     * @return false if the player can do another move, otherwise returns true
     */
    @Override
    public boolean buildBlock(int indexWorker, int row, int column) {
        return gameModel.buildBlock(indexWorker, row, column);
    }

    /**
     * This method checks if a player won
     *
     * @param rowStart    row of the starting box
     * @param columnStart column of the starting box
     * @param indexWorker worker index that the players moved
     * @return true if the players won, otherwise returns false
     */
    @Override
    public boolean checkWin(int rowStart, int columnStart, int indexWorker) {
        return gameModel.checkWin(rowStart, columnStart, indexWorker);
    }

    /**
     * This method checks if someone could win if there are complete towers on the board
     *
     * @return true if a player won, otherwise returns false
     */
    @Override
    public boolean checkWinAfterBuild() {
        return gameModel.checkWinAfterBuild();
    }

    /**
     * Winner getter
     *
     * @return integer of the winner
     */
    @Override
    public int getWinner() {
        return gameModel.getWinner();
    }

    /**
     * dead Player setter
     *
     * @param indexPlayer index of the player
     */
    @Override
    public void setDeadPlayer(int indexPlayer) {
        gameModel.setDeadPlayer(indexPlayer);
    }

    /**
     * Sate getter
     *
     * @return game state
     */
    @Override
    public GameState getState() {
        return gameModel.getState();
    }

    /**
     * This method notifies to the observers that they have to update the players in the game
     */
    @Override
    public void notifyAddPlayer() {
        observer.updatePlayer();
    }

    /**
     * This method checks if all the players are been added
     *
     * @return true if all the players are been added
     */
    @Override
    public boolean checkAckPlayer() {
        return gameModel.checkAckPlayer();
    }

    /**
     * This method notifies to the observers that a new player is added in the game
     *
     * @param indexClient client index of the player who is playing
     */
    @Override
    public void notifyNewAddPlayer(int indexClient) {
        observer.updateNewAddPlayer(indexClient);
    }

    /**
     * This method notifies to the observers that they have to update their state
     *
     * @param indexClient client index of the player who is playing
     * @param indexPlayer player index that is playing
     */
    @Override
    public void notifyAskState(int indexClient, int indexPlayer) {
        observer.updateAskState(indexClient, indexPlayer);
    }

    /**
     * This method notifies to the observers that they have to update the temporary cards
     *
     * @param indexClient client index of the player who is playing
     */
    @Override
    public void notifyTempCard(int indexClient) {
        observer.updateTempCard(indexClient);
    }

    /**
     * This method notifies to the observers that they have to update the workers
     */
    @Override
    public void notifyAddWorker() {
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateInitializeWorker(indexClient, gameModel.whoIsPlaying());
    }

    /**
     * This method notifies to the observers that the workers are not initialized because at least one of the given boxes are already occupied
     */
    @Override
    public void notifyWorkersNotInitialized() {
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateNotInitializeWorker(indexClient);
    }

    /**
     * This method notifies to the observers that they have to update the reachable boxes because of a worker move
     *
     * @param indexWorker worker index that is moved
     * @param secondMove  true if it is a second worker move because of a God ability, otherwise it is false
     */
    @Override
    public void notifySetReachable(int indexWorker, boolean secondMove) {
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateReachable(indexClient, gameModel.whoIsPlaying(), indexWorker, secondMove);
    }

    /**
     * This method notifies to the observers that they have pick the other worker because this one can't move
     *
     * @param indexWorker worker index that is moved
     * @param secondMove  true if it is a second worker move because of a God ability, otherwise it is false
     */
    @Override
    public void notifyNotReachable(int indexWorker, boolean secondMove) {
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateNotReachable(indexClient, gameModel.whoIsPlaying(), indexWorker, secondMove);
    }

    /**
     * This method notifies to the observers that it is a special turn for a player
     *
     * @param row               row of the box where the chosen worker is
     * @param column            column of the box where the chosen worker is
     * @param indexWorkerToMove worker index that is chosen
     */
    @Override
    public void notifySpecialTurn(int row, int column, int indexWorkerToMove) {
        observer.updateSpecialTurn(row, column, indexWorkerToMove);
    }

    /**
     * This method notifies to the observers that it is a basic turn for a player
     *
     * @param indexWorker  worker index that is chosen to move
     * @param rowWorker    row of the box where the chosen worker is
     * @param columnWorker column of the box where the chosen worker is
     */
    @Override
    public void notifyBasicTurn(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateBasicTurn(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method notifies to the observers if they can build before the worker move or not
     *
     * @param indexWorker  worker index that is chosen to move
     * @param rowWorker    row of the box where the chosen worker is
     * @param columnWorker column of the box where the chosen worker is
     */
    @Override
    public void notifyAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateAskBuildBeforeMove(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method notifies to the observers that the worker move is done
     *
     * @param askMoveEvent object with all the information about the worker move
     * @param clientIndex  client index of the player who is playing
     */
    @Override
    public void notifyMovedWorker(AskMoveEvent askMoveEvent, int clientIndex) {
        observer.updateMove(askMoveEvent, clientIndex);
    }

    /**
     * This method notifies to the observers that a player wins
     *
     * @param winnerClient client index of the winner
     */
    @Override
    public void notifyWin(int winnerClient) {
        observer.updateWin(winnerClient);
    }

    /**
     * This method notifies to the observers that a player loses and sends a message to who has lost
     *
     * @param indexClient client index of the loser
     */
    @Override
    public void notifyLoser(int indexClient) {
        observer.updateLoser(indexClient);
    }

    /**
     * This method notifies to the observers that a player loses and sends a message to the other players telling who has lost
     *
     * @param loserClient client index of the loser
     */
    @Override
    public void notifyWhoHasLost(int loserClient) {
        observer.updateWhoHasLost(loserClient);
    }

    /**
     * This method notifies to the observers that a player can do another worker move
     *
     * @param askMoveEvent object with all the information about the worker move
     */
    @Override
    public void notifyContinueMove(AskMoveEvent askMoveEvent) {
        observer.updateContinueMove(askMoveEvent);
    }

    /**
     * This method notifies to the observers they have to update the reachable boxes because of a build move
     */
    @Override
    public void notifySetBuilding() {
        observer.updateSetBuilding();
        observer.updateBoard(true);
    }

    /**
     * This method notifies to the observers that the build move is done
     *
     * @param askBuildEvent object with all the information about the build move
     * @param clientIndex   client index of the player who is playing
     */
    @Override
    public void notifyBuildBlock(AskBuildEvent askBuildEvent, int clientIndex) {
        observer.updateBuild(askBuildEvent, clientIndex);
    }

    /**
     * This method notifies to the observers that a player can do another build move
     *
     * @param askBuildEvent object with all the information about the build move
     */
    @Override
    public void notifyContinueBuild(AskBuildEvent askBuildEvent) {
        observer.updateContinueBuild(askBuildEvent);
    }

    /**
     * This method notifies to the observers that the player turn is started
     */
    @Override
    public void notifyStartTurn() {
        observer.updateStartTurn();
    }

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     *
     * @param indexClient client index who sends the heartbeat response
     */
    @Override
    public void controlHeartBeat(int indexClient) {
        gameModel.controlHeartBeat(indexClient);
    }

    /**
     * This method sets the timers to an added players
     *
     * @param timer     start the timer
     * @param timerTask timer that sends heartbeats
     */
    public void incrementHeartBeats(Timer timer, TimerTask timerTask) {
        timer.scheduleAtFixedRate(timerTask, 10000, 100000);
    }

    /**
     * This method sets the index of the possible block that the player wants to build
     *
     * @param indexPossibleBlock possible block index
     */
    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        gameModel.setIndexPossibleBlock(indexPossibleBlock);
    }

    /**
     * This method resets all the class so if a game ends, the players can start a new game
     */
    @Override
    public void reset() {
        gameModel.reset();
    }
}

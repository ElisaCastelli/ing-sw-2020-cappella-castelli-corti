package it.polimi.ingsw.server.model;

import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.gameState.GameStateManager;
import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.parse.CardCreator;

/**
 * This is the main class that represents the game
 */
public class Game implements GameModel {

    /**
     * This attribute is the playing board
     */
    private Board board;
    /**
     * this is the array list of the players
     */
    private ArrayList<Player> players;
    private int ackCounter;
    /**
     * this is the array list of the players
     */
    private ArrayList<Player> playersDead;
    /**
     * This integer attribute is the number of the players
     */
    private int nPlayers;
    /**
     * Array of all cards
     */
    private ArrayList<God> godsArray;
    /**
     * Temporary array of chosen cards
     */
    private ArrayList<God> tempCard;
    /**
     * Array of drawn cards
     */
    private GameStateManager stateManager;
    /**
     * Attribute for the cards parsing
     */
    private CardCreator parser = new CardCreator();

    /**
     * Constructor without parameters
     */
    public Game() {
        board = new Board();
        players = new ArrayList<>();
        playersDead = new ArrayList<>();
        nPlayers = 0;
        ackCounter = 0;
        tempCard = new ArrayList<>();
        godsArray = new ArrayList<>();
        stateManager = new GameStateManager(players, playersDead);
    }

    /**
     * This method checks if a player has the same name of another player
     *
     * @param namePlayer player name
     * @return true if there is already that name, otherwise returns false
     */
    public boolean someoneWithYourName(String namePlayer) {
        boolean exist = false;
        int i = 0;
        while (i < players.size() && !exist) {
            if (namePlayer.equals(players.get(i).getName())) {
                exist = true;
            }
            i++;
        }
        return exist;
    }

    /**
     * This method puts in isPlaying state the player who has to play next
     */
    @Override
    public void goPlayingNext() {
        int indexPlay = whoIsPlaying();
        if (!players.get(indexPlay).amIDead()) {
            players.get(indexPlay).goWaiting();
        }

        if (indexPlay == nPlayers - 1) {
            if (players.get(0).amIDead()) {
                players.get(1).goPlay();
            } else {
                players.get(0).goPlay();
            }
        } else {
            int nextPlayer = indexPlay + 1;
            if (players.get(nextPlayer).amIDead()) {
                if (nextPlayer == nPlayers - 1) {
                    players.get(0).goPlay();
                } else {
                    players.get(nextPlayer + 1).goPlay();
                }
            } else {
                players.get(indexPlay + 1).goPlay();
            }
        }
    }

    /**
     * This method puts in isPlaying state the previous player of who has lost
     *
     * @param indexLoser index of the player who has lost
     */
    public void fakePlaying(int indexLoser) {
        if (indexLoser == 0) {
            players.get(nPlayers - 1).goPlay();
        } else {
            players.get(indexLoser - 1).goPlay();
        }
    }

    /**
     * Board getter
     *
     * @return Board
     */
    @Override
    public Board getBoard() {
        return board;
    }

    /**
     * Row of the worker getter
     *
     * @param indexWorker index of the worker
     * @return integer of the row
     */
    @Override
    public int getRowWorker(int indexWorker) {
        return players.get(whoIsPlaying()).getWorkerBox(indexWorker - 1).getRow();
    }

    /**
     * Column of the worker getter
     *
     * @param indexWorker index of the worker
     * @return integer of the column
     */
    @Override
    public int getColumnWorker(int indexWorker) {
        return players.get(whoIsPlaying()).getWorkerBox(indexWorker - 1).getColumn();
    }

    /**
     * Method to sort gamers by age
     */
    public void sortGamers() {
        players.sort(Comparator.comparingInt(Player::getAge));
    }

    /**
     * Number of players setter
     *
     * @param nPlayers number of players
     */
    @Override
    public void setNPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    /**
     * Number of players setter
     *
     * @return number of players
     */
    @Override
    public int getNPlayers() {
        return nPlayers;
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
        players.add(new Player(indexClient, timer, timerTask));
    }

    /**
     * This method adds the player and sets all the information
     *
     * @param name        player name
     * @param age         player age
     * @param indexClient client index of the player
     * @return false if the player is not added because has a similar name with another player, otherwise returns true
     */
    public boolean addPlayer(String name, int age, int indexClient) {
        if (!someoneWithYourName(name)) {
            int playerIndex = searchByClientIndex(indexClient);
            players.get(playerIndex).setName(name);
            players.get(playerIndex).setAge(age);
            ackCounter++;
            sortGamers();
            for (Player player : players) {
                int indexPlayer = searchByName(player.getName());
                player.setIndexPlayer(indexPlayer);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks if all the players are been added
     *
     * @return true if all the players are been added
     */
    @Override
    public boolean checkAckPlayer() {
        if (ackCounter == nPlayers) {
            ackCounter = 0;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method removes all the players that cannot play because out of game player size
     */
    @Override
    public void removeExtraPlayer() {
        int playerSize = players.size();
        for (int index = playerSize - 1; index >= nPlayers; index--) {
            players.get(index).getTimerTask().cancel();
            players.get(index).getTimer().cancel();
            players.get(index).getTimer().purge();
            players.remove(index);
        }
    }

    /**
     * This method removes a player if he is in a game
     *
     * @param indexPlayer player index that has to be removed
     */
    @Override
    public void remove(int indexPlayer) {
        players.get(indexPlayer).getTimerTask().cancel();
        players.get(indexPlayer).getTimer().cancel();
        players.get(indexPlayer).getTimer().purge();
        players.remove(indexPlayer);
        updateIndexInArray(indexPlayer);
    }

    /**
     * This method changes various indexes in the players after the player that has to be removed
     *
     * @param indexPlayer player index that has to be removed
     */
    public void updateIndexInArray(int indexPlayer) {
        for (Player player : players) {
            if (player.getIndexPlayer() > indexPlayer) {
                player.setIndexClient(player.getIndexPlayer() - 1);
                player.setIndexPlayer(player.getIndexPlayer() - 1);
            }
        }
    }


    /**
     * This method puts in isPlaying the first player who has to play and in goingState the game
     */
    @Override
    public void startGame() {
        players.get(0).goPlay();
        stateManager.start();
    }

    /**
     * This method counts how many players has been added
     *
     * @return true if there are all the players in the game, otherwise returns false
     */
    @Override
    public synchronized boolean askState() {
        ackCounter++;
        if (ackCounter == nPlayers) {
            ackCounter = 0;
            return true;
        }
        return false;
    }

    /**
     * Players array getter
     *
     * @return array of players
     */
    @Override
    public ArrayList<Player> getPlayerArray() {
        return players;
    }

    /**
     * This method searches the player that has a determinate name
     *
     * @param name player name that you are looking for
     * @return index of the array where the player is
     */
    @Override
    public int searchByName(String name) {
        return players.stream().map(Player::getName).collect(Collectors.toList()).indexOf(name);
    }

    /**
     * This method searches the player that has a determinate client index
     *
     * @param clientIndex client index that you are looking for
     * @return index of the array where the player is
     */
    @Override
    public int searchByClientIndex(int clientIndex) {
        return players.stream().map(Player::getIndexClient).collect(Collectors.toList()).indexOf(clientIndex);
    }

    /**
     * This method searches the player that has a determinate player index
     *
     * @param playerIndex player index that you are looking for
     * @return client index of the player
     */
    @Override
    public int searchByPlayerIndex(int playerIndex) {
        for (Player player : players) {
            if (player.getIndexPlayer() == playerIndex)
                return player.getIndexClient();
        }
        return 0;
    }

    /**
     * This method puts some player information and the game board in a object that is going to send to the clients
     *
     * @param reach it tells if the client has to print the reachable boxes
     * @return object message that has to be sent to the clients
     */
    @Override
    public UpdateBoardEvent gameData(boolean reach) {
        ArrayList<User> users = new ArrayList<>();
        for (Player player : players) {
            User user = new User(player.getName(), player.getGod().getName(), player.getIndexClient());
            if (player.amIDead())
                user.setDead(true);
            users.add(user);
        }
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent(users, board, reach);
        int playerIndex = whoIsPlaying();
        updateBoardEvent.setCurrentClientPlaying(searchByPlayerIndex(playerIndex));
        return updateBoardEvent;
    }

    /**
     * This method adds the chosen cards in tempCard array
     *
     * @param NCard two or three cards that the first player chooses
     * @return client index of the player that has to play
     */
    @Override
    public int chooseTempCard(ArrayList<Integer> NCard) {
        for (int i = 0; i < nPlayers; i++) {
            if (NCard.get(i) < godsArray.size()) {
                tempCard.add(godsArray.get(NCard.get(i)));
            }
        }
        goPlayingNext();
        int playerIndex = whoIsPlaying();
        return searchByPlayerIndex(playerIndex);
    }

    /**
     * This method assigns the chosen card to the player
     *
     * @param indexCard index of the card chosen by the player
     * @return client index of the player that has to play
     */
    public int chooseCard(int indexCard) {
        int playerIndex = whoIsPlaying();
        players.get(playerIndex).setGod(tempCard.get(indexCard));
        tempCard.remove(tempCard.get(indexCard));
        if (tempCard.size() != 0) {
            goPlayingNext();
            playerIndex = whoIsPlaying();
        }
        return searchByPlayerIndex(playerIndex);
    }

    /**
     * This method does the parsing of the cards
     */
    public void loadCards() {
        godsArray = parser.parseCard();
    }

    /**
     * This method gets all the cards of the game
     *
     * @return all the cards of the game
     */
    @Override
    public ArrayList<String> getCards() {
        if (godsArray.size() == 0) {
            loadCards();
        }
        ArrayList<String> cards = new ArrayList<>();
        for (God god : godsArray) {
            cards.add(god.getName());
        }
        return cards;
    }

    /**
     * This method gets the temporary cards chosen by the first player
     *
     * @return two or three cards chosen by the first player
     */
    @Override
    public ArrayList<String> getTempCard() {
        ArrayList<String> temporary = new ArrayList<>();
        for (God god : tempCard) {
            temporary.add(god.getName());
        }
        return temporary;
    }

    /**
     * This method tells which player is playing
     *
     * @return player index of the player who is playing
     */
    @Override
    public int whoIsPlaying() {
        int indexPlay = 0;
        boolean found = false;
        while (!found && indexPlay < players.size()) {
            if (players.get(indexPlay).isPlaying()) {
                found = true;
            } else {
                indexPlay++;
            }
        }
        if (found)
            return indexPlay;
        else
            return -1;
    }

    /**
     * Position of the worker
     *
     * @param indexPlayer index of the worker
     * @return array of box
     */
    @Override
    public ArrayList<Box> getWorkersPos(int indexPlayer) {
        return players.get(indexPlayer).getWorkersBox();
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
        int indexPlayer = whoIsPlaying();
        return players.get(indexPlayer).initializeWorker(box1, box2, board);
    }

    /**
     * This method is to control if a box is reachable
     *
     * @param row    row of the worker
     * @param column column of the worker
     * @return true if is reachable
     */

    @Override
    public boolean isReachable(int row, int column) {
        return board.getBox(row, column).isReachable();
    }

    /**
     * Game state getter
     *
     * @return game state
     */
    @Override
    public GameState getState() {
        return stateManager.getCurrentState();
    }

    /**
     * This method tells if a player can build before the worker move
     *
     * @return true if the player can build, otherwise returns false
     */
    @Override
    public boolean canBuildBeforeWorkerMove() {
        int indexPlayer = whoIsPlaying();
        return stateManager.canBuildBeforeWorkerMove(indexPlayer);
    }

    /**
     * This method checks if at least one of the two workers can move
     *
     * @return true if a worker can move, otherwise returns false
     */
    @Override
    public boolean canMove() {
        goPlayingNext();
        int playerIndex = whoIsPlaying();
        boolean canMove = stateManager.canMove(playerIndex);
        if (!canMove) {
            setDeadPlayer(playerIndex);
            if (nPlayers == 2 || playersDead.size() == 2) {
                thereIsAWinner();
            } else {
                fakePlaying(playerIndex);
            }
        }
        return canMove;
    }

    /**
     * This method checks if the worker can move
     *
     * @param indexWorker worker index that the player has to move
     * @return true if the worker can move, otherwise returns false
     */
    @Override
    public boolean canMoveSpecialTurn(int indexWorker) {
        int playerIndex = whoIsPlaying();
        boolean canMove = stateManager.canMoveSpecialTurn(playerIndex, indexWorker);
        if (!canMove) {
            setDeadPlayer(playerIndex);
            if (nPlayers == 2 || playersDead.size() == 2) {
                thereIsAWinner();
            } else {
                fakePlaying(playerIndex);
            }
        }
        return canMove;
    }

    /**
     * This method sets all the boxes that a worker can reach
     *
     * @param indexWorker worker index that the player wants to move
     */
    @Override
    public boolean setBoxReachable(int indexWorker) {
        board.clearReachable();
        int indexPlayer = whoIsPlaying();
        return stateManager.setBoxReachable(indexPlayer, indexWorker);
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
        int indexPlayer = whoIsPlaying();
        return stateManager.movePlayer(indexPlayer, indexWorker, row, column, board);
    }

    /**
     * This method checks if the worker can build
     *
     * @param indexWorker worker index that has to build
     * @return true if the worker can build, otherwise returns false
     */
    @Override
    public boolean canBuild(int indexWorker) {
        int indexPlayer = whoIsPlaying();
        boolean canBuild = stateManager.canBuild(indexPlayer, indexWorker);
        if (!canBuild) {
            setDeadPlayer(indexPlayer);
            if (nPlayers == 2 || playersDead.size() == 2) {
                thereIsAWinner();
            } else {
                fakePlaying(indexPlayer);
            }
        }
        return canBuild;
    }

    /**
     * This method sets the winner if all the opponents die
     */
    public void thereIsAWinner() {
        for (Player player : players) {
            int mayBeTheWinner = 0;
            int i = 0;
            while (i <= (playersDead.size() - 1)) {
                if (playersDead.get(i).getIndexClient() != player.getIndexClient()) {
                    mayBeTheWinner = mayBeTheWinner + 1;
                }
                if (mayBeTheWinner == playersDead.size()) {
                    player.goWin();
                    stateManager.goEnd(player.getIndexClient());
                }
                i++;
            }
        }
    }

    /**
     * This method sets all the boxes that a worker can reach with the building
     *
     * @param indexWorker worker index that has to build
     */
    @Override
    public boolean setBoxBuilding(int indexWorker) {
        board.clearReachable();
        int indexPlayer = whoIsPlaying();
        return stateManager.setBoxBuilding(indexPlayer, indexWorker);
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
        int indexPlayer = whoIsPlaying();
        return stateManager.buildBlock(indexPlayer, indexWorker, row, column, board);
    }

    /**
     * This method sets the index of the possible block that the player wants to build
     *
     * @param indexPossibleBlock possible block index
     */
    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        int indexPlayer = whoIsPlaying();
        stateManager.setIndexPossibleBlock(indexPlayer, indexPossibleBlock);
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
        return stateManager.checkWin(whoIsPlaying(), board.getBox(rowStart, columnStart), indexWorker);
    }

    /**
     * This method checks if someone could win if there are complete towers on the board
     *
     * @return true if a player won, otherwise returns false
     */
    @Override
    public boolean checkWinAfterBuild() {
        return stateManager.checkWinAfterBuild();
    }

    /**
     * Winner getter
     *
     * @return integer of th winner
     */
    @Override
    public int getWinner() {
        return stateManager.getWinner();
    }

    /**
     * Player dead setter
     *
     * @param indexPlayer index of the player
     */
    @Override
    public void setDeadPlayer(int indexPlayer) {
        stateManager.setDeadPlayer(indexPlayer);
    }

    /**
     * This method resets the heartbeat counter if it receives a response from the client
     *
     * @param indexClient client index who sends the heartbeat response
     */
    @Override
    public void controlHeartBeat(int indexClient) {
        try {
            int indexPlayer = searchByClientIndex(indexClient);
            players.get(indexPlayer).controlHeartBeat();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("the client is not anymore in the array because it was closed");
        }
    }

    /**
     * This method counts how many heartbeats a client misses
     *
     * @param indexPlayer player index who sends the heartbeat
     * @return true if the client misses the maximum number of heartbeats that could miss, otherwise returns false
     */
    @Override
    public boolean incrementHeartBeat(int indexPlayer) {
        try {
            return players.get(indexPlayer).incrementMissedHeartBeat();
        } catch (IndexOutOfBoundsException e) {
            System.out.println("the client is not anymore in the array because it was closed");
        }
        return true;
    }

    /**
     * This method resets all the class so if a game ends, the players can start a new game
     */
    @Override
    public void reset() {
        board.clear();
        players.clear();
        playersDead.clear();
        nPlayers = 0;
        ackCounter = 0;
        tempCard.clear();
        stateManager = new GameStateManager(players, playersDead);
    }
}


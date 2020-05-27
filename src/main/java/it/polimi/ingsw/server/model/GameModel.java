package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

public interface GameModel {
    Board getBoard();
    ArrayList<Player> getPlayerArray();
    int getNPlayers();
    void setNPlayers(int nPlayers);
    void startGame();
    boolean addPlayer(String name, int age, int indexClient);
    boolean askState();
    int searchByName(String name);
    int searchByClientIndex(int indexClient);
    public int searchByPlayerIndex(int playerIndex);
    ArrayList<String> getCards()throws Exception;
    UpdateBoardEvent gameData();
    ArrayList<String> getTempCard();
    ArrayList<String> getCardUsed();
    int chooseTempCard(ArrayList<Integer> tempCard);
    int chooseCard(int playerIndex, int godCard)throws Exception ;
    int whoIsPlaying();
    void goPlayingNext();
    boolean initializeWorker(int indexPlayer,Box box1, Box box2);
    GameState getState();
    ArrayList<Box> getWorkersPos(int indexPlayer);
    boolean isReachable(int row, int column);
    boolean canMove(int indexPlayer);
    void setBoxReachable(int indexPlayer, int indexWorker);
    boolean movePlayer(int indexPlayer, int indexWorker, int row, int column);
    boolean canBuild(int indexPlayer, int indexWorker);
    void setBoxBuilding(int indexPlayer, int indexWorker);
    boolean buildBlock(int indexPlayer, int indexWorker, int row, int column);
    boolean checkWin(int indexPlayer, int rowStart, int columnStart, int indexWorker);
    boolean checkWinAfterBuild();
    int getWinner();
    void setDeadPlayer(int indexPlayer);
    void setPause();
}

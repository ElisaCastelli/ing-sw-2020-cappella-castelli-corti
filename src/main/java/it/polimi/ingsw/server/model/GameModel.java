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
import java.util.Timer;

public interface GameModel {
    Board getBoard();
    ArrayList<Player> getPlayerArray();
    int getNPlayers();
    void setNPlayers(int nPlayers);
    void startGame();
    void addPlayer(int indexClient, Timer timer);
    boolean addPlayer(String name, int age, int indexClient);
    void removeExtraPlayer();
    boolean askState();
    int searchByName(String name);
    int searchByClientIndex(int indexClient);
    public int searchByPlayerIndex(int playerIndex);
    ArrayList<String> getCards()throws Exception;
    UpdateBoardEvent gameData(boolean reach);
    ArrayList<String> getTempCard();
    ArrayList<String> getCardUsed();
    int chooseTempCard(ArrayList<Integer> tempCard);
    int chooseCard(int playerIndex, int godCard)throws Exception ;
    int whoIsPlaying();
    void goPlayingNext();
    boolean initializeWorker(Box box1, Box box2);
    GameState getState();
    ArrayList<Box> getWorkersPos(int indexPlayer);
    boolean isReachable(int row, int column);
    boolean canMove();
    boolean canMoveSpecialTurn(int indexWorker);
    boolean canBuildBeforeWorkerMove();
    void setBoxReachable(int indexWorker);
    boolean movePlayer(int indexWorker, int row, int column);
    boolean canBuild(int indexWorker);
    void setBoxBuilding(int indexWorker);
    boolean buildBlock(int indexWorker, int row, int column);
    void setIndexPossibleBlock(int indexPossibleBlock);
    boolean checkWin(int rowStart, int columnStart, int indexWorker);
    boolean checkWinAfterBuild();
    int getWinner();
    void setDeadPlayer(int indexPlayer);
    void setPause();
    void controlHeartBeat(int indexClient, long timeStamp);
    boolean incrementHeartBeat(int indexClient, Timer timer );
}

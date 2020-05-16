package it.polimi.ingsw.server.model;

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
    void addPlayer(String name, int age);
    int searchByName(String name);
    ArrayList<String> getCards()throws Exception;
    ArrayList<String> getTempCard();
    ArrayList<String> getCardUsed();
    void chooseTempCard(ArrayList<Integer> tempCard);
    void chooseCard(int playerIndex, int godCard)throws Exception ;
    int whoIsPlaying();
    void goPlayingNext();
    boolean initializeWorker(int indexPlayer,Box box1, Box box2);
    GameState getState();
    ArrayList<Box> getWorkersPos(int indexPlayer);
    boolean canMove(int indexPlayer);
    void setBoxReachable(int indexPlayer, int indexWorker);
    boolean movePlayer(int indexPlayer, int indexWorker, int row, int column);
    boolean canBuild(int indexPlayer, int indexWorker);
    void setBoxBuilding(int indexPlayer, int indexWorker);
    boolean buildBlock(int indexPlayer, int indexWorker, int row, int column);
    boolean checkWin(int indexPlayer, Box startBox, int indexWorker);
    boolean checkWinAfterBuild();
    void setWinningPlayer(int indexPlayer);
    void setDeadPlayer(int indexPlayer);
    void setPause();
}

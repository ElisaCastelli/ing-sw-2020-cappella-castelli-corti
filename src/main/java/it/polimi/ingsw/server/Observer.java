package it.polimi.ingsw.server;


import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

public interface Observer {
    void subscribe();
    int updateNPlayer();
    void updateAddPlayer();
    void updatePlayerCard(int indexPlayer);
    void updateInizializaWorker();
    Board updateBoard();
    GameState updateState();
    void updateStartTurn();
    PlayerState updatePlayerState(int indexPlayer);
    void updateReachable();
    boolean updateCanMove(int indexPlayer);
    void updateMove();
    void updateSetBuilding();
    boolean updateCanBuild(int indexPlayer, int indexWorker);
    void updateBuild();
    void updateFinishTurn();
    //void updateWinningPlayer();
}

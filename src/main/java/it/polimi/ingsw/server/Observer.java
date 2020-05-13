package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

public interface Observer {
    void subscribe();
    ObjNumPlayer updateNPlayer();
    void updateAddPlayer();
    AskCard updateTempCard();
    void updatePlayerCard(int indexPlayer);
    void updateInizializaWorker();
    Board updateBoard();
    ObjState updateWhoIsPlaying();
    void updateStartTurn();
    void updateReachable();
    boolean updateCanMove(int indexPlayer);
    void updateMove();
    void updateSetBuilding();
    boolean updateCanBuild(int indexPlayer, int indexWorker);
    void updateBuild();
    void updateFinishTurn();

    //void updateWinningPlayer();
}

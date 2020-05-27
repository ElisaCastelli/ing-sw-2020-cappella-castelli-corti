package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;


public interface Observer {
    void subscribe();
    ObjNumPlayer updateNPlayer();
    void updateTempCard(int clientIndex);
    void updateInitializeWorker(int indexClient,int indexPlayer);
    void updateNotInitializeWorker(int indexClient);
    void updatePlayer();
    void updateAskState(int indexClient, int indexPlayer);
    void updateBoard(boolean reach);
    void updateWhoIsPlaying();
    void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove);
    boolean canMove(int indexPlayer);
    void updateMove(AskMoveEvent askMoveEvent,int clientIndex);
    void updateWin(int indexClient);
    void updateContinueMove(AskMoveEvent askMoveEvent);
    void updateLoser(int indexClient);
    void updateCanBuild(AskMoveEvent askMoveEvent);
    void updateSetBuilding();
    void canBuild(AskMoveEvent askMoveEvent);
    void updateBuild();

}

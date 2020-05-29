package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.network.objects.ObjWorkerToMove;
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
    void updateSpecialTurn(ObjWorkerToMove objWorkerToMove);
    void updateBasicTurn(int indexWorker, int rowWorker, int columnWorker);
    void updateAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    void canMove();
    void updateMove(AskMoveEvent askMoveEvent,int clientIndex);
    void updateWin(int indexClient);
    void updateContinueMove(AskMoveEvent askMoveEvent);
    void updateLoser(int indexClient);
    void updateCanBuild(int indexWorker, int rowWorker, int columnWorker);
    void updateSetBuilding();
    void canBuild(int indexClient, int indexWorker, int rowWorker, int columnWorker);
    void updateBuild(AskBuildEvent askBuildEvent, int clientIndex);
    void updateContinueBuild(AskBuildEvent askBuildEvent);
    void updateStartTurn();
    void updateWhoHasLost(int indexClient);
}

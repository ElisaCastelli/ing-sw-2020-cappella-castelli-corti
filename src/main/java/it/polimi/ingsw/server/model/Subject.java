package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.network.objects.ObjWorkerToMove;
import it.polimi.ingsw.server.Observer;

public interface Subject {
    void subscribeObserver(Observer observer);
    ObjNumPlayer notifySetNPlayers();
    void notifyAddPlayer();
    void notifyAskState(int indexClient,int indexPlayer);
    void notifyTempCard(int indexClient);
    void notifyAddWorker();
    void notifyWorkersNotInitialized();
    void notifyWhoIsPlaying();
    void notifySetReachable(int indexWorker, boolean secondMove);
    void notifySpecialTurn(ObjWorkerToMove objWorkerToMove);
    void notifyBasicTurn(int indexWorker, int rowWorker, int columnWorker);
    void notifyAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    void notifyUpdateBoard(boolean reach);
    void notifyMovedWorker(AskMoveEvent askMoveEvent, int clientIndex);
    void notifyWin(int indexClient);
    void notifyContinueMove(AskMoveEvent askMoveEvent);
    void notifyLoser(int indexClient);
    void notifyCanBuild(int indexWorker, int rowWorker, int columnWorker);
    void notifySetBuilding();
    void notifyBuildBlock(AskBuildEvent askBuildEvent, int clientIndex);
    void notifyContinueBuild(AskBuildEvent askBuildEvent);
    void notifyStartTurn();
    void notifyWhoHasLost(int indexClient);
}

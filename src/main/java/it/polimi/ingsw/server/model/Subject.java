package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
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
    void notifyMovedWorker(AskMoveEvent askMoveEvent, int clientIndex);
    void notifyWin();
    void notifyContinueMove(AskMoveEvent askMoveEvent);
    void notifyLoser();
    void notifyCanBuild(AskMoveEvent askMoveEvent);
    void notifySetBuilding();
    void notifyBuildBlock();
}

package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.Observer;

public interface Subject {
    void subscribeObserver(Observer observer);
    ObjNumPlayer notifySetNPlayers();
    void notifyAddPlayer();
    void notifyAskState(int indexClient,int indexPlayer);
    AskCard notifyTempCard();
    void notifyAddWorker();
    ObjState notifyWhoIsPlaying();
    UpdateBoardEvent notifySetReachable();
    void notifyMovedWorker();
    UpdateBoardEvent notifySetBuilding();
    void notifyBuildBlock();
}

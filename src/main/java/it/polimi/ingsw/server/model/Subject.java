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
    void notifyTempCard(int clientIndex);
    void notifyAddWorker();
    void notifyWhoIsPlaying();
    void notifySetReachable();
    void notifyMovedWorker();
    void notifySetBuilding();
    void notifyBuildBlock();
}

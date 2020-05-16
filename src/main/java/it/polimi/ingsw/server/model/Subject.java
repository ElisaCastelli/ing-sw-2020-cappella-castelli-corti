package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.Observer;

public interface Subject {
    void subscribeObserver(Observer observer);
    ObjNumPlayer notifySetNPlayers();
    AskCard notifyTempCard();
    void notifyAddWorker();
    ObjState notifyWhoIsPlaying();
    UpdateBoardEvent notifySetReachable();
    void notifyMovedWorker();
    void notifySetBuilding();
    void notifyBuildBlock();
}

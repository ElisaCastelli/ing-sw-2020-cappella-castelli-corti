package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.server.Observer;

public interface Subject {
    void subscribeObserver(Observer observer);
    ObjNumPlayer notifySetNPlayers();
    void notifyAddPlayer();
    AskCard notifyTempCard();
    void notifyAddCard(int indexPlayer);
    void notifyAddWorker();
    void notifyState();
    void notifyPlayerStateStart(int indexPlayer);
    void notifySetReachable();
    void notifyMovedWorker();
    void notifySetBuilding();
    void notifyBuildBlock();
    void notifyPlayerStateWaiting(int indexPlayer);
}

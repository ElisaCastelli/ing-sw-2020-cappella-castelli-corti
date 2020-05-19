package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;


public interface Observer {
    void subscribe();
    ObjNumPlayer updateNPlayer();
    AskCard updateTempCard();
    void updateInitializeWorker();
    UpdateBoardEvent updateBoard();
    ObjState updateWhoIsPlaying();
    void updateReachable();
    boolean canMove(int indexPlayer);
    void updateMove();
    void updateSetBuilding();
    boolean canBuild(int indexPlayer, int indexWorker);
    void updateBuild();

}

package it.polimi.ingsw.server;


import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;


public interface Observer {
    void subscribe();
    ObjNumPlayer updateNPlayer();
    AskCard updateTempCard();
    void updateInizializaWorker();
    Board updateBoard();
    ObjState updateWhoIsPlaying();
    void updateReachable();
    boolean updateCanMove(int indexPlayer);
    void updateMove();
    void updateSetBuilding();
    boolean updateCanBuild(int indexPlayer, int indexWorker);
    void updateBuild();

}

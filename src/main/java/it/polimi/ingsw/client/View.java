package it.polimi.ingsw.client;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public interface View {
    void askWantToPlay(AskWantToPlay askWantToPlay);
    void youCanPlay();
    void youHaveToWait();
    void setSendMessageToServer(SendMessageToServer sendMessageToServer);
    void setBoard(Board board);
    void updateBoard(UpdateBoardEvent updateBoardEvent);
    void askNPlayer();
    void setNPlayer(int nPlayer);
    void askPlayer(int clientIndex);
    void setIndexPlayer(ObjState objState);
    int getIndexPlayer();
    void ask3Card(ArrayList<String> cards);
    void askCard(ArrayList<String> cards);
    void initializeWorker();
    void askWorker(AskWorkerToMoveEvent askMoveEvent);
    void areYouSure(AskWorkerToMoveEvent askMoveEvent);
    void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    void moveWorker(AskMoveEvent askMoveEvent);
    void anotherMove(AskMoveEvent askMoveEvent);
    void printPossibleBlocks(int row, int column);
    void wrongMove();
    void buildMove(AskBuildEvent askBuildEvent);
    void anotherBuild(AskBuildEvent askBuildEvent);
    void loserEvent();
    void winnerEvent();
    void someoneWon();
    void whoHasLost();
    void printHeartBeat(ObjHeartBeat objHeartBeat);
    void closingConnectionEvent(int indexClient, boolean GameNotAvailable);
}

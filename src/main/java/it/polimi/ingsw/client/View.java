package it.polimi.ingsw.client;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public abstract class View {

    public abstract void askWantToPlay(AskWantToPlay askWantToPlay);

    public abstract void setBoard(Board board);
    public abstract void updateBoard(UpdateBoardEvent updateBoardEvent);
    public abstract void askNPlayer();
    public abstract void setNPlayer(int nPlayer);
    public abstract void askPlayer(int clientIndex);

    public abstract void setIndexPlayer(ObjState objState);
    public abstract int getIndexPlayer();
    public abstract void ask3Card(ArrayList<String> cards);
    public abstract void askCard(ArrayList<String> cards);
    public abstract void initializeWorker();
    public abstract void askWorker(AskWorkerToMoveEvent askMoveEvent);
    public abstract void areYouSure(AskWorkerToMoveEvent askMoveEvent);
    public abstract void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    public abstract void moveWorker(AskMoveEvent askMoveEvent);
    public abstract void anotherMove(AskMoveEvent askMoveEvent);
    public abstract void printPossibleBlocks(int row, int column);
    public abstract void wrongMove();
    public abstract void buildMove(AskBuildEvent askBuildEvent);
    public abstract void anotherBuild(AskBuildEvent askBuildEvent);
    public abstract void loserEvent();
    public abstract void winnerEvent();
    public abstract void someoneWon();
    public abstract void whoHasLost();

    public abstract void printHeartBeat(ObjHeartBeat objHeartBeat);
    public abstract void ClosingConnectionEvent(int clientIndex);
}

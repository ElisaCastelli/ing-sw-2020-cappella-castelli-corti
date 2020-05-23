package it.polimi.ingsw.client;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public abstract class View {

    public abstract void initialize(ObjInitialize objInitialize);
    public abstract void setBoard(Board board);
    public abstract void updateBoard(UpdateBoardEvent updateBoardEvent);
    public abstract void setUsers(ArrayList<User> users);
    public abstract void setWhoIsPlaying(int whoIsPlaying);
    public abstract void askNPlayer();
    public abstract void setNPlayer(int nPlayer);
    public abstract void askPlayer();

    public abstract void setIndexPlayer(ObjState objState);
    public abstract int getIndexPlayer();
    public abstract void setPlaying(ObjState objState);
    public abstract boolean isPlaying();
    public abstract void ask3Card(ArrayList<String> cards);
    public abstract void askCard(ArrayList<String> cards);
    public abstract void printBoard(boolean printReachable);
    public abstract void initializeWorker();
    public abstract void askWorker(AskWorkerToMoveEvent askMoveEvent);
    public abstract void areYouSure(AskWorkerToMoveEvent askMoveEvent);
    public abstract void moveWorker(AskMoveEvent askMoveEvent);
    public abstract void anotherMove(AskMoveEvent askMoveEvent);
    public abstract void wrongMove();
    public abstract void buildMove(AskBuildEvent askBuildEvent);
    public abstract void anotherBuild(AskBuildEvent askBuildEvent);
}

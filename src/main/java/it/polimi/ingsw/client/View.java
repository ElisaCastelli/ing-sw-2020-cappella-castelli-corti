package it.polimi.ingsw.client;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.ObjBlock;
import it.polimi.ingsw.network.objects.ObjMove;
import it.polimi.ingsw.network.objects.ObjWokerToMove;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public abstract class View {

    public abstract void setBoard(Board board);
    public abstract void setUsers(ArrayList<User> users);
    public abstract int askNPlayer();
    public abstract void setNPlayer(int nPlayer);
    public abstract String askName();
    public abstract int askAge();
    public abstract void setIndexPlayer(int indexPlayer);
    public abstract int getIndexPlayer();
    public abstract void setPlaying(boolean isPlaying);
    public abstract boolean isPlaying();
    public abstract ArrayList<Integer> ask3Card(ArrayList<String> cards);
    public abstract int askCard(ArrayList<String> cards);
    public abstract void printBoard(boolean printReachable);
    public abstract ArrayList<Box> initializeWorker();
    public abstract ObjWokerToMove askWorker(AskWorkerToMoveEvent askMoveEvent);
    public abstract ObjWokerToMove areYouSure(AskWorkerToMoveEvent askMoveEvent);
    public abstract ObjMove moveWorker(AskMoveEvent askMoveEvent);
    public abstract ObjMove anotherMove(AskMoveEvent askMoveEvent);
    public abstract ObjBlock buildMove(AskBuildEvent askBuildEvent);
    public abstract ObjBlock anotherBuild(AskBuildEvent askBuildEvent);
}

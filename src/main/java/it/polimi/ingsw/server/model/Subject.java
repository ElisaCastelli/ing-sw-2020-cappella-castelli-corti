package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.server.Observer;

/**
 * This interface is the subject class of the observer pattern
 */
public interface Subject {

    void subscribeObserver(Observer observer);

    /**
     * This method notifies to the observers that they have to update the players in the game
     */
    void notifyAddPlayer();

    /**
     * This method notifies to the observers that they have to update their state
     * @param indexClient client index of the player who is playing
     * @param indexPlayer player index that is playing
     */
    void notifyAskState(int indexClient,int indexPlayer);

    /**
     * This method notifies to the observers that they have to update the temporary cards
     * @param indexClient client index of the player who is playing
     */
    void notifyTempCard(int indexClient);

    /**
     * This method notifies to the observers that they have to update the workers
     */
    void notifyAddWorker();

    /**
     * This method notifies to the observers that a new player is added in the game
     * @param indexClient client index of the player who is playing
     */
    void notifyNewAddPlayer(int indexClient);

    /**
     * This method notifies to the observers that the workers are not initialized because at least one of the given boxes are already occupied
     */
    void notifyWorkersNotInitialized();

    /**
     * This method notifies to the observers that they have to update the reachable boxes because of a worker move
     * @param indexWorker worker index that is moved
     * @param secondMove true if it is a second worker move because of a God ability, otherwise it is false
     */
    void notifySetReachable(int indexWorker, boolean secondMove);

    /**
     * This method notifies to the observers that they have pick the other worker because this one can't move
     * @param indexWorker worker index that is moved
     * @param secondMove true if it is a second worker move because of a God ability, otherwise it is false
     */
    void notifyNotReachable(int indexWorker, boolean secondMove);
    /**
     * This method notifies to the observers that it is a special turn for a player
     * @param row row of the box where the chosen worker is
     * @param column column of the box where the chosen worker is
     * @param indexWorkerToMove worker index that is chosen
     */
    void notifySpecialTurn(int row, int column, int indexWorkerToMove);

    /**
     * This method notifies to the observers that it is a basic turn for a player
     * @param indexWorker worker index that is chosen to move
     * @param rowWorker row of the box where the chosen worker is
     * @param columnWorker column of the box where the chosen worker is
     */
    void notifyBasicTurn(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method notifies to the observers if they can build before the worker move or not
     * @param indexWorker worker index that is chosen to move
     * @param rowWorker row of the box where the chosen worker is
     * @param columnWorker column of the box where the chosen worker is
     */
    void notifyAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method notifies to the observers that they have to update the board
     * @param reach true if the client has to print the reachable boxes, otherwise is false
     */
    void notifyUpdateBoard(boolean reach);

    /**
     * This method notifies to the observers that the worker move is done
     * @param askMoveEvent object with all the information about the worker move
     * @param clientIndex client index of the player who is playing
     */
    void notifyMovedWorker(AskMoveEvent askMoveEvent, int clientIndex);

    /**
     * This method notifies to the observers that a player wins
     * @param indexClient client index of the winner
     */
    void notifyWin(int indexClient);

    /**
     * This method notifies to the observers that a player can do another worker move
     * @param askMoveEvent object with all the information about the worker move
     */
    void notifyContinueMove(AskMoveEvent askMoveEvent);

    /**
     * This method notifies to the observers that a player loses and sends a message to who has lost
     * @param indexClient client index of the loser
     */
    void notifyLoser(int indexClient);

    /**
     * This method notifies to the observers that the player can build
     * @param indexWorker worker index that builds
     * @param rowWorker row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    void notifyCanBuild(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method notifies to the observers they have to update the reachable boxes because of a build move
     */
    void notifySetBuilding();

    /**
     * This method notifies to the observers that the build move is done
     * @param askBuildEvent object with all the information about the build move
     * @param clientIndex client index of the player who is playing
     */
    void notifyBuildBlock(AskBuildEvent askBuildEvent, int clientIndex);

    /**
     * This method notifies to the observers that a player can do another build move
     * @param askBuildEvent object with all the information about the build move
     */
    void notifyContinueBuild(AskBuildEvent askBuildEvent);

    /**
     * This method notifies to the observers that the player turn is started
     */
    void notifyStartTurn();

    /**
     * This method notifies to the observers that a player loses and sends a message to the other players telling who has lost
     * @param indexClient client index of the loser
     */
    void notifyWhoHasLost(int indexClient);
}

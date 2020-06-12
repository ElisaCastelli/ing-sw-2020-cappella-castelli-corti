package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;

/**
 * This interface is the subject class of the observer pattern
 */
public interface Observer {
    void subscribe();

    /**
     * This method sends the two or three cards to the player and he has to choose one. When each player chose a cards, it sends the board and the request to initialize the workers to the first player of the game
     * @param clientIndex client index who is playing
     */
    void updateTempCard(int clientIndex);

    /**
     * This method sends an update of the board and the request to choose the worker to move, if the first player has to play; otherwise sends the request to initialize the workers
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     */
    void updateInitializeWorker(int indexClient,int indexPlayer);

    /**
     * This method sends another request to initialize the workers, if the player chooses an occupied box
     * @param indexClient client index who is playing
     */
    void updateNotInitializeWorker(int indexClient);

    /**
     * This method starts the game and send an update to the players
     */
    void updatePlayer();
    /**
     * This method sends another request about player information to the client
     * @param indexClient client index who is playing
     */
    void updateNewAddPlayer(int indexClient);

    /**
     * This method sends an update about player index to the player
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     */
    void updateAskState(int indexClient, int indexPlayer);

    /**
     * This method sends an update of the board to the players
     * @param reach boolean that identifies if the clients have to print the reachable boxes
     */
    void updateBoard(boolean reach);
    void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove);
    void updateSpecialTurn(int row, int column, int indexWorkerToMove );
    void updateBasicTurn(int indexWorker, int rowWorker, int columnWorker);
    void updateAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    void updateMove(AskMoveEvent askMoveEvent,int clientIndex);
    void updateWin(int indexClient);
    void updateContinueMove(AskMoveEvent askMoveEvent);
    void updateLoser(int indexClient);
    void updateCanBuild(int indexWorker, int rowWorker, int columnWorker);
    void updateSetBuilding();
    void updateBuild(AskBuildEvent askBuildEvent, int clientIndex);
    void updateContinueBuild(AskBuildEvent askBuildEvent);
    void updateStartTurn();
    void updateWhoHasLost(int indexClient);
    void updateUnreachableClient(int indexClient);
    void updateControlSetNPlayer();
    void reset();
    void closeGame();
}

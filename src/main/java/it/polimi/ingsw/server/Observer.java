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

    /**
     * This method sends an update of the board and if it is not the second move, it sends again the request to initialize the workers
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     * @param indexWorker worker index that the player wants to move
     * @param secondMove boolean that identifies if it is the first or the second move
     */
    void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove);

    /**
     * This method tells that the player can build before the worker move
     * @param row row of the box where the worker is
     * @param column column of the box where the worker is
     * @param indexWorkerToMove worker index that the player wants to move
     */
    void updateSpecialTurn(int row, int column, int indexWorkerToMove );

    /**
     * This method sends a message where requires to choose a worker to move
     * @param indexWorker worker index that the player wants to move
     * @param rowWorker row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    void updateBasicTurn(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method sends a request where the player can choose to build before worker move or not
     * @param indexWorker worker index that the player wants to move
     * @param rowWorker row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    void updateAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method sends an update of the board and checks if the player wins with the worker move
     * @param askMoveEvent object with all the information about the worker move
     * @param clientIndex client index who is playing
     */
    void updateMove(AskMoveEvent askMoveEvent,int clientIndex);

    /**
     * This method sends a win message to the player who won
     * @param indexClient client index who won
     */
    void updateWin(int indexClient);

    /**
     * This method sends the possibility to move again the worker if the player has a God with this ability, otherwise calls canBuild method
     * @param askMoveEvent object with all the information about the worker move
     */
    void updateContinueMove(AskMoveEvent askMoveEvent);

    /**
     * This method sends a loss message to the player who has lost
     * @param indexClient client index who has lost
     */
    void updateLoser(int indexClient);

    /**
     * This method sends an update of the board and the request to build a block
     * @param indexWorker worker index that has to build
     * @param rowWorker row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    void updateCanBuild(int indexWorker, int rowWorker, int columnWorker);

    /**
     * This method sends an update of the board
     */
    void updateSetBuilding();

    /**
     * This method sends an update of the board and checks if a player has won
     * @param askBuildEvent object with all the information about the worker move
     * @param clientIndex client index who is playing
     */
    void updateBuild(AskBuildEvent askBuildEvent, int clientIndex);

    /**
     * This method sends the possibility to build again a block if the player has a God with this ability, otherwise calls canMove method
     * @param askBuildEvent object with all the information about the build move
     */
    void updateContinueBuild(AskBuildEvent askBuildEvent);

    /**
     * This method starts the turn of the next player sending the request to choose a worker
     */
    void updateStartTurn();

    /**
     * This method sends a message to the players who are still in the game saying which player has lost
     * @param indexClient client index who has lost
     */
    void updateWhoHasLost(int indexClient);

    /**
     * This method calls controlStillOpen method because the client doesn't send a response
     * @param indexClient client index who doesn't send a response
     */
    void updateUnreachableClient(int indexClient);

    /**
     * This method controls if the first player sets the number of player in the game
     */
    void updateControlSetNPlayer();

    /**
     * This method resets the arrays in echoServer class
     */
    void reset();

    /**
     * This method closes all the client and invokes reset method
     */
    void closeGame();
}

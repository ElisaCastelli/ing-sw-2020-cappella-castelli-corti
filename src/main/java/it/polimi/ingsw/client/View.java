package it.polimi.ingsw.client;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.network.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

/**
 * Interface that defines the methods used by both the CLI view and the GUI view
 */
public interface View {
    /**
     * Method called from the VisitorClient when the clientHandler received an AskWantToPlay message
     * @param askWantToPlay is the message send from the server to ask to the player if he wants to play
     */
    void askWantToPlay(AskWantToPlay askWantToPlay);
    /**
     * Method called from the VisitorClient when the clientHandler received an YouCanPlay message
     */
    void youCanPlay();
    /**
     *Method called from the VisitorClient when the clientHandler received an YouHaveToWait message
     */
    void youHaveToWait();
    /**
     * Setter method for the SendMessageToServer object
     * @param sendMessageToServer is the object to set
     */
    void setSendMessageToServer(SendMessageToServer sendMessageToServer);
    /**
     * Method called from the VisitorClient everytime the clientHandler received an updateBoard message
     * @param usersArray is the ArrayList of users taking part to the game
     * @param board is the object Board describe the game field
     * @param isShowReachable is a boolean that indicates if the printed board has to show the reachable boxes
     * @param currentPlaying is the integer index of the gamer playing in this turn
     * @param indexClient is the index of the client associated with the player
     */
    void updateBoard(ArrayList<User> usersArray, Board board, boolean isShowReachable, int currentPlaying, int indexClient);
    /**
     * Method called from the VisitorClient of the first player connected when the ClientHandler receives an AskNPlayer message.
     */
    void askNPlayer();
    /**
     * Method used to set the number of the gamers playing and then send an AckStartGame message to answer
     * to the startGameEvent message
     * @param nPlayer is the number of the gamers playing
     */
    void setNPlayer(int nPlayer);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskPlayer message.
     * @param clientIndex is the index of the client associated with the player
     */
    void askPlayer(int clientIndex);
    /**
     * Method used to set the personal index of the player to know if the player can play or must wait his turn
     * and then send an AckPlayer message to reply to the ObjState message
     * @param indexPlayer is the index of the player
     */
    void setIndexPlayer(int indexPlayer);
    /**
     * Method calledfrom the VisitorClient when the ClientHandler receives an Ask3Card message.
     * @param cards is the ArrayList of cards name from which the user can choose
     */
    void ask3Card(ArrayList<String> cards);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskCard message.
     * @param cards is the ArrayList of the cards name from which the user can choose
     */
    void askCard(ArrayList<String> cards);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskInizializeWorker message.
     */
    void initializeWorker();
    /**
     *Method called from the VisitorClient when the ClientHandler receives for the first time an AskWorkerToMoveEvent message.
     * @param row1 is the row of the box occupied by the worker 1
     * @param column1 is the column of the box occupied by the worker 1
     * @param row2 is the row of the box occupied by the worker 2
     * @param column2 is the column of the box occupied by the worker 2
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex is the integer index associated to the client
     */
    void askWorker(int row1, int column1, int row2, int column2, int currentPlaying, int clientIndex);
    /**
     * Method called from the VisitorClient when the ClientHandler receives not for the first time an AskWorkerToMoveEvent message.
     * @param row1 is the row of the box occupied by the worker 1
     * @param column1 is the column of the box occupied by the worker 1
     * @param row2 is the row of the box occupied by the worker 2
     * @param column2 is the column of the box occupied by the worker 2
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex is the integer index associated to the client
     */
    void areYouSure(int row1, int column1, int row2, int column2, int indexWorker, int currentPlaying, int clientIndex);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskBeforeBuildMove message.
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     */
    void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskMoveEVent message.
     * @param row is the starting row of the worker to move
     * @param column is the starting column of the worker to move
     * @param indexWorker is the integer index of the worker the player chose to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     */
    void moveWorker(int row, int column, int indexWorker, boolean isWrongBox, boolean firstTime, int clientIndex, int currentPlaying);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskMoveEVent message and firstTime is false.
     * @param row is the row of the box occupied by the worker
     * @param column is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    void anotherMove(int row, int column, int indexWorker, boolean isWrongBox,boolean firstTime, int clientIndex, int currentPlaying, boolean done);
    /**
     * This method it's used to print the possible block to build in the chosen position
     * @param row is the integer of the row of the Box where the player wants to build
     * @param column is the integer of the column of the Box where the player wants to build
     */
    void printPossibleBlocks(int row, int column);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskBuildEVent message and firstTime is true.
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param isFirstTime is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn is a boolean used to identify special moves
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    void buildMove(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done);
    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskBuildEVent message and firstTime is false.
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param isFirstTime is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn is a boolean used to identify special moves
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    void anotherBuild(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done);
    /**
     * This method is used to show to the user who lost the end of the game
     */
    void loserEvent();
    /**
     * This method is used to show to the user who win the end of the game
     */
    void winnerEvent();
    /**
     * This method is used to show to the user that an opponent has won
     */
    void someoneWon();
    /**
     * This method is used to show to the user that an opponent has lost
     */
    void whoHasLost();
    /**
     * Method used to send, using the SendMessageToServer object, a Pong message to the server after received a ping
     * @param objHeartBeat is the message Ping received from the visitorClient
     */
    void printHeartBeat(ObjHeartBeat objHeartBeat);
    /**
     * Method used to send to the server the request of closing the connection
     * @param indexClient is the index associated to the client
     * @param GameNotAvailable is a boolean used to indicate if the connection will be close because the game is already
     * started or because of a problem
     */
    void closingConnectionEvent(int indexClient, boolean GameNotAvailable);
}

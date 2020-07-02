package it.polimi.ingsw.server;

import it.polimi.ingsw.network.SendMessageToClient;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

/**
 * This class is the view of the MVC pattern server's side
 */

public class VirtualView implements Observer {

    private ProxyGameModel gameModel;
    private Controller controller;
    SendMessageToClient sendMessageToClient;
    private final Object LOCK = new Object();


    public VirtualView(SendMessageToClient sendMessageToClient) {
        gameModel = new ProxyGameModel();
        controller = new Controller(gameModel);
        subscribe();
        this.sendMessageToClient = sendMessageToClient;
    }

    /**
     * this method is used to subscribe to the model
     */
    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    /**
     * this method return the array of player
     *
     * @return array of player
     */

    public ArrayList<Player> getPlayerArray() {
        return gameModel.getPlayerArray();
    }

    /**
     * This method handles all the clients which are connecting at the beginning of the game
     *
     * @param indexClient client index who is connecting in the game
     */
    public void askWantToPlay(int indexClient) {
        if (gameModel.getNPlayers() == 0 && getPlayerArray().size() == 0) {
            controller.addPlayer(indexClient);
            sendMessageToClient.sendAskNPlayer(false);
        } else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() == 0) {
            controller.addPlayer(indexClient);
            sendMessageToClient.sendYouHaveToWait(indexClient);
        } else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() != 0) {
            controller.addPlayer(indexClient);
            if (getPlayerArray().size() == gameModel.getNPlayers()) {
                sendMessageToClient.YouCanPlay(gameModel.getNPlayers());
            } else if (getPlayerArray().size() < gameModel.getNPlayers()) {
                sendMessageToClient.sendYouHaveToWait(indexClient);
            } else {
                sendMessageToClient.sendCloseConnection(indexClient, true);
            }
        }
    }

    /**
     * This method sets the number of players and sends the requests of player information
     *
     * @param npLayer number of players that are going to play the game
     */
    public void setNPlayers(int npLayer) {
        controller.setNPlayers(npLayer);
        if (getPlayerArray().size() == 1) {
            sendMessageToClient.sendAskPlayer(npLayer, false);
        } else if (getPlayerArray().size() >= npLayer) {
            sendMessageToClient.sendAskPlayer(npLayer, true);
        } else {
            sendMessageToClient.sendYouHaveToWait(0);
        }
    }

    /**
     * This method adds the player: if the players are more than the possible players in the game, this method is going to remove the extra players
     *
     * @param name        player name
     * @param age         player age
     * @param indexClient client index of the player
     */
    public void addPlayer(String name, int age, int indexClient) {
        synchronized (LOCK) {
            if (getPlayerArray().size() > gameModel.getNPlayers()) {
                controller.removeExtraPlayer();
            }
            controller.addPlayer(name, age, indexClient);
        }
    }

    /**
     * This method calls askState method in the controller class
     */
    public void askState() {
        controller.askState();
    }

    /**
     * This method sends all the cards to the first player
     */
    public void getCards() {
        try {
            AskNCardsEvent askNCardsEvent = new AskNCardsEvent(gameModel.getCards());
            int clientIndex = gameModel.searchByPlayerIndex(0);
            askNCardsEvent.setCurrentClientPlaying(clientIndex);
            sendMessageToClient.sendCards(askNCardsEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the two or three cards chosen by the first player
     *
     * @param tempCard two or three cards chosen
     */
    public void setTempCard(ArrayList<Integer> tempCard) {
        controller.setTempCard(tempCard);
    }

    /**
     * This method sets the card chosen by the player
     *
     * @param godCard chosen card
     */
    public void setCard(int godCard) {
        controller.setCard(godCard);
    }

    /**
     * This method starts the game and sends an update to the players
     */
    @Override
    public void updatePlayer() {
        startGame();
        sendMessageToClient.getEchoServer().resetWaiting();
        sendMessageToClient.sendStartGameEvent(gameModel.getNPlayers());
    }

    /**
     * This method calls startGame method in the controller class
     */
    public void startGame() {
        controller.startGame();
    }

    /**
     * This method sends an update about player index to the players
     *
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     */
    @Override
    public void updateAskState(int indexClient, int indexPlayer) {
        ObjState objState = new ObjState(indexPlayer);
        sendMessageToClient.sendObjState(indexClient, objState);
    }

    /**
     * This method sends the two or three cards to the player and he has to choose one. When each player chose a cards, it sends the board and the request to initialize the workers to the first player of the game
     *
     * @param clientIndex client index who is playing
     */
    @Override
    public void updateTempCard(int clientIndex) {
        AskCardEvent askCardEvent = new AskCardEvent(gameModel.getTempCard());
        askCardEvent.setCurrentClientPlaying(clientIndex);

        if (askCardEvent.getCardTemp().size() != 0) {
            sendMessageToClient.sendAskCard(askCardEvent);
        } else {
            updateBoard(false);
            AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
            askInitializeWorkerEvent.setCurrentClientPlaying(clientIndex);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
        }
    }

    /**
     * This method sends an update of the board to the players
     *
     * @param reach boolean that identifies if the clients have to print the reachable boxes
     */
    @Override
    public void updateBoard(boolean reach) {
        sendMessageToClient.sendUpdateBoard(gameModel.gameData(reach));
    }

    /**
     * This method sends another request of player information to the client
     *
     * @param indexClient client index who is playing
     */
    @Override
    public void updateNewAddPlayer(int indexClient) {
        sendMessageToClient.sendAskPlayerAgain(indexClient);
    }

    /**
     * This method calls the initializeWorker method in the controller class
     *
     * @param box1 box where the player wants to put the first worker
     * @param box2 box where the player wants to put the second worker
     */
    public void initializeWorker(Box box1, Box box2) {
        controller.initializeWorker(box1, box2);
    }

    /**
     * This method sends an update of the board and the request to choose the worker to move, if the first player has to play; otherwise sends the request to initialize the workers
     *
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     */
    @Override
    public void updateInitializeWorker(int indexClient, int indexPlayer) {
        updateBoard(false);
        if (indexPlayer == 0) {
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true, true);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
        } else {
            AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
            askInitializeWorkerEvent.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
        }
    }

    /**
     * This method sends another request to initialize the workers, if the player chooses an occupied box
     *
     * @param indexClient client index who is playing
     */
    @Override
    public void updateNotInitializeWorker(int indexClient) {
        AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
        askInitializeWorkerEvent.setCurrentClientPlaying(indexClient);
        sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
    }

    /**
     * This method tells if a box is reachable by a worker
     *
     * @param row    row of the box where the worker wants to move or to build
     * @param column column of the box where the worker wants to move or to build
     * @return true if the box is reachable, otherwise returns false
     */
    public boolean isReachable(int row, int column) {
        return gameModel.isReachable(row, column);
    }

    /**
     * This method calls the canMove method in the controller class
     */
    public void canMove() {
        controller.canMove();
    }

    /**
     * This method calls the canMoveSpecialTurn method in the controller class
     *
     * @param indexWorker  worker index that the player wants to move
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canMoveSpecialTurn(int indexWorker, int rowWorker, int columnWorker) {
        controller.canMoveSpecialTurn(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method calls the setBoxReachable method in the controller class if the player didn't choose definitively the worker, otherwise calls the canBuildBeforeWorkerMove method in the controller
     *
     * @param row               row of the box where the worker is
     * @param column            column of the box where the worker is
     * @param indexWorkerToMove worker index that the player wants to move
     * @param isReady           boolean that identifies if the player has chosen the worker or not
     */
    public void setBoxReachable(int row, int column, int indexWorkerToMove, boolean isReady) {
        if (isReady) {
            controller.canBuildBeforeWorkerMove(row, column, indexWorkerToMove);
        } else {
            controller.setBoxReachable(indexWorkerToMove, false);
        }
    }

    /**
     * This method sends an update of the board and if it is not the second move, it sends again the request to initialize the workers
     *
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     * @param indexWorker worker index that the player wants to move
     * @param secondMove  boolean that identifies if it is the first or the second move
     */
    @Override
    public void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove) {
        updateBoard(true);
        if (!secondMove) {
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, false, true);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            askWorkerToMoveEvent.setIndexWorker(indexWorker);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
        }
    }

    @Override
    public void updateNotReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove) {
        updateBoard(true);
        AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true, false);
        askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
        askWorkerToMoveEvent.setIndexWorker(indexWorker);
        sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
    }


    /**
     * This method sends a message memorizing the positions of the player workers
     *
     * @param indexPlayer player index who is playing
     * @param firstMove   boolean that identifies if it is the first time for this player
     * @param canMove boolean if the worker can make a move
     * @return object with the positions of the player workers
     */
    public AskWorkerToMoveEvent getWorkersPos(int indexPlayer, boolean firstMove, boolean canMove) {
        ArrayList<Box> positions = gameModel.getWorkersPos(indexPlayer);
        return new AskWorkerToMoveEvent(positions.get(0).getRow(), positions.get(0).getColumn(), positions.get(1).getRow(), positions.get(1).getColumn(), firstMove, canMove);
    }

    /**
     * This method controls if the box given by the player is reachable: if it is, the method calls the movePlayer method in the controller, otherwise it sends an update of the board and sends again the request to choose a reachable box
     *
     * @param rowStart          row of the box where the worker is
     * @param columnStart       column of the box where the worker is
     * @param row               row of the box where the player wants to move the worker
     * @param column            column of the box where the player wants to move the worker
     * @param indexWorkerToMove worker index that the player wants to move
     * @param firstTime         boolean that identifies if it is the first player build or the second
     */
    public void move(int rowStart, int columnStart, int row, int column, int indexWorkerToMove, boolean firstTime) {
        if (isReachable(row, column)) {
            controller.movePlayer(rowStart, columnStart, row, column, indexWorkerToMove);
        } else {
            updateBoard(true);
            AskMoveEvent askMoveEvent = new AskMoveEvent(indexWorkerToMove, rowStart, columnStart, true, false);
            if (!firstTime) {
                askMoveEvent.setFirstTime(false);
            }
            askMoveEvent.setWrongBox(true);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }
    }

    /**
     * This method tells that the player can build before the worker move
     *
     * @param row               row of the box where the worker is
     * @param column            column of the box where the worker is
     * @param indexWorkerToMove worker index that the player wants to move
     */
    @Override
    public void updateSpecialTurn(int row, int column, int indexWorkerToMove) {
        canBuildSpecialTurn(indexWorkerToMove, row, column);
    }

    /**
     * This method sends a message where requires to choose a worker to move
     *
     * @param indexWorker  worker index that the player wants to move
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    @Override
    public void updateBasicTurn(int indexWorker, int rowWorker, int columnWorker) {
        AskMoveEvent askMoveEvent = new AskMoveEvent(indexWorker, rowWorker, columnWorker, true, false);
        askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskMoveEvent(askMoveEvent);
    }

    /**
     * This method sends a request where the player can choose to build before worker move or not
     *
     * @param indexWorker  worker index that the player wants to move
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    @Override
    public void updateAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        AskBuildBeforeMoveEvent askBuildBeforeMoveEvent = new AskBuildBeforeMoveEvent(indexWorker, rowWorker, columnWorker);
        askBuildBeforeMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildBeforeMove(askBuildBeforeMoveEvent);
    }

    /**
     * This method sends the request to build if the player chooses to build before worker move, otherwise calls updateBasicTurn method
     *
     * @param indexWorker  worker index that the player wants to move
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     * @param wantToBuild  boolean that identifies if the player wants to build before worker move or not
     */
    public void buildBeforeMove(int indexWorker, int rowWorker, int columnWorker, boolean wantToBuild) {
        if (wantToBuild) {
            setBoxBuilding(indexWorker);
            AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, true);
            askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskBuildEvent(askBuildEvent);
        } else {
            updateBasicTurn(indexWorker, rowWorker, columnWorker);
        }
    }

    /**
     * This method sends an update of the board and checks if the player wins with the worker move
     *
     * @param askMoveEvent object with all the information about the worker move
     * @param clientIndex  client index who is playing
     */
    @Override
    public void updateMove(AskMoveEvent askMoveEvent, int clientIndex) {
        updateBoard(false);
        checkWin(askMoveEvent, clientIndex);
    }

    /**
     * This method calls the checkWin method in the controller class
     *
     * @param askMoveEvent object with all the information about the worker move
     * @param clientIndex  client index who is playing
     */
    public void checkWin(AskMoveEvent askMoveEvent, int clientIndex) {
        controller.checkWin(askMoveEvent, clientIndex);
    }

    /**
     * This method sends a message of victory to the player who has won
     *
     * @param indexClient client index who has won
     */
    @Override
    public void updateWin(int indexClient) {
        sendMessageToClient.sendWin(indexClient);
    }

    /**
     * This method sends a loss message to the player who has lost
     *
     * @param indexClient client index who has lost
     */
    @Override
    public void updateLoser(int indexClient) {
        sendMessageToClient.sendLoser(indexClient);
    }

    /**
     * This method sends the possibility to move again the worker if the player has a God with this ability, otherwise calls canBuild method
     *
     * @param askMoveEvent object with all the information about the worker move
     */
    @Override
    public void updateContinueMove(AskMoveEvent askMoveEvent) {
        if (askMoveEvent.isDone()) {
            canBuild(askMoveEvent.getClientIndex(), askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn());
        } else {
            if (controller.setBoxReachable(askMoveEvent.getIndexWorker(), true)){
                askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
                sendMessageToClient.sendAskMoveEvent(askMoveEvent);
            }
            else{
                canBuild(askMoveEvent.getClientIndex(), askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn());
            }
        }
    }

    /**
     * This method calls canBuild method in the controller class
     *
     * @param indexClient  client index who is playing
     * @param indexWorker  worker index that builds
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canBuild(int indexClient, int indexWorker, int rowWorker, int columnWorker) {
        controller.canBuild(indexClient, indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method calls canBuildSpecialTurn method in the controller class
     *
     * @param indexWorker  worker index that has to build
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canBuildSpecialTurn(int indexWorker, int rowWorker, int columnWorker) {
        controller.canBuildSpecialTurn(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method sends an update of the board and the request to build a block
     *
     * @param indexWorker  worker index that has to build
     * @param rowWorker    row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    @Override
    public void updateCanBuild(int indexWorker, int rowWorker, int columnWorker) {
        updateBoard(true);
        AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, false);
        askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildEvent(askBuildEvent);
    }

    /**
     * This method calls setBoxBuilding method in the controller class
     *
     * @param indexWorker worker index that has to build
     * @return true if it is reachable
     */
    public boolean setBoxBuilding(int indexWorker) {
        return controller.setBoxBuilding(indexWorker);
    }

    /**
     * This method sends an update of the board
     */
    @Override
    public void updateSetBuilding() {
        updateBoard(true);
    }

    /**
     * This method controls if the box given by the player is reachable: if it is, the method calls the buildBlock method in the controller class, otherwise it sends an update of the board and sends again the request to choose a reachable box
     *
     * @param indexClient        client index who is playing
     * @param indexWorker        worker index that has to build
     * @param rowWorker          row of the box where the worker is
     * @param columnWorker       column of the box where the worker is
     * @param row                row of the box where the player wants to build
     * @param column             column of the box where the player wants to build
     * @param firstTime          boolean that identifies if it is the first player build or the second
     * @param isSpecialTurn      boolean that identifies if the player has built before worker move
     * @param indexPossibleBlock index of the block that the player wants to build
     */
    public void buildBlock(int indexClient, int indexWorker, int rowWorker, int columnWorker, int row, int column, boolean firstTime, boolean isSpecialTurn, int indexPossibleBlock) {
        if (isReachable(row, column)) {
            controller.buildBlock(indexClient, indexWorker, rowWorker, columnWorker, row, column, isSpecialTurn, indexPossibleBlock);
        } else {
            updateBoard(true);
            AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, isSpecialTurn);
            if (!firstTime) {
                askBuildEvent.setFirstTime(false);
            }
            askBuildEvent.setWrongBox(true);
            askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskBuildEvent(askBuildEvent);
        }
    }

    /**
     * This method sends an update of the board and checks if a player has won
     *
     * @param askBuildEvent object with all the information about the worker move
     * @param clientIndex   client index who is playing
     */
    @Override
    public void updateBuild(AskBuildEvent askBuildEvent, int clientIndex) {
        updateBoard(false);
        checkWinAfterBuild(askBuildEvent);
    }

    /**
     * This method sends the possibility to build again a block if the player has a God with this ability, otherwise calls canMove method
     *
     * @param askBuildEvent object with all the information about the build move
     */
    @Override
    public void updateContinueBuild(AskBuildEvent askBuildEvent) {
        if (askBuildEvent.isSpecialTurn()) {
            canMoveSpecialTurn(askBuildEvent.getIndexWorker(), askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker());
        } else {
            if (askBuildEvent.isDone()) {
                canMove();
            } else {
                if (setBoxBuilding(askBuildEvent.getIndexWorker())){
                    askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
                    sendMessageToClient.sendAskBuildEvent(askBuildEvent);
                } else {
                    canMove();
                }
            }
        }
    }

    /**
     * This method calls checkWinAfterBuild in the controller class
     *
     * @param askBuildEvent object with all the information about the build move
     */
    public void checkWinAfterBuild(AskBuildEvent askBuildEvent) {
        controller.checkWinAfterBuild(askBuildEvent);
    }

    /**
     * This method starts the turn of the next player sending the request to choose a worker
     */
    @Override
    public void updateStartTurn() {
        int indexPlayer = gameModel.whoIsPlaying();
        int indexClient = gameModel.searchByPlayerIndex(indexPlayer);
        AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true, true);
        askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
        sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
    }

    /**
     * This method sends a message to the players who are still in the game saying which player has lost
     *
     * @param loserClient client index who has lost
     */
    @Override
    public void updateWhoHasLost(int loserClient) {
        sendMessageToClient.sendWhoHasLost(loserClient,gameModel.gameData(false));
        canMove();
    }

    /**
     * This method calls heartbeat method in the controller class
     *
     * @param messageHeartbeat message of the heartbeat
     * @param indexClient      client index who has sent heartbeat
     */
    public void printHeartBeat(String messageHeartbeat, int indexClient) {
        controller.heartBeat(indexClient);
        System.out.println(messageHeartbeat);
    }

    /**
     * This method calls controlStillOpen method because the client doesn't send a response
     *
     * @param indexClient client index who doesn't send a response
     */
    @Override
    public void updateUnreachableClient(int indexClient) {
        synchronized (LOCK) {
            controlStillOpen(indexClient,false);
        }
    }

    /**
     * This method receive the notify of the connection closing
     *
     * @param indexClient client index who get disconnected
     */
    public void close(int indexClient) {
        System.out.println("The client: " + indexClient + " will be closed");
    }

    /**
     * This method controls if a client is still open
     *
     * @param indexClient client index who has to be checked
     * @param beforeStart if the game has not started yet
     */
    public void controlStillOpen(int indexClient, boolean beforeStart) {
        synchronized (LOCK) {
            ArrayList<ConnectionHandlerServerSide> connectionHandlersWaitingServerSide = sendMessageToClient.getEchoServer().getClientWaiting();
            ArrayList<ConnectionHandlerServerSide> clientArray = sendMessageToClient.getEchoServer().getClientArray();
            if (!beforeStart) {
                afterStart(connectionHandlersWaitingServerSide, clientArray, indexClient);
            } else {
                connectionHandlersWaitingServerSide.remove(indexClient);
            }
        }

    }

    /**
     * This method is recall if there is a disconnection before the game has started
     *
     * @param connectionHandlersWaitingServerSide array of client waiting for playing
     * @param clientArray           array of client playing
     * @param indexClient           index identifying the client who sent the message to the server
     */

    public void afterStart(ArrayList<ConnectionHandlerServerSide> connectionHandlersWaitingServerSide, ArrayList<ConnectionHandlerServerSide> clientArray, int indexClient) {
        if (connectionHandlersWaitingServerSide.size() > 0 && indexClient == 0 && gameModel.getNPlayers() == 0) {
            beforeInitializationFirstClient(connectionHandlersWaitingServerSide, indexClient);
        } else if (connectionHandlersWaitingServerSide.size() > 0 && gameModel.getNPlayers() == 0) {
            beforeInitializationOtherClient(connectionHandlersWaitingServerSide, indexClient);
        } else if (clientArray.size() == gameModel.getNPlayers() && gameModel.getNPlayers() != 0) {
            afterInitialization(connectionHandlersWaitingServerSide, clientArray, indexClient);
        }
    }

    /**
     * This method is recall if there is a disconnection before the game has started and is the first client that has disconnected
     *
     * @param connectionHandlersWaitingServerSide array of client waiting for playing
     * @param indexClient           index identifying the client who sent the message to the server
     */

    public void beforeInitializationFirstClient(ArrayList<ConnectionHandlerServerSide> connectionHandlersWaitingServerSide, int indexClient) {
        boolean closed = connectionHandlersWaitingServerSide.get(indexClient).isClosed();
        if (closed) {
            connectionHandlersWaitingServerSide.remove(indexClient);
            controller.removePlayer(indexClient);
        } else {
            sendMessageToClient.sendCloseConnection(indexClient, false);
        }
    }

    /**
     * This method is recall if there is a disconnection before the game has started and a client different from the first has disconnected
     *
     * @param connectionHandlersWaitingServerSide array of client waiting for playing
     * @param indexClient           index identifying the client who sent the message to the server
     */

    public void beforeInitializationOtherClient(ArrayList<ConnectionHandlerServerSide> connectionHandlersWaitingServerSide, int indexClient) {
        boolean closed = connectionHandlersWaitingServerSide.get(indexClient).isClosed();
        if (closed) {
            connectionHandlersWaitingServerSide.remove(indexClient);
            controller.removePlayer(indexClient);
            sendMessageToClient.getEchoServer().updateIndexClientWaiting(indexClient);
        } else {
            sendMessageToClient.sendCloseConnection(indexClient, false);
        }
    }

    /**
     * This method is recall if there is a disconnection and the game has already started
     *
     * @param connectionHandlersWaitingServerSide array of client waiting for playing
     * @param clientArray           array of client playing
     * @param indexClient           index identifying the client who sent the message to the server
     */

    public void afterInitialization(ArrayList<ConnectionHandlerServerSide> connectionHandlersWaitingServerSide, ArrayList<ConnectionHandlerServerSide> clientArray, int indexClient) {
        if (indexClient < gameModel.getNPlayers()) {
            boolean closed = clientArray.get(indexClient).isClosed();
            if (closed) {
                clientArray.remove(indexClient);
                controller.removePlayer(indexClient);
                sendMessageToClient.getEchoServer().updateIndexClient(indexClient);
            } else {
                sendMessageToClient.sendCloseConnection(indexClient, false);
            }
        } else if (connectionHandlersWaitingServerSide.size() != 0) {
            connectionHandlersWaitingServerSide.remove(indexClient);
            controller.removePlayer(indexClient);
        }
    }

    /**
     * This method controls if the first player sets the number of player in the game
     */
    public void updateControlSetNPlayer() {
        boolean done = controller.controlSetNPlayer();
        if (!done) {
            sendMessageToClient.sendAskNPlayer(true);
        } else {
            sendMessageToClient.getEchoServer().closeServerHandlers();

        }
    }

    /**
     * This method closes all the client and invokes reset method
     */
    @Override
    public void closeGame() {
        sendMessageToClient.sendCloseConnection(false);
        reset();
    }

    /**
     * This method resets the arrays in echoServer class
     */
    @Override
    public void reset() {
        sendMessageToClient.getEchoServer().getClientArray().clear();
        sendMessageToClient.getEchoServer().getClientWaiting().clear();
    }
}

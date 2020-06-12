package it.polimi.ingsw.server;

import it.polimi.ingsw.network.SendMessageToClient;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;


import java.util.ArrayList;

public class VirtualView implements Observer {

    private ProxyGameModel gameModel;
    private Controller controller;
    SendMessageToClient sendMessageToClient;
    private final Object LOCK = new Object();


    public VirtualView(SendMessageToClient sendMessageToClient) throws Exception {
        gameModel= new ProxyGameModel();
        controller= new Controller(gameModel);
        subscribe();
        this.sendMessageToClient=sendMessageToClient;
    }

    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    public ArrayList<Player> getPlayerArray(){
        return gameModel.getPlayerArray();
    }

    /**
     * This method handles all the clients which are connecting at the beginning of the game
     * @param indexClient client index who is connecting in the game
     */
    public void askWantToPlay(int indexClient){
        if(gameModel.getNPlayers() == 0 && getPlayerArray().size() == 0){
            controller.addPlayer(indexClient);
            sendMessageToClient.sendAskNPlayer(false);
        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() == 0){
            controller.addPlayer(indexClient);
            sendMessageToClient.sendYouHaveToWait(indexClient);
        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() != 0){
            controller.addPlayer(indexClient);
            if(getPlayerArray().size() == gameModel.getNPlayers()){
                sendMessageToClient.YouCanPlay(gameModel.getNPlayers());
            }else if (getPlayerArray().size() < gameModel.getNPlayers() ){
                sendMessageToClient.sendYouHaveToWait(indexClient);
            }else{
                sendMessageToClient.sendCloseConnection(indexClient, true);
            }
        }
    }

    /**
     * This method sets the number of players and sends the requests of player information
     * @param npLayer number of players that are going to play the game
     */
    public void setNPlayers(int npLayer){
        controller.setNPlayers(npLayer);
        if(getPlayerArray().size() == 1 ){
            sendMessageToClient.sendAskPlayer(npLayer, false);
        }else if (getPlayerArray().size() >= npLayer){
            sendMessageToClient.sendAskPlayer(npLayer, true);
        }else{
            sendMessageToClient.sendYouHaveToWait(0);
        }
    }

    /**
     * This method adds the player: if the players are more than the possible players in the game, this method is going to remove the extra players
     * @param name player name
     * @param age player age
     * @param indexClient client index of the player
     */
    public void addPlayer(String name, int age, int indexClient){
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
    public void askState(){
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
     * @param tempCard two or three cards chosen
     */
    public void setTempCard(ArrayList<Integer> tempCard){
        controller.setTempCard(tempCard);
    }

    /**
     * This method sets the card chosen by the player
     * @param godCard chosen card
     */
    public void setCard(int godCard){
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
    public void startGame(){
        controller.startGame();
    }

    /**
     * This method sends an update about player index to the players
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
     * @param clientIndex client index who is playing
     */
    @Override
    public void updateTempCard(int clientIndex){
        AskCardEvent askCardEvent = new AskCardEvent(gameModel.getTempCard());
        askCardEvent.setCurrentClientPlaying(clientIndex);

        if(askCardEvent.getCardTemp().size() != 0){
            sendMessageToClient.sendAskCard(askCardEvent);
        }
        else{
            updateBoard(false);
            AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
            askInitializeWorkerEvent.setCurrentClientPlaying(clientIndex);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
        }
    }

    /**
     * This method sends an update of the board to the players
     * @param reach boolean that identifies if the clients have to print the reachable boxes
     */
    @Override
    public void updateBoard(boolean reach){
        sendMessageToClient.sendUpdateBoard(gameModel.gameData(reach));
    }

    /**
     * This method sends another request of player information to the client
     * @param indexClient client index who is playing
     */
    @Override
    public void updateNewAddPlayer(int indexClient){
        sendMessageToClient.sendAskPlayerAgain(indexClient);
    }

    /**
     * This method calls the initializeWorker method in the controller class
     * @param box1 box where the player wants to put the first worker
     * @param box2 box where the player wants to put the second worker
     */
    public void initializeWorker(Box box1, Box box2) {
        controller.initializeWorker(box1,box2);
    }

    /**
     * This method sends an update of the board and the request to choose the worker to move, if the first player has to play; otherwise sends the request to initialize the workers
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     */
    @Override
    public void updateInitializeWorker(int indexClient, int indexPlayer){
        updateBoard(false);
        if(indexPlayer == 0){
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
        }else{
            AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
            askInitializeWorkerEvent.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
        }
    }

    /**
     * This method sends another request to initialize the workers, if the player chooses an occupied box
     * @param indexClient client index who is playing
     */
    @Override
    public void updateNotInitializeWorker(int indexClient){
        AskInitializeWorkerEvent askInitializeWorkerEvent = new AskInitializeWorkerEvent();
        askInitializeWorkerEvent.setCurrentClientPlaying(indexClient);
        sendMessageToClient.sendAskInitializeWorker(askInitializeWorkerEvent);
    }

    /**
     * This method tells if a box is reachable by a worker
     * @param row row of the box where the worker wants to move or to build
     * @param column column of the box where the worker wants to move or to build
     * @return true if the box is reachable, otherwise returns false
     */
    public boolean isReachable(int row, int column){
        return gameModel.isReachable(row,column);
    }

    /**
     * This method calls the canMove method in the controller class
     */
    public void canMove(){
        controller.canMove();
    }

    /**
     * This method calls the canMoveSpecialTurn method in the controller class
     * @param indexWorker worker index that the player wants to move
     * @param rowWorker row of the box where the worker is
     * @param columnWorker column of the box where the worker is
     */
    public void canMoveSpecialTurn(int indexWorker, int rowWorker, int columnWorker ){
        controller.canMoveSpecialTurn(indexWorker, rowWorker, columnWorker);
    }

    /**
     * This method calls the setBoxReachable method in the controller class if the player didn't choose definitively the worker, otherwise calls the canBuildBeforeWorkerMove method in the controller
     * @param row row of the box where the worker is
     * @param column column of the box where the worker is
     * @param indexWorkerToMove worker index that the player wants to move
     * @param isReady boolean that identifies if the player has chosen the worker or not
     */
    public void setBoxReachable(int row, int column, int indexWorkerToMove, boolean isReady){
        if(isReady){
            controller.canBuildBeforeWorkerMove(row, column, indexWorkerToMove);
        }else {
            controller.setBoxReachable(indexWorkerToMove , false);
        }
    }

    /**
     * This method sends an update of the board and
     * @param indexClient client index who is playing
     * @param indexPlayer player index
     * @param indexWorker worker index that the player wants to move
     * @param secondMove boolean that identifies if it is the first or the second move
     */
    @Override
    public void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove){
        updateBoard(true);
        if(!secondMove) {
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, false);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            askWorkerToMoveEvent.setIndexWorker(indexWorker);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
        }
    }
    ///richiamato
    public AskWorkerToMoveEvent getWorkersPos(int indexPlayer, boolean firstMove){
        ArrayList<Box> positions = gameModel.getWorkersPos(indexPlayer);
        return new AskWorkerToMoveEvent(positions.get(0).getRow(), positions.get(0).getColumn(), positions.get(1).getRow(), positions.get(1).getColumn(),firstMove);
    }

    ///richiamato
    public void move(int rowStart, int columnStart, int row, int column, int indexWorkerToMove){
        if(isReachable( row, column)){
            controller.movePlayer(rowStart, columnStart, row, column, indexWorkerToMove);
        }else{
            updateBoard(true);
            AskMoveEvent askMoveEvent = new AskMoveEvent(indexWorkerToMove, rowStart, columnStart, true, false);
            askMoveEvent.setWrongBox(true);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }
    }

    @Override
    public void updateSpecialTurn(int row, int column, int indexWorkerToMove) {
        canBuildSpecialTurn(indexWorkerToMove, row, column);
    }

    @Override
    public void updateBasicTurn(int indexWorker, int rowWorker, int columnWorker) {
        AskMoveEvent askMoveEvent = new AskMoveEvent( indexWorker, rowWorker, columnWorker,true,false);
        askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskMoveEvent(askMoveEvent);
    }

    @Override
    public void updateAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        AskBuildBeforeMoveEvent askBuildBeforeMoveEvent = new AskBuildBeforeMoveEvent(indexWorker, rowWorker, columnWorker);
        askBuildBeforeMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildBeforeMove(askBuildBeforeMoveEvent);
    }

    public void buildBeforeMove(int indexWorker, int rowWorker, int columnWorker, boolean wantToBuild){
        if(wantToBuild){
            ///gli chiedo dove vuole costruire
            setBoxBuilding(indexWorker);
            AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, true);
            askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskBuildEvent(askBuildEvent);

        }else{
            updateBasicTurn(indexWorker, rowWorker, columnWorker);
        }
    }

    ///richiamato
    @Override
    public void updateMove(AskMoveEvent askMoveEvent, int clientIndex){
        System.out.println("Pedina mossa");
        updateBoard(false);
        checkWin(askMoveEvent, clientIndex);
    }

    public void checkWin(AskMoveEvent askMoveEvent, int clientIndex){
        controller.checkWin(askMoveEvent, clientIndex);
    }

    @Override
    public void updateWin(int indexClient) {
        sendMessageToClient.sendWin(indexClient);
    }

    @Override
    public void updateLoser(int indexClient) {
        sendMessageToClient.sendLoser(indexClient);
    }

    @Override
    public void updateContinueMove(AskMoveEvent askMoveEvent) {
        if(askMoveEvent.isDone()){
            canBuild(askMoveEvent.getClientIndex(), askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn());
        }else{
            controller.setBoxReachable(askMoveEvent.getIndexWorker(), true);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }
    }

    //Metodo per verificare se è possibile costruire attorno al proprio worker, se non è possibile il giocatore ha perso
    public void canBuild(int indexClient, int indexWorker, int rowWorker, int columnWorker){
        controller.canBuild(indexClient, indexWorker, rowWorker, columnWorker);
    }
    public void canBuildSpecialTurn(int indexWorker, int rowWorker, int columnWorker){
        controller.canBuildSpecialTurn(indexWorker, rowWorker, columnWorker);
    }


    @Override
    public void updateCanBuild(int indexWorker, int rowWorker, int columnWorker) {
        updateBoard(true);
        AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, false);
        askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildEvent(askBuildEvent);
    }

    //Metodo per fare la setPossibleBuild
    public void setBoxBuilding(int indexWorker) {
        controller.setBoxBuilding(indexWorker);
    }

    //Probabilmente non serve
    @Override
    public void updateSetBuilding(){
        updateBoard(true);
        System.out.println("Aggiornate celle dove si può costruire");
    }

    //Metodo per fare la costruzione del piano
    public void buildBlock(int indexClient, int indexWorker, int rowWorker, int columnWorker, int row, int column, boolean isSpecialTurn, int indexPossibleBlock) {
        if(isReachable(row, column)){
            controller.buildBlock(indexClient, indexWorker, rowWorker, columnWorker, row, column, isSpecialTurn, indexPossibleBlock);
        }else{
            updateBoard(true);
            //todo Controllo se è la prima o la seconda mossa
            AskBuildEvent askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, true, false, isSpecialTurn);
            askBuildEvent.setWrongBox(true);
            askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskBuildEvent(askBuildEvent);
        }
    }

    @Override
    public void updateBuild(AskBuildEvent askBuildEvent, int clientIndex){
        System.out.println("Costruito");
        updateBoard(false);
        checkWinAfterBuild(askBuildEvent);
    }

    @Override
    public void updateContinueBuild(AskBuildEvent askBuildEvent) {
        if(askBuildEvent.isSpecialTurn()){
            canMoveSpecialTurn(askBuildEvent.getIndexWorker(), askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker());
        }else {
            if (askBuildEvent.isDone()) {
                canMove();
            } else {
                setBoxBuilding(askBuildEvent.getIndexWorker());
                askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
                sendMessageToClient.sendAskBuildEvent(askBuildEvent);
            }
        }
    }

    //Verifica se crono ha vinto
    public void checkWinAfterBuild(AskBuildEvent askBuildEvent) {
        controller.checkWinAfterBuild(askBuildEvent);
    }

    @Override
    public void updateStartTurn(){
        int indexPlayer = gameModel.whoIsPlaying();
        int indexClient = gameModel.searchByPlayerIndex(indexPlayer);
        AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true);
        askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
        sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
    }

    @Override
    public void updateWhoHasLost(int loserClient) {
        sendMessageToClient.sendWhoHasLost(loserClient);
        canMove();
    }

    public void setPause(){
        controller.setPause();
    }

    public void printHeartBeat(String messageHeartbeat, int indexClient) {
        controller.heartBeat(indexClient);
        System.out.println(messageHeartbeat);
    }

    @Override
    public void updateUnreachableClient(int indexClient) {
        synchronized (LOCK) {
            controlStillOpen(indexClient);
        }
    }

    public void close(int indexClient) {
        System.out.println("client: "+ indexClient +" will be closed");
    }

    public void controlStillOpen(int indexClient){
        //todo da controllare quanto migliorabile
        synchronized (LOCK) {
            ArrayList<ServerHandler> serverHandlersWaiting = sendMessageToClient.getEchoServer().getClientWaiting();
            ArrayList<ServerHandler> serverHandlers = sendMessageToClient.getEchoServer().getClientArray();


            //quando ancora stanno aspettando l'nplayer e il tizio 0 si disconnette o un altro x
            if (serverHandlersWaiting.size() > 0 && indexClient == 0 && gameModel.getNPlayers() == 0) {
                boolean closed = serverHandlersWaiting.get(indexClient).isClosed();
                if (closed) {
                    serverHandlersWaiting.remove(indexClient);
                    controller.removePlayer(indexClient);
                } else {
                    sendMessageToClient.sendCloseConnection(indexClient, false);
                }
            } else if (serverHandlersWaiting.size() > 0 && gameModel.getNPlayers() == 0) {
                boolean closed = serverHandlersWaiting.get(indexClient).isClosed();
                if (closed) {
                    serverHandlersWaiting.remove(indexClient);
                    controller.removePlayer(indexClient);
                    sendMessageToClient.getEchoServer().updateIndexClientWaiting(indexClient);
                } else {
                    sendMessageToClient.sendCloseConnection(indexClient, false);
                }
            } else if (serverHandlers.size() == gameModel.getNPlayers() && gameModel.getNPlayers() != 0) {
                if(indexClient < gameModel.getNPlayers()) {
                    boolean closed = serverHandlers.get(indexClient).isClosed();
                    if (closed) {
                        serverHandlers.remove(indexClient);
                        controller.removePlayer(indexClient);
                        sendMessageToClient.getEchoServer().updateIndexClient(indexClient);
                    } else {
                        sendMessageToClient.sendCloseConnection(indexClient, false);
                    }
                }else if(serverHandlersWaiting.size() != 0){
                    serverHandlersWaiting.remove(indexClient);
                    controller.removePlayer(indexClient);
                }
            }
        }
    }


    public void updateControlSetNPlayer(){
        boolean done = controller.controlSetNPlayer();
        if(!done){
            sendMessageToClient.sendAskNPlayer(true);
        }else{
            sendMessageToClient.getEchoServer().closeServerHandlers();

        }
    }

    @Override
    public void closeGame() {
        sendMessageToClient.sendCloseConnection( false);
        reset();
    }

    @Override
    public void reset() {
        sendMessageToClient.getEchoServer().getClientArray().clear();
        sendMessageToClient.getEchoServer().getClientWaiting().clear();
    }
}

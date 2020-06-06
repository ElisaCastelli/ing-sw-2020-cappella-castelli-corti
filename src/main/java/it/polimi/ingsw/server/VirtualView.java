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

    public void askWantToPlay(int indexClient){
        if(gameModel.getNPlayers() == 0 && getPlayerArray().size() == 0){

            controller.addPlayer(indexClient);
            sendMessageToClient.sendAskNPlayer(false);

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() == 0){
            //non hanno settato ancora ma hai la possibilità di giocare

            controller.addPlayer(indexClient);
            sendMessageToClient.sendYouHaveToWait(indexClient);

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() != 0){

            controller.addPlayer(indexClient);

            if(getPlayerArray().size() == gameModel.getNPlayers()){
                ///se sei arrivato per terzo poi giocare e sei quello che manda il broardcast di AskPlayer
                sendMessageToClient.YouCanPlay(gameModel.getNPlayers());

            }else if (getPlayerArray().size() < gameModel.getNPlayers() ){
                //sei arrivato per secondo e poi giocare ma devi aspettare il terzo
                sendMessageToClient.sendYouHaveToWait(indexClient);
            }else{
                //non giochi mai
                sendMessageToClient.sendCloseConnection(indexClient, true);
            }
        }
    }

    public void setNPlayers(int npLayer){
        controller.setNPlayers(npLayer);

        boolean sendBroadcast = true;
        if(getPlayerArray().size() == 1 ){
            sendBroadcast= false;
            sendMessageToClient.sendAskPlayer(npLayer, sendBroadcast);
        }else if (getPlayerArray().size() >= npLayer){
            sendMessageToClient.sendAskPlayer(npLayer, sendBroadcast);
        }else{
            sendMessageToClient.sendYouHaveToWait(0);
        }
    }

    @Override
    public void updateNPlayer() {
        System.out.println("setting nplayer "+gameModel.getNPlayers());

    }

    public void addPlayer(String name, int age, int indexClient){
        synchronized (LOCK) {
            if (getPlayerArray().size() > gameModel.getNPlayers()) {
                controller.removeExtraPlayer();
            }
            controller.addPlayer(name, age, indexClient);
        }
    }

    public void askState(){
        controller.askState();
    }

    public void getCards() {
        try {
            Ask3CardsEvent ask3CardsEvent = new Ask3CardsEvent(gameModel.getCards());
            int clientIndex = gameModel.searchByPlayerIndex(0);
            ask3CardsEvent.setCurrentClientPlaying(clientIndex);
            sendMessageToClient.sendCards(ask3CardsEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTempCard(ArrayList<Integer> tempCard){
        controller.setTempCard(tempCard);
    }

    public void setCard(int playerIndex, int godCard) throws Exception {
        controller.setCard(playerIndex, godCard);
    }

    @Override
    public void updatePlayer() {
        startGame();
        sendMessageToClient.getEchoServer().resetWaiting();
        sendMessageToClient.sendStartGameEvent(gameModel.getNPlayers());
    }

    public void startGame(){
        controller.startGame();
    }

    @Override
    public void updateAskState(int indexClient, int indexPlayer) {
        ObjState objState = new ObjState(indexPlayer);
        sendMessageToClient.sendObjState(indexClient, objState);
    }

    @Override
    public void updateTempCard(int clientIndex){
        AskCard askCard = new AskCard(gameModel.getTempCard());
        askCard.setCurrentClientPlaying(clientIndex);

        if(askCard.getCardTemp().size() != 0){
            sendMessageToClient.sendAskCard(askCard);
        }
        else{
            System.out.println("Sending board");
            updateBoard(false);
            AskInitializeWorker askInitializeWorker = new AskInitializeWorker();
            askInitializeWorker.setCurrentClientPlaying(clientIndex);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorker);
        }
    }

    public synchronized void goPlayingNext(){
        controller.goPlayingNext();
    }

    @Override
    public void updateWhoIsPlaying() {
        /*ObjState objState = new ObjState();
        objState.setCurrentPlayer(gameModel.whoIsPlaying());*/
    }

    @Override
    public void updateBoard(boolean reach){
        sendMessageToClient.sendUpdateBoard(gameModel.gameData(reach));
    }

    @Override
    public void updateNewAddPlayer(int indexClient){
        sendMessageToClient.sendAskPlayerAgain(indexClient);
    }

    public void initializeWorker(Box box1, Box box2) {
        controller.initializeWorker(box1,box2);
    }

    @Override
    public void updateInitializeWorker(int indexClient, int indexPlayer){
        System.out.println("Ho inizializzato la pedina");
        updateBoard(false);
        if(indexPlayer == 0){
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, true);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
        }else{
            AskInitializeWorker askInitializeWorker = new AskInitializeWorker();
            askInitializeWorker.setCurrentClientPlaying(indexClient);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorker);
        }
    }

    @Override
    public void updateNotInitializeWorker(int indexClient){
        AskInitializeWorker askInitializeWorker = new AskInitializeWorker();
        askInitializeWorker.setCurrentClientPlaying(indexClient);
        sendMessageToClient.sendAskInitializeWorker(askInitializeWorker);
    }


    public boolean isReachable(int row, int column){
        return gameModel.isReachable(row,column);
    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    public void canMove(){
        controller.canMove();
    }
    public void canMoveSpecialTurn(int indexWorker, int rowWorker, int columnWorker ){ controller.canMoveSpecialTurn(indexWorker, rowWorker, columnWorker);}

    /// richiamato
    public void setBoxReachable(int row, int column, int indexWorkerToMove, boolean isReady){
        if(isReady){
            controller.canBuildBeforeWorkerMove(row, column, indexWorkerToMove);
        }else {
            controller.setBoxReachable(indexWorkerToMove , false);
        }
    }
    ///richiamato
    @Override
    public void updateReachable(int indexClient, int indexPlayer, int indexWorker, boolean secondMove){
        updateBoard(true);
        if(!secondMove) {
            AskWorkerToMoveEvent askWorkerToMoveEvent = getWorkersPos(indexPlayer, false);
            askWorkerToMoveEvent.setCurrentClientPlaying(indexClient);
            askWorkerToMoveEvent.setIndexWorker(indexWorker);
            sendMessageToClient.sendAskWorkerToMoveEvent(askWorkerToMoveEvent);
            System.out.println("Aggiornato celle raggiungibili");
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
        AskBuildBeforeMove askBuildBeforeMove = new AskBuildBeforeMove(indexWorker, rowWorker, columnWorker);
        askBuildBeforeMove.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildBeforeMove(askBuildBeforeMove);
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

    @Override
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

    //Probabilmente non serve
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

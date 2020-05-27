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
    private boolean ready;
    private int winnerPlayer;
    SendMessageToClient sendMessageToClient;
    private final Object LOCK = new Object();


    public VirtualView(SendMessageToClient sendMessageToClient) throws Exception {
        gameModel= new ProxyGameModel();
        controller= new Controller(gameModel);
        subscribe();
        ready=false;
        winnerPlayer=-1;
        this.sendMessageToClient=sendMessageToClient;
    }
    public synchronized boolean isReady() {
        return ready;
    }
    public synchronized void setReady(boolean ready) {
        this.ready = ready;
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

            getPlayerArray().add(new Player(indexClient));
            sendMessageToClient.sendAskNPlayer();

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() == 0){
            //non hanno settato ancora ma hai la possibilità di giocare
            getPlayerArray().add(new Player(indexClient));
            sendMessageToClient.sendYouHaveToWait(indexClient);

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() != 0){
            getPlayerArray().add(new Player(indexClient));
            if(getPlayerArray().size() == gameModel.getNPlayers()){
                ///se sei arrivato per terzo poi giocare e sei quello che manda il broardcast di AskPlayer
                sendMessageToClient.YouCanPlay(gameModel.getNPlayers());

            }else if (getPlayerArray().size() < gameModel.getNPlayers() ){
                //sei arrivato per secondo e poi giocare ma devi aspettare il terzo
                sendMessageToClient.sendYouHaveToWait(indexClient);
            }else{
                //non giochi mai
                sendMessageToClient.sendYouHaveToWait(indexClient);
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
    public ObjNumPlayer updateNPlayer() {
        return new ObjNumPlayer(gameModel.getNPlayers());
    }

    public void addPlayer(String name, int age, int indexClient){
        synchronized (LOCK) {
            if (getPlayerArray().size() > gameModel.getNPlayers()) {
                if (getPlayerArray().size() > gameModel.getNPlayers()) {
                    getPlayerArray().subList(gameModel.getNPlayers(), getPlayerArray().size()).clear();
                }
            }
            controller.addPlayer(name, age, indexClient);
        }
    }

    public void askState(){
        controller.askState();
    }

    public int searchByName(String name){
        return gameModel.searchByName(name);
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


    public void initializeWorker(ObjWorkers objWorkers) {
        controller.initializeWorker(objWorkers.getBox1(),objWorkers.getBox2());
    }

    @Override
    public void updateInitializeWorker(int indexClient,int indexPlayer){
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
    public boolean canMove(int indexPlayer){
        return controller.canMove(indexPlayer);
    }

    /// richiamato
    public void setBoxReachable(ObjWorkerToMove objWorkerToMove){
        if(objWorkerToMove.isReady()){
            AskMoveEvent askMoveEvent= new AskMoveEvent(objWorkerToMove.getIndexWorkerToMove(), objWorkerToMove.getRow(), objWorkerToMove.getColumn(),true,false);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }else {
            controller.setBoxReachable(objWorkerToMove.getIndexWorkerToMove(), false);
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
    public void move(ObjMove objMove){
        if(isReachable(objMove.getRow(),objMove.getColumn())){
            controller.movePlayer(objMove);
        }else{
            updateBoard(true);
            AskMoveEvent askMoveEvent = new AskMoveEvent(objMove.getIndexWorkerToMove(), objMove.getRowStart(), objMove.getColumnStart(), true, false);
            askMoveEvent.setWrongBox(true);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }
    }
    ///richiamato
    @Override
    public void updateMove(AskMoveEvent askMoveEvent,int clientIndex){
        System.out.println("Pedina mossa");
        updateBoard(false);
        checkWin(askMoveEvent,clientIndex);
    }

    public void checkWin(AskMoveEvent askMoveEvent, int clientIndex){
        controller.checkWin(askMoveEvent);
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
            canBuild(askMoveEvent);
        }else{
            controller.setBoxReachable(askMoveEvent.getIndexWorker(), true);
            askMoveEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
            sendMessageToClient.sendAskMoveEvent(askMoveEvent);
        }
    }
    @Override
    //Metodo per verificare se è possibile costruire attorno al proprio worker, se non è possibile il giocatore ha perso
    public void canBuild(AskMoveEvent askMoveEvent){
        controller.canBuild(askMoveEvent);
    }

    @Override
    public void updateCanBuild(AskMoveEvent askMoveEvent) {
        updateBoard(true);
        AskBuildEvent askBuildEvent = new AskBuildEvent(askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn(), true, false);
        askBuildEvent.setCurrentClientPlaying(gameModel.searchByPlayerIndex(gameModel.whoIsPlaying()));
        sendMessageToClient.sendAskBuildEvent(askBuildEvent);
    }

    //Metodo per fare la setPossibleBuild
        public void setBoxBuilding(int indexPlayer, int indexWorker) {
        controller.setBoxBuilding(indexPlayer, indexWorker);
    }

    //Probabilmente non serve
    @Override
    public void updateSetBuilding(){
        System.out.println("Aggiornate celle dove si può costruire");
    }

    //Metodo per fare la costruzione del piano
    public AskBuildEvent buildBlock(int indexPlayer, int indexWorker, int rowWorker, int columnWorker, int row, int column) {
        return controller.buildBlock(indexPlayer, indexWorker, rowWorker, columnWorker, row, column);
    }

    //Probabilmente non serve
    @Override
    public void updateBuild(){
        System.out.println("Costruito");
    }

    //Verifica se crono ha vinto
    public boolean checkWinAfterBuild() {
        return controller.checkWinAfterBuild();
    }

    public void setPause(){
        controller.setPause();
    }

}

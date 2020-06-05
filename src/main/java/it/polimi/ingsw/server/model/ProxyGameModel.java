package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.network.objects.ObjWorkerToMove;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.Observer;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ProxyGameModel implements GameModel, Subject{
    private GameModel gameModel;
    private final Object LOCK = new Object();

    private Observer observer;
    public ProxyGameModel() throws Exception {
        this.gameModel = new Game();
    }

    @Override
    public void subscribeObserver(Observer observer) {
        this.observer=observer;
    }
    @Override
    public Board getBoard(){
        return gameModel.getBoard();
    }
    @Override
    public ArrayList<Player> getPlayerArray(){
        return gameModel.getPlayerArray();
    }
    @Override
    public int getNPlayers(){
        return gameModel.getNPlayers();
    }
    @Override
    public void setNPlayers(int nPlayers) {
        gameModel.setNPlayers(nPlayers);
    }

    public void addPlayerProxy(int indexClient, Timer timer) {
        TimerTask timerTask =new TimerTask() {
            @Override
            public void run() {
                synchronized (LOCK) {
                    int indexPlayer = searchByClientIndex(indexClient);
                    if(indexPlayer != -1) {
                        boolean connected = gameModel.incrementHeartBeat(indexPlayer);
                        if (!connected) {
                            observer.updateUnreachableClient(indexClient);
                        }
                    }else{
                        timer.cancel();
                        timer.purge();
                    }
                }
            }
        };
        synchronized (LOCK) {
            gameModel.addPlayer(indexClient, timer, timerTask);
            incrementHeartBeats(timer, timerTask);
        }

    }

    @Override
    public void addPlayer(int indexClient, Timer timer, TimerTask timerTask) {

    }

    @Override
    public boolean incrementHeartBeat(int indexClient) {
        return false;
    }

    @Override
    public boolean addPlayer(String name, int age, int indexClient) {
        return gameModel.addPlayer(name, age, indexClient);
    }

    @Override
    public void removeExtraPlayer() {
        gameModel.removeExtraPlayer();
    }

    @Override
    public void remove(int indexClient) {
        int indexPlayer = searchByClientIndex(indexClient);
        gameModel.remove(indexPlayer);
        int sizePlayerArray = gameModel.getPlayerArray().size();
        if(sizePlayerArray != 0){
            if(getNPlayers() == 0 && indexClient == 0) {
                observer.updateControlSetNPlayer();
            }
            //il gioo è già iniziato e ci sono tutti
            if(getNPlayers() != 0 &&  sizePlayerArray == getNPlayers() - 1){
                reset();
                observer.closeGame();
            }

        }else{
            observer.reset();
        }
    }

    @Override
    public boolean askState() {
        return gameModel.askState();
    }

    @Override
    public int searchByName(String name){
        return gameModel.searchByName(name);
    }

    @Override
    public int searchByClientIndex(int indexClient) {
        return gameModel.searchByClientIndex(indexClient);
    }

    @Override
    public int searchByPlayerIndex(int playerIndex) {
        return gameModel.searchByPlayerIndex(playerIndex);
    }

    @Override
    public ArrayList<String> getCards() throws Exception {
        return gameModel.getCards();
    }

    @Override
    public UpdateBoardEvent gameData(boolean reach) {
        return gameModel.gameData(reach);
    }

    @Override
    public ArrayList<String> getTempCard(){
        return gameModel.getTempCard();
    }
    @Override
    public int chooseTempCard(ArrayList<Integer> tempCard){
        return gameModel.chooseTempCard(tempCard);
    }
    @Override
    public int chooseCard(int playerIndex, int godCard) throws Exception {
        return gameModel.chooseCard(playerIndex, godCard);
    }
    @Override
    public void goPlayingNext(){
        gameModel.goPlayingNext();
    }
    @Override
    public int whoIsPlaying(){
        return gameModel.whoIsPlaying();
    }
    @Override
    public ArrayList<String> getCardUsed(){
        return gameModel.getCardUsed();
    }
    @Override
    public void startGame(){
        gameModel.startGame();
    }
    @Override
    public boolean initializeWorker(Box box1, Box box2) {
        return gameModel.initializeWorker(box1, box2);
    }

    @Override
    public boolean isReachable(int row, int column){
        return gameModel.isReachable(row,column);
    }

    @Override
    public boolean canBuildBeforeWorkerMove() {
        return gameModel.canBuildBeforeWorkerMove();
    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    @Override
    public boolean canMove() {
        return gameModel.canMove();
    }

    @Override
    public boolean canMoveSpecialTurn(int indexWorker) {
        return gameModel.canMoveSpecialTurn(indexWorker);
    }

    @Override
    public void notifyUpdateBoard(boolean reach) {
        observer.updateBoard(reach);
    }

    @Override
    public void setBoxReachable(int indexWorker) {
        gameModel.setBoxReachable(indexWorker);
    }

    @Override
    public ArrayList<Box> getWorkersPos(int indexPlayer) {
        return gameModel.getWorkersPos(indexPlayer);
    }

    @Override
    public boolean movePlayer(int indexWorker, int row, int column) {
        return gameModel.movePlayer(indexWorker, row, column);
    }

    @Override
    public boolean canBuild(int indexWorker) {
        return gameModel.canBuild(indexWorker);
    }


    @Override
    public void notifyCanBuild(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateCanBuild(indexWorker, rowWorker, columnWorker);
    }

    @Override
    public void setBoxBuilding(int indexWorker) {
        gameModel.setBoxBuilding(indexWorker);
    }

    @Override
    public boolean buildBlock(int indexWorker, int row, int column) {
        return gameModel.buildBlock(indexWorker, row, column);
    }

    @Override
    public boolean checkWin(int rowStart, int columnStart, int indexWorker) {
        return gameModel.checkWin(rowStart,columnStart, indexWorker);
    }

    @Override
    public boolean checkWinAfterBuild() {
        return gameModel.checkWinAfterBuild();
    }

    @Override
    public int getWinner() {
        return gameModel.getWinner();
    }

    @Override
    public void setDeadPlayer(int indexPlayer){
        gameModel.setDeadPlayer(indexPlayer);
    }

    @Override
    public void setPause() {
        gameModel.setPause();
    }

    @Override
    public GameState getState(){
        return gameModel.getState();
    }

    @Override
    public void notifySetNPlayers(){
        observer.updateNPlayer();
    }

    @Override
    public void notifyAddPlayer(){
        observer.updatePlayer();
    }

    @Override
    public void notifyAskState(int indexClient,int indexPlayer) {
        observer.updateAskState(indexClient,indexPlayer);
    }

    @Override
    public void notifyTempCard(int indexClient){
        observer.updateTempCard(indexClient);
    }

    @Override
    public void notifyAddWorker(){
        int indexClient= searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateInitializeWorker(indexClient, gameModel.whoIsPlaying());
    }
    @Override
    public void notifyWorkersNotInitialized(){
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateNotInitializeWorker(indexClient);
    }



    @Override
    public void notifyWhoIsPlaying(){
        observer.updateWhoIsPlaying();
    }


    @Override
    public void notifySetReachable(int indexWorker, boolean secondMove){
        int indexClient = searchByPlayerIndex(gameModel.whoIsPlaying());
        observer.updateReachable(indexClient,gameModel.whoIsPlaying(), indexWorker, secondMove);
    }

    @Override
    public void notifySpecialTurn(int row, int column, int indexWorkerToMove) {
        observer.updateSpecialTurn(row, column, indexWorkerToMove);
    }

    @Override
    public void notifyBasicTurn(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateBasicTurn( indexWorker, rowWorker, columnWorker);
    }

    @Override
    public void notifyAskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        observer.updateAskBuildBeforeMove(indexWorker, rowWorker, columnWorker);
    }

    @Override
    public void notifyMovedWorker(AskMoveEvent askMoveEvent, int clientIndex){
        observer.updateMove(askMoveEvent, clientIndex);
    }

    @Override
    public void notifyWin(int winnerClient) {
        observer.updateWin(winnerClient);
    }

    @Override
    public void notifyLoser(int indexClient) {
        observer.updateLoser(indexClient);
    }

    @Override
    public void notifyWhoHasLost(int loserClient) {
        observer.updateWhoHasLost(loserClient);
    }

    @Override
    public void notifyContinueMove(AskMoveEvent askMoveEvent) {
        observer.updateContinueMove(askMoveEvent);
    }

    @Override
    public void notifySetBuilding(){
        observer.updateSetBuilding();
        observer.updateBoard(true);
    }

    @Override
    public void notifyBuildBlock(AskBuildEvent askBuildEvent, int clientIndex){
        observer.updateBuild(askBuildEvent, clientIndex);
    }

    @Override
    public void notifyContinueBuild(AskBuildEvent askBuildEvent) {
        observer.updateContinueBuild(askBuildEvent);
    }

    @Override
    public void notifyStartTurn() {
        observer.updateStartTurn();
    }

    @Override
    public void controlHeartBeat(int indexClient) {
        gameModel.controlHeartBeat(indexClient);
    }

    //da richiamare quando si aggiunge il player sull'array di giocatori
    public boolean incrementHeartBeats(Timer timer ,TimerTask timerTask){
        timer.scheduleAtFixedRate(timerTask , 10000, 100000);
        return  true;
    }

    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        gameModel.setIndexPossibleBlock(indexPossibleBlock);
    }

    @Override
    public void reset() {
        gameModel.reset();
    }
}

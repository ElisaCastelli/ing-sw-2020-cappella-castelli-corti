package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.Observer;

import java.util.ArrayList;

public class ProxyGameModel implements GameModel, Subject{
    private GameModel gameModel;

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
    @Override
    public boolean addPlayer(String name, int age, int indexClient) {
        return gameModel.addPlayer(name, age, indexClient);
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
    public UpdateBoardEvent gameData() {
        return gameModel.gameData();
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
    public boolean initializeWorker(int indexPlayer, Box box1, Box box2) {
        return gameModel.initializeWorker(indexPlayer, box1, box2);
    }

    @Override
    public boolean isReachable(int row, int column){
        return gameModel.isReachable(row,column);
    }


    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    @Override
    public boolean canMove(int indexPlayer) {
        return gameModel.canMove(indexPlayer);
    }

    @Override
    public void setBoxReachable(int indexPlayer, int indexWorker) {
        gameModel.setBoxReachable(indexPlayer, indexWorker);
    }

    @Override
    public ArrayList<Box> getWorkersPos(int indexPlayer) {
        return gameModel.getWorkersPos(indexPlayer);
    }

    @Override
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column) {
        return gameModel.movePlayer(indexPlayer, indexWorker, row, column);
    }

    @Override
    public boolean canBuild(int indexPlayer, int indexWorker) {
        return gameModel.canBuild(indexPlayer, indexWorker);
    }

    @Override
    public void setBoxBuilding(int indexPlayer, int indexWorker) {
        gameModel.setBoxBuilding(indexPlayer, indexWorker);
    }

    @Override
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column) {
        return gameModel.buildBlock(indexPlayer, indexWorker, row, column);
    }

    @Override
    public boolean checkWin(int indexPlayer, int rowStart, int columnStart, int indexWorker) {
        return gameModel.checkWin(indexPlayer, rowStart,columnStart, indexWorker);
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
    public ObjNumPlayer notifySetNPlayers(){
        return observer.updateNPlayer();
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
    public void notifyTempCard(int clientIndex){
        observer.updateTempCard(clientIndex);
    }

    @Override
    public void notifyAddWorker(){
        observer.updateInitializeWorker();
        observer.updateBoard();
    }
    @Override
    public void notifyWhoIsPlaying(){
        observer.updateWhoIsPlaying();
    }


    @Override
    public void notifySetReachable(){
        observer.updateReachable();
        observer.updateBoard();
    }

    @Override
    public void notifyMovedWorker(){
        observer.updateMove();
    }

    @Override
    public void notifySetBuilding(){
        observer.updateSetBuilding();
        observer.updateBoard();
    }

    @Override
    public void notifyBuildBlock(){
        observer.updateBuild();
    }

}

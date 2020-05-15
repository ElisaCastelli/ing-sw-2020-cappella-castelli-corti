package it.polimi.ingsw.server.model;

import it.polimi.ingsw.network.events.AskCard;
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
    public void addPlayer(String name, int age) {
        gameModel.addPlayer(name, age);
    }
    @Override
    public int searchByName(String name){
        return gameModel.searchByName(name);

    }
    @Override
    public ArrayList<String> getCards() throws Exception {
        return gameModel.getCards();
    }
    @Override
    public ArrayList<String> getTempCard(){
        return gameModel.getTempCard();
    }
    @Override
    public void chooseTempCard(ArrayList<Integer> tempCard){
        gameModel.chooseTempCard(tempCard);
    }
    @Override
    public void chooseCard(int playerIndex, int godCard) throws Exception {
        gameModel.chooseCard(playerIndex, godCard);
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
    public boolean canMove(int indexPlayer) {
        return gameModel.canMove(indexPlayer);
    }

    @Override
    public void setBoxReachable(int indexPlayer, int indexWorker) {
        gameModel.setBoxReachable(indexPlayer, indexWorker);
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
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker) {
        return gameModel.checkWin(indexPlayer, startBox, indexWorker);
    }

    @Override
    public boolean checkWinAfterBuild() {
        return gameModel.checkWinAfterBuild();
    }

    @Override
    public void setWinningPlayer(int indexPlayer) {
        gameModel.setWinningPlayer(indexPlayer);
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
    public AskCard notifyTempCard(){
        return observer.updateTempCard();
    }
    @Override
    public void notifyAddWorker(){
        observer.updateInizializaWorker();
        observer.updateBoard();
    }
    @Override
    public ObjState notifyWhoIsPlaying(){
        return observer.updateWhoIsPlaying();
    }


    @Override
    public void notifySetReachable(){
        observer.updateReachable();
        observer.updateBoard();
    }

    @Override
    public void notifyMovedWorker(){
        observer.updateMove();
        observer.updateBoard();
    }

    @Override
    public void notifySetBuilding(){
        observer.updateSetBuilding();
        observer.updateBoard();
    }

    @Override
    public void notifyBuildBlock(){
        observer.updateBuild();
        observer.updateBoard();
    }

}

package it.polimi.ingsw.model;

import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.god.God;
import it.polimi.ingsw.view.Observer;

import java.util.ArrayList;

public class ProxyGameModel implements GameModel, Subject{
    private GameModel gameModel;

    private Observer observer;
    public ProxyGameModel(GameModel gameModel){
        this.gameModel=gameModel;
    }

    @Override
    public void setNPlayers(int nPlayers) {
        gameModel.setNPlayers(nPlayers);
    }

    @Override
    public void addPlayer(String name, int age, Game.COLOR color) {
        gameModel.addPlayer(name, age, color);
    }

    @Override
    public ArrayList<God> getCards() throws Exception {
        return gameModel.getCards();
    }

    @Override
    public void chooseCard(int playerIndex, int godCard) throws Exception {
        gameModel.chooseCard(playerIndex, godCard);
    }

    @Override
    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box) {
        return gameModel.initializeWorker(indexPlayer, indexWorker, box);
    }

    @Override
    public void startTurn(int indexPlayer) {
        gameModel.startTurn(indexPlayer);
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
    public void finishTurn(int indexPlayer) {
        gameModel.finishTurn(indexPlayer);
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
    public void subscribeObserver(Observer observer) {
        this.observer=observer;
    }

    @Override
    public void notifyObservers() {
        observer.updateObserver();
    }
}

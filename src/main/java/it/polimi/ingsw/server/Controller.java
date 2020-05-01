package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;

public class Controller  {
    private ProxyGameModel gameModel;

    public Controller(ProxyGameModel gameModel){
        this.gameModel=gameModel;
    }
    public void setNPlayers(int nPlayers){
        gameModel.setNPlayers(nPlayers);
        gameModel.notifySetNPlayers();
    }
    public void addPlayer(String name, int age, Game.COLOR color){
        gameModel.addPlayer(name, age, color);
        gameModel.notifyAddPlayer();
    }
    public void chooseCard(int playerIndex, int godCard) throws Exception {
        gameModel.chooseCard(playerIndex, godCard);
    }
    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box) {
        return gameModel.initializeWorker(indexPlayer, indexWorker, box);
    }

    public void startTurn(int indexPlayer) {
        gameModel.startTurn(indexPlayer);
    }
    public void setBoxReachable(int indexPlayer, int indexWorker) {
        gameModel.setBoxReachable(indexPlayer, indexWorker);
    }
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column) {
        return gameModel.movePlayer(indexPlayer, indexWorker, row, column);
    }
    public void setBoxBuilding(int indexPlayer, int indexWorker) {
        gameModel.setBoxBuilding(indexPlayer, indexWorker);
    }
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column) {
        return gameModel.buildBlock(indexPlayer, indexWorker, row, column);
    }
    public void finishTurn(int indexPlayer) {
        gameModel.finishTurn(indexPlayer);
    }
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker) {
        return gameModel.checkWin(indexPlayer, startBox, indexWorker);
    }
    public boolean checkWinAfterBuild() {
        return gameModel.checkWinAfterBuild();
    }
    public void setWinningPlayer(int indexPlayer) {
        gameModel.setWinningPlayer(indexPlayer);
    }
    public void setDeadPlayer(int indexPlayer){
        gameModel.setDeadPlayer(indexPlayer);
    }
    public void setPause() {
        gameModel.setPause();
    }
}

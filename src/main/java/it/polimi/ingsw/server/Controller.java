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
    public void addPlayer(String name, int age){
        gameModel.addPlayer(name, age);
        gameModel.notifyAddPlayer();
    }
    public void chooseCard(int playerIndex, int godCard) throws Exception {
        gameModel.chooseCard(playerIndex, godCard);
        gameModel.notifyAddCard(playerIndex);
    }
    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box) {
        boolean init= gameModel.initializeWorker(indexPlayer, indexWorker, box);
        gameModel.notifyAddWorker();
        return init;
    }

    public void startTurn(int indexPlayer) {
        gameModel.startTurn(indexPlayer);
        gameModel.notifyPlayerStateStart(indexPlayer);
    }
    public void setBoxReachable(int indexPlayer, int indexWorker) {
        gameModel.setBoxReachable(indexPlayer, indexWorker);
        gameModel.notifySetReachable();
    }
    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column) {
        boolean moved=gameModel.movePlayer(indexPlayer, indexWorker, row, column);
        gameModel.notifyMovedWorker();
        return moved;
    }
    public void setBoxBuilding(int indexPlayer, int indexWorker) {
        gameModel.setBoxBuilding(indexPlayer, indexWorker);
        gameModel.notifySetBuilding();
    }
    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column) {
        boolean built=gameModel.buildBlock(indexPlayer, indexWorker, row, column);
        gameModel.notifyBuildBlock();
        return built;
    }
    public void finishTurn(int indexPlayer) {
        gameModel.finishTurn(indexPlayer);
        gameModel.notifyPlayerStateWaiting(indexPlayer);
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

package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class Controller  {
    private ProxyGameModel gameModel;

    public Controller(ProxyGameModel gameModel){
        this.gameModel=gameModel;
    }
    public ObjNumPlayer setNPlayers(int nPlayers){
        gameModel.setNPlayers(nPlayers);
        return gameModel.notifySetNPlayers();
    }
    public void addPlayer(String name, int age){
        gameModel.addPlayer(name, age);
    }

    public AskCard setTempCard(ArrayList<Integer> threeCard) {
        gameModel.chooseTempCard(threeCard);
        return gameModel.notifyTempCard();
    }

    public void startGame(){
        gameModel.startGame();
    }

    public AskCard setCard(int playerIndex, int godCard) throws Exception {
        gameModel.chooseCard(playerIndex, godCard);
        return gameModel.notifyTempCard();
    }

    public ObjState goPlayingNext(){
        gameModel.goPlayingNext();
        return gameModel.notifyWhoIsPlaying();
    }

    public boolean initializeWorker(int indexPlayer, Box box1, Box box2) {
        boolean init= gameModel.initializeWorker(indexPlayer, box1, box2);
        gameModel.notifyAddWorker();
        return init;
    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    public boolean canMove(int indexPlayer){
        return gameModel.canMove(indexPlayer);
    }

    /// richiamato
    public UpdateBoardEvent setBoxReachable(int indexPlayer, int indexWorker) {
        gameModel.setBoxReachable(indexPlayer, indexWorker);
        return gameModel.notifySetReachable();
    }
    ///richiamato
    public AskMoveEvent movePlayer(int indexPlayer, int indexWorker, int row, int column) {
        boolean moved=gameModel.movePlayer(indexPlayer, indexWorker, row, column);
        AskMoveEvent askMoveEvent;
        if(moved){
            askMoveEvent=new AskMoveEvent(true);
        }else{
            askMoveEvent=new AskMoveEvent(indexWorker,row,column,false,false);
        }

        gameModel.notifyMovedWorker();
        return askMoveEvent;
    }
    ///
    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker) {
        return gameModel.checkWin(indexPlayer, startBox, indexWorker);
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

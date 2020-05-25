package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskBuildEvent;
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
        boolean addCompleted= gameModel.addPlayer(name, age);
        if(addCompleted){
            gameModel.notifyAddPlayer();
        }
    }
    public void askState(){
        boolean startCompleted= gameModel.askState();
        if(startCompleted){
            for(int indexClient = 0; indexClient < gameModel.getNPlayers(); indexClient++ ){
                int indexPlayer = gameModel.searchByClientIndex(indexClient);
                gameModel.notifyAskState(indexClient,indexPlayer);
            }
        }
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
        boolean moved = gameModel.movePlayer(indexPlayer, indexWorker, row, column);
        AskMoveEvent askMoveEvent;
        if(moved){
            askMoveEvent = new AskMoveEvent(indexWorker, row, column, false, true);
        }else{
            askMoveEvent = new AskMoveEvent(indexWorker,row,column,false,false);
        }

        gameModel.notifyMovedWorker();
        return askMoveEvent;
    }
    ///
    public boolean checkWin(int indexPlayer, int rowStart, int columnStart, int indexWorker) {
        return gameModel.checkWin(indexPlayer, rowStart, columnStart, indexWorker);
    }



    public boolean canBuild(int indexPlayer, int indexWorker){
        return gameModel.canBuild(indexPlayer, indexWorker);
    }

    public UpdateBoardEvent setBoxBuilding(int indexPlayer, int indexWorker) {
        gameModel.setBoxBuilding(indexPlayer, indexWorker);
        return gameModel.notifySetBuilding();
    }

    public AskBuildEvent buildBlock(int indexPlayer, int indexWorker, int rowWorker, int columnWorker, int row, int column) {
        boolean built = gameModel.buildBlock(indexPlayer, indexWorker, row, column);
        AskBuildEvent askBuildEvent;
        if(built){
            askBuildEvent = new AskBuildEvent(true);
        }
        else{
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, false);
        }

        gameModel.notifyBuildBlock();
        return askBuildEvent;
    }

    public boolean checkWinAfterBuild() {
        return gameModel.checkWinAfterBuild();
    }



    public void setDeadPlayer(int indexPlayer){
        gameModel.setDeadPlayer(indexPlayer);
    }
    public void setPause() {
        gameModel.setPause();
    }
}

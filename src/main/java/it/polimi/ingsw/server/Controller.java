package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.network.objects.ObjMove;
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

    public void addPlayer(String name, int age, int indexClient){
        boolean addCompleted = gameModel.addPlayer(name, age, indexClient);
        if(addCompleted){
            gameModel.notifyAddPlayer();
        }
    }

    public void startGame(){
        gameModel.startGame();
    }

    public void askState(){
        boolean startCompleted = gameModel.askState();
        if(startCompleted){
            for(int indexClient = 0; indexClient < gameModel.getNPlayers(); indexClient++ ){
                int indexPlayer = gameModel.searchByClientIndex(indexClient);
                gameModel.notifyAskState(indexClient, indexPlayer);
            }
        }
    }

    public void setTempCard(ArrayList<Integer> threeCard) {
        int clientIndex = gameModel.chooseTempCard(threeCard);
        gameModel.notifyTempCard(clientIndex);
    }

    public void setCard(int playerIndex, int godCard) throws Exception {
        int clientIndex = gameModel.chooseCard(playerIndex, godCard);
        gameModel.notifyTempCard(clientIndex);
    }

    public void goPlayingNext(){
        gameModel.goPlayingNext();
        gameModel.notifyWhoIsPlaying();
    }

    public void initializeWorker(Box box1, Box box2) {
        boolean init = gameModel.initializeWorker(box1, box2);
        if(init){
            gameModel.goPlayingNext();
            gameModel.notifyAddWorker();
        }else{
            gameModel.notifyWorkersNotInitialized();
        }

    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    public void canMove(){
        //todo da rifare
        boolean goAhead = gameModel.canMove();
        if(goAhead){
            gameModel.notifyStartTurn();
        }else{
            //gameModel.notifyLoser();
        }
    }

    /// richiamato
    public void setBoxReachable(int indexWorker, boolean secondMove) {
        gameModel.setBoxReachable(indexWorker);
        gameModel.notifySetReachable(indexWorker , secondMove);
    }
    ///richiamato
    public void movePlayer(ObjMove objMove) {

        boolean moved = gameModel.movePlayer(objMove.getIndexWorkerToMove(), objMove.getRow(), objMove.getColumn());
        AskMoveEvent askMoveEvent;
        if(moved){
            askMoveEvent = new AskMoveEvent(objMove.getIndexWorkerToMove(), objMove.getRow(), objMove.getColumn(), false, true);
        }else{
            askMoveEvent = new AskMoveEvent(objMove.getIndexWorkerToMove(), objMove.getRow(), objMove.getColumn(),false,false);
        }

        askMoveEvent.setRowStart(objMove.getRowStart());
        askMoveEvent.setColumnStart(objMove.getColumnStart());
        int clientIndex = gameModel.searchByPlayerIndex(gameModel.whoIsPlaying());
        gameModel.notifyMovedWorker(askMoveEvent, clientIndex);
    }

    ///
    public void checkWin(AskMoveEvent askMoveEvent, int indexClient) {
        boolean winCondition = gameModel.checkWin(askMoveEvent.getRowStart(), askMoveEvent.getColumnStart(), askMoveEvent.getIndexWorker());
        if(winCondition){
            gameModel.notifyWin(indexClient);
        }else{
            gameModel.notifyContinueMove(askMoveEvent);
        }
    }



    public void canBuild(int indexClient, int indexWorker, int rowWorker, int columnWorker){
        boolean goAhead = gameModel.canBuild(indexWorker);
        if(!goAhead){
            gameModel.notifyLoser(indexClient);
            int winnerClient = gameModel.getWinner();
            if(winnerClient != -1){
                gameModel.notifyWin(winnerClient);
            }else{
                gameModel.notifyWhoHasLost(indexClient);
            }
        }else{
            gameModel.notifyCanBuild(indexWorker, rowWorker, columnWorker);
        }
    }

    public void setBoxBuilding(int indexWorker) {
        gameModel.setBoxBuilding(indexWorker);
        gameModel.notifySetBuilding();
    }

    public void buildBlock(int indexClient, int indexWorker, int rowWorker, int columnWorker, int row, int column) {
        boolean built = gameModel.buildBlock(indexWorker, row, column);
        AskBuildEvent askBuildEvent;
        if(built){
            askBuildEvent = new AskBuildEvent(true);
        }
        else{
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, false);
        }

        gameModel.notifyBuildBlock(askBuildEvent, indexClient);
    }

    public void checkWinAfterBuild(AskBuildEvent askBuildEvent) {
        boolean winCondition = gameModel.checkWinAfterBuild();
        if(winCondition){
            //gameModel.notifyWin(indexClient); //todo indexClient di chi ha vinto, fare la ricerca
        }else{
            gameModel.notifyContinueBuild(askBuildEvent);
        }
    }



    public void setDeadPlayer(int indexPlayer){
        gameModel.setDeadPlayer(indexPlayer);
    }
    public void setPause() {
        gameModel.setPause();
    }
}

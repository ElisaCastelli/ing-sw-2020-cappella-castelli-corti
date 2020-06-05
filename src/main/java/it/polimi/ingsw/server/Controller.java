package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskCard;
import it.polimi.ingsw.network.events.AskMoveEvent;

import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Timer;

public class Controller  {
    private ProxyGameModel gameModel;

    public Controller(ProxyGameModel gameModel){
        this.gameModel=gameModel;
    }

    public void setNPlayers(int nPlayers){
        gameModel.setNPlayers(nPlayers);
        gameModel.notifySetNPlayers();
    }

    public boolean controlSetNPlayer(){
        int nPlayers= gameModel.getNPlayers();
        if(nPlayers != 0){
            return true;
        }
        return false;
    }

    public void addPlayer(int indexPlayer){
        Timer timer = new Timer();
        gameModel.addPlayerProxy(indexPlayer, timer);
    }
    public void addPlayer(String name, int age, int indexClient){
        boolean addCompleted = gameModel.addPlayer(name, age, indexClient);
        if(addCompleted){
            gameModel.notifyAddPlayer();
        }
    }

    public void removeExtraPlayer(){
        gameModel.removeExtraPlayer();
    }

    public void removePlayer(int indexClient){
        gameModel.remove(indexClient);
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

    public void canBuildBeforeWorkerMove ( int row, int column, int indexWorkerToMove){
        boolean canBuildBeforeWorkerMove = gameModel.canBuildBeforeWorkerMove();
        if(canBuildBeforeWorkerMove){
            gameModel.notifySpecialTurn( row, column, indexWorkerToMove );
        }else{
            gameModel.notifyBasicTurn(indexWorkerToMove, row , column);
        }
    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    public void canMove(){
        int loserClient = gameModel.whoIsPlaying();
        boolean goAhead = gameModel.canMove();
        if(goAhead){
            gameModel.notifyStartTurn();
        }else{
            gameModel.notifyLoser(loserClient);
            int winnerClient = gameModel.getWinner();
            if(winnerClient != -1){
                gameModel.notifyWin(winnerClient);
            }else{
                gameModel.notifyWhoHasLost(loserClient);
            }
        }
    }

    public void canMoveSpecialTurn(int indexWorker, int rowWorker, int columnWorker){
        int loserClient = gameModel.whoIsPlaying();
        boolean goAhead = gameModel.canMoveSpecialTurn(indexWorker);
        if(goAhead){
            gameModel.setBoxReachable(indexWorker);
            gameModel.notifyUpdateBoard(true);
            gameModel.notifyBasicTurn(indexWorker, rowWorker, columnWorker);
        }else{
            gameModel.notifyLoser(loserClient);
            int winnerClient = gameModel.getWinner();
            if(winnerClient != -1){
                gameModel.notifyWin(winnerClient);
            }else{
                gameModel.notifyWhoHasLost(loserClient);
            }
        }
    }




    /// richiamato
    public void setBoxReachable(int indexWorker, boolean secondMove) {
        gameModel.setBoxReachable(indexWorker);
        gameModel.notifySetReachable(indexWorker , secondMove);
    }
    ///richiamato
    public void movePlayer(int rowStart, int columnStart, int row, int column, int indexWorkerToMove) {
        boolean moved = gameModel.movePlayer(indexWorkerToMove, row, column);
        AskMoveEvent askMoveEvent;
        if(moved){
            askMoveEvent = new AskMoveEvent(indexWorkerToMove, row, column, false, true);
        }else{
            askMoveEvent = new AskMoveEvent(indexWorkerToMove, row, column,false,false);
        }

        askMoveEvent.setRowStart(rowStart);
        askMoveEvent.setColumnStart(columnStart);
        int clientIndex = gameModel.searchByPlayerIndex(gameModel.whoIsPlaying());
        gameModel.notifyMovedWorker(askMoveEvent, clientIndex);
    }

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

    public void canBuildSpecialTurn(int indexWorker, int rowWorker, int columnWorker){
        //controllo che può costruire prima di chiederglielo
        boolean specialCondition = gameModel.canBuildBeforeWorkerMove();
        if(specialCondition){
            //gli vado a chiedere se vuole fare la mossa prima
            gameModel.notifyAskBuildBeforeMove( indexWorker, rowWorker, columnWorker);
        }else{
            gameModel.notifyBasicTurn( indexWorker, rowWorker, columnWorker);
        }
    }

    public void setBoxBuilding(int indexWorker) {
        gameModel.setBoxBuilding(indexWorker);
        gameModel.notifySetBuilding();
    }

    public void buildBlock(int indexClient, int indexWorker, int rowWorker, int columnWorker, int row, int column, boolean isSpecialTurn, int indexPossibleBlock) {
        gameModel.setIndexPossibleBlock(indexPossibleBlock);
        boolean built = gameModel.buildBlock(indexWorker, row, column);
        AskBuildEvent askBuildEvent;
        if(built){
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, true, isSpecialTurn);
        }
        else{
            askBuildEvent = new AskBuildEvent(indexWorker, rowWorker, columnWorker, false, false, isSpecialTurn);
        }

        gameModel.notifyBuildBlock(askBuildEvent, indexClient);
    }

    public void checkWinAfterBuild(AskBuildEvent askBuildEvent) {
        boolean winCondition = gameModel.checkWinAfterBuild();
        if(winCondition){
            int winnerClient = gameModel.getWinner();
            gameModel.notifyWin(winnerClient);
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

    public void heartBeat(int indexClient) {
        gameModel.controlHeartBeat(indexClient);
    }
}

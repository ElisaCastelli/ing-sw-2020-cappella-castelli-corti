package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

public class VirtualView implements Observer {

    private ProxyGameModel gameModel;
    private Controller controller;
    private boolean ready;
    private int ackCounter;
    private int winnerPlayer;


    public VirtualView() throws Exception {
        gameModel= new ProxyGameModel();
        controller= new Controller(gameModel);
        subscribe();
        ready=false;
        ackCounter=0;
        winnerPlayer=-1;
    }
    public synchronized boolean isReady() {
        return ready;
    }
    public synchronized void setReady(boolean ready) {
        this.ready = ready;
    }
    public synchronized int getAckCounter() {
        return ackCounter;
    }
    public synchronized void incCounterOpponent() {
        ackCounter++;
        if(ackCounter == (gameModel.getNPlayers()-1)){
            ready=true;
            ackCounter=0;
        }
    }
    public synchronized void incCounter() {
        ackCounter++;
        if(ackCounter == gameModel.getNPlayers()){
            ready=true;
            ackCounter=0;
        }
    }

    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    public ArrayList<Player> getPlayerArray(){
        return gameModel.getPlayerArray();
    }

    public ObjNumPlayer setNPlayers(int nPlayers){
        return controller.setNPlayers(nPlayers);
    }

    @Override
    public ObjNumPlayer updateNPlayer() {
        return new ObjNumPlayer(gameModel.getNPlayers());
    }

    public synchronized void addPlayer(String name, int age){
        controller.addPlayer(name, age);
        ackCounter++;
        if(  ackCounter == gameModel.getNPlayers() ){
            ready=true;
            ackCounter=0;
        }
    }

    public int searchByName(String name){
        return gameModel.searchByName(name);
    }

    public ArrayList<String> getCards() throws Exception {
        return gameModel.getCards();
    }

    public synchronized AskCard setTempCard(ArrayList<Integer> tempCard){
        return controller.setTempCard(tempCard);
    }

    public synchronized AskCard setCard(int playerIndex, int godCard) throws Exception {
        return controller.setCard(playerIndex, godCard);
    }

    @Override
    public AskCard updateTempCard(){
        return new AskCard(gameModel.getTempCard());
    }

    public synchronized ObjState goPlayingNext(){
        return controller.goPlayingNext();
    }

    @Override
    public ObjState updateWhoIsPlaying() {
        ObjState objState = new ObjState();
        objState.setCurrentPlayer(gameModel.whoIsPlaying());
        return objState;
    }

    public void startGame(){
        controller.startGame();
    }

    @Override
    public UpdateBoardEvent updateBoard(){
        Board boardToSend= gameModel.getBoard();
        ///metto a falso lo show reachable ma lo metterà poi a vero se è richiamata da un setReachable
        return new UpdateBoardEvent(boardToSend,false);
    }

    public boolean initializeWorker(int indexPlayer, Box box1, Box box2) {
        return controller.initializeWorker(indexPlayer, box1, box2);
    }

    @Override
    public void updateInizializaWorker(){
        System.out.println("Ho inizializzato la pedina");
    }

    public boolean isReachable(int row, int column){
        return gameModel.isReachable(row,column);
    }

    //da richiamare senza fare la notify visto che il metodo can move ritorna già un booleano
    public boolean canMove(int indexPlayer){
        return controller.canMove(indexPlayer);
    }

    /// richiamato
    public UpdateBoardEvent setReachable(int indexPlayer, int indexWorker){
        return controller.setBoxReachable(indexPlayer, indexWorker);
    }
    ///richiamato
    @Override
    public void updateReachable(){
        System.out.println("Aggiornato celle raggiungibili");
    }
    ///richiamato
    public AskWorkerToMoveEvent getWorkersPos(int indexPlayer, boolean firstMove){
        ArrayList<Box> positions= gameModel.getWorkersPos(indexPlayer);
        return new AskWorkerToMoveEvent(positions.get(0).getRow(), positions.get(0).getColumn(),positions.get(1).getRow(), positions.get(1).getColumn(),1 ,firstMove);
    }

    ///richiamato
    public AskMoveEvent move(int indexPlayer, int indexWorker, int row, int column){
        return controller.movePlayer(indexPlayer, indexWorker, row, column);
    }
    ///richiamato
    @Override
    public void updateMove(){
        System.out.println("Pedina mossa");
    }

    public boolean checkWin(int indexPlayer, int startRow, int startColumn, int indexWorker){

        return controller.checkWin(indexPlayer, startRow, startColumn, indexWorker);


    }


    //Metodo per verificare se è possibile costruire attorno al proprio worker, se non è possibile il giocatore ha perso
    public boolean canBuild(int indexPlayer, int indexWorker){
        return controller.canBuild(indexPlayer, indexWorker);
    }

    //Metodo per fare la setPossibleBuild
    public UpdateBoardEvent setBoxBuilding(int indexPlayer, int indexWorker) {
        return controller.setBoxBuilding(indexPlayer, indexWorker);
    }

    //Probabilmente non serve
    @Override
    public void updateSetBuilding(){
        System.out.println("Aggiornate celle dove si può costruire");
    }

    //Metodo per fare la costruzione del piano
    public AskBuildEvent buildBlock(int indexPlayer, int indexWorker, int rowWorker, int columnWorker, int row, int column) {
        return controller.buildBlock(indexPlayer, indexWorker, rowWorker, columnWorker, row, column);
    }

    //Probabilmente non serve
    @Override
    public void updateBuild(){
        System.out.println("Costruito");
    }

    //Verifica se crono ha vinto
    public boolean checkWinAfterBuild() {
        return controller.checkWinAfterBuild();
    }




    public void setPause(){
        controller.setPause();
    }

}

package it.polimi.ingsw.server;

import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.ProxyGameModel;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.god.God;
import it.polimi.ingsw.server.model.playerState.PlayerState;

import java.util.ArrayList;

public class VirtualView implements Observer {

    ProxyGameModel gameModel;
    Controller controller;

    public VirtualView() throws Exception {
        gameModel= new ProxyGameModel();
        controller= new Controller(gameModel);
        subscribe();
    }

    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    @Override
    public int updateNPlayer() {
        return gameModel.getNPlayers();
    }

    public void setNPlayers(int nPlayers){
        controller.setNPlayers(nPlayers);
    }

    public void addPlayer(String name, int age){
        controller.addPlayer(name, age);
    }

    @Override
    public void updateAddPlayer() {
        System.out.println("Giocatore aggiunto");
    }

    public ArrayList<God> getCard() throws Exception {
        return gameModel.getCards();
    }

    public ArrayList<God> getTempCard(){
       return gameModel.getTempCard();
    }

    public ArrayList<God> getCardUsed(){
        return gameModel.getCardUsed();
    }

    public void setCard(int playerIndex, int godCard) throws Exception {
        controller.chooseCard(playerIndex, godCard);
    }

    public void startGame(){
        controller.startGame();
    }

    @Override
    public void updatePlayerCard(int indexPlayer){
        System.out.println("Carta aggiunta");
    }

    @Override
    public Board updateBoard(){
        return gameModel.getBoard();
    }

    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box) {
        return controller.initializeWorker(indexPlayer, indexWorker, box);
    }

    @Override
    public void updateInizializaWorker(){
        System.out.println("Ho inizializzato la pedina");
    }


    public void setStartState(int indexPlayer){
        controller.startTurn(indexPlayer);
    }
    @Override
    public void updateStartTurn(){
        System.out.println("Inizio turno");
    }


    @Override
    public boolean updateCanMove(int indexPlayer){
        return gameModel.canMove(indexPlayer);
    }

    public void setReachable(int indexPlayer, int indexWorker){
        controller.setBoxReachable(indexPlayer, indexWorker);
    }

    @Override
    public void updateReachable(){
        System.out.println("Aggiornato celle raggiungibili");
    }

    public boolean move(int indexPlayer, int indexWorker, int row, int column){
        return controller.movePlayer(indexPlayer, indexWorker, row, column);
    }

    @Override
    public void updateMove(){
        System.out.println("Pedina mossa");
    }

    @Override
    public boolean updateCanBuild(int indexPlayer, int indexWorker){
        return gameModel.canBuild(indexPlayer, indexWorker);
    }

    public void setBoxBuilding(int indexPlayer, int indexWorker) {
        controller.setBoxBuilding(indexPlayer, indexWorker);
    }

    @Override
    public void updateSetBuilding(){
        System.out.println("Aggiornate celle dove si può costruire");
    }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column) {
        return controller.buildBlock(indexPlayer, indexWorker, row, column);
    }

    @Override
    public void updateBuild(){
        System.out.println("Costruito");
    }

    public void setfinishTurn(int indexPlayer){
        controller.finishTurn(indexPlayer);
    }

    @Override
    public void updateFinishTurn(){
        System.out.println("Fine turno");
    }

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        return controller.checkWin(indexPlayer, startBox, indexWorker);
    }
    public boolean checkWinAfterBuild() {
        return controller.checkWinAfterBuild();
    }
    public void setWinningPlayer(int indexPlayer){
        controller.setWinningPlayer(indexPlayer);
    }
    public void setDeadPlayer(int indexPlayer){
        controller.setDeadPlayer(indexPlayer);
    }


    public void setPause(){
        controller.setPause();
    }

    @Override
    public GameState updateState(){
        return gameModel.getState();
    }
    @Override
    public PlayerState updatePlayerState(int indexPlayer) {
        return gameModel.getPlayerState(indexPlayer);
    }
}

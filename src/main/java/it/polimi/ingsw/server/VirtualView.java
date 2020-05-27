package it.polimi.ingsw.server;

import it.polimi.ingsw.network.SendMessageToClient;
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
    private int winnerPlayer;
    SendMessageToClient sendMessageToClient;


    public VirtualView(SendMessageToClient sendMessageToClient) throws Exception {
        gameModel= new ProxyGameModel();
        controller= new Controller(gameModel);
        subscribe();
        ready=false;
        winnerPlayer=-1;
        this.sendMessageToClient=sendMessageToClient;
    }
    public synchronized boolean isReady() {
        return ready;
    }
    public synchronized void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    public ArrayList<Player> getPlayerArray(){
        return gameModel.getPlayerArray();
    }

    public void askWantToPlay(int indexClient){
        if(gameModel.getNPlayers() == 0 && getPlayerArray().size() == 0){

            getPlayerArray().add(new Player(indexClient));
            sendMessageToClient.sendAskNPlayer();

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() == 0){
            //non hanno settato ancora ma hai la possibilità di giocare
            getPlayerArray().add(new Player(indexClient));
            sendMessageToClient.sendYouHaveToWait(indexClient);

        }else if (getPlayerArray().size() != 0 && gameModel.getNPlayers() != 0){
            getPlayerArray().add(new Player(indexClient));
            if(getPlayerArray().size() == gameModel.getNPlayers()){
                ///se sei arrivato per terzo poi giocare e sei quello che manda il broardcast di AskPlayer
                sendMessageToClient.YouCanPlay(gameModel.getNPlayers());

            }else if (getPlayerArray().size() < gameModel.getNPlayers() ){
                //sei arrivato per secondo e poi giocare ma devi aspettare il terzo
                sendMessageToClient.sendYouHaveToWait(indexClient);
            }else{
                //non giochi mai
                sendMessageToClient.sendYouHaveToWait(indexClient);
            }
        }
    }

    public void setNPlayers(int npLayer){
        controller.setNPlayers(npLayer);

        boolean sendBroadcast = true;
        if(getPlayerArray().size() == 1 ){
            sendBroadcast= false;
            sendMessageToClient.sendAskPlayer(npLayer, sendBroadcast);
        }else if (getPlayerArray().size() >= npLayer){
            sendMessageToClient.sendAskPlayer(npLayer, sendBroadcast);
        }else{
            sendMessageToClient.sendYouHaveToWait(0);
        }
    }

    @Override
    public ObjNumPlayer updateNPlayer() {
        return new ObjNumPlayer(gameModel.getNPlayers());
    }

    public synchronized void addPlayer(String name, int age, int indexClient){
        if(getPlayerArray().size() > gameModel.getNPlayers()){
            for(int i = getPlayerArray().size() - 1; i >= gameModel.getNPlayers(); i--) {
                getPlayerArray().remove(i);
            }
        }
        controller.addPlayer(name, age, indexClient);
    }

    public void askState(){
        controller.askState();
    }

    public int searchByName(String name){
        return gameModel.searchByName(name);
    }

    public void getCards() {
        try {
            Ask3CardsEvent ask3CardsEvent = new Ask3CardsEvent(gameModel.getCards());
            int clientIndex = gameModel.searchByPlayerIndex(0);
            ask3CardsEvent.setCurrentPlayer(clientIndex);
            sendMessageToClient.sendCards(ask3CardsEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setTempCard(ArrayList<Integer> tempCard){
        controller.setTempCard(tempCard);
    }

    public void setCard(int playerIndex, int godCard) throws Exception {
        controller.setCard(playerIndex, godCard);
    }

    @Override
    public void updatePlayer() {
        startGame();
        sendMessageToClient.sendStartGameEvent(gameModel.getNPlayers());
    }

    public void startGame(){
        controller.startGame();
    }

    @Override
    public void updateAskState(int indexClient, int indexPlayer) {
        ObjState objState = new ObjState(indexPlayer);
        sendMessageToClient.sendObjState(indexClient, objState);
    }

    @Override
    public void updateTempCard(int clientIndex){
        AskCard askCard = new AskCard(gameModel.getTempCard());
        askCard.setCurrentPlayer(clientIndex);

        if(askCard.getCardTemp().size() != 0){
            sendMessageToClient.sendAskCard(askCard);
        }
        else{
            System.out.println("Sending board");
            updateBoard();
            AskInitializeWorker askInitializeWorker = new AskInitializeWorker();
            askInitializeWorker.setCurrentPlayer(clientIndex);
            sendMessageToClient.sendAskInitializeWorker(askInitializeWorker);
        }
    }

    public synchronized void goPlayingNext(){
        controller.goPlayingNext();
    }

    @Override
    public void updateWhoIsPlaying() {
        /*ObjState objState = new ObjState();
        objState.setCurrentPlayer(gameModel.whoIsPlaying());*/
    }

    @Override
    public void updateBoard(){
        sendMessageToClient.sendUpdateBoard(gameModel.gameData());
    }

    public boolean initializeWorker(int indexPlayer, Box box1, Box box2) {
        return controller.initializeWorker(indexPlayer, box1, box2);
    }

    @Override
    public void updateInitializeWorker(){
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
    public void setReachable(int indexPlayer, int indexWorker){
        controller.setBoxReachable(indexPlayer, indexWorker);
    }
    ///richiamato
    @Override
    public void updateReachable(){
        System.out.println("Aggiornato celle raggiungibili");
    }
    ///richiamato
    public AskWorkerToMoveEvent getWorkersPos(int indexPlayer, boolean firstMove){
        ArrayList<Box> positions = gameModel.getWorkersPos(indexPlayer);
        return new AskWorkerToMoveEvent(positions.get(0).getRow(), positions.get(0).getColumn(), positions.get(1).getRow(), positions.get(1).getColumn(),firstMove);
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
    public void setBoxBuilding(int indexPlayer, int indexWorker) {
        controller.setBoxBuilding(indexPlayer, indexWorker);
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

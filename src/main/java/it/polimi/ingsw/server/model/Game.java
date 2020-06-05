package it.polimi.ingsw.server.model;


import java.util.*;
import java.util.stream.Collectors;

import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.events.UpdateBoardEvent;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.gameState.GameStateManager;
import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.parse.CardCreator;


/**
 * This is the main class that represents the game
 */


public class Game implements GameModel{
    /**
     * This attribute is the playing board
     */
    private Board board;
    /**
     * this is the array list of the players
     */
    private ArrayList<Player> players;
    private int ackCounter;

    /**
     * this is the array list of the players
     */
    private ArrayList<Player> playersDead;
    /**
     * This integer attribute is the number of the players
     */
    private  int nPlayers;
    /**
     * Array of all cards
     */
    private ArrayList<God> godsArray;

    private ArrayList<God> tempCard;
    /**
     * Array of drawn cards
     */
    private ArrayList<God> cardUsed;

    private GameStateManager stateManager;
    private CardCreator parser = new CardCreator();

    /**
     * Constructor without parameters
     */
    Game() throws Exception {
        board = new Board();
        players = new ArrayList<>();
        playersDead = new ArrayList<>();
        nPlayers = 0;
        ackCounter = 0;
        tempCard= new ArrayList<>();
        godsArray = new ArrayList<>();
        cardUsed = new ArrayList<>();
        stateManager = new GameStateManager(players, playersDead);
    }

    public void goPlayingNext(){
        int indexPlay = whoIsPlaying();
        if(!players.get(indexPlay).amIDead()){
            players.get(indexPlay).goWaiting();
        }

        if(indexPlay == nPlayers - 1){
            if(players.get(0).amIDead()){
                players.get(1).goPlay();
            }else{
                players.get(0).goPlay();
            }
        }
        else{
            int nextPlayer = indexPlay + 1;
            if(players.get(nextPlayer).amIDead()){
                if (nextPlayer == nPlayers - 1){
                    players.get(0).goPlay();
                }
                else{
                    players.get(nextPlayer + 1).goPlay();
                }
            }
            else {
                players.get(indexPlay + 1).goPlay();
            }
        }
    }

    public void fakePlaying(int indexLoser){
        if(indexLoser == 0){
            players.get(nPlayers - 1).goPlay();
        }else{
            players.get(indexLoser - 1).goPlay();
        }
    }

    public Board getBoard(){
        return board;
    }

    /**
     * Method to sort gamers by age
     */
    public void sortGamers(){
        Collections.sort(players, new Comparator<Player>() {
            @Override public int compare(Player p1, Player p2) {
                return p1.getAge() - p2.getAge(); // Ascending
            }

        });
    }
    public void setNPlayers(int nPlayers){
        this.nPlayers = nPlayers;
    }
    public int getNPlayers(){
        return nPlayers;
    }

    @Override
    public void addPlayer(int indexClient, Timer timer, TimerTask timerTask) {
        players.add(new Player(indexClient, timer, timerTask));
    }

    public boolean addPlayer(String name, int age, int indexClient){
        int playerIndex = searchByClientIndex(indexClient);
        players.get(playerIndex).setName(name);
        players.get(playerIndex).setAge(age);
        ackCounter++;

        sortGamers();
        for(Player player : players ){
            int indexPlayer = searchByName(player.getName());
            player.setIndexPlayer(indexPlayer);
        }
        if(ackCounter == nPlayers){
            ackCounter = 0;
            return true;
        }
        return false;
    }

    @Override
    public void removeExtraPlayer() {
        int playerSize = players.size();
        for(int index = playerSize - 1; index >= nPlayers  ; index-- ){
            players.get(index).getTimerTask().cancel();
            players.get(index).getTimer().cancel();
            players.get(index).getTimer().purge();
            players.remove(index);
        }
    }

    @Override
    public void remove ( int indexPlayer ){
        players.get(indexPlayer).getTimerTask().cancel();
        players.get(indexPlayer).getTimer().cancel();
        players.get(indexPlayer).getTimer().purge();
        players.remove(indexPlayer);
        updateIndexInArray( indexPlayer );
    }

    public void updateIndexInArray( int indexPlayer ){
        for(Player player : players){
            if(player.getIndexPlayer() > indexPlayer ){
                player.setIndexClient(player.getIndexPlayer() - 1);
                player.setIndexPlayer(player.getIndexPlayer() - 1);
            }
        }
    }



    public void startGame(){
        players.get(0).goPlay();
        stateManager.start();
    }

    @Override
    public synchronized boolean askState() {
        ackCounter++;
        if(ackCounter == nPlayers){
            ackCounter = 0;
            return true;
        }
        return false;
    }

    public ArrayList<Player> getPlayerArray(){
        return players;
    }
    public int searchByName(String name){
        return players.stream().map(Player::getName).collect(Collectors.toList()).indexOf(name);
    }
    public int searchByClientIndex(int clientIndex){
        return players.stream().map(Player::getIndexClient).collect(Collectors.toList()).indexOf(clientIndex);
    }
    public int searchByPlayerIndex(int playerIndex){
        for(Player player : players){
            if(player.getIndexPlayer() == playerIndex)
                return player.getIndexClient();
        }
        return 0;
    }

    public UpdateBoardEvent gameData(boolean reach){
        ArrayList<User> users = new ArrayList<>();
        for (Player player : players){
            User user = new User(player.getName(), player.getGod().getName(), player.getIndexClient());
            if (player.amIDead())
                user.setDead(true);
            users.add(user);
        }
        UpdateBoardEvent updateBoardEvent = new UpdateBoardEvent(users, board, reach);
        int playerIndex = whoIsPlaying();
        updateBoardEvent.setCurrentClientPlaying(searchByPlayerIndex(playerIndex));
        return updateBoardEvent;
    }

    public int chooseTempCard(ArrayList<Integer> threeCard){
       for(int i = 0; i < nPlayers; i++){
           if(threeCard.get(i) < godsArray.size()){
               tempCard.add(godsArray.get(threeCard.get(i)));
           }
       }
       goPlayingNext();
       int playerIndex = whoIsPlaying();
       return searchByPlayerIndex(playerIndex);
    }

    public int chooseCard(int playerIndex, int indexCard) {
        players.get(playerIndex).setGod(tempCard.get(indexCard));
        cardUsed.add(tempCard.get(indexCard));
        tempCard.remove(tempCard.get(indexCard));
        if(tempCard.size() != 0){
            goPlayingNext();
            playerIndex = whoIsPlaying();
        }
        return searchByPlayerIndex(playerIndex);
    }
    public void loadCards() {
        godsArray= parser.parseCard();
    }
    public ArrayList<String> getCards() {
        if(godsArray.size()==0){
            loadCards();
        }
        ArrayList<String> cards= new ArrayList<>();
        for (God god : godsArray) {
            cards.add(god.getName());
        }
        return cards;
    }
    public ArrayList<String> getTempCard(){
        ArrayList<String> temporanee = new ArrayList<>();
        for (God god : tempCard) {
            temporanee.add(god.getName());
        }
        return temporanee;
    }
    public ArrayList<String> getCardUsed(){
        ArrayList<String> drawnCard = new ArrayList<>();
        for (God god : cardUsed) {
            drawnCard.add(god.getName());
        }
        return drawnCard;
    }

    public int whoIsPlaying(){
        int indexPlay = 0;
        boolean found = false;
        while(!found && indexPlay < players.size()){
            if(players.get(indexPlay).isPlaying()){
                found = true;
            }
            else{
                indexPlay++;
            }
        }
        if(found)
            return indexPlay;
        else
            return -1;
    }

    public ArrayList<Box> getWorkersPos(int indexPlayer){
       return players.get(indexPlayer).getWorkersBox();
    }

    public boolean initializeWorker(Box box1, Box box2){
        int indexPlayer = whoIsPlaying();
        return players.get(indexPlayer).initializeWorker(box1, box2, board);
    }
    
    public boolean isReachable(int row, int column){
        return board.getBox(row,column).isReachable();
    }

    public GameState getState(){
        return stateManager.getCurrentState();
    }

    @Override
    public boolean canBuildBeforeWorkerMove() {
        int indexPlayer = whoIsPlaying();
        return stateManager.canBuildBeforeWorkerMove(indexPlayer);
    }

    //mette in deadPlayers se canMove ritorna false

    public boolean canMove(){
        goPlayingNext();
        int playerIndex = whoIsPlaying();
        boolean canMove = stateManager.canMove(playerIndex);
        if(!canMove){
            setDeadPlayer(playerIndex);
            if(nPlayers == 2 || playersDead.size() == 2){
                thereIsAWinner();
            }else{
                fakePlaying(playerIndex);
            }
        }
        return canMove;
    }

    public boolean canMoveSpecialTurn(int indexWorker) {
        int playerIndex = whoIsPlaying();
        boolean canMove = stateManager.canMoveSpecialTurn(playerIndex, indexWorker);
        if(!canMove){
            setDeadPlayer(playerIndex);
            if(nPlayers == 2 || playersDead.size() == 2){
                thereIsAWinner();
            }
        }
        return canMove;
    }

    public void setBoxReachable(int indexWorker){
        board.clearReachable();
        int indexPlayer = whoIsPlaying();
        stateManager.setBoxReachable(indexPlayer, indexWorker);
    }

    public boolean movePlayer(int indexWorker, int row, int column){
        int indexPlayer = whoIsPlaying();
        return stateManager.movePlayer(indexPlayer,indexWorker,row,column,board);
    }

    public boolean canBuild(int indexWorker){
        int indexPlayer = whoIsPlaying();
        boolean canBuild = stateManager.canBuild(indexPlayer, indexWorker);
        if(!canBuild){
            setDeadPlayer(indexPlayer);
            if(nPlayers == 2 || playersDead.size() == 2){
                thereIsAWinner();
            }else{
                fakePlaying(indexPlayer);
            }
        }
        return canBuild;
    }

    //Richiamato per settare il vincitore se sono morti gli avversari
    public void thereIsAWinner(){
        for (Player player : players){
            int mayBeTheWinner = 0;
            int i = 0;
            while(i < (playersDead.size() - 1)){
                if(playersDead.get(i).getIndexClient() != player.getIndexClient()){
                    mayBeTheWinner = mayBeTheWinner + 1;
                    i = i + 1;
                }
                if(mayBeTheWinner == playersDead.size()){
                    player.goWin();
                    stateManager.goEnd(player.getIndexClient());
                }
            }
        }
    }

    public void setBoxBuilding(int indexWorker){
        board.clearReachable();
        int indexPlayer = whoIsPlaying();
        stateManager.setBoxBuilding(indexPlayer, indexWorker);
    }

    public boolean buildBlock(int indexWorker, int row, int column){
        int indexPlayer = whoIsPlaying();
        return stateManager.buildBlock(indexPlayer, indexWorker, row, column, board);
    }

    @Override
    public void setIndexPossibleBlock(int indexPossibleBlock) {
        int indexPlayer = whoIsPlaying();
        stateManager.setIndexPossibleBlock(indexPlayer ,indexPossibleBlock);
    }

    public boolean checkWin(int rowStart, int columnStart, int indexWorker){
        return stateManager.checkWin(whoIsPlaying(), board.getBox(rowStart, columnStart), indexWorker);
    }

    public boolean checkWinAfterBuild(){
        return stateManager.checkWinAfterBuild();
    }

    public int getWinner(){
        return stateManager.getWinner();
    }

    public void setDeadPlayer(int indexPlayer){
        stateManager.setDeadPlayer(indexPlayer);
    }

    public void setPause(){
        stateManager.goPause();
    }

    public void controlHeartBeat(int indexClient) {
        int indexPlayer= searchByClientIndex(indexClient);
        players.get(indexPlayer).controlHeartBeat();
    }

    public boolean incrementHeartBeat(int indexPlayer){
        return players.get(indexPlayer).incrementMissedHeartBeat();
    }

    @Override
    public void reset() {
        board.clear();
        players.clear();
        playersDead.clear();
        nPlayers = 0;
        ackCounter = 0;
        tempCard.clear();
        cardUsed.clear();
        ///todo da fare ready stato per reinizializzazione
        stateManager = new GameStateManager(players, playersDead);
    }
}


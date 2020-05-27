package it.polimi.ingsw.server.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        players.get(indexPlay).goWaiting();
        if(indexPlay == nPlayers-1){
            players.get(0).goPlay();
        }
        else{
            players.get(indexPlay+1).goPlay();
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
            User user = new User(player.getName(), player.getGod().getName());
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
        for(int i=0; i<godsArray.size();i++){
            cards.add(godsArray.get(i).getName());
        }
        return cards;
    }
    public ArrayList<String> getTempCard(){
        ArrayList<String> temporanee= new ArrayList<>();
        for(int i=0; i<tempCard.size();i++){
            temporanee.add(tempCard.get(i).getName());
        }
        return temporanee;
    }
    public ArrayList<String> getCardUsed(){
        ArrayList<String> drawnCard= new ArrayList<>();
        for(int i=0; i<cardUsed.size();i++){
            drawnCard.add(cardUsed.get(i).getName());
        }
        return drawnCard;
    }

    public int whoIsPlaying(){
        int indexPlay=0;
        boolean found=false;
        while(!found && indexPlay < players.size()){
            if(players.get(indexPlay).isPlaying()){
                found=true;
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
        int indexPlayer=whoIsPlaying();
        return players.get(indexPlayer).initializeWorker(box1, box2, board);
    }
    
    public boolean isReachable(int row, int column){
        return board.getBox(row,column).isReachable();
    }

    public GameState getState(){
        return stateManager.getCurrentState();
    }

    //mette in deadPlayers se canMove ritorna false
    public boolean canMove(int indexPlayer){
        boolean canMove = stateManager.canMove(indexPlayer);
        if(!canMove){
            playersDead.add(players.get(indexPlayer));
            int winner = 0;
            int i = 0;
            boolean found = false;
            while(i < nPlayers && !found){
                if(players.get(i) != null){
                    winner = i;
                    found = true;
                }
            }
            stateManager.goEnd(winner);
        }
        return canMove;
    }

    public void setBoxReachable(int indexWorker){
        board.clearReachable();
        int indexPlayer= whoIsPlaying();
        stateManager.setBoxReachable(indexPlayer, indexWorker);
    }

    public boolean movePlayer(int indexWorker, int row, int column){
        int indexPlayer= whoIsPlaying();
        return stateManager.movePlayer(indexPlayer,indexWorker,row,column,board);
    }

    public boolean canBuild(int indexWorker){
        int indexPlayer= whoIsPlaying();
        boolean canBuild = stateManager.canBuild(indexPlayer, indexWorker);
        if(!canBuild){
            playersDead.add(players.get(indexPlayer));
            int winner = 0;
            int i = 0;
            boolean found=false;
            while(i < nPlayers && !found){
                if(players.get(i) != null){
                    winner = i;
                    found = true;
                }
            }
            stateManager.goEnd(winner);
        }
        return canBuild;
    }

    public void setBoxBuilding(int indexPlayer, int indexWorker){
        board.clearReachable();
        stateManager.setBoxBuilding(indexPlayer, indexWorker);
    }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column){
        return stateManager.buildBlock(indexPlayer, indexWorker, row, column, board);
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

}


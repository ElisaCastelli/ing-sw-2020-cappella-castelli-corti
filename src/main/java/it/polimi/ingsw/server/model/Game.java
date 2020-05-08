package it.polimi.ingsw.server.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;
import it.polimi.ingsw.server.model.gameState.GameState;
import it.polimi.ingsw.server.model.gameState.GameStateManager;
import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.parse.CardCreator;
import it.polimi.ingsw.server.model.playerState.PlayerState;


/**
 * This is the main class that represents the game
 */


public class Game implements GameModel{
    /**
     * This attribute is the playing board
     */
    private static Board board;
    /**
     * this is the array list of the players
     */
    private ArrayList<Player> players;

    /**
     * This is a class enum made to choose the color associated with the player and his workers
     */
    public enum COLOR {

        BLU("#005EA5"),
        ORANGE("#FF7FF0"),
        RED("#A71010");

        COLOR(String c) {
        }
    }


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
    //private Move lastMove = new Move();

    /**
     * Constructor without parameters
     */
    Game() throws Exception {
        board = new Board();
        players = new ArrayList<>();
        playersDead = new ArrayList<>();
        nPlayers = 0;
        godsArray = new ArrayList<>();
        cardUsed = new ArrayList<>();
        stateManager=new GameStateManager(players, playersDead);
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
    public void loadCards() {
        godsArray= parser.parseCard();
    }
    public void setNPlayers(int nPlayers){
        this.nPlayers=nPlayers;
    }
    public int getNPlayers(){
        return nPlayers;
    }
    public void addPlayer(String name, int age){
        Game.COLOR color;
        if(players.size()==0){
            color=COLOR.BLU;
        }
        else  if(players.size()==1){
            color=COLOR.ORANGE;
        }
        else{
            color=COLOR.RED;
        }
        players.add(new Player(name,age,color));
        sortGamers();
    }

    public God getPlayerCard(int indexPlayer){
        return players.get(indexPlayer).getGod();
    }

    public ArrayList<God> getTempCard(){
        return tempCard;
    }

    public ArrayList<God> getCardUsed(){
        return cardUsed;
    }

    public ArrayList<God> getCards() throws Exception {
        loadCards();
        return godsArray;
    }

    public void chooseThreeCard(ArrayList<God> threeCard){
        tempCard=threeCard;
    }

    public void chooseCard(int playerIndex, int godCard) {
        players.get(playerIndex).setGod(godsArray.get(godCard));
        tempCard.remove(godsArray.get(godCard));
        cardUsed.add(godsArray.get(godCard));
    }

    //index worker 1 o 2, nelle altre classi arriva già a -1
    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box){
        return players.get(indexPlayer).initializeWorker(indexWorker-1, box);
    }

    public GameState getState(){
        return stateManager.getCurrentState();
    }

    public PlayerState getPlayerState(int indexPlayer){
        return players.get(indexPlayer).getState();
    }


    public void startTurn(int indexPlayer){
        players.get(indexPlayer).goPlay();
    }

    public boolean canMove(int indexPlayer){
        return stateManager.canMove(indexPlayer);
    }

    public void setBoxReachable(int indexPlayer, int indexWorker){
       stateManager.setBoxReachable(indexPlayer, indexWorker);
    }

    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column){
        return stateManager.movePlayer(indexPlayer,indexWorker,row,column,board);
    }

    public boolean canBuild(int indexPlayer, int indexWorker){
        return stateManager.canBuild(indexPlayer, indexWorker);
    }

    public void setBoxBuilding(int indexPlayer, int indexWorker){
        stateManager.setBoxBuilding(indexPlayer, indexWorker);
    }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column){
        return stateManager.buildBlock(indexPlayer,indexWorker,row,column,board);
    }

    public void finishTurn(int indexPlayer){
        stateManager.finishTurn(indexPlayer);
        stateManager.startTurn(indexPlayer+1);
    }

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        return stateManager.checkWin(indexPlayer, startBox, indexWorker);
    }

    public boolean checkWinAfterBuild(){
        return stateManager.checkWinAfterBuild();
    }


    public void setWinningPlayer(int indexPlayer){
        stateManager.setWinningPlayer(indexPlayer);
    }

    public void setDeadPlayer(int indexPlayer){
        stateManager.setDeadPlayer(indexPlayer);
    }
    public void setPause(){
        stateManager.goPause();
    }
}


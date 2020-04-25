package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import it.polimi.ingsw.model.gameState.GameState;
import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.model.parse.CardCreator;


/**
 * This is the main class that represents the game
 */

public class Game {
    /**
     * This attribute is the playing board
     */
    private static Board board;
    /**
     * this is the array list of the players
     */
    private ArrayList<Player> players;
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
    /**
     * Array of drawn cards
     */
    private ArrayList<God> cardUsed;

    private GameState stateManager;
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
    }


    public Player getPlayer(int index){
        return players.get(index);
    }
    public int getnPlayers(){
        return nPlayers;
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
    public ArrayList<Player> getPlayers(){
        return players;
    }

    public void setNPlayers(int nPlayers){
        this.nPlayers=nPlayers;
    }

    public void addPlayer(String name, int age){
        players.add(new Player(name,age));
        sortGamers();
    }

    public void loadCards() throws Exception {
        godsArray= parser.parseCard();
    }

    public ArrayList<God> getCards() throws Exception {
        loadCards();
        return godsArray;
    }

    public void chooseCard(int playerIndex, int godCard) throws Exception {
        players.get(playerIndex).setGod(godsArray.get(godCard));
    }

    //index worker 1 o 2, nelle altre classi arriva già a -1
    public boolean initializeWorker(int indexPlayer, int indexWorker, Box box){
        return players.get(indexPlayer).initializeWorker(indexWorker-1, box);
    }

    public void startTurn(int indexPlayer){
        players.get(indexPlayer).goPlay();
    }

    public boolean canMove(int indexPlayer){
        return players.get(indexPlayer).checkWorkers();
    }

    public void setBoxReachable(int indexPlayer, int indexWorker){
        players.get(indexPlayer).setPossibleMove(indexWorker-1);
    }

    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column){
        boolean movedPlayer= false;
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        players.get(indexPlayer).setPossibleMove(indexWorker);
        if(board.getBox(row,column).isReachable()){
             movedPlayer= players.get(indexPlayer).playWorker(indexWorker - 1, board.getBox( row, column));
        }
        starterBox.clearBoxesNextTo();
        return movedPlayer;
    }

    public boolean canBuild(int indexPlayer, int indexWorker){
        return players.get(indexPlayer).checkBuilding(indexWorker-1);
    }

    public void setBoxBuilding(int indexPlayer, int indexWorker){
        players.get(indexPlayer).setPossibleBuild(indexWorker-1);
    }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column){
        boolean movedBlock = false;
        players.get(indexPlayer).setPossibleBuild(indexWorker);
        if(board.getBox(row,column).isReachable()){
            movedBlock=players.get(indexPlayer).playBlock(indexWorker,board.getBox(row, column));
        }
        players.get(indexPlayer).getWorkerBox(indexWorker-1).clearBoxesNextTo();
        return movedBlock;
    }

    public void finishTurn(int indexPlayer){
        players.get(indexPlayer).goWaiting();
    }

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        return players.get(indexPlayer).checkWin(startBox, players.get(indexPlayer).getWorkerBox(indexWorker));
    }

    public boolean checkWinAfterBuild(){
        boolean win=false;
        int player = 0;
        while(player< nPlayers && !win ){
            int worker = 0;
            while( worker<2 && !win){
                win= players.get(player).checkWin(players.get(player).getWorkerBox(worker), players.get(player).getWorkerBox(worker));
                worker++;
            }
            player++;
        }
        return win;
    }


    public void setWinningPlayer(int indexPlayer){
        players.get(indexPlayer).goWin();
    }

    public void setDeadPlayer(int indexPlayer){
        players.get(indexPlayer).goDead();
        playersDead.add(players.get(indexPlayer));
        players.remove(indexPlayer);
    }

}


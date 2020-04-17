package it.polimi.ingsw;


import java.util.ArrayList;
import it.polimi.ingsw.god.*;
import it.polimi.ingsw.parse.ParserXML;


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

    private ParserXML parser = new ParserXML();
    //private Move lastMove = new Move();
    /**
     * Constructor without parameters
     */
    Game(){
        board = new Board();
        players = new ArrayList<>();
        playersDead = new ArrayList<>();
        nPlayers = 0;
        godsArray = new ArrayList<>();
        cardUsed = new ArrayList<>();
    }

    /**
     * Method to sort gamers by age
     */
    public void sortGamers(){
        for(int i = 0; i < nPlayers-1; i++){
            if(players.get(i).getAge() > players.get(i+1).getAge()){
                players.get(i).swap(players.get(i+1));
            }
        }
    }

    public void setNPlayers(int nPlayers){
        this.nPlayers=nPlayers;
    }

    public void addPlayer(String name, int age){
        players.add(new Player(name,age));
    }

    public ArrayList<God> showCards(){
        parser.parseBase();
        parser.setEffects();
        return parser.getGodsArray();
    }

    public void chooseCard(int playerIndex, int godCard){
        players.get(playerIndex).setGod(parser.getGod(godCard));
    }

    public void initializeWorker(int indexPlayer, int indexWorker, int row, int column){
        players.get(indexPlayer).initializeWorker(indexWorker-1, board.getBox(row, column));
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
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        boolean movedPlayer = players.get(indexPlayer).playWorker(indexWorker - 1, board.getBox( row, column));
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
        boolean movedBlock = players.get(indexPlayer).playBlock(board.getBox(row, column));
        players.get(indexPlayer).getWorkerBox(indexWorker-1).clearBoxesNextTo();
        return movedBlock;
    }

    public void finishTurn(int indexPlayer){
        players.get(indexPlayer).goWaiting();
    }

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        return players.get(indexPlayer).checkWin(startBox, players.get(indexPlayer).getWorkerBox(indexWorker));
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


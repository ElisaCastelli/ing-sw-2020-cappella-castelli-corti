package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Player;

import java.util.ArrayList;

public class GoingState extends GameState{
    private ArrayList<Player> players;
    private ArrayList<Player> playersDead ;
    private final GameStateManager manager;

    public GoingState(ArrayList<Player> players, ArrayList<Player> playersDead, GameStateManager manager){
        this.players=players;
        this.playersDead=playersDead;
        this.manager=manager;
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

    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column, Board board){
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        boolean movedPlayer = players.get(indexPlayer).playWorker(indexWorker - 1, board.getBox(row, column));
        starterBox.clearBoxesNextTo();
        return movedPlayer;
    }

    public boolean canBuild(int indexPlayer, int indexWorker){
        return players.get(indexPlayer).checkBuilding(indexWorker-1);
    }

    public void setBoxBuilding(int indexPlayer, int indexWorker){
        players.get(indexPlayer).setPossibleBuild(indexWorker-1);
    }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column, Board board){
        Box starterBox = players.get(indexPlayer).getWorkerBox(indexWorker - 1);
        boolean movedBlock = players.get(indexPlayer).playBlock(board.getBox(row, column));
        starterBox.clearBoxesNextTo();
        return movedBlock;
    }

    public void finishTurn(int indexPlayer){
        players.get(indexPlayer).goWaiting();
    }

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){
        boolean win = players.get(indexPlayer).checkWin(startBox, players.get(indexPlayer).getWorkerBox(indexWorker-1));
        if(win){
            manager.goEnd(indexPlayer);
        }
        return win;
    }

    public boolean checkWinAfterBuild(){
        boolean win = false;
        int player = 0;
        while(player < players.size() && !win ){
            int worker = 0;
            while( worker < 2 && !win){
                win = players.get(player).checkWin(players.get(player).getWorkerBox(worker), players.get(player).getWorkerBox(worker));
                worker++;
            }
            player++;
        }

        if(win){
            manager.goEnd(player);
        }
        return win;
    }


//TODO TOGLIERE
    /*public void setDeadPlayer(int indexPlayer){
        players.get(indexPlayer).goDead();
        playersDead.add(players.get(indexPlayer));
        players.remove(indexPlayer);
        //TODO notify gioco finito
        if(players.size()==2){
            manager.goEnd();
        }
    }*/
}

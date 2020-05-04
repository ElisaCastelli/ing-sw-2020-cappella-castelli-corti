package it.polimi.ingsw.server.model.gameState;

import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

public class GameState {
    public void startTurn(int indexPlayer){}

    public boolean canMove(int indexPlayer){return false;}

    public void setBoxReachable(int indexPlayer, int indexWorker){}

    public boolean movePlayer(int indexPlayer, int indexWorker, int row, int column, Board board){return false;}

    public boolean canBuild(int indexPlayer, int indexWorker){return false;}

    public void setBoxBuilding(int indexPlayer, int indexWorker){ }

    public boolean buildBlock(int indexPlayer, int indexWorker, int row, int column, Board board){return false;}

    public void finishTurn(int indexPlayer){}

    public boolean checkWin(int indexPlayer, Box startBox, int indexWorker){return false;}

    public boolean checkWinAfterBuild(){return false;}

    public void setWinningPlayer(int indexPlayer){}

    public void setDeadPlayer(int indexPlayer){ }
}

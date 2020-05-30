package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.god.God;

public class PlayerState {
    //public void goNext(GamerStateManager next){};
    public void setMyGod(God myGod){}

    public boolean moveWorker (Worker worker, Box pos){ return false;}

    public boolean moveBlock(Box pos){ return false;}

    public boolean checkWin(Box boxReach, Box boxStart){return false;}

    public boolean canBuildBeforeWorkerMove(){ return false; }

    public void setPossibleMove( Worker worker ){ }

    public void setPossibleBuild( Worker worker){ }

    public void setIndexPossibleBlock( int indexPossibleBlock){}

    public boolean amITheWinner(){return false;}

    public boolean amIDead() {
        return false;
    }
}

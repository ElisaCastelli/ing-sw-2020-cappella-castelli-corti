package it.polimi.ingsw.model.playerState;

import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.gameComponents.Worker;

public class PlayerState {
    //public void goNext(GamerStateManager next){};
    public boolean moveWorker (Worker worker, Box pos){return false;}

    public boolean moveBlock(Box pos){ return false;}

    public boolean checkWin(Box boxReach, Box boxStart){return false;}

    public void setPossibleMove( Worker worker ){ }

    public void setPossibleBuild( Worker worker){ }
}

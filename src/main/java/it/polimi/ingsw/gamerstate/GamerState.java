package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;

public class GamerState {
    //public void goNext(GamerStateManager next){};
    public boolean moveWorker (Worker worker, Box pos){return false;}

    public boolean moveBlock(Box pos){ return false;}

    public boolean checkWin(Box boxReach, Box boxStart){return false;}

    public void setPossibleMove( Worker worker ){ }

    public void setPossibleBuild( Worker worker){ }

}

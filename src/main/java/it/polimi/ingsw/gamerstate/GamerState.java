package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;

public class GamerState {
    //public void goNext(GamerStateManager next){};
    public int moveWorker (Worker worker, Box pos, String godName ){return 0;};
    public int moveBlock(Worker worker, Box pos, String godName){ return 0;};
    public boolean checkWin(Box boxReach, Box boxStart, String godName){return false;};
    public boolean checkPossibleMove( Box actualBox , Board boardToControl){ return false;};
    public int checkPossibleBuild( Box finalBox, Board boardToControl, String name ){ return 0;}
}

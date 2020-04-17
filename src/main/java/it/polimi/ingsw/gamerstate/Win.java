package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class Win extends GamerState{
    private final GamerStateManager gamerManager;
    private God myGod;
    public Win(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
        myGod=gamerManager.getMyGod();
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Vittoria");
        return false;
    }
    @Override
    public void setPossibleMove( Worker worker ) {
        myGod.setPossibleMove(worker);
    }
    @Override
    public void setPossibleBuild(Worker worker ){
        myGod.setPossibleBuild(worker);
    }
}

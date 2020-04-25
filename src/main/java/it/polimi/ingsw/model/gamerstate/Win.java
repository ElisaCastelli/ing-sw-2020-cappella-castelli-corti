package it.polimi.ingsw.model.gamerstate;

import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.model.Box;
import it.polimi.ingsw.model.Worker;

public class Win extends GamerState{
    private God myGod;
    public Win(God myGod){
        this.myGod=myGod;
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

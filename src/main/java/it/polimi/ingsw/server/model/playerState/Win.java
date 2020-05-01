package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

public class Win extends PlayerState{
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

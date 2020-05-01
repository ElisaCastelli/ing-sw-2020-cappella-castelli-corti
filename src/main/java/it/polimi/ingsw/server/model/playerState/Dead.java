package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

public class Dead extends PlayerState{
    private God myGod;
    public Dead(God myGod){
        this.myGod=myGod;
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Morto");
        return super.moveWorker(worker,pos);
    }
    @Override
    public boolean moveBlock(Box pos){
        System.out.println("Morto");
        return super.moveBlock(pos);
    }

    @Override
    public void setPossibleMove(Worker worker) {
        myGod.setPossibleMove(worker);
    }

    @Override
    public void setPossibleBuild(Worker worker) {
        myGod.setPossibleBuild(worker);
    }
}

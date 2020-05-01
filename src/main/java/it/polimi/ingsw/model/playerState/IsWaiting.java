package it.polimi.ingsw.model.playerState;

import it.polimi.ingsw.model.god.*;
import it.polimi.ingsw.model.gameComponents.Box;
import it.polimi.ingsw.model.gameComponents.Worker;

public class IsWaiting extends PlayerState{
    private God myGod;

    public IsWaiting(God myGod){
        this.myGod=myGod;
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Pausa NOOOOOOOOOOOO");
        return super.moveWorker(worker,pos);
    }

    @Override
    public boolean moveBlock(Box pos) {
        return myGod.moveBlock(pos);
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

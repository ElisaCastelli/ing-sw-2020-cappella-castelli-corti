package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class IsWaiting extends GamerState{
    private final GamerStateManager gamerManager;
    private God myGod;

    public IsWaiting(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
        myGod=gamerManager.getMyGod();
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

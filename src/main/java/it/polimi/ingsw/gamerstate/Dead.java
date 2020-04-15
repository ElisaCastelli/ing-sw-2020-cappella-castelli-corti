package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class Dead extends GamerState{
    private final GamerStateManager gamerManager;
    private God myGod;
    public Dead(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
        myGod=gamerManager.getMyGod();
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
        super.setPossibleMove(worker);
    }

    @Override
    public void setPossibleBuild(Worker worker) {
        super.setPossibleBuild(worker);
    }
}

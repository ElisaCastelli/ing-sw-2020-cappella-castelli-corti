package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

public class IsWaiting extends PlayerState{
    private God myGod;
    private final PlayerStateManager playerManager;
    public IsWaiting(God myGod, PlayerStateManager playerManager){
        this.myGod=myGod;
        this.playerManager=playerManager;
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

package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.god.*;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

public class IsPlaying extends PlayerState{
    private God myGod;
    private final PlayerStateManager playerManager;
    public IsPlaying(God myGod, PlayerStateManager playerManager){
        this.myGod = myGod;
        this.playerManager = playerManager;
    }

    public void setMyGod(God myGod) {
        this.myGod = myGod;
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Muovo"); //mossa effettiva
        return myGod.moveWorker( worker , pos);
    }

    @Override
    public boolean moveBlock(Box pos){
        System.out.println("Costruisco"); //costruzione effettiva
        return myGod.moveBlock(pos);
    }

    @Override
    public boolean checkWin(Box boxReach, Box boxStart) {
        boolean win = myGod.checkWin(boxStart, boxReach);
        if(win){
            playerManager.goWin();
        }
        return win;
    }


    @Override
    public void setPossibleMove( Worker worker){
        myGod.setPossibleMove(worker);
    }

    @Override
    public void setPossibleBuild( Worker worker){
        myGod.setPossibleBuild(worker);
    }
}

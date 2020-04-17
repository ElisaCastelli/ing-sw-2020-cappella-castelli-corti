package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class IsPlaying extends GamerState{
    private God myGod;
    public IsPlaying(God myGod){
        this.myGod=myGod;
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Muovo"); //mossa effettiva
        return myGod.moveWorker( worker , pos);
    }

    @Override
    public boolean moveBlock( Box pos){
        System.out.println("Costruisco"); //costruzione effettiva
        return myGod.moveBlock(pos);
    }

    @Override
    public boolean checkWin(Box starterBox, Box finalBox) {
        return myGod.checkWin( starterBox , finalBox );
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

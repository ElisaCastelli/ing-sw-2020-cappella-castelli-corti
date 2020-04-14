package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class IsPlaying extends GamerState{
    private final GamerStateManager gamerManager;
    private God myGod;
    public IsPlaying(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
        this.myGod=gamerManager.getMyGod();
    }

    @Override
    public boolean moveWorker(Worker worker, Box pos){
        System.out.println("Muovo"); //mossa effettiva
        boolean movedWorker = false;
        movedWorker= myGod.moveWorker( worker , pos);
        return movedWorker;
    }

    @Override
    public boolean moveBlock( Box pos){
        System.out.println("Costruisco"); //costruzione effettiva
        boolean movedBlock = false;
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

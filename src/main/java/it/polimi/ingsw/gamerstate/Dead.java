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
    public int moveWorker(Worker worker, Box pos, String godName){
        System.out.println("Morto");
        return super.moveWorker(worker,pos,godName);
    }
    @Override
    public int moveBlock(Worker worker, Box pos, String godName){
        System.out.println("Morto");
        return super.moveBlock(worker,pos,godName);
    }

    @Override
    public boolean checkPossibleMove( Box actualBox , Board boardToControl ) {
        return super.checkPossibleMove(actualBox,boardToControl);
    }
    @Override
    public int checkPossibleBuild( Box finalBox, Board boardToControl, String name ){
        return super.checkPossibleBuild(finalBox, boardToControl, name);
    }
}

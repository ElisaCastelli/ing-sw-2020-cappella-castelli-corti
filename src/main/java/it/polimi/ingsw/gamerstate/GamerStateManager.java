package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class GamerStateManager {
    private final GamerState isPlaying;
    private final GamerState isWaiting;
    private final GamerState dead;
    private final GamerState win;
    private GamerState currentState;



    public GamerStateManager(God myGod){
        isPlaying = new IsPlaying(myGod);
        isWaiting = new IsWaiting(myGod);
        dead = new Dead(myGod);
        win = new Win(myGod);
        currentState = isWaiting;
    }

    public GamerState getIsPlaying() {
        return isPlaying;
    }
    public GamerState getIsWaiting() {
        return isWaiting;
    }
    public GamerState getDead() {
        return dead;
    }
    public GamerState getWin() {
        return win;
    }


    public void setCurrentState(GamerState newState ){
        currentState=newState;
    }

    public GamerState getCurrentState(){
        return currentState;
    }


    public void goPlaying(){
        if(currentState==isWaiting){
            setCurrentState(isPlaying);
        }

    }

    public void goWaiting(){
        if(currentState==isPlaying){
            setCurrentState(isWaiting);
        }

    }

    public void goDead(){
        if(currentState==isPlaying || currentState==isWaiting){
            setCurrentState(dead);
        }

    }

    public void goWin(){
        if(currentState==isPlaying){
            setCurrentState(win);
        }
    }

    public boolean moveWorker ( Worker worker, Box pos ){
        return currentState.moveWorker(worker,pos);
    }
    public boolean moveBlock( Box pos){
        return currentState.moveBlock(pos);
    }
    public boolean checkWin(Box boxReach, Box boxStart){
        return currentState.checkWin(boxReach, boxStart);
    }
    public void setPossibleMove( Worker worker ){
        currentState.setPossibleMove(worker);
    }
    public void setPossibleBuild( Worker worker){
        currentState.setPossibleBuild(worker);
    }

}

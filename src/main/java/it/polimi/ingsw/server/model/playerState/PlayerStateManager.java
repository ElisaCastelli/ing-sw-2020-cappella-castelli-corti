package it.polimi.ingsw.server.model.playerState;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;
import it.polimi.ingsw.server.model.god.God;

public class PlayerStateManager {
    private final PlayerState isPlaying;
    private final PlayerState isWaiting;
    private final PlayerState dead;
    private final PlayerState win;
    private PlayerState currentState;



    public PlayerStateManager(God myGod){
        isPlaying = new IsPlaying(myGod);
        isWaiting = new IsWaiting(myGod);
        dead = new Dead(myGod);
        win = new Win(myGod);
        currentState = isWaiting;
    }

    public PlayerState getIsPlaying() {
        return isPlaying;
    }
    public PlayerState getIsWaiting() {
        return isWaiting;
    }
    public PlayerState getDead() {
        return dead;
    }
    public PlayerState getWin() {
        return win;
    }


    public void setCurrentState(PlayerState newState ){
        currentState=newState;
    }

    public PlayerState getCurrentState(){
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

    public boolean moveWorker (Worker worker, Box pos ){
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
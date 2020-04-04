package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class GamerStateManager {
    private GamerState isPlaying;
    private GamerState isWaiting;
    private GamerState dead;
    private GamerState win;
    private GamerState currentState;
    private God myGod;


    public GamerStateManager(God myGod){
        isPlaying = new IsPlaying(this);
        isWaiting = new IsWaiting(this);
        dead = new Dead(this);
        win = new Win(this);
        myGod = this.myGod;
        currentState = isWaiting;
    }
    public God getMyGod() {
        return myGod;
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
    };
    public GamerState getCurrentState(){
        return currentState;
    };


    public void goPlaying(){
        if(currentState==isWaiting){
            setCurrentState(isPlaying);
        }

    };
    public void goWaiting(){
        if(currentState==isPlaying){
            setCurrentState(isWaiting);
        }

    };
    public void goDead(){
        if(currentState==isPlaying || currentState==isWaiting){
            setCurrentState(dead);
        }

    };
    public void goWin(){
        if(currentState==isPlaying){
            setCurrentState(win);
        }
    };
    public int moveWorker ( Worker worker, Box pos, String godName ){
        return currentState.moveWorker(worker,pos,godName);
    }
    public int moveBlock(Worker worker, Box pos, String godName){
        return currentState.moveBlock(worker, pos, godName);
    }
    public boolean checkWin(Box boxReach, Box boxStart, String godName){
        return currentState.checkWin(boxReach, boxStart, godName);
    };
    public boolean checkPossibleMove( Box actualBox , Board boardToControl ){
        return currentState.checkPossibleMove(actualBox, boardToControl);
    }
    public int checkPossibleBuild( Box finalBox, Board boardToControl,  String name ){
        return currentState.checkPossibleBuild(finalBox, boardToControl,name);
    }
    public boolean checkWorkers(Box actualBoxW1, Box actualBoxW2, Board boardToControl){
            return currentState.checkWorkers(actualBoxW1, actualBoxW2, boardToControl);
    }
}

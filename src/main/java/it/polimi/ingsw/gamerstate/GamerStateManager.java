package it.polimi.ingsw.gamerstate;

public class GamerStateManager {
    private GamerState currentState;
    GamerStateManager(){
        currentState= new IsAlive();
    }
    public void goNext(){
        currentState.goNext(this);
    };
    public void setCurrentState(GamerState newState ){
        currentState=newState;
    };
    public GamerState getCurrentState(){
        return currentState;
    };
}

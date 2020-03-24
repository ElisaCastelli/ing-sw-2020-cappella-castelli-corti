package it.polimi.ingsw.gamerstate;

public class IsAlive extends GamerState{
    public void goNext(GamerStateManager next){
        next.setCurrentState(new IsPlaying());
    }
}

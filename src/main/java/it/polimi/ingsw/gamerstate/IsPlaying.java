package it.polimi.ingsw.gamerstate;

public class IsPlaying extends GamerState{
    public void goNext(GamerStateManager next){
        next.setCurrentState(new Dead());
    }
}

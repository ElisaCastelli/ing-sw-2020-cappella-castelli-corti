package it.polimi.ingsw.gamerstate;

public class IsWaiting extends GamerState{
    private final GamerStateManager gamerManager;
    public IsWaiting(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
    }
    public void goNext(GamerStateManager next){
        //next.setCurrentState(new IsPlaying());
    }
    public void move(){
        System.out.println("Pausa NOOOOOOOOOOOO");
        gamerManager.setCurrentState(gamerManager.getIsPlaying());
    }
}

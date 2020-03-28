package it.polimi.ingsw.gamerstate;

public class IsPlaying extends GamerState{
    private final GamerStateManager gamerManager;
    public IsPlaying(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
    }
    public void goNext(GamerStateManager next){
        //next.setCurrentState(new Dead());
    }
    public void move(){
        System.out.println("Gioco io"); //mossa effettiva
        gamerManager.setCurrentState(gamerManager.getIsWaiting());
    }
}

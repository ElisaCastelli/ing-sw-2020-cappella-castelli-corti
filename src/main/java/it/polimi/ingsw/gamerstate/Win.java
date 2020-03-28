package it.polimi.ingsw.gamerstate;

public class Win extends GamerState{
    private final GamerStateManager gamerManager;
    public Win(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
    }
    @Override
    public void goNext(GamerStateManager next){

    }
    @Override
    public void move(){
        System.out.println("Vittoria");
    }
}

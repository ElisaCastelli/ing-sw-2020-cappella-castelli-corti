package it.polimi.ingsw.gamerstate;

public class Dead extends GamerState{
    private final GamerStateManager gamerManager;
    public Dead(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
    }
    @Override
    public void goNext(GamerStateManager next){

    }
    @Override
    public void move(){
        System.out.println("Morto");
    }
}

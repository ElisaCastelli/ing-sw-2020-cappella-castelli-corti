package it.polimi.ingsw.gamerstate;

public class GamerStateManager {
    private GamerState isPlaying;
    private GamerState isWaiting;
    private GamerState dead;
    private GamerState win;

    private GamerState currentState;



    public GamerStateManager(){
        isPlaying=new IsPlaying(this);
        isWaiting=new IsWaiting(this);
        dead=new Dead(this);
        win=new Win(this);
        currentState= isWaiting;
    }

    public GamerState getIsPlaying() {
        return isPlaying;
    }
    public void setIsPlaying(GamerState isPlaying) {
        this.isPlaying = isPlaying;
    }
    public GamerState getIsWaiting() {
        return isWaiting;
    }
    public void setIsWaiting(GamerState isWaiting) {
        this.isWaiting = isWaiting;
    }
    public GamerState getDead() {
        return dead;
    }
    public void setDead(GamerState dead) {
        this.dead = dead;
    }
    public GamerState getWin() {
        return win;
    }
    public void setWin(GamerState win) {
        this.win = win;
    }
    public void setCurrentState(GamerState newState ){
        currentState=newState;
    };
    public GamerState getCurrentState(){
        return currentState;
    };

    public void goNext(){
        currentState.goNext(this);
    };
    public void move(){
        currentState.move();
    }
}

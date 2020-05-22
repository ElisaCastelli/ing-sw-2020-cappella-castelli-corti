package it.polimi.ingsw.server.model.gameState;

public class EndState extends GameState{
    int winner;
    public EndState(){

    }

    public void setWinner(int winner){
        this.winner = winner;
    }

    public int getWinner(){
        return winner;
    }
}

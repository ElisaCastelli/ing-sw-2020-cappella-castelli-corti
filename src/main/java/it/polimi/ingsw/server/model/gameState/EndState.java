package it.polimi.ingsw.server.model.gameState;

/**
 * State used if the game is over
 */
public class EndState extends GameState {
    /**
     * index of the winner player
     */
    int winner;

    public EndState() {
    }

    @Override
    public void setWinner(int winner) {
        this.winner = winner;
    }

    @Override
    public int getWinner() {
        return winner;
    }
}

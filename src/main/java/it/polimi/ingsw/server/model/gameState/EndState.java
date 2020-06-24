package it.polimi.ingsw.server.model.gameState;

/**
 * State used if the game is over
 */
public class EndState extends GameState {
    /**
     * index of the winner player
     */
    int winner;

    /**
     * Constructor of the class
     */

    public EndState() {
    }

    /**
     * Winner setter
     *
     * @param winner integer of the winner
     */
    @Override
    public void setWinner(int winner) {
        this.winner = winner;
    }

    /**
     * Winner getter
     *
     * @return integer of the winner
     */
    @Override
    public int getWinner() {
        return winner;
    }
}

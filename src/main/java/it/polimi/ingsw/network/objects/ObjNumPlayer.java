package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message number of player
 */
public class ObjNumPlayer extends ObjMessage {
    /**
     * number of players
     */
    private final int nPlayer;

    /**
     * constructor of the class
     *
     * @param nPlayer number of player
     */
    public ObjNumPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    /**
     * Number of the players getter
     *
     * @return number of players
     */
    public int getNPlayer() {
        return nPlayer;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitor the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitor) {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

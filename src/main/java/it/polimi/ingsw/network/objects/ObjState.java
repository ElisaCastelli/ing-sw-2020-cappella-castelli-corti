package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message state of the game
 */
public class ObjState extends ObjMessage {

    private static final long serialVersionUID = -10489342L;

    int indexPlayer;

    /**
     * constructor of the class
     *
     * @param indexPlayer index of the player
     */
    public ObjState(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

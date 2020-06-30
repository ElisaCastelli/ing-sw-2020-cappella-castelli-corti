package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message state of the game
 */
public class ObjState extends ObjMessage {

    private static final long serialVersionUID = -10489342L;
    /**
     * index of the player
     */
    int indexPlayer;

    /**
     * constructor of the class
     *
     * @param indexPlayer index of the player
     */
    public ObjState(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    /**
     * Index of the player getter
     *
     * @return index of the player
     */
    public int getIndexPlayer() {
        return indexPlayer;
    }

    /**
     * Index of the player setter
     *
     * @param indexPlayer index of the player
     */
    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        throw new UnsupportedOperationException();
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        visitorMessageFromServer.visit(this);
    }
}

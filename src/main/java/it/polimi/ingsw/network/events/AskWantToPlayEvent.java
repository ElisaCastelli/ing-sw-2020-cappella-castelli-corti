package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message starting connection event
 */
public class AskWantToPlayEvent extends Event {
    /**
     * index of the client
     */
    final int indexClient;

    /**
     * constructor of the class
     *
     * @param indexClient index of the client
     */
    public AskWantToPlayEvent(int indexClient) {
        this.indexClient = indexClient;
    }

    /**
     * Index of the player getter
     *
     * @return index of the client
     */
    public int getIndexClient() {
        return indexClient;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */

    @Override
    public void accept(VisitorServer visitorServer) {
        visitorServer.visit(this);
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

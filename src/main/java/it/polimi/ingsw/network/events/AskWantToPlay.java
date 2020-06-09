package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message starting connection event
 */
public class AskWantToPlay extends ObjMessage {

    final int indexClient;

    /**
     * constructor of the class
     *
     * @param indexClient index of the client
     */
    public AskWantToPlay(int indexClient) {
        this.indexClient = indexClient;
    }

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

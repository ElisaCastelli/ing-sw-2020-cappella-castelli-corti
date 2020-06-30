package it.polimi.ingsw.network.ack;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message block's ack
 */
public class AckBlock extends ObjMessage {

    private static final long serialVersionUID = 30298492012L;

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        visitorMessageFromClient.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        throw new UnsupportedOperationException();
    }
}

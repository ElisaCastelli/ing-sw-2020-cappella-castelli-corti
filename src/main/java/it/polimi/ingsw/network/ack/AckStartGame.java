package it.polimi.ingsw.network.ack;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * message start game
 */
public class AckStartGame extends ObjMessage {

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorMessageFromClient visitorMessageFromClient) {
        try {
            visitorMessageFromClient.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

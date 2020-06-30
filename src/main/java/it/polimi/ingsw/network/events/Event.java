package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * abstract message for the visitor pattern
 */

public abstract class Event extends ObjMessage {
    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    public abstract void accept(VisitorMessageFromClient visitorMessageFromClient);

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    public abstract void accept(VisitorMessageFromServer visitorMessageFromServer);
}

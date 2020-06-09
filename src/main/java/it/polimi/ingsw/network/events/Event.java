package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

/**
 * abstract message for the visitor pattern
 */

public abstract class Event extends ObjMessage {
    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    public abstract void accept(VisitorServer visitorServer);

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    public abstract void accept(VisitorClient visitorClient);
}

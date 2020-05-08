package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public abstract class Event extends ObjMessage {
    public abstract void accept(VisitorServer visitorServer);
    public abstract void accept(VisitorClient visitorClient);
}

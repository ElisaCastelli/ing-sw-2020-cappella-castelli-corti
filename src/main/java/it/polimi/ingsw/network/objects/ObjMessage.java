package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

import java.io.Serializable;

public abstract class ObjMessage implements Serializable {
    public abstract void accept(VisitorServer visitorServer);
    public abstract void accept(VisitorClient visitorClient);
}

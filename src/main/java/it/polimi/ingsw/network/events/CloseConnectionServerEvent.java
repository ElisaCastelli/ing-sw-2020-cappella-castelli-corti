package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class CloseConnectionServerEvent extends  Event {

    @Override
    public void accept(VisitorServer visitorServer) {

    }

    @Override
    public void accept(VisitorClient visitorClient) {

    }
}

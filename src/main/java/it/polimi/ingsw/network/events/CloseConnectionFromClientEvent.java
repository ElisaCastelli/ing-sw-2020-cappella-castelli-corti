package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class CloseConnectionFromClientEvent extends Event {

    int indexClient;

    public CloseConnectionFromClientEvent(int indexClient) {
        this.indexClient = indexClient;
    }

    public int getIndexClient() {
        return indexClient;
    }

    public void setIndexClient(int indexClient) {
        this.indexClient = indexClient;
    }

    @Override
    public void accept(VisitorServer visitorServer) {
        visitorServer.visit(this);
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class CloseConnectionFromServerEvent extends  Event {

    boolean GameNotAvailable;

    public CloseConnectionFromServerEvent(boolean gameNotAvailable) {
        GameNotAvailable = gameNotAvailable;
    }

    public boolean isGameNotAvailable() {
        return GameNotAvailable;
    }

    public void setGameNotAvailable(boolean gameNotAvailable) {
        GameNotAvailable = gameNotAvailable;
    }

    @Override
    public void accept(VisitorServer visitorServer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

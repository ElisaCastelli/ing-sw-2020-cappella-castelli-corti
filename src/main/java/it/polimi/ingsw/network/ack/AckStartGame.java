package it.polimi.ingsw.network.ack;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AckStartGame extends ObjMessage {
    @Override
    public void accept(VisitorServer visitorServer) {
        try {
            visitorServer.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

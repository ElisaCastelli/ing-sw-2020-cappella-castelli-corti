package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjAck extends ObjMessage{
    String ack;
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
        try {
            visitorClient.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

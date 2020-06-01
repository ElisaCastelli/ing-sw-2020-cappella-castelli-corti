package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjHeartBeat extends ObjMessage {

    private long timeStamp;

    public ObjHeartBeat( long timeStamp) {
        this.timeStamp= timeStamp;
    }

    public String getMessageHeartbeat() {
        return "--HEARTBEATS--";
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        visitorServer.visit(this);
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

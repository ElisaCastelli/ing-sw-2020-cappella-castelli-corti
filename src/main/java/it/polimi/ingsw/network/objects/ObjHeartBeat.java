package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjHeartBeat extends ObjMessage {

    private int indexClient;
    private long timeStamp;

    public ObjHeartBeat(int indexClient, long timeStamp) {
        this.indexClient = indexClient;
        this.timeStamp= timeStamp;
    }

    public int getIndexClient() {
        return indexClient;
    }

    public void setIndexClient(int indexClient) {
        this.indexClient = indexClient;
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

package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskWantToPlay extends ObjMessage {

    int indexClient;

    public AskWantToPlay(int indexClient) {
        this.indexClient = indexClient;
    }

    public int getIndexClient() {
        return indexClient;
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

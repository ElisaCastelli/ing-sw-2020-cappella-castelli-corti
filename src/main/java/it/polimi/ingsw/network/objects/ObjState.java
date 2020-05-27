package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjState extends ObjMessage{

    private static final long serialVersionUID = -10489342L;

    int indexPlayer;

    public ObjState(int indexPlayer){
        this.indexPlayer=indexPlayer;
    }
    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
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

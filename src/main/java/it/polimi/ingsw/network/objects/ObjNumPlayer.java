package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjNumPlayer  extends ObjMessage{
    private int nPlayer;

    public ObjNumPlayer(int nPlayer){
        this.nPlayer=nPlayer;
    }
    public int getnPlayer() {
        return nPlayer;
    }


    @Override
    public void accept(VisitorServer visitor) {
        try {
            visitor.visit(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

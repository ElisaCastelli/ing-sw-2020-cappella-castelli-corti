package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

public class ObjState extends ObjMessage{

    private static final long serialVersionUID = -10489342L;

    int indexPlayer;
    int currentPlayer;

    public  ObjState(){
        indexPlayer=-1;
        currentPlayer=-1;
    }
    public ObjState(int indexPlayer, int currentPlayer){
        this.indexPlayer=indexPlayer;
        this.currentPlayer=currentPlayer;
    }
    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
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

package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

import java.io.Serializable;

public abstract class ObjMessage implements Serializable {
    private int clientIndex;
    private int currentPlayer;

    public int getClientIndex() {
        return clientIndex;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }

    public abstract void accept(VisitorServer visitorServer) throws Exception;
    public abstract void accept(VisitorClient visitorClient);
}

package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

import java.io.Serializable;

/**
 * abstract message for the visitor pattern
 */
public abstract class ObjMessage implements Serializable {
    private int clientIndex;
    private int currentClientPlaying;

    public int getClientIndex() {
        return clientIndex;
    }

    public int getCurrentClientPlaying() {
        return currentClientPlaying;
    }

    public void setCurrentClientPlaying(int currentClientPlaying) {
        this.currentClientPlaying = currentClientPlaying;
    }

    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    public abstract void accept(VisitorServer visitorServer);

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    public abstract void accept(VisitorClient visitorClient);
}

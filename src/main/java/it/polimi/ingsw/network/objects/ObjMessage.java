package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

import java.io.Serializable;

/**
 * abstract message for the visitor pattern
 */
public abstract class ObjMessage implements Serializable {
    /**
     * Index of the client
     */
    private int clientIndex;
    /**
     * Index of the client playing
     */
    private int currentClientPlaying;
    /**
     * boolean if is the game started
     */
    private boolean beforeStart = false;

    /**
     * Index of the client getter
     *
     * @return index of the client
     */
    public int getClientIndex() {
        return clientIndex;
    }

    /**
     * Index of the client playing getter
     *
     * @return index of the client playing
     */
    public int getCurrentClientPlaying() {
        return currentClientPlaying;
    }

    /**
     * Index of the client playing setter
     *
     * @param currentClientPlaying of the client plying
     */
    public void setCurrentClientPlaying(int currentClientPlaying) {
        this.currentClientPlaying = currentClientPlaying;
    }

    /**
     * Index of the client setter
     *
     * @param  clientIndex of the client
     */
    public void setClientIndex(int clientIndex) {
        this.clientIndex = clientIndex;
    }

    /**
     * Before start getter
     *
     * @return true if is the game is not started
     */
    public boolean isBeforeStart() {
        return beforeStart;
    }

    /**
     * Before start setter
     *
     * @param beforeStart true if is the game is not started
     */
    public void setBeforeStart(boolean beforeStart) {
        this.beforeStart = beforeStart;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromClient the class of the visitor pattern server's side
     */
    public abstract void accept(VisitorMessageFromClient visitorMessageFromClient);

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    public abstract void accept(VisitorMessageFromServer visitorMessageFromServer);
}

package it.polimi.ingsw.network;

public class User {
    private int indexArrayDiClient;
    private String name;
    private String nameCard;
    private int nPlayer;
    private int indexPlayer;
    private boolean initialized;

    public synchronized int getIndexArrayDiClient() {
        return indexArrayDiClient;
    }

    public synchronized void setIndexArrayDiClient(int indexArrayDiClient) {
        this.indexArrayDiClient = indexArrayDiClient;
    }

    public synchronized String getNameCard() {
        return nameCard;
    }

    public synchronized void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized int getnPlayer() {
        return nPlayer;
    }

    public synchronized void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public synchronized int getIndexPlayer() {
        return indexPlayer;
    }

    public synchronized void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public synchronized boolean isInitialized() {
        return initialized;
    }

    public synchronized void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }
}

package it.polimi.ingsw.network;

import java.io.Serializable;

public class User implements Serializable {
    private int indexArrayDiClient;
    private String name;
    private String nameCard;
    private int nPlayer;
    private int indexPlayer;
    private boolean initialized;

    public int getIndexArrayDiClient() {
        return indexArrayDiClient;
    }

    public void setIndexArrayDiClient(int indexArrayDiClient) {
        this.indexArrayDiClient = indexArrayDiClient;
    }

    public String getNameCard() {
        return nameCard;
    }

    public void setNameCard(String nameCard) {
        this.nameCard = nameCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getnPlayer() {
        return nPlayer;
    }

    public void setnPlayer(int nPlayer) {
        this.nPlayer = nPlayer;
    }

    public int getIndexPlayer() {
        return indexPlayer;
    }

    public void setIndexPlayer(int indexPlayer) {
        this.indexPlayer = indexPlayer;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public int getIndexNext(){
        if(indexPlayer<nPlayer-1){
            return indexPlayer+1;
        }
        else {
            return 0;
        }
    }
}

package it.polimi.ingsw.network;

import java.io.Serializable;

/**
 * class that contains information of a player in a game
 */

public class User implements Serializable {

    private String name;
    private final String nameCard;
    private final int client;
    private boolean dead;

    /**
     *
     * @param name the name of the player
     * @param nameCard the name of the card associated with the player
     * @param client the index of the client
     */
    public User(String name, String nameCard, int client) {
        this.name = name;
        this.nameCard = nameCard;
        this.client = client;
        dead = false;
    }

    public String getNameCard() {
        return nameCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClient() {
        return client;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }
}

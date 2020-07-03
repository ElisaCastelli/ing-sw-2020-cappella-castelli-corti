package it.polimi.ingsw.network;

import java.io.Serializable;

/**
 * class that contains information of a player in a game
 */

public class User implements Serializable {
    /**
     * Name of the player
     */
    private String name;
    /**
     * Name of the card
     */
    private final String nameCard;
    /**
     * Index of the client
     */
    private final int client;
    /**
     * Boolean true if is dead
     */
    private boolean dead;

    /**
     * Constructor of the class
     *
     * @param name     the name of the player
     * @param nameCard the name of the card associated with the player
     * @param client   the index of the client
     */
    public User(String name, String nameCard, int client) {
        this.name = name;
        this.nameCard = nameCard;
        this.client = client;
        dead = false;
    }

    /**
     * Name card getter
     *
     * @return name of the card
     */
    public String getNameCard() {
        return nameCard;
    }

    /**
     * Name of the player getter
     *
     * @return name of the player
     */

    public String getName() {
        return name;
    }

    /**
     * Index of client getter
     *
     * @return index of client
     */
    public int getClient() {
        return client;
    }

    /**
     * This method is used to control if is dead
     *
     * @return true if is dead
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Is dead setter
     *
     * @param dead true if is dead
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
}

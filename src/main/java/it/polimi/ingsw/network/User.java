package it.polimi.ingsw.network;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String nameCard;
    private int client;
    private boolean dead;

    public User(String name, String nameCard, int client) {
        this.name = name;
        this.nameCard = nameCard;
        this.client = client;
        dead = false;
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

package it.polimi.ingsw.network;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private String nameCard;

    public User(String name, String nameCard) {
        this.name = name;
        this.nameCard = nameCard;
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
}

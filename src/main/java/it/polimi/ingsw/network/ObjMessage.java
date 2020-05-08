package it.polimi.ingsw.network;

import java.io.Serializable;

public abstract class ObjMessage implements Serializable {
    public abstract String accept(Visitor visitor);
}

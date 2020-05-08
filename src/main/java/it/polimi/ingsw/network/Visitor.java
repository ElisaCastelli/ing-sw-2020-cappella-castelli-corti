package it.polimi.ingsw.network;

public abstract class Visitor {
    public abstract String visitPlayer(ObjPlayer player);
    public abstract String visitCard(ObjCard card);
}

package it.polimi.ingsw.god;

import it.polimi.ingsw.Move;

import java.util.ArrayList;

public interface Observer{
    void update(Move last);
    void subscribe();
    void unSubscribe();
    public void setObservers(ArrayList<Observer> observers);
    public void setSubject(God subject);
}

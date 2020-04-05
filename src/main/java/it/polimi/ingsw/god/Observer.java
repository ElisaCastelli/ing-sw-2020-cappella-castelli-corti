package it.polimi.ingsw.god;

import java.util.ArrayList;

public interface Observer{
    void update(String godName);
    void subscribe();
    void unSubscribe();
    public void setObservers(ArrayList<Observer> observers);
    public void setSubject(God subject);
}

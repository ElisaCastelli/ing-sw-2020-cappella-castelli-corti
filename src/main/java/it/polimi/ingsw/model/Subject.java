package it.polimi.ingsw.model;

import it.polimi.ingsw.view.Observer;

public interface Subject {
    void subscribeObserver(Observer observer);
    void notifyObservers();
}

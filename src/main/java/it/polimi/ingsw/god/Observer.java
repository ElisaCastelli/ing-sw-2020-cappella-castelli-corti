package it.polimi.ingsw.god;

public interface Observer {
    void update(String godName);
    void subscribe();
    void unSubscribe();
}

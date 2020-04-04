package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

/**
 * This interface represents the God for the decorator pattern
 */
public interface God {
    /**
     * This attribute is the God name
     */
    String godName = "";
    String description = "";
    String effect = "";
    Worker lastWorker = null;
    Box lastBox = null;
    String lastGod = "";

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    int moveWorker ( Worker worker, Box pos, String godName );

    /**
     * This method builds a building block in a position on the board by a chosen worker
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker builds a building block
     * @param godName The God name card
     * @return False if the move is not possible; true if we do the move because it passes all the controls
     */
    int moveBlock ( Worker worker, Box pos, String godName );

    /**
     * This method checks if the player's won
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @param godName The God name card
     * @return False if the player doesn't win; true if the player wins
     */
    boolean checkWin ( Box initialPos, Box finalBox, String godName );

    int upDownOrStayAtTheSameLevel ( int counterBuilding, int counterWorker );

    String getGodName();
    void setGodName(String newName);
    String getDescription();
    void setDescription(String newDescription);
    String getEffect();
    void setEffect(String effect);
    Worker getLastWorker();
    void setLastWorker(Worker lastWorker);
    Box getLastBox();
    void setLastBox(Box lastBox);
    String getLastGod();
    void setLastGod(String lastGod);

    /**
     * METODI PER OSSERVATORE
     *
     */
    void subscribeObserver(Observer observer);
    void unSubscribeObserver(Observer observer);
    void notifyObserver();

}

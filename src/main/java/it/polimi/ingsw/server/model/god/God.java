package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This interface represents the God for the decorator pattern
 */
public interface God {

    /**
     * These attributes are the God name, his ability description and the decorators that it is gone to use
     */
    String name = "";
    ArrayList<String> effects = new ArrayList<>();

    String getName();

    void setName(String name);

    void setEffect(ArrayList<String> effects);

    ArrayList<String> getEffects();

    /**
     * This method tells which positions can get reached by a worker
     * @param worker Which worker is the check applied
     */
    void setPossibleMove (Worker worker);

    /**
     * This method tells which positions can get built by a worker
     * @param worker Which worker is the check applied
     */
    void setPossibleBuild (Worker worker);

    /**
     * This method moves the chosen worker to the new position on the board
     * @param worker Which worker is applied the move
     * @param pos Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    boolean moveWorker(Worker worker, Box pos );

    /**
     * This method builds a building block in a position on the board
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    boolean moveBlock ( Box pos );

    /**
     * This methods checks if the player win
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    boolean checkWin ( Box initialPos, Box finalBox );
}

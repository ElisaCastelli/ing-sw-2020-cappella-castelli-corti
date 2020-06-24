package it.polimi.ingsw.server.model.god;

import it.polimi.ingsw.server.model.building.Block;
import it.polimi.ingsw.server.model.gameComponents.Box;
import it.polimi.ingsw.server.model.gameComponents.Worker;

import java.util.ArrayList;

/**
 * This interface represents the God for the decorator pattern
 */
public interface God {

    /**
     * These attribute is the God name
     */
    String name = "";
    /**
     * This array will contain all the decorator classes that each God is going to use
     */
    ArrayList<String> effects = new ArrayList<>();

    /**
     * Name getter
     *
     * @return name of the card
     */
    String getName();

    /**
     * Name setter
     *
     * @param name name of the card
     */
    void setName(String name);

    /**
     * Effects setter
     *
     * @param effects array of effects of the card
     */
    void setEffect(ArrayList<String> effects);

    /**
     * Effects getter
     *
     * @return array of effects of the card
     */
    ArrayList<String> getEffects();

    /**
     * This method tells which positions can get reached by a worker
     *
     * @param worker Which worker is the check applied
     * @return true if is reachable
     */
    boolean setPossibleMove(Worker worker);

    /**
     * This method tells which positions can get built by a worker
     *
     * @param worker Which worker is the check applied
     */
    void setPossibleBuild(Worker worker);

    /**
     * This method moves the chosen worker to the new position on the board
     *
     * @param worker Which worker is applied the move
     * @param pos    Position on the board where the worker wants to go
     * @return False if you can do another move; true if the move has done successfully
     */
    boolean moveWorker(Worker worker, Box pos);

    /**
     * This method builds a building block in a position on the board
     *
     * @param pos Position on the board where the worker builds a building block
     * @return False if you can do another construction; true if the move has done successfully
     */
    boolean moveBlock(Box pos);

    /**
     * This method checks which block could be built in a box
     *
     * @param box box that is going to be checked
     * @return type of block that can be built
     */
    Block whatCanIBuild(Box box);

    /**
     * This method sets the index of the block that could be built
     *
     * @param indexPossibleBlock index of the block
     */
    void setIndexPossibleBlock(int indexPossibleBlock);

    /**
     * This methods checks if the player win
     *
     * @param initialPos Position on the board where the worker starts to move
     * @param finalBox   Position on the board where the worker arrives
     * @return False if the player doesn't win; true if the player wins
     */
    boolean checkWin(Box initialPos, Box finalBox);

    /**
     * This method is used by a God with the ability to build before the worker move
     *
     * @return true if the player has this God, otherwise returns always false
     */
    boolean canBuildBeforeWorkerMove();
}

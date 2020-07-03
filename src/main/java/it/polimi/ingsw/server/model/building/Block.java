package it.polimi.ingsw.server.model.building;

import java.io.Serializable;

/**
 * This interface represents the general Block that composes a building
 */
public interface Block extends Serializable {

    /**
     * @return this method print out the name of the block you have selected
     */
    String toString();

    /**
     * @return the int of the identifier of the block
     */
    int getBlockIdentifier();
}

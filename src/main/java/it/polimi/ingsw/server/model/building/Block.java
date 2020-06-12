package it.polimi.ingsw.server.model.building;

import java.io.Serializable;

/**
 * This interface represents the general Block that composes a building
 */
public interface Block extends Serializable {
    // --Commented out by Inspe// --Commented out by Inspection (17/04/2020 09:38):ction (17/04/2020 09:38):int blockIdentifier=0;
    /**
     * This attribute is the name of the block
     */
    String blockName = null;

    /**
     * @return this method print out the name of the block you have selected
     */
    String toString();

    /**
     * @return the int of the identifier of the block
     */
    int getBlockIdentifier();
}

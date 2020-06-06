package it.polimi.ingsw.server.model.building;

import java.io.Serializable;

public interface Block extends Serializable {
    // --Commented out by Inspe// --Commented out by Inspection (17/04/2020 09:38):ction (17/04/2020 09:38):int blockIdentifier=0;
    String blockName = null;
    String toString();
    int getBlockIdentifier();
}

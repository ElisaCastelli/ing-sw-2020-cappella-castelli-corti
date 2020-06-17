package it.polimi.ingsw.server.model.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvisibleBlockTest {
    final InvisibleBlock invisibleBlock=new InvisibleBlock();
    @Test
    void getBlockName() {
        assertEquals("I'm Invisible",invisibleBlock.getBlockName());
    }
    @Test
    void getBlockIdentifier(){
        assertEquals(-1, invisibleBlock.getBlockIdentifier());
    }


}
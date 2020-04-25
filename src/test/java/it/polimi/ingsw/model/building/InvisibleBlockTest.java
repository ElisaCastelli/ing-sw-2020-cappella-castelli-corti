package it.polimi.ingsw.model.building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InvisibleBlockTest {
    final InvisibleBlock invisibleBlock=new InvisibleBlock();
    @Test
    void getBlockName() {
        assertEquals("I'm invisible",invisibleBlock.getBlockName());
    }

}
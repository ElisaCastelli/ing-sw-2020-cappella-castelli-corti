package it.polimi.ingsw.server.model.parse;

import it.polimi.ingsw.server.model.god.BasicGod;
import it.polimi.ingsw.server.model.god.God;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CardCreatorTest {
    CardCreator cardCreator;

    @BeforeEach
    public void init() {
        cardCreator = new CardCreator();
    }


    @Test
    void parseCard() {
        ArrayList<God> cards;
        cards = cardCreator.parseCard();
        assertEquals(14, cards.size());
    }

    @Test
    void readCard() {
    }

    @Test
    void setHashMap() {
    }

    @Test
    void setGodsByString() {
        ArrayList<God> cardsString = new ArrayList<>();
        cardsString = cardCreator.setGodsByString();
        assertEquals(14, cardsString.size());
    }

    @Test
    void setGodsByHashMap() {
    }
}
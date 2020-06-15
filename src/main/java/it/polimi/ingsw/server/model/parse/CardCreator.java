package it.polimi.ingsw.server.model.parse;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.server.model.god.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * class to initialize the deck of cards for a game
 */
public class CardCreator {

    private ArrayList<God> cardsGod = new ArrayList<>();
    private ArrayList<BasicGod> godsByJson = new ArrayList<>();
    private Map<String, God> map = new HashMap<>();
    public ArrayList<God> godArrayListToHash = new ArrayList<>();

    /**
     * empty constructor
     */
    public CardCreator() {
        readCard();
        for (int i = 0; i < godsByJson.size(); i++) {
            godArrayListToHash.add(new BasicGod());
        }
    }

    /**
     * @return array of decorated god parsed by file
     */
    public ArrayList<God> parseCard() {
        setGodsByHashMap();
        return cardsGod;
    }

    /**
     * method used to read cards from file and to parse them into a array of gods
     */
    public void readCard() {

        ObjectMapper mapper = new ObjectMapper();
        JsonParser parser;
        try {
            parser = mapper.getFactory().createParser(new File("src/main/java/it/polimi/ingsw/server/model/parse/gods.json"));
            if (parser.nextToken() != JsonToken.START_ARRAY) {
                throw new IllegalStateException("Expected an array");
            }
            while (parser.nextToken() == JsonToken.START_OBJECT) {
                BasicGod godRead = null;
                try {
                    godRead = mapper.readValue(parser, BasicGod.class);
                } catch (IOException e) {
                    System.out.println("Exception while reading from file");
                    e.printStackTrace();
                }
                godsByJson.add(godRead);
            }
            try {
                parser.close();
            } catch (IOException e) {
                System.out.println("Unexpected file's closure");
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * method to initialize the hashMap
     *
     * @param god God interface
     */
    public void setHashMap(God god) {

        map.put("NotMoveUp", new NotMoveUp(god));
        map.put("MoveWorkerTwice", new MoveWorkerTwice(god));
        map.put("OpponentBlock", new OpponentBlock(god));
        map.put("BuildDome", new BuildDome(god));
        map.put("BuildBeforeWorkerMove", new BuildBeforeWorkerMove(god));
        map.put("OtherPositionToBuild", new OtherPositionToBuild(god));
        map.put("BuildInTheSamePosition", new BuildInTheSamePosition(god));
        map.put("ShiftWorker", new ShiftWorker(god));
        map.put("SwitchWorker", new SwitchWorker(god));
        map.put("BuildABlockUnderItself", new BuildABlockUnderItself(god));
        map.put("BuildNotAlongThePerimeter", new BuildNotAlongThePerimeter(god));
        map.put("MoveInfinityTimesAlongThePerimeter", new MoveInfinityTimesAlongThePerimeter(god));
        map.put("CompleteTowersWin", new CompleteTowersWin(god));
        map.put("FerryAnOpponentWorker", new FerryAnOpponentWorker(god));
        map.put("DownTwoOrMoreLevelsWin", new DownTwoOrMoreLevelsWin(god));
    }

    /**
     * alternative method to read from Json file and to generate decorated god's using strings
     * Not used because the hashMap method was more solid
     */
    public ArrayList<God> setGodsByString() {

        BasicGod godJson;
        for (BasicGod basicGod : godsByJson) {

            God g;
            godJson = basicGod;
            g = godJson;

            for (int indexEffects = 0; indexEffects < godJson.getEffects().size(); indexEffects++) {

                if ("BuildBeforeWorkerMove".equals((godJson.getEffects().get(indexEffects)))) {
                    g = new BuildBeforeWorkerMove(g);
                } else if ("NotMoveUp".equals(godJson.getEffects().get(indexEffects))) {
                    g = new NotMoveUp(g);
                } else if ("MoveWorkerTwice".equals(godJson.getEffects().get(indexEffects))) {
                    g = new MoveWorkerTwice(g);
                } else if ("OpponentBlock".equals(godJson.getEffects().get(indexEffects))) {
                    g = new OpponentBlock(g);
                } else if ("BuildDome".equals(godJson.getEffects().get(indexEffects))) {
                    g = new BuildDome(g);
                } else if ("OtherPositionToBuild".equals(godJson.getEffects().get(indexEffects))) {
                    g = new OtherPositionToBuild(g);
                } else if ("BuildInTheSamePosition".equals(godJson.getEffects().get(indexEffects))) {
                    g = new BuildInTheSamePosition(g);
                } else if ("ShiftWorker".equals(godJson.getEffects().get(indexEffects))) {
                    g = new ShiftWorker(g);
                } else if ("SwitchWorker".equals(godJson.getEffects().get(indexEffects))) {
                    g = new SwitchWorker(g);
                } else if ("DownTwoOrMoreLevelsWin".equals(godJson.getEffects().get(indexEffects))) {
                    g = new DownTwoOrMoreLevelsWin(g);
                }

            }
            cardsGod.add(g);
        }
        return cardsGod;
    }

    /**
     * method to generate decorated god's using hashMap
     */
    public void setGodsByHashMap() {

        for (int index = 0; index < godsByJson.size(); index++) {

            God temp = godArrayListToHash.get(index);


            for (int indexEffects = 0; indexEffects < godsByJson.get(index).getEffects().size(); indexEffects++) {
                String effect = godsByJson.get(index).getEffects().get(indexEffects);
                setHashMap(temp);
                temp = map.get(effect);
            }
            temp.setName(godsByJson.get(index).getName());
            temp.setEffect(godsByJson.get(index).getEffects());
            cardsGod.add(temp);
        }
    }
}


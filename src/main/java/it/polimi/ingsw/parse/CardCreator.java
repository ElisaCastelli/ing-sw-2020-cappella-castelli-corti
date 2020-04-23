package it.polimi.ingsw.parse;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.god.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CardCreator {

    private ArrayList<God> cardsGod= new ArrayList<>();
    private  ArrayList<BasicGod> godsByJson = new ArrayList<>();
    private Map <String,God> map = new HashMap<>();
    public ArrayList<God> godArrayListToHash = new ArrayList<>();

    public CardCreator() throws Exception {
        readCard();
        for ( int i =0; i < godsByJson.size(); i++ ){
            godArrayListToHash.add(new BasicGod());
        }
    }

    public ArrayList<God> parseCard() {
        //setGodsByString();
        setGodsByHashMap();
        return cardsGod;
    }

    public void readCard() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        //legge tutto l'array di gods
        /*try{
            g = objectMapper.readValue(new File("gods.json"), new TypeReference<ArrayList<BasicGod>>(){});
        }catch (IOException e){

        }*/
        ObjectMapper mapper = new ObjectMapper();
        JsonParser parser = mapper.getFactory().createParser(new File("gods.json"));
        if(parser.nextToken() != JsonToken.START_ARRAY) {
            throw new IllegalStateException("Expected an array");
        }
        while(parser.nextToken() == JsonToken.START_OBJECT) {
            // read everything from this START_OBJECT to the matching END_OBJECT
            // and return it as a tree model ObjectNode

            //JsonNode node = mapper.readTree(parser);
            BasicGod godRead = mapper.readValue(parser, BasicGod.class);
            godsByJson.add(godRead);
        }

        parser.close();

    }

    public void setHashMap(God god){

        map.put("MoveBeforeBuild", new MoveBeforeBuild(god));
        map.put("NotMoveUp",new NotMoveUp(god));
        map.put("MoveWorkerTwice",new MoveWorkerTwice(god));
        map.put("OpponentBlock",new OpponentBlock(god));
        map.put("BuildDome",new BuildDome(god));
        map.put("BuildBeforeWorkerMove",new BuildBeforeWorkerMove(god));
        map.put("OtherPositionToBuild",new OtherPositionToBuild(god));
        map.put("BuildInTheSamePosition",new BuildInTheSamePosition(god));
        map.put("ShiftWorker",new ShiftWorker(god));
        map.put("SwitchWorker",new SwitchWorker(god));
        map.put("BuildABlockUnderItself",new BuildABlockUnderItself(god));
        map.put("BuildNotAlongThePerimeter",new BuildNotAlongThePerimeter(god));
        map.put("MoveInfinityTimesAlongThePerimeter",new MoveInfinityTimesAlongThePerimeter(god));
    }

    public void setGodsByString(){

        BasicGod godJson;
        for(int index=0; index < godsByJson.size(); index++){

            God g;
            godJson=godsByJson.get(index);
            g=godJson;

            for(int indexEffects=0; indexEffects<godJson.getEffects().size(); indexEffects++){

                if("MoveBeforeBuild".equals(godJson.getEffects().get(indexEffects))){
                    g = new MoveBeforeBuild(g);
                }
                else if("NotMoveUp".equals(godJson.getEffects().get(indexEffects))){
                    g = new NotMoveUp(g);
                }
                else if("MoveWorkerTwice".equals(godJson.getEffects().get(indexEffects))){
                    g = new MoveWorkerTwice(g);
                }
                else if("OpponentBlock".equals(godJson.getEffects().get(indexEffects))){
                    g = new OpponentBlock(g);
                }
                else if("BuildDome".equals(godJson.getEffects().get(indexEffects))){
                    g = new BuildDome(g);
                }
                else if("OtherPositionToBuild".equals(godJson.getEffects().get(indexEffects))){
                    g = new OtherPositionToBuild(g);
                }
                else if("BuildInTheSamePosition".equals(godJson.getEffects().get(indexEffects))){
                    g = new BuildInTheSamePosition(g);
                }
                else if("ShiftWorker".equals(godJson.getEffects().get(indexEffects))){
                    g = new ShiftWorker(g);
                }
                else if("SwitchWorker".equals(godJson.getEffects().get(indexEffects))){
                    g = new SwitchWorker(g);
                }
                else if("DownTwoOrMoreLevelsWin".equals(godJson.getEffects().get(indexEffects))){
                    g = new DownTwoOrMoreLevelsWin(g);
                }

            }
            cardsGod.add( g);
        }
    }

    public void setGodsByHashMap() {

        for (int index = 0; index < godsByJson.size(); index++) {

            God temp= godArrayListToHash.get(index);

            for (int indexEffects = 0; indexEffects < godsByJson.get(index).getEffects().size(); indexEffects++) {
                String effect = godsByJson.get(index).getEffects().get(indexEffects);
                setHashMap(temp);
                temp=map.get(effect);
            }
            cardsGod.add(temp);
        }
    }


    public static void main (String[] args) throws Exception {

        CardCreator cardCreator=new CardCreator();
        cardCreator.parseCard();

    }


}


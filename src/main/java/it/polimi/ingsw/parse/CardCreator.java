package it.polimi.ingsw.parse;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.god.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class CardCreator {
    private ArrayList<God> cardsGod= new ArrayList<God>();
    private  ArrayList<BasicGod> godsByJson = new ArrayList<BasicGod>();
    private HashMap <String,Object> map = new HashMap<>();

    public CardCreator(){
        setHashMap();
    }
    public ArrayList<God> parseCard() throws Exception {
        readCard();
        setGodsByString();
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
            // do whatever you need to do with this object
        }

        parser.close();

    }

    public void setHashMap(){
        BasicGod g = new BasicGod();
        map.put("MoveBeforeBuild", new MoveBeforeBuild(g));
        map.put("NotMoveUp",new NotMoveUp(g));
        map.put("MoveWorkerTwice",new MoveWorkerTwice(g));
        map.put("OpponentBlock",new OpponentBlock(g));
        map.put("BuildDome",new BuildDome(g));
        map.put("OtherPositionToBuild",new OtherPositionToBuild(g));
        map.put("BuildInTheSamePosition",new BuildInTheSamePosition(g));
        map.put("ShiftWorker",new ShiftWorker(g));
        map.put("SwitchWorker",new SwitchWorker(g));
        map.put("DownTwoOrMoreLevelsWin",new DownTwoOrMoreLevelsWin(g));
    }

    public void setGodsByString(){

        BasicGod godJson;
        for(int index=0; index < godsByJson.size(); index++){

            God g = new BasicGod();
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
        BasicGod g;
        for (int index = 0; index < godsByJson.size(); index++) {
            g = godsByJson.get(index);
            for (int indexEffects = 0; indexEffects < g.getEffects().size(); indexEffects++) {
                String effect = g.getEffects().get(indexEffects);
                //g=map.get(effect);
            }
        }
    }


}


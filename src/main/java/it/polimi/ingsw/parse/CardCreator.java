package it.polimi.ingsw.parse;



import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.god.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardCreator {
    ArrayList<BasicGod> godsArray= new ArrayList<BasicGod>();

    public ArrayList<BasicGod> parseCard() throws Exception {
        readCard();
        BasicGod g;
        for(int index=0; index < godsArray.size(); index++){
            g=godsArray.get(index);
            for(int indexEffects=0; indexEffects<g.getEffects().size(); indexEffects++){
                if("MoveBeforeBuild".equals(g.getEffects().get(indexEffects))){
                    new MoveBeforeBuild(g);
                }
                else if("NotMoveUp".equals(g.getEffects().get(indexEffects))){
                    new NotMoveUp(g);
                }
                else if("MoveWorkerTwice".equals(g.getEffects().get(indexEffects))){
                    new MoveWorkerTwice(g);
                }
                else if("OpponentBlock".equals(g.getEffects().get(indexEffects))){
                    new OpponentBlock(g);
                }
                else if("BuildDome".equals(g.getEffects().get(indexEffects))){
                    new BuildDome(g);
                }
                else if("OtherPositionToBuild".equals(g.getEffects().get(indexEffects))){
                    new OtherPositionToBuild(g);
                }
                else if("BuildInTheSamePosition".equals(g.getEffects().get(indexEffects))){
                    new BuildInTheSamePosition(g);
                }
                else if("ShiftWorker".equals(g.getEffects().get(indexEffects))){
                    new ShiftWorker(g);
                }
                else if("SwitchWorker".equals(g.getEffects().get(indexEffects))){
                    new SwitchWorker(g);
                }
                else if("DownTwoOrMoreLevelsWin".equals(g.getEffects().get(indexEffects))){

                }
            }
        }
        return godsArray;
    }

    public void readCard() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<BasicGod> g= new ArrayList<>();
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

            // do whatever you need to do with this object
        }

        parser.close();
        godsArray=g;
    }

}


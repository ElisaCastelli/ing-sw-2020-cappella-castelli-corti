package it.polimi.ingsw.parse;

import it.polimi.ingsw.god.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;

public class ParserXML {
    private ArrayList<God> godsArray = new ArrayList<>();

    public void parseBase(){
        try {
            File inputFile = new File("./god.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("god");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                God g= new BasicGod();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    g.setGodName(eElement
                            .getElementsByTagName("name")
                            .item(0)
                            .getTextContent());
                    g.setDescription(eElement
                            .getElementsByTagName("description")
                            .item(0)
                            .getTextContent());
                    g.setEffect(eElement
                            .getElementsByTagName("effect")
                            .item(0)
                            .getTextContent());

                    godsArray.add(temp,g);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setEffects(){

    }
    public God getGod(int index){
        return godsArray.get(index);
    }
    public ArrayList<God> getGodsArray(){
        return godsArray;
    }
}

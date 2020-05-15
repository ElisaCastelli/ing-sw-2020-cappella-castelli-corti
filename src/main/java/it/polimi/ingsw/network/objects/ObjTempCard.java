package it.polimi.ingsw.network.objects;
import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import java.util.ArrayList;


public class ObjTempCard extends ObjMessage{

    private static final long serialVersionUID = -3829471843029385138L;

    private ArrayList<Integer> cardsTemp;
    public ObjTempCard(ArrayList<Integer> cardsTemp){
        this.cardsTemp=cardsTemp;
    }
    public ArrayList<Integer> getCardsTemp() {
        return cardsTemp;
    }
    public void setCardsTemp(ArrayList<Integer> cardsTemp) {
        this.cardsTemp = cardsTemp;
    }
    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        visitorServer.visit(this);
    }
    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}
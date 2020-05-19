package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.gameComponents.Box;

public class ObjWorkers extends ObjMessage{

    private static final long serialVersionUID = -4928461924327192103L;

    private Box box1;
    private Box box2;

    public ObjWorkers(Box box1, Box box2){
        this.box1=box1;
        this.box2=box2;
    }

    public Box getBox1() {
        return box1;
    }

    public void setBox1(Box box1) {
        this.box1 = box1;
    }

    public Box getBox2() {
        return box2;
    }

    public void setBox2(Box box2) {
        this.box2 = box2;
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

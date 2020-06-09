package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.server.model.gameComponents.Box;

/**
 * message workers
 */
public class ObjWorkers extends ObjMessage {

    private static final long serialVersionUID = -4928461924327192103L;

    private final Box box1;
    private final Box box2;

    /**
     * constructor of the class
     *
     * @param box1 box first worker
     * @param box2 box second worker
     */
    public ObjWorkers(Box box1, Box box2) {
        this.box1 = box1;
        this.box2 = box2;
    }

    public Box getBox1() {
        return box1;
    }

    public Box getBox2() {
        return box2;
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorServer the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitorServer) {
        visitorServer.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorClient the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorClient visitorClient) {
        throw new UnsupportedOperationException();
    }
}

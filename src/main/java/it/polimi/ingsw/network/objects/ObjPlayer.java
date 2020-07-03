package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorMessageFromServer;
import it.polimi.ingsw.network.VisitorMessageFromClient;

/**
 * message player's information
 */
public class ObjPlayer extends ObjMessage {
    /**
     * name of the player
     */
    private String name;
    /**
     * age of the player
     */
    private int age;

    /**
     * Constructor of the class
     *
     * @param name name of the player
     * @param age  age of the player
     */
    public ObjPlayer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Name getter
     *
     * @return name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * Age getter
     *
     * @return age of the player
     */

    public int getAge() {
        return age;
    }


    /**
     * accept method of the visitor pattern
     *
     * @param visitor the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorMessageFromClient visitor) {
        visitor.visit(this);
    }

    /**
     * accept method of the visitor pattern
     *
     * @param visitorMessageFromServer the class of the visitor pattern client's side
     */
    @Override
    public void accept(VisitorMessageFromServer visitorMessageFromServer) {
        throw new UnsupportedOperationException();
    }
}

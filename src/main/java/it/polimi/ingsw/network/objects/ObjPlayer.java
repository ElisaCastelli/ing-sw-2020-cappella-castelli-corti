package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

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
     * Name setter
     *
     * @param name name of the player
     */

    public void setName(String name) {
        this.name = name;
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
     * Age setter
     *
     * @param age age of the player
     */

    public void setAge(int age) {
        this.age = age;
    }


    /**
     * accept method of the visitor pattern
     *
     * @param visitor the class of the visitor pattern server's side
     */
    @Override
    public void accept(VisitorServer visitor) {
        visitor.visit(this);
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

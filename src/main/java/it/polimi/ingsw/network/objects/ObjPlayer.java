package it.polimi.ingsw.network.objects;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;

/**
 * message player's information
 */
public class ObjPlayer extends ObjMessage {

    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public ObjPlayer(String name, int age) {
        this.name = name;
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

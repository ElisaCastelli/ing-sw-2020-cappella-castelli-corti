package it.polimi.ingsw.network;

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

    public ObjPlayer(){
        super();
    }
    public ObjPlayer(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String accept(Visitor visitor) {
        return visitor.visitPlayer(this);
    }
}

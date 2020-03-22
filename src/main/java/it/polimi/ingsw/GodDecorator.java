package it.polimi.ingsw;
//decorator that implements the different Gods abilities
public abstract class GodDecorator implements God {
    private God newGod;
    public GodDecorator(God newGod) {
        this.newGod=newGod;
    }

   /* @Override
    public void moveWorker(Worker worker, Box pos) {
        newGod.moveWorker(worker, pos);
    }

    @Override
    public void moveBlock(Block block, Box pos) {
        newGod.moveBlock(block, pos);
    }*/
}

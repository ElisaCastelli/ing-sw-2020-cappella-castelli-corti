package it.polimi.ingsw;
//Gods concrete class with the basic moves of the workers and of the build
public class Gods implements God {
    private String godName;

    public Gods (String godName) {
        this.godName=godName;
    }

    public String getName() {return godName;}
    public void setName(String name) {this.godName=name;}

    @Override
    public void moveWorker (Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();
        int heightWorker=worker.getHeight();
        int counterPos=pos.getCounter();

        if (boxWorker.reachable(pos) && pos.notWorker()) {
            if (counterPos-heightWorker==1) {
                worker.setHeight(heightWorker++);
                worker.setActualBox(pos);
            }
            else if (heightWorker-counterPos==1) {
                worker.setHeight(heightWorker--);
                worker.setActualBox(pos);
            }
            else if (heightWorker==counterPos) {
                worker.setActualBox(pos);
            }
            //se si prova un caso in cui non entra in nessuna delle tre condizioni significa che la mossa non è valida
            //in quanto sale o scende di troppi livelli
            //checkWin();
        }
    }

    @Override
    public void moveBlock(Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();

        if (boxWorker.reachable(pos) && pos.notWorker()) {
            pos.build();
        }
    }
}

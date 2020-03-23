package it.polimi.ingsw.god;

import it.polimi.ingsw.Box;
import it.polimi.ingsw.Worker;

//Gods concrete class with the basic moves of the workers and of the build
public class Gods implements God {
    private String godName;
    private int isCorrectWorkerMove;
    private boolean isCorrectBlockMove;

    public Gods (String godName, int isCorrectWorkerMove, boolean isCorrectBlockMove) {
        this.godName=godName;
        this.isCorrectWorkerMove=isCorrectWorkerMove;
        this.isCorrectBlockMove=isCorrectBlockMove;
    }

    public String getName() {return godName;}
    public void setName(String name) {this.godName=name;}

    @Override
    public void moveWorker (Worker worker, Box pos) {
        Box boxWorker=worker.getActualBox();
        int heightWorker=worker.getHeight();
        int counterPos=pos.getCounter();
        isCorrectWorkerMove=0;

        if (boxWorker.reachable(pos)) {
            //first case: the worker moves up; second case: the worker moves down; third case: the worker stays at the same level
            if (counterPos-heightWorker==1) {
                if (pos.notWorker()) {
                    worker.setHeight(heightWorker++);
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=1;
            }
            else if (heightWorker-counterPos==1) {
                if(pos.notWorker()) {
                    worker.setHeight(heightWorker--);
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=2;
            }
            else if (heightWorker==counterPos) {
                if (pos.notWorker())
                {
                    worker.setActualBox(pos);
                }
                isCorrectWorkerMove=3;
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
            isCorrectBlockMove=true;
        }
    }
}

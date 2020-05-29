package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;

public class AskBuildBeforeMove extends ObjMessage {

    int indexWorker;
    int rowWorker;
    int columnWorker;

    public AskBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        this.indexWorker = indexWorker;
        this.rowWorker = rowWorker;
        this.columnWorker = columnWorker;
    }

    public int getIndexWorker() {
        return indexWorker;
    }

    public int getRowWorker() {
        return rowWorker;
    }

    public int getColumnWorker() {
        return columnWorker;
    }

    @Override
    public void accept(VisitorServer visitorServer) throws Exception {
        throw new UnsupportedOperationException();
    }

    @Override
    public void accept(VisitorClient visitorClient) {
        visitorClient.visit(this);
    }
}

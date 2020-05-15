package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.server.model.gameComponents.Board;

public class UpdateBoardEvent extends ObjMessage {
    Board board;
    public UpdateBoardEvent(Board board){
        this.board=board;
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

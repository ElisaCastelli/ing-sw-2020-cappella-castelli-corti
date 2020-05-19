package it.polimi.ingsw.network.events;

import it.polimi.ingsw.network.VisitorClient;
import it.polimi.ingsw.network.VisitorServer;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

public class UpdateBoardEvent extends ObjMessage {

    private static final long serialVersionUID = 1038573990901284957L;

    Board board;
    boolean showReachable;

    public UpdateBoardEvent(Board board, boolean showReachable){
        this.board = board;
        this.showReachable = showReachable;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isShowReachable() {
        return showReachable;
    }

    public void setShowReachable(boolean showReachable) {
        this.showReachable = showReachable;
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

package it.polimi.ingsw.network;

import it.polimi.ingsw.network.events.CloseConnectionClientEvent;
import it.polimi.ingsw.network.objects.ObjCard;
import it.polimi.ingsw.network.objects.ObjPlayer;
import it.polimi.ingsw.server.ServerHandler;

public class VisitorServer {

    private ServerHandler serverHandler;

    public VisitorServer(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void visit(ObjCard objCard) throws Exception {
        serverHandler.getVirtualView().setCard(objCard.getPlayer(), objCard.getCardChose());
    }

    public void visit(ObjPlayer objPlayer){
        serverHandler.getVirtualView().addPlayer(objPlayer.getName(), objPlayer.getAge());
    }

    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }
}

package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.events.CloseConnectionClientEvent;
import it.polimi.ingsw.network.objects.ObjCard;
import it.polimi.ingsw.network.objects.ObjPlayer;

public class VisitorClient {
    private final ClientHandler clientHandler;

    public VisitorClient(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
    }

    public void visit(ObjCard objCard) throws Exception {
        clientHandler.getView();
    }

    public void visit(ObjPlayer objPlayer){
        clientHandler.getView();
    }

}

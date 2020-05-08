package it.polimi.ingsw.network;

import it.polimi.ingsw.network.events.CloseConnectionClientEvent;
import it.polimi.ingsw.network.objects.ObjAck;
import it.polimi.ingsw.network.objects.ObjCard;
import it.polimi.ingsw.network.objects.ObjNumPlayer;
import it.polimi.ingsw.network.objects.ObjPlayer;
import it.polimi.ingsw.server.ServerHandler;

public class VisitorServer {

    private ServerHandler serverHandler;

    public VisitorServer(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
    }

    public void visit(ObjNumPlayer objNumPlayer){
        serverHandler.getVirtualView().setNPlayers(objNumPlayer.getnPlayer());
        int nPlayer= serverHandler.getVirtualView().updateNPlayer();
        ObjNumPlayer numPlayer = new ObjNumPlayer(nPlayer);
        serverHandler.sendUpdate(numPlayer);
    }

    public void visit(ObjPlayer objPlayer){
        serverHandler.getVirtualView().addPlayer(objPlayer.getName(), objPlayer.getAge());
        serverHandler.sendUpdate(new ObjAck());
    }

    public void visit(ObjCard objCard) throws Exception {
        serverHandler.getVirtualView().setCard(objCard.getPlayer(), objCard.getCardChose());
        serverHandler.sendUpdate(new ObjAck());
    }


    public void visit(ObjAck objAck){
        //ho ricevuto l'ack posso mandarlo alla virtual view
    }
    public void visit(CloseConnectionClientEvent closeConnectionClientEvent){
        serverHandler.close();
    }
}

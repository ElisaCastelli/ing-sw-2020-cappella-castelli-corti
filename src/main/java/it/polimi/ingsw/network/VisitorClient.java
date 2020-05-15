package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.ack.AckPlayer;
import it.polimi.ingsw.network.ack.AckStartGame;
import it.polimi.ingsw.network.ack.AckState;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;

import java.util.ArrayList;

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

    public void visit(AckStartGame ackStartGame){

    }
    public void visit(AskNPlayerEvent askNPlayerEvent){
        int nPlayer=clientHandler.getView().askNPlayer();
        ObjNumPlayer objNumPlayer= new ObjNumPlayer(nPlayer);
        clientHandler.sendMessage(objNumPlayer);
    }

    public void visit(AskPlayerEvent askPlayerEvent){
        String name= clientHandler.getView().askName();
        int age=clientHandler.getView().askAge();
        ObjPlayer objPlayer= new ObjPlayer(name,age);
        clientHandler.sendMessage(objPlayer);
    }

    public void visit(StartGameEvent start){
        System.out.println("Inizia");
        clientHandler.getView().setNPlayer(start.getnPlayer());
        AckStartGame ack= new AckStartGame();
        clientHandler.sendMessage(ack);
    }

    public void visit(ObjState objState){
        //se è la prima volta entra e ti setti il valore
        if(clientHandler.getView().getIndexPlayer() == -1){
            clientHandler.getView().setIndexPlayer(objState.getIndexPlayer());
            System.out.println("sto settando l'indice del player quando inizia il gioco: "+clientHandler.getView().getIndexPlayer());
            //qua arrivavano tutti e tre perchè si dovevano settare il valore iniziale
            //se sei anche il primo che deve giocare
            if(clientHandler.getView().getIndexPlayer()==objState.getCurrentPlayer()) {
                clientHandler.getView().setPlaying(true);
                System.out.println("Devo giocare");
                //invia un ackplayer(ne dovrà arrivare solo uno)
                clientHandler.sendMessage(new AckPlayer());
            }else{
                clientHandler.getView().setPlaying(false);
                System.out.println("Devo aspettare il mio turno");
                //altrimenti mandi solo un ack(ne dovranno arrivare 2/3)
                clientHandler.sendMessage(new AckState());
            }
        }else {//se non è la prima volta che ti arriva uno state
            if (clientHandler.getView().getIndexPlayer() == objState.getCurrentPlayer()) {
                clientHandler.getView().setPlaying(true);
                System.out.println("Sto giocando");
                clientHandler.sendMessage(new AckState());
            } else {
                clientHandler.getView().setPlaying(false);
                System.out.println("Aspetta il tuo turno oh");
                clientHandler.sendMessage(new AckState());
            }
        }
    }

    public void visit(Ask3CardsEvent ask3CardsEvent) {
        if(clientHandler.getView().isPlaying()) {
            ArrayList<Integer> cardTemp = clientHandler.getView().ask3Card(ask3CardsEvent.getCardArray());
            System.out.println("Carte scelte");
            ObjTempCard objCard = new ObjTempCard(cardTemp);
            clientHandler.sendMessage(objCard);
        }
        else{
            System.out.println("Stanno scegliendo le tre carte");
            //AGGIUNGO QUESTA RIGA COME RITORNO DI STATO CAMBIATO
            clientHandler.sendMessage(new AckState());
        }
    }
    public void visit(AskCard askCard) {
        if (clientHandler.getView().isPlaying()) {
            int card = clientHandler.getView().askCard(askCard.getCardTemp());
            ObjCard objCard = new ObjCard(card);
            System.out.println("invio carta scelta al server");
            clientHandler.sendMessage(objCard);
        } else {
            System.out.println("Un avversario sta scegliendo una carta");
            //AGGIUNGO QUESTA RIGA COME RITORNO DI STATO CAMBIATO
            clientHandler.sendMessage(new AckState());
        }
    }

    public void visit(ObjInitialize objInitialize){
        clientHandler.getView().setBoard(objInitialize.getBoard());
        clientHandler.getView().setUsers(objInitialize.getUserArray());
    }
}

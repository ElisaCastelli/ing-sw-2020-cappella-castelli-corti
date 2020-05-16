package it.polimi.ingsw.network;

import it.polimi.ingsw.client.ClientHandler;
import it.polimi.ingsw.network.ack.*;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;

public class VisitorClient {
    private final ClientHandler clientHandler;



    public VisitorClient(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
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

    public void visit(AskInitializeWorker askInitializeWorker){
        ArrayList<Box> boxes=clientHandler.getView().initializeWorker();
        ObjWorkers objWorkers= new ObjWorkers(boxes.get(0),boxes.get(1));
        clientHandler.sendMessage(objWorkers);
    }

    public void visit (UpdateBoardEvent updateBoardEvent){
        if(!clientHandler.getView().isPlaying()){
            System.out.println("Someone is changing the Board: ");
            clientHandler.getView().printBoard(updateBoardEvent.isShowReachable());
            clientHandler.sendMessage(new AckUpdateBoard());
        }else{
            System.out.println("You recived an update of the Board: ");
            clientHandler.getView().printBoard(updateBoardEvent.isShowReachable());
        }

    }
    public void visit(ObjInitialize objInitialize){
        clientHandler.getView().setBoard(objInitialize.getBoard());
        //clientHandler.getView().setUsers(objInitialize.getUserArray());
    }



    public void visit(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        if (clientHandler.getView().isPlaying()) {
            if(askWorkerToMoveEvent.isFirstAsk()){
                ObjWokerToMove objWokerToMove= clientHandler.getView().askWorker(askWorkerToMoveEvent);
                clientHandler.sendMessage(objWokerToMove);
            }else{
                ObjWokerToMove objWokerToMove= clientHandler.getView().AreYouSure(askWorkerToMoveEvent);
                clientHandler.sendMessage(objWokerToMove);
            }
        }else{
            System.out.println("Someone else is choosing the worker to move");
            //TODO si potrebbe cambiare con un nuovo ack che fa la stessa cosa
            clientHandler.sendMessage(new AckState());
        }
    }

    public void visit(AskMoveEvent askMoveEvent){
        if(clientHandler.getView().isPlaying()){
            //Questa è la prima ask
            if(askMoveEvent.isFirstTime()){
                ObjMove objMove= clientHandler.getView().moveWorker(askMoveEvent);
                clientHandler.sendMessage(objMove);
            ///se non è la prima volta significa che sei speciale e puoi fare un'altra mossa
            }else {
                ObjMove objMove= clientHandler.getView().anotherMove(askMoveEvent);
                if(objMove.isDone()){
                    clientHandler.sendMessage(new AckMove());
                }else{
                    clientHandler.sendMessage(objMove);
                }
            }
        }else{
            System.out.println("Someone else is moving his Worker");
            clientHandler.sendMessage(new AckState());
        }

    }

}

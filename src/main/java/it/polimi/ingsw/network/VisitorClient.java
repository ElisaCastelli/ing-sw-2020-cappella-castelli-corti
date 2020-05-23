package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;


public class VisitorClient {

    public final View view;

    public VisitorClient(View view) {
        this.view=view;
    }

    public void visit(AskNPlayerEvent askNPlayerEvent){
        //todo da fare il controllo che arrivi solo ad uno solo dei client e non a tutti e tre
        view.askNPlayer();
    }

    public void visit(AskPlayerEvent askPlayerEvent){
        //lo devono fare tutti
        view.askPlayer();
    }

    public void visit(StartGameEvent start){
        //lo devono fare tutti
        view.setNPlayer(start.getnPlayer());
    }

    public void visit(ObjState objState){
        view.setWhoIsPlaying(objState.getCurrentPlayer());

        if(view.getIndexPlayer() == -1){
            view.setIndexPlayer(objState);
        }else {
            view.setPlaying(objState);
        }
    }

    public void visit(Ask3CardsEvent ask3CardsEvent) {

            view.ask3Card(ask3CardsEvent.getCardArray());

            //clientHandler.sendMessage(new AckState());

    }
    public void visit(AskCard askCard) {

            view.askCard(askCard.getCardTemp());

            //clientHandler.sendMessage(new AckState());

    }

    public void visit(AskInitializeWorker askInitializeWorker){

            view.initializeWorker();

            //clientHandler.sendMessage(new AckState());

    }


    public void visit (UpdateBoardEvent updateBoardEvent){
        view.updateBoard(updateBoardEvent);
    }

    public void visit(ObjInitialize objInitialize){
        view.initialize(objInitialize);
    }

    public void visit(AskWorkerToMoveEvent askWorkerToMoveEvent) {

            if(askWorkerToMoveEvent.isFirstAsk()){
                view.askWorker(askWorkerToMoveEvent);
            }else if(!askWorkerToMoveEvent.isFirstAsk()){
                view.areYouSure(askWorkerToMoveEvent);
            }
            //clientHandler.sendMessage(new AckState());

    }

    public void visit(AskMoveEvent askMoveEvent){

            //Questa è la prima ask
            if(askMoveEvent.isFirstTime()){
                view.moveWorker(askMoveEvent);
            ///se non è la prima volta significa che sei speciale e puoi fare un'altra mossa
            }else {
                view.anotherMove(askMoveEvent);
            }

            //clientHandler.sendMessage(new AckState());
    }

    public void visit(AskBuildEvent askBuildEvent){

            //Qui entra se è veramente la prima volta o se ha inserito una box non valida
            if(askBuildEvent.isFirstTime()){
                view.buildMove(askBuildEvent);
            }else{ //Può fare una mossa speciale
                view.anotherBuild(askBuildEvent);
            }

            //clientHandler.sendMessage(new AckState());

    }
}

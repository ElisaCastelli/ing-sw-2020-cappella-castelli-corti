package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;


public class VisitorClient {

    public final View view;

    public VisitorClient(View view) {
        this.view=view;
    }


    public void visit(AskWantToPlay askWantToPlay){
        view.askWantToPlay(askWantToPlay);
    }

    public void visit(AskNPlayerEvent askNPlayerEvent){
        view.askNPlayer();
    }

    public void visit (ObjWait objWait){
        System.out.println("devo aspettare");
    }

    public void visit (ObjYouCanPlay ObjYouCanPlay){
        System.out.println("puoi giocare");
    }

    public void visit(AskPlayerEvent askPlayerEvent){
        view.askPlayer(askPlayerEvent.getClientIndex());
    }

    public void visit(StartGameEvent start){
        view.setNPlayer(start.getnPlayer());
    }

    public void visit(ObjState objState){
        view.setIndexPlayer(objState);
        System.out.println("ho settato lo stato");
    }

    public void visit(Ask3CardsEvent ask3CardsEvent) {
        if(ask3CardsEvent.getClientIndex() == ask3CardsEvent.getCurrentPlayer())
            view.ask3Card(ask3CardsEvent.getCardArray());

        //clientHandler.sendMessage(new AckState());

    }
    public void visit(AskCard askCard) {
        if(askCard.getClientIndex() == askCard.getCurrentPlayer())
            view.askCard(askCard.getCardTemp());

        //clientHandler.sendMessage(new AckState());

    }

    public void visit(AskInitializeWorker askInitializeWorker){
        if(askInitializeWorker.getClientIndex() == askInitializeWorker.getCurrentPlayer())
            view.initializeWorker();

        //clientHandler.sendMessage(new AckState());

    }


    public void visit (UpdateBoardEvent updateBoardEvent){
        view.updateBoard(updateBoardEvent);
    }

    public void visit(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        if(askWorkerToMoveEvent.getClientIndex() == askWorkerToMoveEvent.getCurrentPlayer()) {
            if (askWorkerToMoveEvent.isFirstAsk()) {
                view.askWorker(askWorkerToMoveEvent);
            } else if (!askWorkerToMoveEvent.isFirstAsk()) {
                view.areYouSure(askWorkerToMoveEvent);
            }
            //clientHandler.sendMessage(new AckState());
        }
    }

    public void visit(AskMoveEvent askMoveEvent){
        if(askMoveEvent.getClientIndex() == askMoveEvent.getCurrentPlayer()) {
            //Questa è la prima ask
            if (askMoveEvent.isFirstTime()) {
                view.moveWorker(askMoveEvent);
                ///se non è la prima volta significa che sei speciale e puoi fare un'altra mossa
            } else {
                view.anotherMove(askMoveEvent);
            }
        }
        //clientHandler.sendMessage(new AckState());
    }

    public void visit(AskBuildEvent askBuildEvent){
        if(askBuildEvent.getClientIndex() == askBuildEvent.getCurrentPlayer()) {
            //Qui entra se è veramente la prima volta o se ha inserito una box non valida
            if (askBuildEvent.isFirstTime()) {
                view.buildMove(askBuildEvent);
            } else { //Può fare una mossa speciale
                view.anotherBuild(askBuildEvent);
            }

            //clientHandler.sendMessage(new AckState());
        }
    }

    public void visit(ObjHeartBeat objHeartBeat){
        view.printHeartBeat(objHeartBeat);
    }
}
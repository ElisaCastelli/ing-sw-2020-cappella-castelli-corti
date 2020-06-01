package it.polimi.ingsw.network;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.network.objects.ObjWait;
import it.polimi.ingsw.network.objects.ObjWorkerToMove;
import it.polimi.ingsw.server.EchoServer;

public class SendMessageToClient {
    EchoServer echoServer;

    public SendMessageToClient(EchoServer echoServer) {
        this.echoServer = echoServer;
    }

    public EchoServer getEchoServer() {
        return echoServer;
    }

    public void sendAskNPlayer(){
        echoServer.updateClientArray(0);
        echoServer.getClientArray().get(0).sendUpdate(new AskNPlayerEvent());
    }

    public void sendYouHaveToWait(int indexClient ) {
        echoServer.sendWaiting(new ObjWait(), indexClient);
    }

    public void YouCanPlay(int npLayer) {
        for (int indexClientWaiting = 1 ; indexClientWaiting < npLayer; indexClientWaiting++){
            echoServer.updateClientArray(indexClientWaiting);
        }
        echoServer.sendBroadCast(new AskPlayerEvent());
    }

    public void sendAskPlayer(int npLayer, boolean sendBroadcast) {
        if(sendBroadcast){
            for (int indexClientWaiting = 1 ; indexClientWaiting < npLayer; indexClientWaiting++){
                echoServer.updateClientArray(indexClientWaiting);
            }
            echoServer.sendBroadCast(new AskPlayerEvent());
        }else{
            echoServer.send(new ObjWait(),0);
        }
    }

    public void sendStartGameEvent(int nPlayers) {
        echoServer.sendBroadCast(new StartGameEvent(nPlayers));
    }

    public void sendObjState(int indexClient, ObjState objState) {
        echoServer.send(objState, indexClient);
    }

    public void sendCards(Ask3CardsEvent ask3CardsEvent) {
        echoServer.sendBroadCast(ask3CardsEvent);
    }

    public void sendAskCard(AskCard askCard) {
        echoServer.sendBroadCast(askCard);
    }

    public void sendUpdateBoard(UpdateBoardEvent gameData) {
        echoServer.sendBroadCast(gameData);
    }

    public void sendAskInitializeWorker(AskInitializeWorker askInitializeWorker) {
        echoServer.sendBroadCast(askInitializeWorker);
    }
    public void sendAskWorkerToMoveEvent(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        echoServer.sendBroadCast(askWorkerToMoveEvent);
    }

    public void sendAskMoveEvent(AskMoveEvent askMoveEvent) {
        echoServer.sendBroadCast(askMoveEvent);
    }

    public void sendAskBuildEvent(AskBuildEvent askBuildEvent) {
        echoServer.sendBroadCast(askBuildEvent);
    }

    public void sendWin(int indexClient) {
        WinnerEvent winnerEvent = new WinnerEvent();
        winnerEvent.setCurrentClientPlaying(indexClient);
        echoServer.sendBroadCast(winnerEvent);
    }

    public void sendLoser(int indexClient) {
        echoServer.send(new LoserEvent(), indexClient);
    }

    public void sendWhoHasLost(int loserClient) {
        WhoHasLostEvent whoHasLostEvent = new WhoHasLostEvent();
        whoHasLostEvent.setCurrentClientPlaying(loserClient);
        echoServer.sendBroadCast(whoHasLostEvent);
    }

    public void sendAskBuildBeforeMove(AskBuildBeforeMove askBuildBeforeMove) {
        echoServer.sendBroadCast(askBuildBeforeMove);
    }

    public void sendCloseConnection(int indexClient, boolean gameNotAvailable) {
        if(echoServer.getClientWaiting().size() > 0 ){
            echoServer.sendWaiting(new CloseConnectionFromServerEvent(gameNotAvailable), indexClient);
        }else {
            echoServer.send(new CloseConnectionFromServerEvent(gameNotAvailable), indexClient);
        }
    }

}

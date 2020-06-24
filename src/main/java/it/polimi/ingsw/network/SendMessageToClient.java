package it.polimi.ingsw.network;

import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.ObjState;
import it.polimi.ingsw.network.objects.ObjWait;
import it.polimi.ingsw.server.EchoServer;

/**
 * class to send messages to server
 */
public class SendMessageToClient {
    final EchoServer echoServer;

    /**
     * constructor for the class
     *
     * @param echoServer the class identifying
     */
    public SendMessageToClient(EchoServer echoServer) {
        this.echoServer = echoServer;
    }

    /**
     * Echo server getter
     *
     * @return the echoServer
     */

    public EchoServer getEchoServer() {
        return echoServer;
    }

    /**
     * send the request for the number of players
     *
     * @param anotherAsk is a boolean to identify an ask after a disconnection
     */

    public void sendAskNPlayer(boolean anotherAsk) {
        if (anotherAsk) {
            echoServer.getClientArray().remove(0);
        }
        echoServer.updateClientArray(0);
        echoServer.getClientArray().get(0).sendUpdate(new AskNPlayerEvent());
    }

    /**
     * send the notification to the client that he has to wait
     *
     * @param indexClient identify the index of the client
     */


    public void sendYouHaveToWait(int indexClient) {
        echoServer.sendWaiting(new ObjWait(), indexClient);
    }

    /**
     * send the notification to the client that he can play
     *
     * @param npLayer identify the number of players
     */

    public void YouCanPlay(int npLayer) {
        for (int indexClientWaiting = 1; indexClientWaiting < npLayer; indexClientWaiting++) {
            echoServer.updateClientArray(indexClientWaiting);
        }
        echoServer.sendBroadCast(new AskPlayerEvent());
    }

    /**
     * send the request for the info of a player
     *
     * @param npLayer       identify the number of players
     * @param sendBroadcast is a boolean to specify how many clients have to receive the message
     */

    public void sendAskPlayer(int npLayer, boolean sendBroadcast) {
        if (sendBroadcast) {
            for (int indexClientWaiting = 1; indexClientWaiting < npLayer; indexClientWaiting++) {
                echoServer.updateClientArray(indexClientWaiting);
            }
            echoServer.sendBroadCast(new AskPlayerEvent());
        } else {
            echoServer.send(new ObjWait(), 0);
        }
    }

    /**
     * send the request for the info of a player another time to a specific client
     *
     * @param indexClient identify the index of a client
     */

    public void sendAskPlayerAgain(int indexClient) {
        echoServer.send(new AskPlayerEvent(), indexClient);
    }

    /**
     * send the notification about the starting of a game
     *
     * @param nPlayers identify the number of players
     */

    public void sendStartGameEvent(int nPlayers) {
        echoServer.sendBroadCast(new StartGameEvent(nPlayers));
    }

    /**
     * send the information for the beginning of the game
     *
     * @param indexClient identify the index of a client
     * @param objState    an object that contains info for the client
     */

    public void sendObjState(int indexClient, ObjState objState) {
        echoServer.send(objState, indexClient);
    }

    /**
     * send the request for the cards of the game
     *
     * @param askNCardsEvent is an event to ask the cards for a game
     */

    public void sendCards(AskNCardsEvent askNCardsEvent) {
        echoServer.sendBroadCast(askNCardsEvent);
    }

    /**
     * send the request for a card of a player
     *
     * @param askCardEvent is an event to ask a card for a specific player
     */

    public void sendAskCard(AskCardEvent askCardEvent) {
        echoServer.sendBroadCast(askCardEvent);
    }

    /**
     * send the update of game's data with the board
     *
     * @param gameData is an event that contains all the info for a game
     */

    public void sendUpdateBoard(UpdateBoardEvent gameData) {
        echoServer.sendBroadCast(gameData);
    }

    /**
     * send the request for the initialization of the workers
     *
     * @param askInitializeWorkerEvent is a message to ask the user to initialize his Workers on the board
     */
    public void sendAskInitializeWorker(AskInitializeWorkerEvent askInitializeWorkerEvent) {
        echoServer.sendBroadCast(askInitializeWorkerEvent);
    }

    /**
     * send the request to the client to chose the worker to move
     *
     * @param askWorkerToMoveEvent is a message to ask the user what worker he want to move
     */

    public void sendAskWorkerToMoveEvent(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        echoServer.sendBroadCast(askWorkerToMoveEvent);
    }

    /**
     * send the request to the client to build before making a move
     *
     * @param askBuildBeforeMoveEvent is a message to ask the user if he want to move his worker before building a block
     */

    public void sendAskBuildBeforeMove(AskBuildBeforeMoveEvent askBuildBeforeMoveEvent) {
        echoServer.sendBroadCast(askBuildBeforeMoveEvent);
    }

    /**
     * send the request to the client to make a move
     *
     * @param askMoveEvent is a message to ask the user to move a worker on the board
     */

    public void sendAskMoveEvent(AskMoveEvent askMoveEvent) {
        echoServer.sendBroadCast(askMoveEvent);
    }


    /**
     * send the request to the client to build on the board
     *
     * @param askBuildEvent is a message to ask the user to build a block on the board
     */

    public void sendAskBuildEvent(AskBuildEvent askBuildEvent) {
        echoServer.sendBroadCast(askBuildEvent);
    }

    /**
     * send the notification of the win
     *
     * @param indexClient identify the index of a client
     */

    public void sendWin(int indexClient) {
        WinnerEvent winnerEvent = new WinnerEvent();
        winnerEvent.setCurrentClientPlaying(indexClient);
        echoServer.sendBroadCast(winnerEvent);
    }

    /**
     * send the notification to the winner client
     *
     * @param indexClient identify the index of a client
     */

    public void sendLoser(int indexClient) {
        echoServer.send(new LoserEvent(), indexClient);
    }

    /**
     * send the notification to the loser client
     *
     * @param loserClient identify the index of a client who lost
     */

    public void sendWhoHasLost(int loserClient) {
        WhoHasLostEvent whoHasLostEvent = new WhoHasLostEvent();
        whoHasLostEvent.setCurrentClientPlaying(loserClient);
        echoServer.sendBroadCast(whoHasLostEvent);
    }


    /**
     * send the notification of the connection's closing
     *
     * @param indexClient      identify the index of a client
     * @param gameNotAvailable is a boolean to identify the cause of the connection's closure
     */

    public void sendCloseConnection(int indexClient, boolean gameNotAvailable) {
        if (echoServer.getClientWaiting().size() > 0) {
            echoServer.sendWaiting(new CloseConnectionFromServerEvent(gameNotAvailable), indexClient);
        } else {
            echoServer.send(new CloseConnectionFromServerEvent(gameNotAvailable), indexClient);
        }
    }

    /**
     * send the notification of the connection's closing
     *
     * @param gameNotAvailable is a boolean to identify the cause of the connection's closure
     */

    public void sendCloseConnection(boolean gameNotAvailable) {
        echoServer.sendBroadCast(new CloseConnectionFromServerEvent(gameNotAvailable));
    }


}

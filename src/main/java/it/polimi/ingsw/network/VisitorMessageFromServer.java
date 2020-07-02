package it.polimi.ingsw.network;

import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;

/**
 * Visitor pattern client's side to invoke methods on the view
 */

public class VisitorMessageFromServer {
    /**
     * View client's side
     */
    public final View view;

    /**
     * class constructor
     *
     * @param view is the interface allocated for a client
     */
    public VisitorMessageFromServer(View view) {
        this.view = view;
    }

    /**
     * method to establish a connect with the server
     *
     * @param askWantToPlayEvent is a message to start the communication with the server
     */
    public void visit(AskWantToPlayEvent askWantToPlayEvent) {
        view.askWantToPlay(askWantToPlayEvent);
    }

    /**
     * method to ask the player the number of player in a game
     *
     * @param askNPlayerEvent is a message to ask the user for the number of player in a game
     */
    public void visit(AskNPlayerEvent askNPlayerEvent) {
        view.askNPlayer();
    }

    /**
     * method to ask the player to wait for anther game
     *
     * @param objWait is a message to notify the user that he has to wait for a game
     */
    public void visit(ObjWait objWait) {
        view.youHaveToWait();
    }

    /**
     * method to notify the player he can play in a game
     *
     * @param ObjYouCanPlay is a message to notify the user that he can join a game
     */

    public void visit(ObjYouCanPlay ObjYouCanPlay) {
        view.youCanPlay();
    }

    /**
     * method to ask the player for his name and his age
     *
     * @param askPlayerEvent is a message to ask for the name and tha age of the player
     */

    public void visit(AskPlayerEvent askPlayerEvent) {
        view.askPlayer(askPlayerEvent.getClientIndex(), askPlayerEvent.isFirstTime());
    }

    /**
     * method to notify that a game has started
     *
     * @param start is a message to notify the user that the game has started
     */

    public void visit(StartGameEvent start) {
        view.setNPlayer(start.getNPlayer());
    }

    /**
     * method to set some info as cache in the interface
     *
     * @param objState is a message to notify the user of the game state
     */

    public void visit(ObjState objState) {
        view.setIndexPlayer(objState.getIndexPlayer());
    }

    /**
     * method to ask the youngest player the cards of all the players in the game
     *
     * @param askNCardsEvent is a message to ask the player for the cards of all the players in the game
     */

    public void visit(AskNCardsEvent askNCardsEvent) {
        if (askNCardsEvent.getClientIndex() == askNCardsEvent.getCurrentClientPlaying())
            view.askNCard(askNCardsEvent.getCardArray());
        else
            view.isNotMyTurn();
    }

    /**
     * method to ask the player to select a card for the game
     *
     * @param askCardEvent is a message to ask the player to select a card for the game
     */

    public void visit(AskCardEvent askCardEvent) {
        if (askCardEvent.getClientIndex() == askCardEvent.getCurrentClientPlaying())
            view.askCard(askCardEvent.getCardTemp());
        else
            view.isNotMyTurn();
    }

    /**
     * method to ask the player to initialize his worker
     *
     * @param askInitializeWorkerEvent is a message to ask the player to initialize his Workers on the board
     */

    public void visit(AskInitializeWorkerEvent askInitializeWorkerEvent) {
        if (askInitializeWorkerEvent.getClientIndex() == askInitializeWorkerEvent.getCurrentClientPlaying()) {
            view.initializeWorker();
        }else{
            view.isNotMyTurn();
        }
    }

    /**
     * method to update the state of a game
     *
     * @param updateBoardEvent is a message to notify the player for the state of the board
     */

    public void visit(UpdateBoardEvent updateBoardEvent) {
        view.updateBoard(updateBoardEvent.getUserArray(),updateBoardEvent.getBoard(), updateBoardEvent.isShowReachable(), updateBoardEvent.getCurrentClientPlaying(), updateBoardEvent.getClientIndex(),false);
    }

    /**
     * method to ask the player which one of his worker he want to move
     *
     * @param askWorkerToMoveEvent is a message to ask the player what worker he want to move
     */

    public void visit(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        if (askWorkerToMoveEvent.getClientIndex() == askWorkerToMoveEvent.getCurrentClientPlaying()) {
            if (askWorkerToMoveEvent.isFirstAsk()) {
                if (askWorkerToMoveEvent.isCanMove()) {
                    view.askWorker(askWorkerToMoveEvent.getRow1(), askWorkerToMoveEvent.getColumn1(), askWorkerToMoveEvent.getRow2(), askWorkerToMoveEvent.getColumn2(), askWorkerToMoveEvent.getCurrentClientPlaying(), askWorkerToMoveEvent.getClientIndex(), true);
                } else {
                    view.otherWorkerToMove(askWorkerToMoveEvent.getRow1(), askWorkerToMoveEvent.getColumn1(), askWorkerToMoveEvent.getRow2(), askWorkerToMoveEvent.getColumn2(), askWorkerToMoveEvent.getIndexWorker(), askWorkerToMoveEvent.getCurrentClientPlaying(), askWorkerToMoveEvent.getClientIndex());
                }
            } else if (!askWorkerToMoveEvent.isFirstAsk()) {
                view.areYouSure(askWorkerToMoveEvent.getRow1(), askWorkerToMoveEvent.getColumn1(), askWorkerToMoveEvent.getRow2(), askWorkerToMoveEvent.getColumn2(), askWorkerToMoveEvent.getIndexWorker(), askWorkerToMoveEvent.getCurrentClientPlaying(), askWorkerToMoveEvent.getClientIndex());
            }
        } else {
            view.isNotMyTurn();
        }
    }

    /**
     * method to ask the player if he want to build before making a move with his worker
     *
     * @param askBuildBeforeMoveEvent is a message to ask the player if he want to move his worker before building a block
     */

    public void visit(AskBuildBeforeMoveEvent askBuildBeforeMoveEvent) {
        if (askBuildBeforeMoveEvent.getClientIndex() == askBuildBeforeMoveEvent.getCurrentClientPlaying()) {
            view.askBuildBeforeMove(askBuildBeforeMoveEvent.getIndexWorker(), askBuildBeforeMoveEvent.getRowWorker(), askBuildBeforeMoveEvent.getColumnWorker());
        }else{
            view.isNotMyTurn();
        }
    }

    /**
     * method to ask the player to move a worker on the board
     *
     * @param askMoveEvent is a message to ask the player to move a worker on the board
     */

    public void visit(AskMoveEvent askMoveEvent) {
        if (askMoveEvent.getClientIndex() == askMoveEvent.getCurrentClientPlaying()) {
            //Questa è la prima ask
            if (askMoveEvent.isFirstTime()) {
                view.moveWorker(askMoveEvent.getRow(), askMoveEvent.getColumn(), askMoveEvent.getIndexWorker(), askMoveEvent.isWrongBox(),askMoveEvent.isFirstTime(),askMoveEvent.getClientIndex(), askMoveEvent.getCurrentClientPlaying());
                ///se non è la prima volta significa che sei speciale e puoi fare un'altra mossa
            } else {
                view.anotherMove(askMoveEvent.getRow(), askMoveEvent.getColumn(), askMoveEvent.getIndexWorker(), askMoveEvent.isWrongBox(),askMoveEvent.isFirstTime(),askMoveEvent.getClientIndex(), askMoveEvent.getCurrentClientPlaying(), askMoveEvent.isDone());
            }
        }else{
            view.isNotMyTurn();
        }
    }

    /**
     * method to ask the player to build a block on the board
     *
     * @param askBuildEvent is a message to ask the player to build a block on the board
     */

    public void visit(AskBuildEvent askBuildEvent) {
        if (askBuildEvent.getClientIndex() == askBuildEvent.getCurrentClientPlaying()) {
            //Qui entra se è veramente la prima volta o se ha inserito una box non valida
            if (askBuildEvent.isFirstTime()) {
                view.buildMove(askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker(), askBuildEvent.getIndexWorker(), askBuildEvent.isWrongBox(), askBuildEvent.isFirstTime(), askBuildEvent.isSpecialTurn(),askBuildEvent.getClientIndex(), askBuildEvent.getCurrentClientPlaying(), askBuildEvent.isDone());
            } else { //Può fare una mossa speciale
                view.anotherBuild(askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker(), askBuildEvent.getIndexWorker(), askBuildEvent.isWrongBox(), askBuildEvent.isFirstTime(), askBuildEvent.isSpecialTurn(),askBuildEvent.getClientIndex(), askBuildEvent.getCurrentClientPlaying(), askBuildEvent.isDone());
            }
        }else{
            view.isNotMyTurn();
        }
    }

    /**
     * method to notify the winner
     *
     * @param winnerEvent is a message to notify the winner
     */

    public void visit(WinnerEvent winnerEvent) {
        if (winnerEvent.getClientIndex() == winnerEvent.getCurrentClientPlaying())
            view.winnerEvent(winnerEvent.getClientIndex());
        else
            view.someoneWon(winnerEvent.getClientIndex());
    }

    /**
     * method to notify the loser
     *
     * @param loserEvent is a message to notify the loser
     */

    public void visit(LoserEvent loserEvent) {
        view.loserEvent(loserEvent.getClientIndex(), loserEvent.getUserArray(),false);
    }

    /**
     * method to notify the others player that someone died
     *
     * @param whoHasLostEvent is a message to notify others players that someone died
     */

    public void visit(WhoHasLostEvent whoHasLostEvent) {
        if (whoHasLostEvent.getClientIndex() != whoHasLostEvent.getCurrentClientPlaying())
            view.whoHasLost(whoHasLostEvent.getUserArray(),whoHasLostEvent.getBoard(),false, whoHasLostEvent.getCurrentClientPlaying(), whoHasLostEvent.getClientIndex());
    }

    /**
     * method to control the connection with the server
     *
     * @param objHeartBeat is a message to test the connection's validity
     */

    public void visit(ObjHeartBeat objHeartBeat) {
        view.printHeartBeat(objHeartBeat);
    }

    /**
     * method to close the connection with the server
     *
     * @param closeConnectionFromServerEvent is a message to notify the connection's closing
     */

    public void visit(CloseConnectionFromServerEvent closeConnectionFromServerEvent) {
        view.closingConnectionEvent(closeConnectionFromServerEvent.getClientIndex(), closeConnectionFromServerEvent.isGameNotAvailable());
    }
}
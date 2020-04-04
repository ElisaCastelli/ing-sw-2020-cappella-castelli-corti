package it.polimi.ingsw.gamerstate;

import it.polimi.ingsw.*;
import it.polimi.ingsw.god.*;

public class IsPlaying extends GamerState{
    private final GamerStateManager gamerManager;
    private God myGod;
    public IsPlaying(GamerStateManager gamerManager){
        this.gamerManager=gamerManager;
        this.myGod=gamerManager.getMyGod();
    }

    @Override
    public int moveWorker(Worker worker, Box pos, String godName){
        System.out.println("Muovo"); //mossa effettiva
        int movedWorker = 0;
        movedWorker=myGod.moveWorker( worker , pos , godName );
        return movedWorker;
    }
    @Override
    public int moveBlock(Worker worker, Box pos, String godName){
        System.out.println("Costruisco"); //costruzione effettiva
        int movedBlock = 0;
        if(movedBlock==1){
            gamerManager.goWaiting();
        }
        return myGod.moveBlock( worker , pos , godName );
    }

    @Override
    public boolean checkWin(Box boxReach, Box boxStart, String godName) {
        if( myGod.checkWin( boxStart , boxReach , godName )){
            gamerManager.goWin();
            return true;
        }
        return false;
    }


    @Override
    public boolean checkPossibleMove( Box actualBox , Board boardToControl ){
        for( int i = actualBox.getRow() - 1 ; i <= actualBox.getRow() + 1 ; i++ ){
            for( int j = actualBox.getColumn() - 1; j <= actualBox.getColumn() + 1 ; j++){
                if( ( boardToControl.getBox( i , j ).notWorker() && ( boardToControl.getBox( i , j ).getCounter() != 4))
                        && ( ( ( boardToControl.getBox( i , j ).getCounter() - actualBox.getCounter() ) == 1)
                        || ( ( actualBox.getCounter() - boardToControl.getBox( i , j ).getCounter() ) >= 0))){
                    return true;
                }
            }
        }
        return false;
    }
//true posso muovermi false morto
    public boolean checkWorkers(Box actualBoxW1, Box actualBoxW2, Board boardToControl){
        if( checkPossibleMove ( actualBoxW1, boardToControl ) && checkPossibleMove ( actualBoxW2, boardToControl ) ){
            return true;
        }
        gamerManager.goDead();
        return false;
    }

    @Override
    public int checkPossibleBuild( Box finalBox, Board boardToControl , String name){
        for( int i = finalBox.getRow() - 1; i <= finalBox.getRow() + 1 ; i++ ){
            for( int j = finalBox.getColumn() - 1; j <= finalBox.getColumn() + 1 ; j++ ){
                if( !boardToControl.getBox( i , j ).notWorker() && boardToControl.getBox( i , j ).getWorker().getGamerName() == name){
                    return boardToControl.getBox( i , j ).getWorker().getWorkerId();
                }
            }
        }
        return 0;
    }
}

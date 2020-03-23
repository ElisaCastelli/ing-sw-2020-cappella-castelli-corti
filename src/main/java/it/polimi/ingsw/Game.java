package it.polimi.ingsw;

import java.util.Scanner;

public class Game {
    private Board board;
    private Gamer gamers[];
    private  int nPlayer;

    Game(){
        board= new Board();
        gamers= new Gamer[3];
        nPlayer=0;
    }
    Game(int nPlayer){
        board= new Board();
        gamers= new Gamer[3];
        this.nPlayer=nPlayer;
    }

    public void play(){
        //ricevo partecipanti
           for(int i=0;i<nPlayer;i++){
               System.out.println("Nome giocatore "+i+" :");
               Scanner input = new Scanner(System.in);
               String s = input.nextLine();
               gamers[i]=new Gamer(s);
           }
        //getsisco i turni

    }
    /*public static void main( String[] args )
    {
        Game g= new Game();
        g.board.build(0,0);
        g.board.build(1,0);
        g.board.build(0,0);
        g.print();
    }*/
}

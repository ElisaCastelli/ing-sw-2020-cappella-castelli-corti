package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Board board;
    private ArrayList<Gamer> gamers;
    private  int nPlayer;

    Game(){
        board= new Board();
        gamers= new ArrayList<>();
        nPlayer=0;
    }

    public void play(){
        Gamer g;
        //ricevo partecipanti
        for(int i=0;i<nPlayer;i++){
            System.out.println("Nome giocatore "+i+" :");
            Scanner input = new Scanner(System.in);
            String s = input.nextLine();
            g=new Gamer("s");
            gamers.add(i,g);
        }
        //gestisco i turni


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

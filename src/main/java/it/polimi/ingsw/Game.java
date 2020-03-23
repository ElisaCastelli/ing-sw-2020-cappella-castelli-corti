package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private Board board;
    private ArrayList<Gamer> gamers;
    private  int nPlayer;
    private boolean finito;

    Game(){
        board= new Board();
        gamers= new ArrayList<>();
        nPlayer=0;
        finito=false;
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
        int i=1;
        while(!finito && i<gamers.size()){
            gamers.get(i).move();
            i++;
            if(i==4)i=1;
        }
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

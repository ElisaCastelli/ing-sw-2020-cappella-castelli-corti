package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private static Board board;
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
        System.out.println("Inserire nnumero di giocatore:");
        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        for(int i=0;i<nPlayer;i++){

            g=new Gamer("s");
            gamers.add(i,g);
            gamers.get(i).setWorker(1,board);
            gamers.get(i).setWorker(2,board);
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

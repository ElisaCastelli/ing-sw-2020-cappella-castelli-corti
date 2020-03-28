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
        System.out.println("Inserire numero di giocatore:");
        Scanner input = new Scanner(System.in);
        nPlayer = Integer.parseInt(input.nextLine());
        for(int p=0;p<nPlayer;p++){
            System.out.println("Inserire nome del giocatore numero "+p);
            Scanner playerName = new Scanner(System.in);
            String nomePlayer=playerName.nextLine();
            g=new Gamer(nomePlayer);
            gamers.add(p,g);
            gamers.get(p).setWorker(0,board); //assegno pedina 1
            gamers.get(p).setWorker(1,board); //assegno pedina 2
        }
        for(int j=0;j<nPlayer;j++){
            System.out.println(gamers.get(j).getName());
        }

        //gestisco i turni
        int i=0;
        while(!finito && i<=nPlayer){
            gamers.get(i).move();
            i++;
            if(i==gamers.size()){
                i=0;
            }
            System.out.println("Vuoi finire? [1 si - 0 no]");
            Scanner f = new Scanner(System.in);
            int fin = Integer.parseInt(f.nextLine());
            if(fin==1){
                finito=true;
            }else{
                finito=false;
            }
        }
    }

    /*public static void main( String[] args )
    {
        Game g= new Game();
        g.play();
    }*/
}

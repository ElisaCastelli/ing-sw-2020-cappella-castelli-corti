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
        Gamer gamer;
        int indexWorker=0;
        //ricevo partecipanti
        System.out.println("Inserire numero di giocatore:");
        Scanner input = new Scanner(System.in);
        nPlayer = Integer.parseInt(input.nextLine());
        for(int p=0;p<nPlayer;p++){
            System.out.println("Inserire nome del giocatore numero "+p);
            Scanner playerName = new Scanner(System.in);
            String nomePlayer=playerName.nextLine();
            gamer=new Gamer(nomePlayer, board);
            gamers.add(p,gamer);
            for(int index=0; index<2;index++){
                boolean workerCorrect=false;
                while(workerCorrect==false) {
                    //la richiesta del numero di pedina e dello spazio dove voglio muovermi verrà fatta graficamente
                    indexWorker = index + 1;
                    System.out.println("Settare pedina numero " + (indexWorker));
                    System.out.println("Riga:");
                    Scanner r = new Scanner(System.in);
                    int row = Integer.parseInt(r.nextLine());
                    System.out.println("Colonna dove voglio muovermi:");
                    Scanner c = new Scanner(System.in);
                    int column = Integer.parseInt(c.nextLine());
                    workerCorrect=gamers.get(p).initializeWorker(indexWorker, board.getBox(row, column));
                }
            }
        }
        board.print();
        /*
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
        }*/
    }

    public static void main( String[] args )
    {
        Game g= new Game();
        g.play();
    }
}

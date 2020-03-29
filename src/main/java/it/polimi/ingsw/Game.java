package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the main class that represents the game
 */

public class Game {
    /**
     * This attribute is the playing board
     */
    private static Board board;
    /**
     * this is the array list of the players
     */
    private ArrayList<Gamer> gamers;
    /**
     * This integer attribute is the number of the players
     */
    private  int nPlayers;

    //private boolean finito;

    /**
     * Constructor without parameters
     */
    Game(){
        board= new Board();
        gamers= new ArrayList<>();
        nPlayers=0;
        //finito=false;
    }

    public void ordinaGamers(){
        for(int i=0;i<nPlayers-1;i++){
            if(gamers.get(i).getAge()>gamers.get(i+1).getAge()){
                gamers.get(i).swap(gamers.get(i+1));
            }
        }
    }

    /**
     * This method starts the game
     * First of all it manages the insertion of players and the setting of the workers for each of them
     * Then it manages turns by alternating players until one player wins or each player dies
     */
    public void play(){
        Gamer gamer;
        int indexWorker=0;
        //players management
        System.out.println("Inserire numero di giocatore: ");
        Scanner input = new Scanner(System.in);
        nPlayers = Integer.parseInt(input.nextLine());
        for(int p=0;p<nPlayers;p++){
            System.out.print("Inserire nome del giocatore numero "+p+": ");
            Scanner playerName = new Scanner(System.in);
            String nomePlayer=playerName.nextLine();
            System.out.print("Inserire età del giocatore numero "+p+": ");
            Scanner gamerAge = new Scanner(System.in);
            int playerAge=Integer.parseInt(gamerAge.nextLine());
            gamer=new Gamer(nomePlayer, playerAge, board);
            gamers.add(p,gamer);
        }
        ordinaGamers();
        /*for(int p=0;p<nPlayers;p++){
            gamers.get(p).print();
        }*/
        for(int p=0;p<nPlayers;p++){
            for(int index=0; index<2;index++){
                boolean workerCorrect=false;
                while(workerCorrect==false) {
                    //la richiesta del numero di pedina e dello spazio dove voglio muovermi verrà fatta graficamente
                    indexWorker = index + 1;
                    System.out.println("Settare pedina numero " + (indexWorker));
                    System.out.print("Riga: ");
                    Scanner r = new Scanner(System.in);
                    int row = Integer.parseInt(r.nextLine());
                    System.out.print("Colonna dove voglio muovermi: ");
                    Scanner c = new Scanner(System.in);
                    int column = Integer.parseInt(c.nextLine());
                    workerCorrect=gamers.get(p).initializeWorker(indexWorker, board.getBox(row, column));
                }
            }
        }

        board.print();
        /*
        //turns management
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


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
    private ArrayList<Player> players;
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
        players= new ArrayList<>();
        nPlayers=0;
        //finito=false;
    }

    /**
     * Method to sort gamers by age
     */
    public void sortGamers(){
        for(int i=0;i<nPlayers-1;i++){
            if(players.get(i).getAge()>players.get(i+1).getAge()){
                players.get(i).swap(players.get(i+1));
            }
        }
    }

    /**
     * This method starts the game
     * First of all it manages the insertion of players and the setting of the workers for each of them
     * Then it manages turns by alternating players until one player wins or each player dies
     */
    public void play(){
        Player player;
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
            ///TO DO!!!!! togliere board dal costruttore
            player=new Player(nomePlayer, playerAge);
            players.add(p,player);
        }
        //TO DO gamers.sort()
        sortGamers();

        //estraggo carte

        //setto pedine
        //un ciclo per ogni giocatore
        for(int p=0;p<nPlayers;p++){
            //due cicli per le pedine
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
                    workerCorrect=players.get(p).initializeWorker(indexWorker, board.getBox(row, column));
                }
            }
        }

        board.print();

        //turns management
        int i=0;
        int indexWorkerMoved=0;
        boolean movedWorker=false, win=false, dead=false;
        boolean movedBlock=false;
        int row=0, column=0;

        while(/*MOETDO PER CONTROLLARE FINE DEL GIOCO &&*/ i<=nPlayers){
            //boolean sipuoMuovere=gamers.get(i).checkPossibleMove(actualBox,board)
            //TO DO!!!! controllo gamers.get(p).getState ? if morto metodo che toglie giocatore se sono 3 altrimenti termina
            while(!movedWorker){
                //la richiesta del numero di pedina e dello spazio dove voglio muovermi verrà fatta graficamente
                System.out.println("Pedina da muovere [1] 0 [2]:");
                Scanner worker = new Scanner(System.in);
                indexWorkerMoved = Integer.parseInt(worker.nextLine());
                System.out.println("Riga dove voglio muovermi:");
                Scanner r = new Scanner(System.in);
                row = Integer.parseInt(r.nextLine());
                System.out.println("Colonna dove voglio muovermi:");
                Scanner col = new Scanner(System.in);
                column = Integer.parseInt(col.nextLine());
                movedWorker=players.get(i).playWorker(indexWorkerMoved-1,board,row,column);

            }

            win=players.get(i).checkWin(indexWorkerMoved-1,board,row,column);
            if (win==true){
                //cambia stato e termino
            }

            while(!movedBlock){
                //la richiesta del numero di pedina e dello spazio dove voglio muovermi verrà fatta graficamente

                System.out.println("Riga dove voglio costruire:");
                Scanner r2 = new Scanner(System.in);
                int row2 = Integer.parseInt(r2.nextLine());
                System.out.println("Colonna dove voglio costruire:");
                Scanner col2 = new Scanner(System.in);
                int column2= Integer.parseInt(col2.nextLine());

                movedBlock=players.get(i).playBlock(board,row2,column2);
            }

            i++;
            if(i==players.size()){
                i=0;
            }
        }
    }

    public static void main( String[] args )
    {
        Game g= new Game();
        g.play();
    }
}


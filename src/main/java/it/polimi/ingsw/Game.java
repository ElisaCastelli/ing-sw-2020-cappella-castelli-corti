package it.polimi.ingsw;

import java.util.ArrayList;
import java.util.Scanner;
import it.polimi.ingsw.god.*;

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
    private ArrayList<God> godsCard;

    /**
     * Constructor without parameters
     */
    Game(){
        board = new Board();
        players = new ArrayList<>();
        nPlayers = 0;
        godsCard = new ArrayList<God>();
    }

    /**
     * Method to sort gamers by age
     */
    public void sortGamers(){
        for(int i = 0; i < nPlayers-1; i++){
            if(players.get(i).getAge() > players.get(i+1).getAge()){
                players.get(i).swap(players.get(i+1));
            }
        }
    }


    public void addNPlayer(){
        System.out.println("Inserire numero di giocatore: ");
        Scanner input = new Scanner(System.in);
        nPlayers = Integer.parseInt(input.nextLine());
    }

    public String addNamePlayer(int indexPlayer){
        System.out.print("Inserire nome del giocatore numero "+ indexPlayer +": ");
        Scanner playerName = new Scanner(System.in);
        return playerName.nextLine();
    }

    public int addAgePlayer(int indexPlayer){
        System.out.print("Inserire età del giocatore numero "+ indexPlayer +": ");
        Scanner gamerAge = new Scanner(System.in);
       return Integer.parseInt(gamerAge.nextLine());
    }

    public int askRow(){
        System.out.print("Riga: ");
        Scanner r = new Scanner(System.in);
        return Integer.parseInt(r.nextLine());
    }

    public int askColumn(){
        System.out.print("Colonna dove voglio muovermi: ");
        Scanner c = new Scanner(System.in);
        return Integer.parseInt(c.nextLine());
    }

    public int askWorker(){
        System.out.println("Pedina da muovere [1] 0 [2]:");
        Scanner worker = new Scanner(System.in);
        return Integer.parseInt(worker.nextLine());
    }

    /**
     * This method starts the game
     * First of all it manages the insertion of players and the setting of the workers for each of them
     * Then it manages turns by alternating players until one player wins or each player dies
     */
    public void play(){
        Player player;
        int indexWorker = 0;

        //1- PLAYER MANAGEMENT
        addNPlayer();
        for(int p = 0; p < nPlayers; p++){

            String nomePlayer = addNamePlayer(p);
            int playerAge = addAgePlayer(p);

            player = new Player(nomePlayer, playerAge);
            players.add(p,player);
        }
        //TO DO gamers.sort()
        sortGamers();

        //CARDS MANAGEMENT

        //WORKERS SETTING
        //un ciclo per ogni giocatore
        for(int p = 0 ; p < nPlayers; p++){
            //due cicli per le pedine
            for(int index = 0 ; index < 2; index++){
                boolean workerCorrect = false;
                while( workerCorrect == false ) {

                    //GRAFICA
                    indexWorker = index + 1;
                    System.out.println( "Settare pedina numero " + (indexWorker));
                    int row = askRow();
                    int column = askColumn();

                    workerCorrect=players.get(p).initializeWorker(indexWorker, board.getBox(row, column));
                }
            }
        }

        board.print();

        //TURNS MANAGEMENT
        int i = 0;
        int indexWorkerMoved = 0;
        boolean movedWorker = false;
        boolean win = false;
        boolean dead = false;
        boolean movedBlock = false;
        int row = 0, column = 0;

        while(/*MOETDO PER CONTROLLARE FINE DEL GIOCO &&*/ i <= nPlayers){
            //boolean sipuoMuovere=gamers.get(i).checkPossibleMove(actualBox,board)
            //TO DO!!!! controllo gamers.get(p).getState ? if morto metodo che toglie giocatore se sono 3 altrimenti termina
            while( !movedWorker ){

                //GRAFICA
                indexWorkerMoved = askWorker();
                row = askRow();
                column = askColumn();

                movedWorker = players.get(i).playWorker(indexWorkerMoved-1,board,row,column);

            }
            //check win
            win = players.get(i).checkWin(indexWorkerMoved-1,board,row,column);
            if (win == true){
                //cambia stato e termino
            }

            while( !movedBlock ){

                //GRAFICA
                int row2 = askRow();
                int column2 = askColumn();

                movedBlock = players.get(i).playBlock(board,row2,column2);
            }

            //passo al giocatore successivo e se sono all'ultimo ritorno al primo
            i++;
            if( i == players.size() ){
                i = 0;
            }
        }
    }

    public static void main( String[] args )
    {
        Game g= new Game();
        g.play();
    }
}


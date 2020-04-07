package it.polimi.ingsw;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import it.polimi.ingsw.god.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;


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
     * this is the array list of the players
     */
    private ArrayList<Player> playersDead;
    /**
     * This integer attribute is the number of the players
     */
    private  int nPlayers;
    /**
     * Array of all cards
     */
    private ArrayList<God> godsArray;
    /**
     * Array of drawn cards
     */
    private ArrayList<God> cardUsed;
    private Move lastMove = new Move();
    /**
     * Constructor without parameters
     */
    Game(){
        board = new Board();
        players = new ArrayList<>();
        playersDead = new ArrayList<>();
        nPlayers = 0;
        godsArray = new ArrayList<>();
        cardUsed = new ArrayList<>();
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

    public God askGodCard(){
        for(int g = 0; g < godsArray.size(); g++){
            //godsArray.print();
        }
        System.out.println(" Quale carta vuoi scegliere? ");
        Scanner godCard = new Scanner(System.in);
        String nameCard= godCard.nextLine();
        God godDrawn = godsArray.get(godsArray.indexOf(nameCard));
        godsArray.remove(godsArray.indexOf(nameCard));
        return godDrawn;
    }

    public void parseXML(){
        try {
            File inputFile = new File("./god.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("god");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);

                God g= new Gods();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    g.setGodName(eElement
                            .getElementsByTagName("name")
                            .item(0)
                            .getTextContent());
                    g.setDescription(eElement
                            .getElementsByTagName("description")
                            .item(0)
                            .getTextContent());
                    g.setEffect(eElement
                            .getElementsByTagName("effect")
                            .item(0)
                            .getTextContent());
                    godsArray.add(temp,g);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//sotto atena lista osservatori
    /**
     * This method starts the game
     * First of all it manages the insertion of players and the setting of the workers for each of them
     * Then it manages turns by alternating players until one player wins or each player dies
     */
    public void play(){
        Player player;
        God godDrawn;
        int indexWorker = 0;

        //1- PLAYER MANAGEMENT
        addNPlayer();
        for(int p = 0; p < nPlayers; p++){

            String nomePlayer = addNamePlayer(p);
            int playerAge = addAgePlayer(p);

            player = new Player(nomePlayer, playerAge);
            players.add(p,player);
        }
        //TODO gamers.sort()
        sortGamers();

        //2-CARDS MANAGEMENT
        parseXML();
        for(int p = 0; p < nPlayers; p++){
            godDrawn = askGodCard();
            players.get(p).setGod(godDrawn);
            cardUsed.add(godDrawn);
        }

        //3-WORKERS SETTING
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

        //4-TURNS MANAGEMENT
        int i = 0;
        int indexWorkerMoved = 0;
        int movedWorker = 0;
        boolean win = false;
        boolean dead = false;
        int movedBlock = 0;
        int row = 0, column = 0;

        while(/*MOETDO PER CONTROLLARE FINE DEL GIOCO &&*/ i <= nPlayers){
            players.get(i).goPlay();

            //checkWorkers sposta lo stato in dead se non posso muovermi
            boolean canMove=players.get(i).checkWorkers(board);
            if(!canMove ){
                if(nPlayers==3){
                    playersDead.add(players.get(i));
                    players.remove(i);
                }else{
                    //gioco finito
                }
            }

            //Movimento
            while( movedWorker ==0){

                //GRAFICA
                indexWorkerMoved = askWorker();
                row = askRow();
                column = askColumn();
//TODO DA RICHIEDERE AL GIOCATORE "PROMETEO " SE PRIMA VUOLE MUOVERE UN BLOCCO SE NON SALE
                //nel caso del minotauro serve solo a capire se c'è un avversario
                movedWorker = players.get(i).playWorker(indexWorkerMoved-1,board,row,column);
                lastMove=players.get(i).getMyGod().getLastMove();
                if(movedWorker!=2){
                    players.get(i).getMyGod().getLastMove().clear();
                }
                if(movedWorker==3){
                    Box temp = lastMove.getDirection();
                    //muovo l'avversario
                    int indexAvversario=0;
                    boolean find=false;
                    int indexPlayer=0;
                    while(find==false && indexPlayer< nPlayers){
                        if(lastMove.getBoxReached().getWorker().getGamerName()== players.get(indexPlayer).getName()){
                            indexAvversario=indexPlayer;
                            find=true;
                        }
                        indexPlayer++;
                    }
                    int indexWorkerAvv = lastMove.getBoxReached().getWorker().getWorkerId();
                    movedWorker = players.get(indexAvversario).playWorker(indexWorkerAvv-1,board,temp.getRow(),temp.getColumn());
                    if(movedWorker==1){
                        players.get(i).getMyGod().getLastMove().clear();
                    }
                    //mi muovo dove c'era l'avversario
                    movedWorker = players.get(i).playWorker(indexWorkerMoved-1,board,row,column);
                }


            }
            //check win
            win = players.get(i).checkWin(indexWorkerMoved-1,board,row,column);
            if (win == true){
                //cambia stato e termino
            }
            //Costruzione
            while( movedBlock ==0 ){

                //GRAFICA
                int row2 = askRow();
                int column2 = askColumn();

                movedBlock = players.get(i).playBlock(board,row2,column2);
                lastMove=players.get(i).getMyGod().getLastMove();
                if(movedWorker!=2){
                    players.get(i).getMyGod().getLastMove().clear();
                }
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


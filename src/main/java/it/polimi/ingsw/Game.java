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

    private final UserInterface tastiera = new UserInterface();

    //private Move lastMove = new Move();
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

                God g= new BasicGod();
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
                    if("Apollo".equals(g.getGodName())){
                        new SwitchWorker(new NotMoveUp(new MoveBeforeBuilt(g)));
                    }
                    else if("Artemis".equals(g.getGodName())){
                        new MoveWorkerTwice( new NotMoveUp(new MoveBeforeBuilt(g)));
                    }
                    else if("Athena".equals(g.getGodName())){
                        new OpponentBlock(new MoveBeforeBuilt((g));
                    }
                    else if("Atlas".equals(g.getGodName())){
                        new BuildDome(new NotMoveUp(new MoveBeforeBuilt(g)));
                    }
                    else if("Demeter".equals(g.getGodName())){
                        new OtherPositionToBuild(new NotMoveUp(new MoveBeforeBuilt((g)));
                    }
                    else if("Hephaestus".equals(g.getGodName())){
                        new BuildInTheSamePosition(new NotMoveUp(new MoveBeforeBuilt(g)));
                    }
                    else if("Minotaur".equals(g.getGodName())){
                        new ShiftWorker(new SwitchWorker(new NotMoveUp(new MoveBeforeBuilt((g)));
                    }
                    else if("Pan".equals(g.getGodName())){
                        new DownTwoOrMoreLevelsWin(new NotMoveUp(new MoveBeforeBuilt((g)));
                    }
                    else if("Prometheus".equals(g.getGodName())){
                        new BuildBeforeWorkerMove(new NotMoveUp(g));
                    }
                    godsArray.add(temp,g);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void playerManagement(){
        Player player;
        nPlayers=tastiera.addNPlayer();
        for(int p = 0; p < nPlayers; p++){

            String nomePlayer = tastiera.addNamePlayer(p);
            int playerAge = tastiera.addAgePlayer(p);

            player = new Player(nomePlayer, playerAge);
            players.add(p,player);
        }
        //TODO gamers.sort()
        sortGamers();
    }

    public void cardsManagement(){
        God godDrawn;
        parseXML();
        for(int p = 0; p < nPlayers; p++){
            godDrawn = tastiera.askGodCard(godsArray);
            players.get(p).setGod(godDrawn);
            cardUsed.add(godDrawn);
        }
    }

    public void workersSetting(){
        int indexWorker;
        for(int p = 0 ; p < nPlayers; p++){
            //due cicli per le pedine
            for(int index = 0 ; index < 2; index++){
                boolean workerCorrect = false;
                while(!workerCorrect) {

                    indexWorker = index + 1;
                    System.out.println( "Settare pedina numero " + (indexWorker));
                    int row = tastiera.askRow();
                    int column = tastiera.askColumn();

                    workerCorrect=players.get(p).initializeWorker(indexWorker, board.getBox(row, column));
                }
            }
        }
    }

    public void turnManagement(){
        int i = 0;
        int indexWorkerMoved = 0;
        boolean movedWorker = false;
        boolean win;
        boolean movedBlock = false;
        boolean chosenWorker = false;
        int row = 0, column = 0;
        Box starterBox=null;

        while(/* game manager &&*/ i <= nPlayers){

            players.get(i).goPlay();

            boolean canMove=players.get(i).checkWorkers();

            if( !canMove ){
                playersDead.add(players.get(i));
                players.remove(i);
                if(players.size()==1){
                    //GAME MANAGER
                    players.get(0).goWin();
                }
            }
            else {
                //Movimento

                while(!chosenWorker){
                    indexWorkerMoved = tastiera.askWorker();
                    starterBox = players.get(i).getWorkerBox(indexWorkerMoved - 1);
                    players.get(i).setPossibleMove(indexWorkerMoved);
                    System.out.println("Hai scelto? 0 no 1 si");
                    Scanner scelta = new Scanner(System.in);
                    int accendiamo = Integer.parseInt(scelta.nextLine());
                    if(accendiamo==0){
                        chosenWorker=false;
                    }
                    else{
                        chosenWorker=true;
                    }
                }
                while (!movedWorker) {
                    row = tastiera.askRow();
                    column = tastiera.askColumn();
                    players.get(i).setPossibleMove(indexWorkerMoved);
                    movedWorker = players.get(i).playWorker(indexWorkerMoved - 1, board.getBox( row, column));
                    starterBox.clearBoxesNextTo();
                }

                //check win
                win = players.get(i).checkWin(starterBox, board.getBox( row, column));
                if (win) {
                    players.get(i).goWin();
                    //fine gioco
                }
                else {
                    //Costruzione
                    players.get(i).setPossibleBuild(indexWorkerMoved-1);
                    if(!players.get(i).getWorkerBox(indexWorkerMoved-1).checkPossible()){
                        players.get(i).getWorkerBox(indexWorkerMoved-1).clearBoxesNextTo();
                        players.get(i).goDead();
                        players.remove(i);
                    }
                    else {
                        while (!movedBlock) {
                            players.get(i).setPossibleBuild(indexWorkerMoved-1);
                            row = tastiera.askRow();
                            column = tastiera.askColumn();

                            movedBlock = players.get(i).playBlock(board.getBox(row, column));
                            players.get(i).getWorkerBox(indexWorkerMoved-1).clearBoxesNextTo();
                        }
                    }
                }
                players.get(i).goWaiting();
            }
            //passo al giocatore successivo e se sono all'ultimo ritorno al primo
            i++;
            if( i == players.size() ){
                i = 0;
            }
        }
    }

    /**
     * This method starts the game
     * First of all it manages the insertion of players and the setting of the workers for each of them
     * Then it manages turns by alternating players until one player wins or each player dies
     */
    public void play(){

        //1- PLAYER MANAGEMENT
        playerManagement();

        //2-CARDS MANAGEMENT
        cardsManagement();

        //3-WORKERS SETTING
        workersSetting();

        //4-TURNS MANAGEMENT
        turnManagement();
    }

    public static void main( String[] args )
    {
        Game g= new Game();
        g.play();
    }
}


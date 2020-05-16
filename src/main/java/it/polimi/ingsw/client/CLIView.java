package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.ObjMove;
import it.polimi.ingsw.network.objects.ObjWokerToMove;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View {
    private Scanner input = new Scanner(System.in);
    private int indexPlayer = -1;
    private boolean isPlaying;
    private int nPlayer;
    private ArrayList<User> usersArray;
    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board=board;
        printBoard(false);
    }

    @Override
    public void setUsers(ArrayList<User> users) {
        usersArray=users;
    }

    @Override
    public int askNPlayer() {
        System.out.println("Numero giocatori:");
        return input.nextInt();
    }

    @Override
    public String askName() {
        System.out.println("Nome giocatore:");
        return input.next();
    }

    @Override
    public int askAge() {
        System.out.println("Età giocatore:");
        return input.nextInt();
    }

    @Override
    public void setNPlayer(int nPlayer){
        this.nPlayer=nPlayer;
    }

    @Override
    public void setIndexPlayer(int indexPlayer){
        this.indexPlayer=indexPlayer;
    }

    @Override
    public int getIndexPlayer(){
        return indexPlayer;
    }

    @Override
    public void setPlaying(boolean isPlaying){
        this.isPlaying=isPlaying;
    }

    @Override
    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public ArrayList<Integer> ask3Card(ArrayList<String> cards) {
        ArrayList<Integer> cardTemp= new ArrayList<>();
        boolean[] scelte= new boolean[cards.size()];
        for(int i=0;i<cards.size();i++){
            scelte[i]=false;
        }
        System.out.println("Scegli gli indici di " + nPlayer + " carte");
        for(int cardIndex=0; cardIndex < cards.size();cardIndex++){
            System.out.println("[ "+cardIndex+ "] "+cards.get(cardIndex));
        }
        while(cardTemp.size()<nPlayer){
            int cardDrawn= input.nextInt();
            if(!scelte[cardDrawn] && cardDrawn<9){
                System.out.println("Scelta carta numero "+ cardDrawn);
                cardTemp.add(cardDrawn);
                scelte[cardDrawn]=true;
            }
        }
        return cardTemp;
    }


    @Override
    public int askCard(ArrayList<String> cards) {
        boolean choose=false;
        int scelta=-1;
        for(int index=0;index<cards.size();index++){
            System.out.println("[ "+index+ "] "+cards.get(index));
        }
        System.out.println("Scegli la tua carta");
        while(!choose){
            scelta=input.nextInt();
            if(scelta<cards.size()){
                System.out.println("Scelta carta numero "+ scelta);
                choose=true;
            }
        }
        return scelta;
    }

    @Override
    public ArrayList<Box> initializeWorker(){
        ArrayList<Box> boxes= new ArrayList<>();
        for(int indexWorker=0;indexWorker<2;indexWorker++){
            printBoard(false);
            System.out.println("Posiziona la pedina "+indexWorker);
            System.out.println("Riga: ");
            int row= input.nextInt();
            System.out.println("Colonna: ");
            int column= input.nextInt();
            if(board.getBox(row,column).notWorker()){
                if(boxes.size()==1 && (boxes.get(0).getRow()!=row && boxes.get(1).getColumn()!=column)){
                    boxes.add(board.getBox(row,column));
                    //System.out.println("Casella gia' occupata");
                }
                else{
                    boxes.add(board.getBox(row,column));
                    //System.out.println("Casella assegnata");
                }
            }

        }
        return boxes;
    }

    @Override
    public void printBoard(boolean printReachable) {
        if(!printReachable){
            board.print();
        }else{
            board.printReachable();
        }
    }

    @Override
    public ObjWokerToMove askWorker(AskWorkerToMoveEvent askWorkerToMoveEvent) {

        int row1=askWorkerToMoveEvent.getRow1();
        int row2=askWorkerToMoveEvent.getRow2();
        int column1=askWorkerToMoveEvent.getColumn1();
        int column2=askWorkerToMoveEvent.getColumn2();
        int indexFirstWorker=askWorkerToMoveEvent.getIndexFirstWoker();
        int indexSecondWorker=askWorkerToMoveEvent.getIndexSecondWoker();

        System.out.println("You' re going to do your move-> What Worker You wanna move?");
        System.out.println("[ 0 ] -> "+ " in position : "+ row1 + " <-row" + column1 + " <-column");
        System.out.println("[ 1 ] -> "+ " in position : "+ row2 + " <-row" + column2 + " <-column");
        System.out.println("Control the board and choose...");
        printBoard(false);

        int intInputValue;
        while (true) {
            System.out.println("Enter a whole number.");

            String inputString= input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                if(intInputValue>=0 && intInputValue<2){
                    System.out.println("Correct input, exit");
                    break;
                }
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
        ObjWokerToMove objWokerToMove;
        if (intInputValue==0){
            objWokerToMove=new ObjWokerToMove(indexFirstWorker, row1,column1,false);
        }else{
            objWokerToMove=new ObjWokerToMove(indexSecondWorker, row2,column2,false);
        }
        return objWokerToMove;
    }

    @Override
    public ObjWokerToMove AreYouSure(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        int row1=askWorkerToMoveEvent.getRow1();
        int column1=askWorkerToMoveEvent.getRow2();
        int indexWorker=askWorkerToMoveEvent.getIndexFirstWoker();

        System.out.println("Are You sure you want move the "+indexWorker+" worker? ");
        System.out.println("Position : "+ row1 + " <-row" + column1 + " <-column");

        System.out.println("[ 0 ] -> "+ "YES");
        System.out.println("[ 1 ] -> "+ "NO");
        int intInputValue;
        while (true) {
            System.out.println("Enter a whole number.");

            String inputString= input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                break;
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
        if(intInputValue==0){
            return new ObjWokerToMove(indexWorker,row1,column1,true);
        }else{
            return askWorker(askWorkerToMoveEvent);
        }
    }

    @Override
    public ObjMove moveWorker(AskMoveEvent askMoveEvent) {
        int row=askMoveEvent.getRow1();
        int column= askMoveEvent.getColumn1();
        int indexWorker=askMoveEvent.getIndexWoker();

        ObjMove objMove= new ObjMove(indexWorker,0,0,true);
        int intInputValue=0;

        System.out.println("you have chosen the "+indexWorker+" worker ");
        System.out.println("Position : "+ row + " <-row" + column + " <-column");
        System.out.println("You' re going to do your move-> What Position You wanna reach?");

        System.out.println("Select row:");

        while (true) {
            System.out.println("Enter a whole number.");
            String inputString= input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                if(intInputValue>=0 && intInputValue<5){
                    System.out.println("Correct input, exit");
                    break;
                }
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
        objMove.setRow(intInputValue);

        System.out.println("Select Column:");
        while (true) {
            System.out.println("Enter a whole number.");
            String inputString= input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                if(intInputValue>=0 && intInputValue<5){
                    System.out.println("Correct input, exit");
                    break;
                }
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
        objMove.setColumn(intInputValue);
        return objMove;
    }

    @Override
    public ObjMove anotherMove(AskMoveEvent askMoveEvent) {
        System.out.println("You Have the possibility to make another move");
        System.out.println("Do You want to?: ");
        System.out.println("[ 0 ] -> "+ "YES");
        System.out.println("[ 1 ] -> "+ "NO");

        int intInputValue=0;
        while (true) {
            System.out.println("Enter a whole number.");
            String inputString= input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                break;
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
        if(intInputValue==0){
            return new ObjMove(true);
        }else{
            ObjMove objMove =moveWorker(askMoveEvent);
            objMove.setDone(false);
            return objMove;
        }
    }
}

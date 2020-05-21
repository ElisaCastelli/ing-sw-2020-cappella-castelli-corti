package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.ObjBlock;
import it.polimi.ingsw.network.objects.ObjMove;
import it.polimi.ingsw.network.objects.ObjWorkerToMove;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View {
    private Scanner input = new Scanner(System.in);
    private static final int numCards = 14;
    private int indexPlayer = -1;
    private boolean isPlaying;
    private int nPlayer;
    private ArrayList<User> usersArray;
    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void setUsers(ArrayList<User> users) {
        usersArray = users;
    }

    @Override
    public int askNPlayer() {
        System.out.println(Color.CYAN+"How many Player ? [ 2 o 3 ]"+Color.RESET);
        return inputTwoOrThree();
    }

    @Override
    public String askName() {
        System.out.println("Player name :");
        return input.nextLine();
    }

    @Override
    public int askAge() {
        System.out.println("Player age : ");
        return inputNumber();
    }

    @Override
    public void setNPlayer(int nPlayer){
        this.nPlayer=nPlayer;
    }

    @Override
    public int getNPlayer() {
        return nPlayer;
    }

    @Override
    public void setIndexPlayer(int indexPlayer){
        this.indexPlayer = indexPlayer;
    }

    @Override
    public int getIndexPlayer(){
        return indexPlayer;
    }

    @Override
    public void setPlaying(boolean isPlaying){
        this.isPlaying = isPlaying;
    }

    @Override
    public boolean isPlaying(){
        return isPlaying;
    }

    @Override
    public ArrayList<Integer> ask3Card(ArrayList<String> cards) {
        ArrayList<Integer> cardTemp = new ArrayList<>();
        boolean[] scelte = new boolean[cards.size()];
        for(int i = 0; i < cards.size(); i++){
            scelte[i] = false;
        }
        System.out.println("Scegli gli indici di " + nPlayer + " carte");
        for(int cardIndex = 0; cardIndex < cards.size(); cardIndex++){
            System.out.println("[ "+cardIndex+ "] "+cards.get(cardIndex));
        }
        while(cardTemp.size() < nPlayer){
            int cardDrawn = inputNumber();
            while(cardDrawn >= numCards || cardDrawn < 0 || scelte[cardDrawn]){
                System.out.println("Select again the card.");
                cardDrawn = inputNumber();
            }
            System.out.println("Scelta carta numero "+ cardDrawn);
            cardTemp.add(cardDrawn);
            scelte[cardDrawn]=true;
        }
        return cardTemp;
    }


    @Override
    public int askCard(ArrayList<String> cards) {
        boolean choose = false;
        int scelta = -1;
        for(int index = 0; index < cards.size(); index++){
            System.out.println("[ "+index+ "] "+cards.get(index));
        }
        System.out.println("Choose your card");
        while(!choose){
            scelta=inputNumber();
            if(scelta <= cards.size() && scelta >= 0 ){
                System.out.println("Got it! "+ scelta);
                choose=true;
            }
        }
        return scelta;
    }

    @Override
    public ArrayList<Box> initializeWorker(){
        ArrayList<Box> boxes= new ArrayList<>();
        int indexWorker = 0;
        while(indexWorker < 2){

            System.out.println("Place the Worker "+indexWorker);

            int row = rowSelected();
            int column = columnSelected();
            if(boxes.size() == 0 ){
                boxes.add(board.getBox(row,column));
                indexWorker++;
            }
            else{
                if(boxes.get(0).getRow() != row || boxes.get(0).getColumn() != column){
                    boxes.add(board.getBox(row,column));
                    indexWorker++;
                }
            }
        }
        return boxes;
    }



    @Override
    public ObjWorkerToMove askWorker(AskWorkerToMoveEvent askWorkerToMoveEvent) {

        int row1 = askWorkerToMoveEvent.getRow1();
        int column1 = askWorkerToMoveEvent.getColumn1();
        int row2 = askWorkerToMoveEvent.getRow2();
        int column2 = askWorkerToMoveEvent.getColumn2();

        System.out.println("You're going to do your move-> What Worker You wanna move?");
        System.out.println("[ 0 ] -> "+ " in position : "+ row1 + " <-row   " + column1 + " <-column");
        System.out.println("[ 1 ] -> "+ " in position : "+ row2 + " <-row   " + column2 + " <-column");
        System.out.println("Control the board and choose...");
        printBoard(false);

        int intInputValue = twoNumbers();
        ObjWorkerToMove objWorkerToMove;
        if (intInputValue == 0){
            objWorkerToMove = new ObjWorkerToMove(1, row1, column1,false);
        }else{
            objWorkerToMove = new ObjWorkerToMove(2, row2, column2,false);
        }
        return objWorkerToMove;
    }

    @Override
    public ObjWorkerToMove areYouSure(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        int row ;
        int column;
        int indexWorker = askWorkerToMoveEvent.getIndexWorker();
        if(indexWorker == 1){
            row = askWorkerToMoveEvent.getRow1();
            column = askWorkerToMoveEvent.getColumn1();
        }else{
            row = askWorkerToMoveEvent.getRow2();
            column = askWorkerToMoveEvent.getColumn2();
        }

        System.out.println("Are You sure you want move the "+indexWorker+" worker? ");
        System.out.println("Position : "+ row + " <-row   " + column + " <-column");

        System.out.println("[ 0 ] -> "+ " YES");
        System.out.println("[ 1 ] -> "+ " NO");

        int intInputValue = twoNumbers();
        if(intInputValue == 0){
            return new ObjWorkerToMove(indexWorker, row, column,true);
        }else{
            return askWorker(askWorkerToMoveEvent);
        }
    }

    @Override
    public ObjMove moveWorker(AskMoveEvent askMoveEvent) {
        int row = askMoveEvent.getRow1();
        int column = askMoveEvent.getColumn1();
        int indexWorker = askMoveEvent.getIndexWorker();

        ObjMove objMove = new ObjMove(indexWorker, row, column, 0,0,true);

        if (askMoveEvent.isWrongBox()){
            wrongMove();
        }
        System.out.println("you have chosen the worker "+ indexWorker);
        System.out.println("Position : "+ row + " <-row  " + column + " <-column");
        System.out.println("You' re going to do your move -> What Position You wanna reach ? ");

        int intInputValue = rowSelected();
        objMove.setRow(intInputValue);

        intInputValue = columnSelected();
        objMove.setColumn(intInputValue);
        return objMove;
    }

    @Override
    public ObjMove anotherMove(AskMoveEvent askMoveEvent) {
        System.out.println("You Have the possibility to make another move");
        System.out.println("Do You want to?: ");
        System.out.println("[ 1 ] -> "+ "YES");
        System.out.println("[ 0 ] -> "+ "NO");

        int intInputValue = twoNumbers();
        if(intInputValue == 1){
            return new ObjMove(true);
        }else{
            ObjMove objMove = moveWorker(askMoveEvent);
            objMove.setDone(false);
            return objMove;
        }
    }
    @Override
    public void wrongMove(){
        System.out.println("You Made the wrong move: the Box was unreachable");
        System.out.println("Repeat the move...and now don't make the same mistake ");
    }

    @Override
    public ObjBlock buildMove(AskBuildEvent askBuildEvent) {
        int indexWorker = askBuildEvent.getIndexWorker();
        int rowWorker = askBuildEvent.getRowWorker();
        int columnWorker = askBuildEvent.getColumnWorker();
        ObjBlock objBlock = new ObjBlock(indexWorker, rowWorker, columnWorker);
        int intInputValue;

        if(askBuildEvent.isWrongBox()){
            System.out.println("You choose an unreachable box. Please, choose a reachable one");
        }else {
            System.out.println("You're going to build now -> Where do you wanna build?");
        }
        System.out.println("Control the board and choose a box");

        intInputValue = rowSelected();
        objBlock.setRowBlock(intInputValue);

        intInputValue = columnSelected();
        objBlock.setColumnBlock(intInputValue);

        //todo (G) Se atlas chiedere se vuole mettere una cupola. Passare un boolean e inserirlo in tutti i metodi buildMove (solo Atlas andrà ad utilizzarlo)

        //Verificare che la casella selezionata sia effettivamente valida e non abbia fatto lo stronzo (Facciamo lato server)
        return objBlock;
    }

    @Override
    public ObjBlock anotherBuild(AskBuildEvent askBuildEvent) {
        System.out.println("Your god gives you the possibility to make another build.");
        System.out.println("Do You want to?: ");
        System.out.println("[ 1 ] -> "+ "YES");
        System.out.println("[ 0 ] -> "+ "NO");

        int inputValue = twoNumbers();
        if(inputValue == 0){
            ObjBlock objBlock = buildMove(askBuildEvent);
            objBlock.setDone(false);
            return objBlock;
        }else {
            return new ObjBlock(true);
        }
    }



    //Questo metodo va richiamato se si vuole un numero da tastiera
    public int inputNumber(){
        int intInputValue = 0;
        while (true) {
            ///System.out.println("Enter a whole number.");
            String inputString = input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                System.out.println("Correct input, exit");
                return intInputValue;
            } catch (NumberFormatException ne) {
                System.out.println("Input is not a number, continue");
            }
        }
    }

    public int twoNumbers(){
        int intInputValue;
        intInputValue = inputNumber();
        while(intInputValue >= 2 || intInputValue < 0){
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    public int inputTwoOrThree(){
        int intInputValue;
        intInputValue = inputNumber();
        while(intInputValue <2 ||  intInputValue >3){
            System.out.println("Select again");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }


    //Questo metodo serve per selezionare una riga che sia raggiungibile
    public int rowSelected(){
        int intInputValue;
        System.out.println("Select row:");
        intInputValue = inputNumber();
        while(!boxReachable(intInputValue)){
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    //Questo metodo serve per selezionare una colonna che sia raggiungibile
    public int columnSelected(){
        int intInputValue;
        System.out.println("Select column:");
        intInputValue = inputNumber();
        while(!boxReachable(intInputValue)){
            System.out.println("Select again the column. You cannot reach that column");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    //Questo metodo controlla se il numero inserito può appartenere a una casella adiacente
    public boolean boxReachable (int input){
        return input >= 0 && input < 5;
    }
    @Override
    public void printBoard(boolean printReachable) {
        if(!printReachable){
            print();
        }else{
            printReachable();
        }
    }

    public String printByIndexPlayer(int row, int column){
        if(board.getBox(row, column).isReachable()){
            return "X";
        }else {
            if (board.getBox(row, column).getWorker() != null) {
                if (board.getBox(row, column).getWorker().getIndexPlayer() == 0) {
                    return Color.RED_BOLD + board.getBox(row, column).print() + Color.RESET;
                } else if (board.getBox(row, column).getWorker().getIndexPlayer() == 1) {
                    return Color.YELLOW_BOLD + board.getBox(row, column).print() + Color.RESET;
                } else {
                    return Color.CYAN_BOLD + board.getBox(row, column).print() + Color.RESET;
                }
            } else return " ";
        }
    }
    public void print(){
        System.out.println();
        System.out.println();
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "0"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "1"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "2"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "3"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "4"+ " "+ " "+ " "+ " ");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(0,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "0"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(0,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(0,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(1,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "1"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(1,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(1,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(2,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "2"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(2,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(2,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(3,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "3"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(3,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(3,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(4,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "4"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(4,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(4,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
        System.out.println();
        System.out.println();
    }

    public void printReachable() {
        System.out.println();
        System.out.println();
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "0"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "1"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "2"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "3"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "4"+ " "+ " "+ " "+ " ");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "0"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(0,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(0,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "1"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(1,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(1,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "2"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(2,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(2,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "3"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(3,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(3,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
        System.out.println(" "+ " "+ " "+ " "+ "||"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ "4"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(4,0)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,1)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,2)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,3)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(4,4)+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
        System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
        System.out.println();
        System.out.println();
    }
}

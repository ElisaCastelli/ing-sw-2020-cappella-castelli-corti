package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.events.AskBuildEvent;
import it.polimi.ingsw.network.events.AskMoveEvent;
import it.polimi.ingsw.network.events.AskWorkerToMoveEvent;
import it.polimi.ingsw.network.objects.ObjBlock;
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

        System.out.println("You're going to do your move-> What Worker You wanna move?");
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
        System.out.println("You're going to do your move-> What Position You wanna reach?");

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
        //todo Ila hai invertito: 0 è sì, voglio fare un'altra mossa (G)
        if(intInputValue==0){
            return new ObjMove(true);
        }else{
            ObjMove objMove = moveWorker(askMoveEvent);
            objMove.setDone(false);
            return objMove;
        }
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

        intInputValue = rowSelected(rowWorker);
        objBlock.setRowBlock(intInputValue);

        intInputValue = columnSelected(columnWorker);
        objBlock.setColumnBlock(intInputValue);

        //todo (G) Se atlas chiedere se vuole mettere una cupola. Passare un boolean e inserirlo in tutti i metodi buildMove (solo Atlas andrà ad utilizzarlo)

        //Verificare che la casella selezionata sia effettivamente valida e non abbia fatto lo stronzo (Facciamo lato server)
        return objBlock;
    }

    @Override
    public ObjBlock anotherBuild(AskBuildEvent askBuildEvent) {
        System.out.println("Your god gives you the possibility to make another build.");
        System.out.println("Do You want to?: ");
        System.out.println("[ 0 ] -> "+ "YES");
        System.out.println("[ 1 ] -> "+ "NO");

        int inputValue = inputNumber();
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
            System.out.println("Enter a whole number.");
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

    //Questo metodo serve per selezionare una riga che sia raggiungibile
    public int rowSelected(int rowWorker){
        int intInputValue;
        System.out.println("Select row:");
        intInputValue = inputNumber();
        while(!boxReachable(rowWorker, intInputValue)){
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    //Questo metodo serve per selezionare una colonna che sia raggiungibile
    public int columnSelected(int columnWorker){
        int intInputValue;
        System.out.println("Select column:");
        intInputValue = inputNumber();
        while(!boxReachable(columnWorker, intInputValue)){
            System.out.println("Select again the column. You cannot reach that column");
            intInputValue = inputNumber();
        }
        return intInputValue;
    }

    //Questo metodo controlla se il numero inserito può appartenere a una casella adiacente
    public boolean boxReachable (int rowOrColumnWorker, int input){
        return input >= 0 && input < 5 && input <= rowOrColumnWorker+1 && input >= rowOrColumnWorker-1;
    }
}

package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.ack.AckMove;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.building.Block;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.util.ArrayList;
import java.util.Scanner;

public class CLIView extends View {

    SendMessageToServer sendMessageToServer;
    private static final int numCards = 14;
    private static int indexPlayer = -1;
    private int nPlayer;
    private ArrayList<User> usersArray;
    private Board board;

    public CLIView(SendMessageToServer sendMessageToServer) {
        this.sendMessageToServer = sendMessageToServer;
    }

    @Override
    public void askWantToPlay(AskWantToPlay askWantToPlay){
        Thread thread = new Thread(() -> {
            //santoriniName();
            System.out.println("sto cencando di entare nella partita");
            sendMessageToServer.sendAskWantToPlay(askWantToPlay);
        });
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void updateBoard(UpdateBoardEvent updateBoardEvent) {
        this.usersArray = updateBoardEvent.getUserArray();
        this.board = updateBoardEvent.getBoard();
        printBoard(updateBoardEvent.isShowReachable(), updateBoardEvent.getCurrentClientPlaying());
    }

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public void askNPlayer() {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println(Color.CYAN+"How many Player ? [ 2 o 3 ]"+Color.RESET);
            int response=inputTwoOrThree(input);
            sendMessageToServer.sendNPlayer(response);
        });
        thread.setDaemon(true);
        thread.start();
    }
    @Override
    public void askPlayer(int clientIndex){
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            String name = askName(input);
            int age = askAge(input);
            sendMessageToServer.sendPlayer(name, age, clientIndex);
        });
        thread.setDaemon(true);
        thread.start();
    }

    public String askName(Scanner input) {
        System.out.println("Player name :");
        return input.nextLine();
    }
    public int askAge(Scanner input) {
        System.out.println("Player age : ");
        return inputNumber(input);
    }



    @Override
    public void setNPlayer(int nPlayer){
        Thread thread = new Thread(() -> {
            System.out.println("Start");
            this.nPlayer = nPlayer;
            sendMessageToServer.sendAckStartGame();
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void setIndexPlayer(ObjState objState){
        indexPlayer = objState.getIndexPlayer();
        if(indexPlayer == 0) {
            System.out.println("I have to play");
            sendMessageToServer.sendAckPlayer();
        }else{
            System.out.println("I've to wait my turn");
        }
    }

    @Override
    public int getIndexPlayer(){
        return indexPlayer;
    }

    @Override
    public void ask3Card(ArrayList<String> cards) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            while(input.hasNext()) {
                input.next();
            }
            ArrayList<Integer> cardTemp = new ArrayList<>();
            boolean[] scelte = new boolean[cards.size()];
            for (int i = 0; i < cards.size(); i++) {
                scelte[i] = false;
            }
            System.out.println("Scegli gli indici di " + nPlayer + " carte");
            for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
                System.out.println("[ " + cardIndex + "] " + cards.get(cardIndex));
            }

            while (cardTemp.size() < nPlayer) {
                int cardDrawn = inputNumber(input);
                while (cardDrawn >= numCards || cardDrawn < 0 || scelte[cardDrawn]) {
                    System.out.println("Select again the card.");
                    cardDrawn = inputNumber(input);
                }
                System.out.println("Scelta carta numero " + cardDrawn);
                cardTemp.add(cardDrawn);
                scelte[cardDrawn] = true;
            }

            sendMessageToServer.send3card(cardTemp);

        });
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void askCard(ArrayList<String> cards) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            while(input.hasNext()) {
                input.next();
            }
            boolean choose = false;
            int scelta = -1;
            for (int index = 0; index < cards.size(); index++) {
                System.out.println("[ " + index + "] " + cards.get(index));
            }
            while (!choose) {
                System.out.println("Choose your card");
                scelta = inputNumber(input);
                if (scelta < cards.size() && scelta >= 0) {
                    System.out.println("Got it! " + scelta);
                    choose = true;
                }
            }
            sendMessageToServer.sendCard(scelta, indexPlayer);
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void initializeWorker(){
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            ArrayList<Box> boxes = new ArrayList<>();
            int indexWorker = 0;
            while (indexWorker < 2) {

                System.out.println("Place the Worker " + indexWorker);

                int row = rowSelected(input);
                int column = columnSelected(input);
                if (boxes.size() == 0) {
                    boxes.add(board.getBox(row, column));
                    indexWorker++;
                } else {
                    if (boxes.get(0).getRow() != row || boxes.get(0).getColumn() != column) {
                        boxes.add(board.getBox(row, column));
                        indexWorker++;
                    }
                }
            }
            sendMessageToServer.sendWorker(boxes,indexPlayer);
        });
        thread.setDaemon(true);
        thread.start();
    }


    @Override
    public void askWorker(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            int row1 = askWorkerToMoveEvent.getRow1();
            int column1 = askWorkerToMoveEvent.getColumn1();
            int row2 = askWorkerToMoveEvent.getRow2();
            int column2 = askWorkerToMoveEvent.getColumn2();

            System.out.println("You're going to do your move-> What Worker You wanna move?");
            System.out.println("[ 0 ] -> "+ " in position : "+ row1 + " <-row   " + column1 + " <-column");
            System.out.println("[ 1 ] -> "+ " in position : "+ row2 + " <-row   " + column2 + " <-column");
            System.out.println("Control the board and choose...");
            printBoard(false, askWorkerToMoveEvent.getCurrentClientPlaying());

            int intInputValue = twoNumbers(input);
            ObjWorkerToMove objWorkerToMove;
            if (intInputValue == 0){
                objWorkerToMove = new ObjWorkerToMove(1, row1, column1,false);
            }else{
                objWorkerToMove = new ObjWorkerToMove(2, row2, column2,false);
            }
            sendMessageToServer.sendWorkerToMove(objWorkerToMove);
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void areYouSure(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            int row;
            int column;
            int indexWorker = askWorkerToMoveEvent.getIndexWorker();
            if (indexWorker == 1) {
                row = askWorkerToMoveEvent.getRow1();
                column = askWorkerToMoveEvent.getColumn1();
            } else {
                row = askWorkerToMoveEvent.getRow2();
                column = askWorkerToMoveEvent.getColumn2();
            }

            System.out.println("Are You sure you want move the " + indexWorker + " worker? ");
            System.out.println("Position : " + row + " <-row   " + column + " <-column");

            System.out.println("[ 1 ] -> " + " YES")
            ;
            System.out.println("[ 0 ] -> " + " NO");

            int intInputValue = twoNumbers(input);
            if (intInputValue == 1) {
                ObjWorkerToMove objWorkerToMove = new ObjWorkerToMove(indexWorker, row, column, true);
                sendMessageToServer.sendWorkerToMove(objWorkerToMove);
            } else {
                askWorker(askWorkerToMoveEvent);
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("You Have the possibility to Build a Block before moving the Worker");
            System.out.println("If you decide to do so, You'll not be able to move up a building with your Worker");
            System.out.println("Do You want to?: ");
            System.out.println("[ 1 ] -> " + "YES");
            System.out.println("[ 0 ] -> " + "NO");

            int intInputValue = twoNumbers(input);
            ObjBlockBeforeMove objBlockBeforeMove =  new ObjBlockBeforeMove(indexWorker, rowWorker, columnWorker, false);
            if(intInputValue == 1){
                objBlockBeforeMove.setWantToBuild(true);
            }

            sendMessageToServer.sendBlockBeforeMove(objBlockBeforeMove);


        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void moveWorker(AskMoveEvent askMoveEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            int row = askMoveEvent.getRow();
            int column = askMoveEvent.getColumn();
            int indexWorker = askMoveEvent.getIndexWorker();

            ObjMove objMove = new ObjMove(indexWorker, row, column, 0, 0, false);

            if (askMoveEvent.isWrongBox()) {
                wrongMove();
            }
            System.out.println("you have chosen the worker " + indexWorker);
            System.out.println("Position : " + row + " <-row  " + column + " <-column");
            System.out.println("You' re going to do your move -> What Position You wanna reach ? ");

            int intInputValue = rowSelected(input);
            objMove.setRow(intInputValue);

            intInputValue = columnSelected(input);
            objMove.setColumn(intInputValue);

            sendMessageToServer.sendMoveWorker(objMove);
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void anotherMove(AskMoveEvent askMoveEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("You Have the possibility to make another move");
            System.out.println("Do You want to?: ");
            System.out.println("[ 1 ] -> " + "YES");
            System.out.println("[ 0 ] -> " + "NO");

            int intInputValue = twoNumbers(input);
            if (intInputValue == 0) {
                AckMove ackMove = new AckMove(askMoveEvent.getIndexWorker(), askMoveEvent.getRow(), askMoveEvent.getColumn());
                sendMessageToServer.sendAckMove(ackMove);
            } else {
                moveWorker(askMoveEvent);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void wrongMove(){
        System.out.println("You Made the wrong move: the Box was unreachable");
        System.out.println("Repeat the move...and now don't make the same mistake ");
    }

    @Override
    public void buildMove(AskBuildEvent askBuildEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            int indexWorker = askBuildEvent.getIndexWorker();
            int rowWorker = askBuildEvent.getRowWorker();
            int columnWorker = askBuildEvent.getColumnWorker();
            ObjBlock objBlock = new ObjBlock(indexWorker, rowWorker, columnWorker, askBuildEvent.isFirstTime(), askBuildEvent.isSpecialTurn());
            int intInputValue;

            if (askBuildEvent.isWrongBox()) {
                wrongMove();
            } else {
                System.out.println("You're going to build now -> Where do you wanna build?");
            }
            System.out.println("^^ Control the board and choose a box ^^");
            printPossibleBlocks(rowWorker, columnWorker);

            intInputValue = rowSelected(input);
            objBlock.setRowBlock(intInputValue);

            intInputValue = columnSelected(input);
            objBlock.setColumnBlock(intInputValue);

            System.out.println("You selected the Box: "+ "( "+ objBlock.getRowBlock()+" , "+ objBlock.getColumnBlock()+" )");
            System.out.println("Let me Know what Block you want to build, select the correct index");
            int inputPossibleBlock = twoNumbers(input);

            objBlock.setPossibleBlock(inputPossibleBlock);
            if (!askBuildEvent.isFirstTime()) {
                objBlock.setDone(true);
            }
            sendMessageToServer.sendBuildMove(objBlock);
        });
        thread.setDaemon(true);
        thread.start();

    }

    @Override
    public void anotherBuild(AskBuildEvent askBuildEvent) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("Your god gives you the possibility to make another build.");
            System.out.println("Do You want to?: ");
            System.out.println("[ 1 ] -> "+ "YES");
            System.out.println("[ 0 ] -> "+ "NO");

            int inputValue = twoNumbers(input);
            if(inputValue == 1){
                buildMove(askBuildEvent);
            }else {
                ObjBlock objBlock = new ObjBlock(true);
                sendMessageToServer.sendBuildMove(objBlock);
            }
        });
        thread.setDaemon(true);
        thread.start();

    }

    public void printPossibleBlocks(int row, int column){
        Box posWorker = board.getBox(row, column);
        System.out.println("Here you can see what kind of block you can build");
        for (int indexBoxNextTo = 0; indexBoxNextTo < posWorker.getBoxesNextTo().size(); indexBoxNextTo++) {
            Box boxNextTo = posWorker.getBoxesNextTo().get(indexBoxNextTo);
            if(boxNextTo != null){
                System.out.print("Box : " + "( "+ boxNextTo.getRow()+" , "+ boxNextTo.getColumn() +" )"+ " you can build --> ");
                for(int size = 0; size < boxNextTo.getPossibleBlock().size() ; size++ ){
                    Block block = boxNextTo.getPossibleBlock().get(size);
                    System.out.print("["+ size +"]"+block.toString()+"  ");
                }
                System.out.println();
            }
        }
    }

    //Questo metodo va richiamato se si vuole un numero da tastiera
    public int inputNumber(Scanner input){
        int intInputValue = 0;
        while (true) {
            ///System.out.println("Enter a whole number.");
            String inputString = input.nextLine();
            try {
                intInputValue = Integer.parseInt(inputString);
                ///System.out.println("Correct input, exit");
                return intInputValue;
            } catch (NumberFormatException ne) {
                ///System.out.println("Input is not a number, continue");
            }
        }
    }
    public int twoNumbers(Scanner input){
        int intInputValue;
        intInputValue = inputNumber(input);
        while(intInputValue >= 2 || intInputValue < 0){
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }
    public int inputTwoOrThree(Scanner input){
        int intInputValue;
        intInputValue = inputNumber(input);
        while(intInputValue <2 ||  intInputValue > 3){
            System.out.println("Select again");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }
    //Questo metodo serve per selezionare una riga che sia raggiungibile
    public int rowSelected(Scanner input){
        int intInputValue;
        System.out.println("Select row:");
        intInputValue = inputNumber(input);
        while(!boxReachable(intInputValue)){
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }
    //Questo metodo serve per selezionare una colonna che sia raggiungibile
    public int columnSelected(Scanner input){
        int intInputValue;
        System.out.println("Select column:");
        intInputValue = inputNumber(input);
        while(!boxReachable(intInputValue)){
            System.out.println("Select again the column. You cannot reach that column");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }
    //Questo metodo controlla se il numero inserito può appartenere a una casella adiacente
    public boolean boxReachable (int input){
        return input >= 0 && input < 5;
    }


    public String printByIndexPlayer(int row, int column, boolean reach){
        if(board.getBox(row, column).isReachable() && reach){
            if (board.getBox(row, column).getWorker() != null) {
                if (board.getBox(row, column).getWorker().getIndexPlayer() == 0) {
                    return Color.RED_BOLD + board.getBox(row, column).print() + Color.RESET+ " X";
                } else if (board.getBox(row, column).getWorker().getIndexPlayer() == 1) {
                    return Color.YELLOW_BOLD + board.getBox(row, column).print() + Color.RESET+ " X";
                } else {
                    return Color.CYAN_BOLD + board.getBox(row, column).print() + Color.RESET+ " X";
                }
            } else
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

    public String printName(int indexPlayer){
        return usersArray.get(indexPlayer).getName();
    }

    public String printGod(int indexPlayer){
        return usersArray.get(indexPlayer).getNameCard();
    }

    public String printStatePlayer(int indexPlayer, int currentPlayer){
        if(usersArray.get(indexPlayer).isDead()){
            return "Is dead";
        }else if (usersArray.get(indexPlayer).getClient() == currentPlayer){
            return "Is playing";
        }else{
            return "Is waiting";
        }
    }

    public void printBoard(boolean reach, int currentPlayer){
        if (nPlayer == 2){
            System.out.println();
            System.out.println();
            System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "0"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "1"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "2"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "3"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "4"+ " "+ " "+ " "+ " ");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(0,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.RED_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ "0"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(0,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(0,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printName(0));
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printGod(0));
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ " "+ " "+ " "+ " "+ " "+ " "+ printStatePlayer(0, currentPlayer));
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(1,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.RED_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ "1"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(1,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(1,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.YELLOW_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printName(1));
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ " "+ " "+ " "+ " "+ " "+ " "+ printGod(1));
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(2,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printStatePlayer(1, currentPlayer));
            System.out.println(" "+ " "+ "2"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(2,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(2,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.YELLOW_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#");
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(3,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ "3"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(3,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(3,4,reach)+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(4,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ "4"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(4,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(4,4,reach)+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
            System.out.println();
            System.out.println();
        }else{
            System.out.println();
            System.out.println();
            System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "0"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "1"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "2"+ " "+" "+ " "+ " "+ " "+ " "+ " "+ "3"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "4"+ " "+ " "+ " "+ " ");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#");
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(0,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(0,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.RED_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ "0"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(0,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(0,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(0,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printName(0));
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printGod(0));
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ " "+ " "+ " "+ " "+ " "+ " "+ printStatePlayer(0, currentPlayer));
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(1,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(1,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.RED_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ "1"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(1,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(1,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(1,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.YELLOW_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printName(1));
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ " "+ " "+ " "+ " "+ " "+ " "+ printGod(1));
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(2,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(2,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printStatePlayer(1, currentPlayer));
            System.out.println(" "+ " "+ "2"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(2,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(2,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(2,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.YELLOW_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.CYAN_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ " "+ " "+ " "+ " "+ " "+ " "+ printName(2));
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(3,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(3,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printGod(2));
            System.out.println(" "+ " "+ "3"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(3,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(3,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(3,4,reach)+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ printStatePlayer(2, currentPlayer));
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ Color.CYAN_BOLD + "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ "*"+ Color.RESET);
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
            System.out.println(" "+ " "+ " "+ " "+ "||"+board.getBox(4,0).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,1).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,2).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,3).printsize()+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+board.getBox(4,4).printsize()+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ "4"+ " "+ "||"+ " "+ " "+ " "+printByIndexPlayer(4,0,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,1,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,2,reach)+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+printByIndexPlayer(4,3,reach)+ " "+ " "+ " "+ "|"+" "+ " "+" "+printByIndexPlayer(4,4,reach)+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "||"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "|"+" "+ " "+" "+ " "+ " "+ " "+ " "+ "||");
            System.out.println(" "+ " "+ " "+ " "+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+ "#"+"#"+ "#");
            System.out.println();
            System.out.println();
        }
    }

    public void santoriniName(){
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ "S"+ "S");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ "S"+ "S"+ "S"+ "S");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ " "+ " "+ " "+ " "+ "S"+ "S");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ " "+ " "+ " "+ " ");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ "S"+ " "+ " "+ " ");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S");
        System.out.println(" "+ " "+ " "+ " "+ " "+ "S"+ "S"+ " "+ " "+ " "+ " "+ "S"+ "S"+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ " "+ "A");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ "S"+ "S"+ "S"+ "S");
        System.out.println(" "+ " "+ " "+ " "+ " "+ " "+ " "+ "S"+ "S"+ "S"+ "S"+ " "+ " "+ " ");
    }

    @Override
    public void loserEvent() {
        System.out.println("Game Over"); //todo da sistemare
    }

    @Override
    public void winnerEvent() {
        System.out.println("You Won"); //todo da sistemare
    }

    @Override
    public void someoneWon() {
        System.out.println("An opponent won. Game Over"); //todo da sistemare
    }

    @Override
    public void whoHasLost() {
        System.out.println("An opponent lost"); //todo da sistemare
    }

    @Override
    public void printHeartBeat(ObjHeartBeat objHeartBeat){
        Thread thread = new Thread(() -> {
            System.out.println(objHeartBeat.getMessageHeartbeat());
            sendMessageToServer.sendPong(objHeartBeat.getClientIndex());
        });
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void closingConnectionEvent(int indexClient, boolean gameNotAvailable) {
        Thread thread = new Thread(() -> {
            if(gameNotAvailable){
                System.out.println("A game has already started -> Try to connect later");
            }else {
                System.out.println("A client is not responding, the connection will be closed");
            }
            sendMessageToServer.sendAckClosingConnection(indexClient);
        });
        thread.setDaemon(true);
        thread.start();
    }
}


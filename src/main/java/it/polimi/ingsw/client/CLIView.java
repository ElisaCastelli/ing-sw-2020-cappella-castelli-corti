package it.polimi.ingsw.client;

import it.polimi.ingsw.network.*;
import it.polimi.ingsw.network.ack.AckMove;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.building.Block;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that manages the CLI view
 */
public class CLIView implements View {
    /**
     * Object use to send message to the server
     */
    private SendMessageToServer sendMessageToServer;
    /**
     * Number of the gamers playing the match
     */
    private int nPlayer;
    /**
     * Array of User objects that contains some information about the players
     */
    private ArrayList<User> usersArray;
    /**
     * This is a copy of the object Board that describe the game field and that is used to print the actual game situation
     */
    private Board board;

    /**
     * Empty constructor
     */
    public CLIView() {
    }

    /**
     * Setter method for the SendMessageToServer object
     *
     * @param sendMessageToServer is the object to set
     */
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer) {
        this.sendMessageToServer = sendMessageToServer;
    }

    /**
     * Method called from the VisitorClient when the clientHandler received an AskWantToPlay message
     *
     * @param askWantToPlay is the message send from the server to ask to the player if he wants to play
     */
    @Override
    public void askWantToPlay(AskWantToPlayEvent askWantToPlay) {
        Thread thread = new Thread(() -> {
            clearScreen();
            santoriniName();
            System.out.println("I'm trying to join a game");
            sendMessageToServer.sendAskWantToPlay(askWantToPlay);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the clientHandler received an YouCanPlay message
     */
    @Override
    public void youCanPlay() {
        System.out.println("You can play");
    }

    /**
     * Method called from the VisitorClient when the clientHandler received an YouHaveToWait message
     */
    @Override
    public void youHaveToWait() {
        System.out.println("You have to wait");
    }

    /**
     * Method called from the VisitorClient everytime the clientHandler received an updateBoard message
     * to update and print the users state and the actual board situation
     *
     * @param usersArray      is the ArrayList of users taking part to the game
     * @param board           is the object Board describe the game field
     * @param isShowReachable is a boolean that indicates if the printed board has to show the reachable boxes
     * @param currentPlaying  is the integer index of the gamer playing in this turn
     * @param indexClient     is the index of the client associated with the player
     */
    @Override
    public void updateBoard(ArrayList<User> usersArray, Board board, boolean isShowReachable, int currentPlaying, int indexClient) {
        clearScreen();
        this.usersArray = usersArray;
        this.board = board;
        printBoard(isShowReachable, currentPlaying);
    }

    /**
     * Method called from the VisitorClient of the first player connected when the ClientHandler receives an AskNPlayer message.
     * It's used to ask the number of the players taking part in the game, the number of client the server must wait for before
     * start the game
     */
    @Override
    public void askNPlayer() {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println(Color.CYAN + "How many Player ? [ 2 o 3 ]" + Color.RESET);
            int response = inputTwoOrThree(input);
            sendMessageToServer.sendNPlayer(response);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskPlayer message.
     * It's used to ask name and age to the player and then to send this data to the server
     *
     * @param clientIndex is the index of the client associated with the player
     */
    @Override
    public void askPlayer(int clientIndex) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            String name = askName(input);
            int age = askAge(input);
            sendMessageToServer.sendPlayer(name, age, clientIndex);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method used to manage the input of the entering of the player name
     *
     * @param input is the Scanner object used to read the input
     * @return the name string read from the Scanner
     */
    public String askName(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Player name :");
        return input.nextLine();
    }

    /**
     * Method used to manage the input of the entering of the player age
     *
     * @param input is the Scanner object used to read the input
     * @return the age integer from the Scanner
     */
    public int askAge(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Player age : ");
        return inputNumber(input);
    }

    /**
     * Method used to set the number of the gamers playing and then send an AckStartGame message to answer
     * to the startGameEvent message
     *
     * @param nPlayer is the number of the gamers playing
     */
    @Override
    public void setNPlayer(int nPlayer) {
        Thread thread = new Thread(() -> {
            System.out.println("Start");
            this.nPlayer = nPlayer;
            sendMessageToServer.sendAckStartGame();
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method used to set the personal index of the player to know if the player can play or must wait his turn
     * and then send an AckPlayer message to reply to the ObjState message
     *
     * @param indexPlayer is the index of the player
     */
    @Override
    public void setIndexPlayer(int indexPlayer) {
        if (indexPlayer == 0) {
            System.out.println("I have to play");
            sendMessageToServer.sendAckPlayer();
        } else {
            System.out.println("I've to wait my turn");
        }
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an Ask3Card message.
     * It's received only from the youngest player that used it to choose three cards for all the participants
     * and then to send them to the server
     *
     * @param cards is the ArrayList of cards name from which the user can choose
     */
    @Override
    public void askNCard(ArrayList<String> cards) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            ArrayList<Integer> cardTemp = new ArrayList<>();
            boolean[] cardsChoose = new boolean[cards.size()];
            for (int i = 0; i < cards.size(); i++) {
                cardsChoose[i] = false;
            }
            System.out.println("Choose the index of " + nPlayer + " cards");
            for (int cardIndex = 0; cardIndex < cards.size(); cardIndex++) {
                System.out.println("[ " + cardIndex + "] " + cards.get(cardIndex));
            }
            while (cardTemp.size() < nPlayer) {
                int cardDrawn = inputNumber(input);
                while (cardDrawn >= cards.size() || cardDrawn < 0 || cardsChoose[cardDrawn]) {
                    System.out.println("Select again the card");
                    cardDrawn = inputNumber(input);
                }
                System.out.println("Card number " + cardDrawn + " choose!");
                cardTemp.add(cardDrawn);
                cardsChoose[cardDrawn] = true;
            }
            sendMessageToServer.send3card(cardTemp);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskCard message.
     * It's used by a player to choose his own card and then to send it to the server
     *
     * @param cards is the ArrayList of the cards name from which the user can choose
     */
    @Override
    public void askCard(ArrayList<String> cards) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
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
            sendMessageToServer.sendCard(scelta);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskInizializeWorker message.
     * It's used to ask to the player in which cells he wants to initialize his two workers
     * and then send this two positions to the server
     */
    @Override
    public void initializeWorker() {
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
            sendMessageToServer.sendWorker(boxes);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives for the first time an AskWorkerToMoveEvent message.
     * This method is used by the player to choose the worker to move
     *
     * @param row1           is the row of the box occupied by the worker 1
     * @param column1        is the column of the box occupied by the worker 1
     * @param row2           is the row of the box occupied by the worker 2
     * @param column2        is the column of the box occupied by the worker 2
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex    is the integer index associated to the client
     */
    @Override
    public void askWorker(int row1, int column1, int row2, int column2, int currentPlaying, int clientIndex) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);

            System.out.println("You're going to do your move-> What Worker You wanna move?");
            System.out.println("[ 0 ] -> " + " in position : " + row1 + " <-row   " + column1 + " <-column");
            System.out.println("[ 1 ] -> " + " in position : " + row2 + " <-row   " + column2 + " <-column");
            System.out.println("Control the board and choose...");
            printBoard(false, currentPlaying);

            int intInputValue = twoNumbers(input);
            ObjWorkerToMove objWorkerToMove;
            if (intInputValue == 0) {
                objWorkerToMove = new ObjWorkerToMove(1, row1, column1, false);
            } else {
                objWorkerToMove = new ObjWorkerToMove(2, row2, column2, false);
            }
            sendMessageToServer.sendWorkerToMove(objWorkerToMove);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives not for the first time an AskWorkerToMoveEvent message.
     * this method is used to ask to the player if he is sure about the worker to move chosen and in case to send an ObjWorkerToMove object to the server ,
     * otherwise to recall the method askWorker and permit to the player to make another choice
     *
     * @param row1           is the row of the box occupied by the worker 1
     * @param column1        is the column of the box occupied by the worker 1
     * @param row2           is the row of the box occupied by the worker 2
     * @param column2        is the column of the box occupied by the worker 2
     * @param indexWorker    is the integer index of the worker the player wants to move
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex    is the integer index associated to the client
     */
    @Override
    public void areYouSure(int row1, int column1, int row2, int column2, int indexWorker, int currentPlaying, int clientIndex) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            int row;
            int column;
            if (indexWorker == 1) {
                row = row1;
                column = column1;
            } else {
                row = row2;
                column = column2;
            }
            System.out.println("Are You sure you want move the " + indexWorker + " worker? ");
            System.out.println("Position : " + row + " <-row   " + column + " <-column");

            System.out.println("[ 1 ] -> " + " YES");
            System.out.println("[ 0 ] -> " + " NO");

            int intInputValue = twoNumbers(input);
            if (intInputValue == 1) {
                ObjWorkerToMove objWorkerToMove = new ObjWorkerToMove(indexWorker, row, column, true);
                sendMessageToServer.sendWorkerToMove(objWorkerToMove);
            } else {
                askWorker(row1, column1, row2, column2, currentPlaying, clientIndex);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * This method is used when the player select a worker that can't move
     *
     * @param row1           is the row of the box occupied by the worker 1
     * @param column1        is the column of the box occupied by the worker 1
     * @param row2           is the row of the box occupied by the worker 2
     * @param column2        is the column of the box occupied by the worker 2
     * @param indexWorker    is the integer index of the worker the player wants to move
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex    is the integer index associated to the client
     */
    @Override
    public void otherWorkerToMove(int row1, int column1, int row2, int column2, int indexWorker, int currentPlaying, int clientIndex) {
        System.out.println("Sorry but You selected the worker " + indexWorker + " , that can't move in this turn");
        System.out.println("Please pick the other one:");
        askWorker(row1, column1, row2, column2, currentPlaying, clientIndex);
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskBeforeBuildMove message.
     * It's used to ask if the players with a special god wants to build a block before move
     * and then to send an ObjBlockBeforeMove to the server using the object SenMessageToServer
     *
     * @param indexWorker  is the integer index of the worker the player wants to move
     * @param rowWorker    is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     */
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
            ObjBlockBeforeMove objBlockBeforeMove = new ObjBlockBeforeMove(indexWorker, rowWorker, columnWorker, false);
            if (intInputValue == 1) {
                objBlockBeforeMove.setWantToBuild(true);
            }
            sendMessageToServer.sendBlockBeforeMove(objBlockBeforeMove);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskMoveEVent message.
     * It's used to ask to the player in which position he wants to move his worker and then send to the server an ObjMove message using the object SenMessageToServer
     *
     * @param row            is the starting row of the worker to move
     * @param column         is the starting column of the worker to move
     * @param indexWorker    is the integer index of the worker the player chose to move
     * @param isWrongBox     is a boolean that indicates if the move is wrong
     * @param clientIndex    is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param firstTime      is a boolean that indicates if this is the first move tried in this turn
     */
    @Override
    public void moveWorker(int row, int column, int indexWorker, boolean isWrongBox, boolean firstTime, int clientIndex, int currentPlaying) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            ObjMove objMove = new ObjMove(indexWorker, row, column, 0, 0, false);
            objMove.setFirstTime(firstTime);

            if (isWrongBox) {
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

    /**
     * This method is used by player with special gods that can move more than once to ask if they want to move again
     *
     * @param row            is the row of the box occupied by the worker
     * @param column         is the column of the box occupied by the worker
     * @param indexWorker    is the integer index of the worker the player wants to move
     * @param isWrongBox     is a boolean that indicates if the move is wrong
     * @param firstTime      is a boolean that indicates if this is the first move tried in this turn
     * @param clientIndex    is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done           is a boolean used to indicates if the move turn is over
     */
    @Override
    public void anotherMove(int row, int column, int indexWorker, boolean isWrongBox, boolean firstTime, int clientIndex, int currentPlaying, boolean done) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("You have the possibility to make another move");
            System.out.println("Do You want to?: ");
            System.out.println("[ 1 ] -> " + "YES");
            System.out.println("[ 0 ] -> " + "NO");

            int intInputValue = twoNumbers(input);
            if (intInputValue == 0) {
                AckMove ackMove = new AckMove(indexWorker, row, column);
                sendMessageToServer.sendAckMove(ackMove);
            } else {
                moveWorker(row, column, indexWorker, isWrongBox, firstTime, clientIndex, currentPlaying);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * This method is called when a player tries to reach a box unreachable
     */
    public void wrongMove() {
        System.out.println("You Made the wrong move: the Box was unreachable");
        System.out.println("Repeat the move...and now don't make the same mistake ");
    }

    /**
     * Method used to ask to the player where he wants to build after showing him the reachable boxes
     *
     * @param rowWorker      is the row of the box occupied by the worker
     * @param columnWorker   is the column of the box occupied by the worker
     * @param indexWorker    is the integer index of the worker the player wants to move
     * @param isWrongBox     is a boolean that indicates if the move is wrong
     * @param isFirstTime    is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn  is a boolean used to identify special moves
     * @param clientIndex    is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done           is a boolean used to indicates if the move turn is over
     */
    @Override
    public void buildMove(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            ObjBlock objBlock = new ObjBlock(indexWorker, rowWorker, columnWorker, isFirstTime, isSpecialTurn);
            int intInputValue;
            if (isWrongBox) {
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

            System.out.println("You selected the Box: " + "( " + objBlock.getRowBlock() + " , " + objBlock.getColumnBlock() + " )");
            System.out.println("Let me Know what Block you want to build, select the correct index");
            int inputPossibleBlock = twoNumbers(input);
            objBlock.setPossibleBlock(inputPossibleBlock);
            sendMessageToServer.sendBuildMove(objBlock);
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * This method is used by player with special gods that can move build than once to ask him if he wants to
     *
     * @param rowWorker      is the row of the box occupied by the worker
     * @param columnWorker   is the column of the box occupied by the worker
     * @param indexWorker    is the integer index of the worker the player wants to move
     * @param isWrongBox     is a boolean that indicates if the move is wrong
     * @param isFirstTime    is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn  is a boolean used to identify special moves
     * @param clientIndex    is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done           is a boolean used to indicates if the move turn is over
     */
    @Override
    public void anotherBuild(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done) {
        Thread thread = new Thread(() -> {
            Scanner input = new Scanner(System.in);
            System.out.println("Your god gives you the possibility to make another build.");
            System.out.println("Do You want to?: ");
            System.out.println("[ 1 ] -> " + "YES");
            System.out.println("[ 0 ] -> " + "NO");

            int inputValue = twoNumbers(input);
            if (inputValue == 1) {
                buildMove(rowWorker, columnWorker, indexWorker, isWrongBox, isFirstTime, isSpecialTurn, clientIndex, currentPlaying, done);
            } else {
                ObjBlock objBlock = new ObjBlock(true);
                sendMessageToServer.sendBuildMove(objBlock);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * This method it's used to print the possible block to build in the chosen position
     *
     * @param row    is the integer of the row of the Box where the player wants to build
     * @param column is the integer of the column of the Box where the player wants to build
     */
    public void printPossibleBlocks(int row, int column) {
        Box posWorker = board.getBox(row, column);
        System.out.println("Here you can see what kind of block you can build");
        for (int indexBoxNextTo = 0; indexBoxNextTo < posWorker.getBoxesNextTo().size(); indexBoxNextTo++) {
            Box boxNextTo = posWorker.getBoxesNextTo().get(indexBoxNextTo);
            if (boxNextTo != null) {
                System.out.print("Box : " + "( " + boxNextTo.getRow() + " , " + boxNextTo.getColumn() + " )" + " you can build --> ");
                for (int size = 0; size < boxNextTo.getPossibleBlock().size(); size++) {
                    Block block = boxNextTo.getPossibleBlock().get(size);
                    System.out.print("[" + size + "]" + block.toString() + "  ");
                }
                System.out.println();
            }
        }
    }

    /**
     * This method is used to managed the inout of only numbers
     *
     * @param input is the Scanner object used to read the user inputs
     * @return the value read
     */
    private int inputNumber(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    /**
     * This method is used to managed the input only of 0,1 or 2
     *
     * @param input is the Scanner object used to read the user inputs
     * @return the value read
     */
    private int twoNumbers(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        intInputValue = inputNumber(input);
        while (intInputValue >= 2 || intInputValue < 0) {
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }

    /**
     * This method is used to managed the input only of 2 or 3
     *
     * @param input is the Scanner object used to read the user inputs
     * @return the value read
     */
    private int inputTwoOrThree(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        intInputValue = inputNumber(input);
        while (intInputValue < 2 || intInputValue > 3) {
            System.out.println("Select again");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }

    /**
     * This method is used to select a row reachable
     *
     * @param input is the Scanner object used to read the user inputs
     * @return the integer value of the row choose
     */
    private int rowSelected(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        System.out.println("Select row:");
        intInputValue = inputNumber(input);
        while (!boxReachable(intInputValue)) {
            System.out.println("Select again the row. You cannot reach that row");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }

    /**
     * This method is used to select a column reachable
     *
     * @param input is the Scanner object used to read the user inputs
     * @return the integer value of the columnn choose
     */
    private int columnSelected(Scanner input) {
        try {
            System.in.read(new byte[System.in.available()]);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int intInputValue;
        System.out.println("Select column:");
        intInputValue = inputNumber(input);
        while (!boxReachable(intInputValue)) {
            System.out.println("Select again the column. You cannot reach that column");
            intInputValue = inputNumber(input);
        }
        return intInputValue;
    }

    /**
     * This method is used to check if the input number can be associated with a box reachable
     *
     * @param input is the integer value to check
     * @return true if the value is correct or false on the other hand
     */
    private boolean boxReachable(int input) {
        return input >= 0 && input < 5;
    }

    /**
     * This method is used to return the string corresponding to the workers and the box reachable into a requested box
     *
     * @param row    is the integer used to point to the row of the box to be printed
     * @param column is the integer used to point to the column of the box to be printed
     * @param reach  is a boolean used to understand if the method must print the box reachable or only the workers
     * @return the string to print
     */
    private String printByIndexPlayer(int row, int column, boolean reach) {
        if (board.getBox(row, column).getWorker() != null) {
            if (board.getBox(row, column).getWorker().getIndexPlayer() == 0) {
                return Color.RED_BOLD + board.getBox(row, column).print() + Color.RESET;
            } else if (board.getBox(row, column).getWorker().getIndexPlayer() == 1) {
                return Color.YELLOW_BOLD + board.getBox(row, column).print() + Color.RESET;
            } else {
                return Color.CYAN_BOLD + board.getBox(row, column).print() + Color.RESET;
            }
        }
        if (board.getBox(row, column).isReachable() && reach) {
                return "X";
        } else {
            return " ";
        }
    }

    /**
     * This method is used to return the string corresponding to the box reachable into a requested box
     *
     * @param row    is the integer used to point to the row of the box to be printed
     * @param column is the integer used to point to the column of the box to be printed
     * @param reach  is a boolean used to understand if the method must print the box reachable or only the workers
     * @return the string to print
     */
    private String printReachable(int row, int column, boolean reach){
        if (board.getBox(row, column).isReachable() && reach) {
            if (board.getBox(row, column).getWorker() != null)
                return "X";
        }
        return " ";
    }

    /**
     * Method used to return a string of the player name
     *
     * @param indexPlayer is the integer index of the player requested
     * @return the name string of the player requested
     */
    private String printName(int indexPlayer) {
        return usersArray.get(indexPlayer).getName();
    }

    /**
     * Method used to return a string of the god name
     *
     * @param indexPlayer is the integer index of the player requested
     * @return the god name string of the player requested
     */
    private String printGod(int indexPlayer) {
        return usersArray.get(indexPlayer).getNameCard();
    }

    /**
     * Method used to return a string of the player state
     *
     * @param indexPlayer   is the integer index of the player requested
     * @param currentPlayer is the integer index of the player who is playing
     * @return the state string of the player requested
     */
    private String printStatePlayer(int indexPlayer, int currentPlayer) {
        if (usersArray.get(indexPlayer).isDead()) {
            return "Is dead";
        } else if (usersArray.get(indexPlayer).getClient() == currentPlayer) {
            return "Is playing";
        } else {
            return "Is waiting";
        }
    }

    /**
     * This method is called to print the game field and the list of the participants with their states, names and gods
     *
     * @param reach         is a boolean attribute that indicates if it needs to print the reachable boxes
     * @param currentPlayer is the index of the player who is playing his turn
     */
    private void printBoard(boolean reach, int currentPlayer) {

        if (nPlayer == 2) {
            System.out.println();
            System.out.println();
            System.out.println(" " + " " + " " + " " + " " + " " + " " + " " + "0" + " " + " " + " " + " " + " " + " " + " " + "1" + " " + " " + " " + " " + " " + " " + " " + "2" + " " + " " + " " + " " + " " + " " + " " + "3" + " " + " " + " " + " " + " " + " " + " " + "4" + " " + " " + " " + " ");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(0, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.RED_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + "0" + " " + "||" + " " + " " + " " + printByIndexPlayer(0, 0, reach) + " " + printReachable(0,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 1, reach) + " " + printReachable(0,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 2, reach) + " " + printReachable(0,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 3, reach) + " " + printReachable(0,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 4, reach) + " " + printReachable(0,4, reach) + " " + "||" + " " + " " + " " + " " + " " + " " + printName(0));
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printGod(0));
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + " " + " " + " " + " " + " " + " " + printStatePlayer(0, currentPlayer));
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(1, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.RED_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + "1" + " " + "||" + " " + " " + " " + printByIndexPlayer(1, 0, reach) + " " + printReachable(1,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 1, reach) + " " + printReachable(1,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 2, reach) + " " + printReachable(1,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 3, reach) + " " + printReachable(1,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 4, reach) + " " + printReachable(1,4, reach) + " " + "||" + " " + " " + " " + " " + Color.YELLOW_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printName(1));
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + " " + " " + " " + " " + " " + " " + printGod(1));
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(2, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printStatePlayer(1, currentPlayer));
            System.out.println(" " + " " + "2" + " " + "||" + " " + " " + " " + printByIndexPlayer(2, 0, reach) + " " + printReachable(2,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 1, reach) + " " + printReachable(2,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 2, reach) + " " + printReachable(2,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 3, reach) + " " + printReachable(2,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 4, reach) + " " + printReachable(2,4, reach) + " " + "||" + " " + " " + " " + " " + Color.YELLOW_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(3, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 4).printsize() + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + "3" + " " + "||" + " " + " " + " " + printByIndexPlayer(3, 0, reach) + " " + printReachable(3,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 1, reach) + " " + printReachable(3,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 2, reach) + " " + printReachable(3,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 3, reach) + " " + printReachable(3,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 4, reach) + " " + printReachable(3,4, reach) + " " + "||");
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(4, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 4).printsize() + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + "4" + " " + "||" + " " + " " + " " + printByIndexPlayer(4, 0, reach) + " " + printReachable(4,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 1, reach) + " " + printReachable(4,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 2, reach) + " " + printReachable(4,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 3, reach) + " " + printReachable(4,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 4, reach) + " " + printReachable(4,4, reach) + " " + "||");
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println();
            System.out.println();
        } else {
            System.out.println();
            System.out.println();
            System.out.println(" " + " " + " " + " " + " " + " " + " " + " " + "0" + " " + " " + " " + " " + " " + " " + " " + "1" + " " + " " + " " + " " + " " + " " + " " + "2" + " " + " " + " " + " " + " " + " " + " " + "3" + " " + " " + " " + " " + " " + " " + " " + "4" + " " + " " + " " + " ");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(0, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(0, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.RED_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + "0" + " " + "||" + " " + " " + " " + printByIndexPlayer(0, 0, reach) + " " + printReachable(0,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 1, reach) + " " + printReachable(0,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 2, reach) + " " + printReachable(0,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 3, reach) + " " + printReachable(0,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(0, 4, reach) + " " + printReachable(0,4, reach) + " " + "||" + " " + " " + " " + " " + " " + " " + printName(0));
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printGod(0));
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + " " + " " + " " + " " + " " + " " + printStatePlayer(0, currentPlayer));
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(1, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(1, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.RED_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + "1" + " " + "||" + " " + " " + " " + printByIndexPlayer(1, 0, reach) + " " + printReachable(1,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 1, reach) + " " + printReachable(1,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 2, reach) + " " + printReachable(1,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 3, reach) + " " + printReachable(1,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(1, 4, reach) + " " + printReachable(1,4, reach) + " " + "||" + " " + " " + " " + " " + Color.YELLOW_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printName(1));
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + " " + " " + " " + " " + " " + " " + printGod(1));
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(2, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(2, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printStatePlayer(1, currentPlayer));
            System.out.println(" " + " " + "2" + " " + "||" + " " + " " + " " + printByIndexPlayer(2, 0, reach) + " " + printReachable(2,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 1, reach) + " " + printReachable(2,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 2, reach) + " " + printReachable(2,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 3, reach) + " " + printReachable(2,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(2, 4, reach) + " " + printReachable(2,4, reach) + " " + "||" + " " + " " + " " + " " + Color.YELLOW_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.CYAN_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + " " + " " + " " + " " + " " + " " + printName(2));
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(3, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(3, 4).printsize() + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + printGod(2));
            System.out.println(" " + " " + "3" + " " + "||" + " " + " " + " " + printByIndexPlayer(3, 0, reach) + " " + printReachable(3,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 1, reach) + " " + printReachable(3,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 2, reach) + " " + printReachable(3,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 3, reach) + " " + printReachable(3,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(3, 4, reach) + " " + printReachable(3,4, reach) + " " + "||" + " " + " " + " " + " " + " " + " " + printStatePlayer(2, currentPlayer));
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||" + " " + " " + " " + " " + Color.CYAN_BOLD + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + "*" + Color.RESET);
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println(" " + " " + " " + " " + "||" + board.getBox(4, 0).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 1).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 2).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 3).printsize() + " " + " " + " " + " " + " " + " " + "|" + board.getBox(4, 4).printsize() + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + "4" + " " + "||" + " " + " " + " " + printByIndexPlayer(4, 0, reach) + " " + printReachable(4,0, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 1, reach) + " " + printReachable(4,1, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 2, reach) + " " + printReachable(4,2, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 3, reach) + " " + printReachable(4,3, reach) + " " + "|" + " " + " " + " " + printByIndexPlayer(4, 4, reach) + " " + printReachable(4,4, reach) + " " + "||");
            System.out.println(" " + " " + " " + " " + "||" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "|" + " " + " " + " " + " " + " " + " " + " " + "||");
            System.out.println(" " + " " + " " + " " + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#" + "#");
            System.out.println();
            System.out.println();
        }
    }

    /**
     * This method is called to clear the console
     */
    private void clearScreen() {
        final String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method is used when is not the player's turn
     */
    public void isNotMyTurn() {
        System.out.println(Color.CYAN + "Waiting for my turn..." + Color.RESET);
    }

    /**
     * This method is used to print the name of the game
     */
    private void santoriniName() {
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + "S" + "S" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + " " + " " + "T" + "T" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + "S" + "S" + "S" + "S" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + "T" + "T" + "T" + "T" + "T" + "T" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + "S" + "S" + " " + " " + " " + " " + "S" + "S" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + "T" + "T" + "T" + "T" + "T" + "T" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + " " + " " + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + " " + "A" + "A" + "A" + "A" + "A" + "A" + " " + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + "N" + "N" + " " + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + " " + " " + " " + " " + " " + " " + Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + Color.RED_BOLD_BRIGHT + " " + "R" + "R" + " " + " " + " " + "R" + "R" + "R" + "R" + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + "N" + "N" + " " + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + "S" + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "A" + "A" + "A" + " " + " " + "A" + "A" + "A" + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + "N" + "N" + "N" + "N" + "N" + "N" + " " + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + " " + " " + " " + " " + " " + " " + Color.YELLOW_BOLD_BRIGHT + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + Color.RED_BOLD_BRIGHT + " " + " " + "R" + "R" + " " + "R" + "R" + "R" + "R" + "R" + "R" + "R" + " " + Color.MAGENTA_BOLD_BRIGHT + " " + " " + "I" + "I" + "I" + "I" + "I" + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + "N" + "N" + "N" + "N" + "N" + "N" + " " + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + "I" + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + "A" + "A" + " " + " " + " " + " " + " " + "A" + "A" + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + "N" + "N" + " " + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + " " + " " + " " + " " + " " + " " + Color.YELLOW_BOLD_BRIGHT + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + Color.RED_BOLD_BRIGHT + " " + " " + "R" + "R" + "R" + "R" + " " + " " + " " + " " + "R" + "R" + " " + Color.MAGENTA_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + "I" + "I" + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + "N" + "N" + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + "I" + "I" + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + "S" + "S" + " " + " " + " " + " " + "S" + "S" + " " + Color.BLUE_BOLD_BRIGHT + " " + "A" + "A" + " " + " " + " " + " " + " " + "A" + "A" + " " + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + "N" + "N" + "N" + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.GREEN_BOLD_BRIGHT + " " + "T" + "T" + " " + " " + " " + "T" + "T" + " " + Color.YELLOW_BOLD_BRIGHT + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + Color.RED_BOLD_BRIGHT + " " + " " + "R" + "R" + "R" + " " + " " + " " + " " + " " + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + " " + "I" + "I" + Color.BLUE_BOLD_BRIGHT + " " + " " + "N" + "N" + "N" + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + " " + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + "S" + "S" + "S" + "S" + "S" + "S" + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "A" + "A" + "A" + " " + " " + "A" + "A" + "A" + " " + "A" + Color.CYAN_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.GREEN_BOLD_BRIGHT + " " + " " + "T" + "T" + "T" + "T" + "T" + " " + " " + Color.YELLOW_BOLD_BRIGHT + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + Color.RED_BOLD_BRIGHT + " " + " " + "R" + "R" + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + "I" + "I" + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + " " + "I" + "I" + "I" + "I" + "I" + "I" + Color.RESET);
        System.out.println(Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + "S" + "S" + "S" + "S" + " " + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + " " + "A" + "A" + "A" + "A" + "A" + " " + "A" + "A" + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.GREEN_BOLD_BRIGHT + " " + " " + " " + "T" + "T" + "T" + " " + " " + " " + Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + Color.RED_BOLD_BRIGHT + " " + " " + "R" + "R" + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.MAGENTA_BOLD_BRIGHT + " " + " " + " " + " " + "I" + "I" + "I" + "I" + " " + " " + Color.BLUE_BOLD_BRIGHT + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + Color.CYAN_BOLD_BRIGHT + " " + " " + " " + " " + "I" + "I" + "I" + "I" + Color.RESET);
    }

    /**
     * This method is used to show to the user who lost the end of the game
     */
    @Override
    public void loserEvent() {
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + "G" + "G" + "G" + "G" + "G" + "G" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + " " + "G" + "G" + "G" + "G" + "G" + "G" + "G" + "G" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + "G" + "G" + "G" + " " + " " + " " + " " + "G" + "G" + "G" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + "G" + "G" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "A" + "A" + "A" + "A" + "A" + "A" + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + "M" + "M" + "M" + "M" + " " + " " + " " + " " + "M" + "M" + "M" + "M" + " " + " " + " " + " " + " " + " " + " " + "E" + "E" + "E" + "E" + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + "V" + "V" + " " + " " + " " + " " + " " + " " + " " + "V" + "V" + " " + " " + " " + " " + "E" + "E" + "E" + "E" + "E" + "E" + " " + " " + "R" + "R" + " " + " " + " " + "R" + "R" + "R" + "R" + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + "G" + "G" + " " + " " + " " + " " + "G" + "G" + "G" + " " + " " + " " + " " + "A" + "A" + "A" + " " + " " + "A" + "A" + "A" + " " + " " + " " + " " + "M" + "M" + " " + "M" + "M" + "M" + "M" + "M" + "M" + " " + " " + "M" + "M" + "M" + "M" + "M" + "M" + " " + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + "V" + "V" + " " + " " + " " + " " + " " + "V" + "V" + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + "E" + "E" + " " + " " + "R" + "R" + " " + "R" + "R" + "R" + "R" + "R" + "R" + "R" + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + "G" + "G" + " " + " " + " " + "G" + "G" + "G" + "G" + "G" + " " + " " + "A" + "A" + " " + " " + " " + " " + " " + "A" + "A" + " " + " " + " " + " " + "M" + "M" + "M" + "M" + " " + " " + " " + " " + "M" + "M" + "M" + "M" + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + "E" + "E" + " " + "E" + "E" + "E" + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + "V" + "V" + " " + " " + " " + "V" + "V" + " " + " " + " " + " " + "E" + "E" + " " + "E" + "E" + "E" + "E" + "E" + " " + " " + " " + "R" + "R" + "R" + "R" + " " + " " + " " + " " + "R" + "R" + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + "G" + "G" + "G" + " " + " " + " " + " " + " " + "G" + "G" + " " + " " + "A" + "A" + " " + " " + " " + " " + " " + "A" + "A" + " " + " " + " " + " " + "M" + "M" + "M" + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + " " + " " + " " + "V" + "V" + " " + "V" + "V" + " " + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "R" + "R" + "R" + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + " " + "G" + "G" + "G" + "G" + "G" + "G" + "G" + "G" + " " + " " + " " + " " + "A" + "A" + "A" + " " + " " + "A" + "A" + "A" + " " + "A" + " " + " " + "M" + "M" + " " + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + " " + " " + " " + "V" + "V" + "V" + " " + " " + " " + " " + " " + " " + " " + "E" + "E" + " " + " " + " " + " " + "E" + "E" + " " + " " + "R" + "R" + " " + " " + " " + Color.RESET);
        System.out.println(Color.RED_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + "G" + "G" + "G" + "G" + "G" + "G" + " " + " " + " " + " " + " " + " " + "A" + "A" + "A" + "A" + "A" + " " + "A" + "A" + " " + " " + " " + "M" + "M" + " " + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + " " + " " + " " + "M" + "M" + " " + " " + " " + " " + "E" + "E" + "E" + "E" + "E" + "E" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "V" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "E" + "E" + "E" + "E" + "E" + "E" + " " + " " + " " + "R" + "R" + " " + " " + " " + " " + Color.RESET);

        //sendMessageToServer.sendAckClosingConnection(indexClient);
    }

    /**
     * This method is used to show to the user who win the end of the game
     */
    @Override
    public void winnerEvent(int indexClient) {
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + "Y" + "Y" + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + "N" + "N" + " " + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + " " + "W" + "W" + "W" + "W" + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + " " + " " + "N" + "N" + " " + "N" + "N" + "N" + "N" + "N" + "N" + " " + " " + " " + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + "U" + "U" + " " + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + " " + " " + "W" + "W" + " " + " " + "W" + "W" + " " + " " + " " + "W" + "W" + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + "N" + "N" + "N" + "N" + " " + " " + " " + " " + "N" + "N" + " " + " " + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + "U" + "U" + " " + " " + " " + " " + "U" + "U" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + " " + "W" + "W" + " " + " " + " " + " " + "W" + "W" + " " + "W" + "W" + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + " " + " " + " " + " " + "O" + "O" + " " + " " + "N" + "N" + "N" + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + " " + " " + "U" + "U" + "U" + " " + " " + "U" + "U" + "U" + " " + "U" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + "W" + "W" + " " + " " + " " + " " + " " + " " + "W" + "W" + "W" + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + " " + " " + "O" + "O" + "O" + " " + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + " " + Color.RESET);
        System.out.println(Color.YELLOW_BOLD_BRIGHT + " " + " " + " " + " " + " " + " " + " " + " " + "Y" + "Y" + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + " " + "U" + "U" + "U" + "U" + "U" + " " + "U" + "U" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + "W" + " " + " " + " " + " " + " " + " " + " " + " " + "W" + " " + " " + " " + " " + " " + " " + " " + " " + " " + "O" + "O" + "O" + "O" + "O" + "O" + " " + " " + " " + " " + "N" + "N" + " " + " " + " " + " " + " " + " " + " " + "N" + "N" + " " + " " + Color.RESET);

        sendMessageToServer.sendAckClosingConnection(indexClient, false);
    }

    /**
     * This method is used to show to the user that an opponent has won
     */
    @Override
    public void someoneWon() {
        System.out.println("An opponent won. Game Over");
        loserEvent();
    }

    /**
     * This method is used to show to the user that an opponent has lost
     */
    @Override
    public void whoHasLost() {
        System.out.println("An opponent lost");
    }

    /**
     * Method used to send, using the SendMessageToServer object, a Pong message to the server after received a ping
     *
     * @param objHeartBeat is the message Ping received from the visitorClient
     */
    @Override
    public void printHeartBeat(ObjHeartBeat objHeartBeat) {
        Thread thread = new Thread(() -> {
            sendMessageToServer.sendPong(objHeartBeat.getClientIndex());
        });
        thread.setDaemon(true);
        thread.start();
    }

    /**
     * Method used to send to the server the request of closing the connection
     *
     * @param indexClient      is the index associated to the client
     * @param gameNotAvailable is a boolean used to indicate if the connection will be close because the game is already
     *                         started or because of a problem
     */
    @Override
    public void closingConnectionEvent(int indexClient, boolean gameNotAvailable) {
        Thread thread = new Thread(() -> {
            if (gameNotAvailable) {
                System.out.println("A game has already started -> Try to connect later");
            } else {
                System.out.println("A client is not responding, the connection will be closed");
            }
            sendMessageToServer.sendAckClosingConnection(indexClient, false);
        });
        thread.setDaemon(true);
        thread.start();
    }
}


package it.polimi.ingsw.client.GUI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import it.polimi.ingsw.client.View;
import it.polimi.ingsw.network.SendMessageToServer;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.ack.AckMove;
import it.polimi.ingsw.network.events.*;
import it.polimi.ingsw.network.objects.*;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller class associated to every scene to manage the GUIView
 */
public class ViewGUIController  implements Initializable,View {
    /**
     * Object use to send message to the server
     */
    private static SendMessageToServer sendMessageToServer;
    /**
     * Index of the player
     */
    private static int indexPlayer = -1;
    /**
     * Number of the gamers playing the match
     */
    private static int nPlayers;
    /**
     * Index of the client ossociated with the player
     */
    private static int indexClient;
    /**
     * ArrayList of cards to print
     */
    private static ArrayList<String> cards= new ArrayList<String>(Arrays.asList("Apollo.jpg","Artemis.jpg","Athena.jpg","Atlas.jpg",
            "Demeter.jpg","Hephaestus.jpg","Minotaur.jpg","Pan.jpg","Prometheus.jpg","Charon.jpg",
            "Cronus.jpg","Hestia.jpg","Triton.jpg","Zeus.jpg"));
    /**
     * Array of User objects that contains some information about the players
     */
    private static ArrayList<User> usersArray;
    /**
     * Array of chards choose
     */
    private static ArrayList<Integer> cardsChoose= new ArrayList<>();
    /**
     * Array of temporary cards used until every player has a card
     */
    private static ArrayList<String> cardsTemp = new ArrayList<>();
    /**
     * This is a copy of the object Board that describe the game field and that is used to print the actual game situation
     */
    private static Board board;
    /**
     * This is the index of the chosen card
     */
    private static int indexCard;
    /**
     * This is used to check the actual view state
     */
    private static int state;
    /**
     * This is the index of the gamer playing in this turn
     */
    private static int currentPlayer;
    /**
     * Array of boxes where the player wants to set the workers
     */
    private static ArrayList<Box> boxesChoose= new ArrayList<>();
    /**
     * This is the board box containing the worker to move
     */
    private static Box workerToMove;
    /**
     * This is the board box where building
     */
    private static Box boxToBuild;
    /**
     * Boolean to know if is the first move in the actual turn
     */
    private static boolean firstTime;
    /**
     * Boolean to know if the special movse are over
     */
    private static boolean done;
    /**
     * Boolean to know if it's a special turn
     */
    private static boolean specialTurn;
    /**
     * Level of the first block possible to build
     */
    private static int firstBlock;
    /**
     * Level of the second block possible to build
     */
    private static int secondBlock;

    /**
     * Main pane of the scene where are shown all the cards
     */
    @FXML
    private AnchorPane allCards;
    /**
     * Main pane of the scene where is shown bigger a single card
     */
    @FXML
    private AnchorPane cardPane;
    /**
     * GridPane that contains one Pane for each box of the board
     */
    @FXML
    private GridPane gridBoard;
    /**
     * Pane used to show the opponent players if the total number of player is 3
     */
    @FXML
    private AnchorPane TwoOpponents;
    /**
     * Pane used to show the opponent player if the total number of player is 2
     */
    @FXML
    private AnchorPane oneOpponent;
    /**
     * Pane used to ask confirm of some moves or special moves
     */
    @FXML
    private AnchorPane surePane;
    /**
     * Pane used to show the block buildable after choose the position
     */
    @FXML
    private AnchorPane possibleBlockPane;

    /**
     * TextField where the first player insert the number of players who want to participate
     */
    @FXML
    private TextField textNumPlayer;
    /**
     * Text used to report an error in the number of player input
     */
    @FXML
    private Text nPlayerMessage;
    /**
     * Button used to confirm the entering of the number of players
     */
    @FXML
    private Button buttonNplayer;

    /**
     * TextField where the first player insert the player name
     */
    @FXML
    private TextField playerName;
    /**
     * TextField where the first player insert the player age
     */
    @FXML
    private TextField playerAge;
    /**
     * Text used to report an error in the player name input
     */
    @FXML
    private Text messageNameError;
    /**
     * Text used to report an error in the player age input
     */
    @FXML
    private Text messageAgeError;
    /**
     * Button used to confirm the entering of the player name and age
     */
    @FXML
    private Button buttonDataPlayer;

    /**
     * Button used when a card is clicked
     */
    @FXML
    private Button cardPressed;
    /**
     * Button used when the first card of two is clicked
     */
    @FXML
    private Button buttonOneOfTwo;
    /**
     * Button used when the second card of two is clicked
     */
    @FXML
    private Button buttonTwoOfTwo;
    /**
     * Button used when the first card of three is clicked
     */
    @FXML
    private Button buttonOneOfThree;
    /**
     * Button used when the second card of three is clicked
     */
    @FXML
    private Button buttonTwoOfThree;
    /**
     * Button used when the third card of three is clicked
     */
    @FXML
    private Button buttonThreeOfThree;
    /**
     * Button used to click the second block buildable
     */
    @FXML
    private Button block2;

    /**
     * Images associated with the three or two cards
     */
    private Image img0, img1, img2;

    /**
     * Image of the card to show bigger
     */
    @FXML
    private ImageView cardShow;
    /**
     * ImageView of the first card of two
     */
    @FXML
    private ImageView cardOneOfTwo;
    /**
     * ImageView of the second card of two
     */
    @FXML
    private ImageView cardTwoOfTwo;
    /**
     * ImageView of the first card of three
     */
    @FXML
    private ImageView cardOneOfThree;
    /**
     * ImageView of the second card of three
     */
    @FXML
    private ImageView cardTwoOfThree;
    /**
     * ImageView of the third card of three
     */
    @FXML
    private ImageView cardThreeOfThree;
    /**
     * ImageView of the first opponent's card
     */
    @FXML
    private ImageView cardFirstOpponent;
    /**
     * ImageView of the second opponent's card
     */
    @FXML
    private ImageView cardSecondOpponent;
    /**
     * ImageView of the opponent's card
     */
    @FXML
    private ImageView cardOpponent;
    /**
     * ImageView of the player's card
     */
    @FXML
    private ImageView myCard;
    /**
     * ImageView of the first block buildable
     */
    @FXML
    private ImageView firstBlockPossible;
    /**
     * ImageView of the second block buildable
     */
    @FXML
    private ImageView secondBlockPossible;

    /**
     * Text used to print the opponent's name
     */
    @FXML
    private Text nameOpponent;
    /**
     * Text used to print the opponent's state
     */
    @FXML
    private Text stateOpponent;
    /**
     * Text used to print the first opponent's name
     */
    @FXML
    private Text nameFirstOpponent;
    /**
     * Text used to print the first opponent's state
     */
    @FXML
    private Text stateFirstOpponent;
    /**
     * Text used to print the second opponent's name
     */
    @FXML
    private Text nameSecondOpponent;
    /**
     * Text used to print the second opponent's state
     */
    @FXML
    private Text stateSecondOpponent;
    /**
     * Text to print if the player is playing or is waiting
     */
    @FXML
    private Text playOrWaiting;
    /**
     * Text to print what the player can do
     */
    @FXML
    private Text situationTurn;
    /**
     * Text to print some help to the player
     */
    @FXML
    private Text helpText;
    /**
     * Text to print some info to the player
     */
    @FXML
    private Text infoText1;
    /**
     * Text to print some input to the player
     */
    @FXML
    private Text infoText2;
    /**
     * Button used to close the window and the connection
     */
    @FXML
    private Button closeButton;

    /**
     * Cosntructor
     */
    public ViewGUIController(){

    }

    /**
     * Constructor called with the card scene
     * @param image1 first card image
     * @param image2 second card image
     * @param image3 third card image
     * @param nPlayers number of the players
     * @param state is the current situation
     */
    public ViewGUIController(Image image1, Image image2, Image image3, int nPlayers, int state) {
        this.nPlayers=nPlayers;
        img0=image1;
        img1=image2;
        img2=image3;
        this.state=state;
    }

    /**
     * Constructor used to show for the first time the game field
     * @param usersArray is the ArrayList of users taking part to the game
     * @param indexClient is the index of the client associated with the player
     * @param board is the object Board describe the game field
     * @param indexPlayer is the index of the player
     * @param currentPlayer is the integer index of the gamer playing in this turn
     * @param state is the current situation
     */
    public ViewGUIController(ArrayList<User> usersArray, int indexClient, Board board, int indexPlayer, int currentPlayer, int state) {
        this.usersArray = usersArray;
        this.board = board;
        this.indexPlayer = indexPlayer;
        this.currentPlayer = currentPlayer;
        this.indexClient = indexClient;
        this.state = state;
    }

    /**
     * Constructor used during the player turn
     * @param usersArray is the ArrayList of users taking part to the game
     * @param indexClient is the index of the client associated with the player
     * @param currentPlayer is the integer index of the gamer playing in this turn
     * @param board is the object Board describe the game field
     * @param indexPlayer is the index of the player
     * @param workerToMove
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     * @param done is a boolean used to indicates if the move turn is over
     * @param specialTurn is a boolean used to identify special moves
     * @param state is the current situation
     */
    public ViewGUIController(ArrayList<User> usersArray,int indexClient, int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, boolean done, boolean specialTurn, int state){
        this.usersArray=usersArray;
        this.indexClient=indexClient;
        this.currentPlayer=currentPlayer;
        this.board=board;
        this.indexPlayer=indexPlayer;
        this.workerToMove=workerToMove;
        this.firstTime=firstTime;
        this.specialTurn=specialTurn;
        this.done=done;
        this.state=state;
    }

    /**
     * Constructor
     * @param state is the current situation
     */
    public ViewGUIController(int state){
        this.state=state;
    }

    /**
     * This method returns the corresponding index of the chosen card
     * @param id is the id associated with card used to find the corresponding index
     */
    private void setCardIndex(String id){
        if("card1".equals(id)){
            indexCard=0;
        }else if("card2".equals(id)){
            indexCard=1;
        }else if("card3".equals(id)){
            indexCard=2;
        }else if("card4".equals(id)){
            indexCard=3;
        }else if("card5".equals(id)){
            indexCard=4;
        }else if("card6".equals(id)){
            indexCard=5;
        }else if("card7".equals(id)){
            indexCard=6;
        }else if("card8".equals(id)){
            indexCard=7;
        }else if("card9".equals(id)){
            indexCard=8;
        }else if("card10".equals(id)){
            indexCard=9;
        }else if("card11".equals(id)){
            indexCard=10;
        }else if("card12".equals(id)){
            indexCard=11;
        }else if("card13".equals(id)){
            indexCard=12;
        }else{
            indexCard=13;
        }
    }

    /**
     *  This method is used to associate to a Pane the corresponding box of the board
     * @param pane is the pane containing the button clicked
     * @return the box corresponding
     */
    private Box getBoxIndex(AnchorPane pane) {
        int row=-1;
        int column=-1;
        int indexPane=0;
        boolean found= false;
        while(!found && indexPane<25){
            AnchorPane paneTemp=(AnchorPane)gridBoard.getChildren().get(indexPane);
            if(pane.getId().equals(paneTemp.getId())) {
                row = indexPane / 5;
                column = indexPane % 5;
                found = true;
            }
            indexPane++;
        }
        if(found){
            return board.getBox(row,column);
        }else{
            return null;
        }
    }

    /**
     * Setter method for the SendMessageToServer object
     * @param sendMessageToServer is the object to set
     */
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer){
        this.sendMessageToServer=sendMessageToServer;
    }

    /**
     * Used only in cli view
     */
    @Override
    public void youCanPlay() {
    }
    /**
     * Used only in cli view
     */
    @Override
    public void youHaveToWait() {
    }

    /**
     * Method called from the VisitorClient when the clientHandler received an AskWantToPlay message
     * @param askWantToPlay is the message send from the server to ask to the player if he wants to play
     */
    @Override
    public void askWantToPlay(AskWantToPlayEvent askWantToPlay) {
        System.out.println("Vuoi giocare?");
        indexClient=askWantToPlay.getIndexClient();
        new Thread(() -> Application.launch(GUIMain.class)).start();
    }

    /**
     * Method used to send to the server a reply to the AskWantToPlayMessage
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void sendWantToPlay(ActionEvent actionEvent){
        sendMessageToServer.sendAskWantToPlay(new AskWantToPlayEvent(indexClient));
    }

    /**
     * Method called from the VisitorClient everytime the clientHandler received an updateBoard message
     * to update and load the users state and the actual board situation
     * @param usersArray is the ArrayList of users taking part to the game
     * @param board is the object Board describe the game field
     * @param isShowReachable is a boolean that indicates if the printed board has to show the reachable boxes
     * @param currentPlaying is the integer index of the gamer playing in this turn
     * @param indexClient is the index of the client associated with the player
     */
    @Override
    public void updateBoard(ArrayList<User> usersArray, Board board, boolean isShowReachable, int currentPlaying, int indexClient) {
        this.usersArray=usersArray;
        this.board=board;
        currentPlayer=currentPlaying;
        this.indexClient=indexClient;
        Platform.runLater(() -> {
            GUIMain.changeBoard("Scene/board.fxml", usersArray, indexClient, currentPlayer, board, indexPlayer);
        });
    }

    /**
     * Method called from the VisitorClient of the first player connected when the ClientHandler receives an AskNPlayer message.
     * It's used to ask the number of the players taking part in the game, the number of client the server must wait for before
     * start the game
     */
    @Override
    public void askNPlayer(){
        System.out.println("Ask n player");
        Platform.runLater(() -> {
                GUIMain.changeScene("Scene/firstPage.fxml");
        });
    }

    /**
     * This method is called when the user insert the player number and click the buttonNplayer Button
     * to send to the server the input
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void loadNPlayer(ActionEvent actionEvent){
        String nPlayer= textNumPlayer.getText();
        if(nPlayer.equals("2") || nPlayer.equals("3")){
            sendMessageToServer.sendNPlayer(Integer.parseInt(nPlayer));
            nPlayerMessage.setText("Waiting other players");
            nPlayerMessage.setVisible(true);
            buttonNplayer.setDisable(true);
        }else{
            nPlayerMessage.setText("Only 2 or 3 players!");
        }
    }

    /**
     * Method used to set the number of the gamers playing and then send an AckStartGame message to answer
     * to the startGameEvent message
     * @param nPlayers is the number of the gamers playing
     */
    @Override
    public void setNPlayer(int nPlayers){
        System.out.println("Start");
        this.nPlayers = nPlayers;
        sendMessageToServer.sendAckStartGame();
    }

    /**
     * Method used to set the the personal index of the player to know if the player can play or must wait his turn
     * and then send an AckPlayer message to reply to the ObjState message
     * @param indexPlayer is the index of the player
     */
    @Override
    public void setIndexPlayer(int indexPlayer) {
        if(indexPlayer == 0) {
            System.out.println("I have to play");
            sendMessageToServer.sendAckPlayer();
        }else{
            System.out.println("I've to wait my turn");
        }
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskPlayer message.
     * It's used to load the next scene to ask name and age to the player
     * @param clientIndex is the index of the client associated with the player
     */
    @Override
    public void askPlayer(int clientIndex){
        System.out.println("Ask player data");
        indexClient=clientIndex;
        Platform.runLater(() -> {
                GUIMain.changeScene("Scene/SecondPage.fxml");
        });
    }

    /**
     * This method is used to ask name and age to the player and then to send this data to the server when the user click on
     * the buttonDataPlayer Button
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void loadDataPlayer(ActionEvent actionEvent) {
        messageNameError.setText("");
        messageAgeError.setText("");
        String name= playerName.getText();
        String age= playerAge.getText();
        if(name.length()!=0 && age.length()!=0){
            buttonDataPlayer.setDisable(true);
            sendMessageToServer.sendPlayer(name, Integer.parseInt(age), indexClient);
        }else{
            if(name.length()==0){
                messageNameError.setText("The text field is empty!");
            }
            if(age.length()==0){
                messageAgeError.setText("The text field is empty!");
            }
        }

    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an Ask3Card message.
     * It's used to load the next scene to show the cards
     * @param cards is the ArrayList of cards name from which the user can choose
     */
    @Override
    public void askNCard(ArrayList<String> cards){
        System.out.println("Ask cards");
        Platform.runLater(() -> {
                GUIMain.changeScene("Scene/allCardsPage.fxml");
        });
    }

    /**
     * This method is called everytime the first player chooses a card until he chooses as many cards as the number of the players
     * and then send the array list of the chosen cards to the server
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void addCard(ActionEvent actionEvent){
        cardsChoose.add(indexCard);
        cardsTemp.add(cards.get(indexCard));
        cardPane.setVisible(false);
        cardPressed.setDisable(true);
        if(cardsChoose.size()==nPlayers) {
            System.out.println("Carte scelte");
            allCards.setVisible(false);
            sendMessageToServer.send3card(cardsChoose);
        }
    }

    /**
     * This method is used to set invisible the Pane that shows the card on which the player has clicked
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void back(ActionEvent actionEvent) {
        cardPane.setVisible(false);
    }

    /**
     * This method is used to set visible the pane that shows the card, on which the players click, bigger
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void selectCard(ActionEvent actionEvent){
        cardPressed=(Button)actionEvent.getSource();
        setCardIndex(cardPressed.getId());
        cardShow.setImage(new Image(cards.get(indexCard)));
        cardPane.setVisible(true);
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskCard message.
     * It's used to load the new scene that shows the cards
     * @param cards is the ArrayList of the cards name from which the user can choose
     */
    @Override
    public void askCard(ArrayList<String> cards){
        System.out.println("Ask single card");
        cardsTemp=cards;
        if(nPlayers==2){
            Platform.runLater(() -> {
                    GUIMain.changeCard("Scene/TwoCards.fxml", cardsTemp, nPlayers);
            });
        }else{
            Platform.runLater(() -> {
                GUIMain.changeCard("Scene/ThreeCards.fxml", cardsTemp, nPlayers);
            });
        }
    }

    /**
     * This method is used when a player click on the card he chose for himself
     * and then send his choice to the server
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void chooseCard(ActionEvent actionEvent){
        int indexCard;
        cardPressed=(Button)actionEvent.getSource();
        if("buttonOneOfTwo".equals(cardPressed.getId()) || "buttonOneOfThree".equals(cardPressed.getId())){
            indexCard=0;
        }
        else if("buttonTwoOfTwo".equals(cardPressed.getId()) || "buttonTwoOfThree".equals(cardPressed.getId())){
            indexCard=1;
        }
        else{
            indexCard=2;
        }
        if(nPlayers==2) {
            buttonOneOfTwo.setDisable(true);
            buttonTwoOfTwo.setDisable(true);
        }
        if(nPlayers==3){
            buttonOneOfThree.setDisable(true);
            buttonTwoOfThree.setDisable(true);
            buttonThreeOfThree.setDisable(true);
        }
        sendMessageToServer.sendCard(indexCard);
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskInizializeWorker message.
     * It's used to load the game field scene
     */
    @Override
    public void initializeWorker() {
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer,null,false,false,false,  3);
        });
    }

    /**
     * This method is associated to the click on each button on the game field
     * In based on the variable state it can do different things
     * If the state is 3, it's used to assign a position to each worker and then send this two boxes to the server
     * If the state is 4, it's used to choose which worker the player want to move and the send an ObjWorkerToMove to the server
     * If the state is 6, it's used to chose the position where the user wants to move the worker and send onn ObjMove to the server
     * If the state is 9, it's used to print the possible blocks the user can build after he chooses the position
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void selectPositionInGameField(ActionEvent actionEvent) {
        Button cell = (Button) actionEvent.getSource();
        AnchorPane pane = (AnchorPane) cell.getParent();
        if (state == 3) {
            Box boardBox = getBoxIndex(pane);
            if(boardBox!=null){
                boxesChoose.add(boardBox);
                printWorker(pane,indexPlayer);
                cell.toFront();
                cell.setDisable(true);
                if (boxesChoose.size() == 2) {
                    disableAllButtons();
                    playOrWaiting.setText("Wait, an opponent is playing");
                    situationTurn.setText("Workers choose");
                    sendMessageToServer.sendWorker(boxesChoose);
                }
            }
        }
        if(state==4){
            Box boardBox = getBoxIndex(pane);
            if(boardBox.getWorker()!=null && boardBox.getWorker().getIndexPlayer()==indexPlayer){
                disableAllButtons();
                ObjWorkerToMove objWorkerToMove= new ObjWorkerToMove(boardBox.getWorker().getWorkerId(), boardBox.getRow(), boardBox.getColumn(),false);
                situationTurn.setText("Worker choose");
                sendMessageToServer.sendWorkerToMove(objWorkerToMove);
            }
        }
        if(state==6){
            disableAllButtons();
            Box boardBox = getBoxIndex(pane);
            ObjMove objMove = new ObjMove(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), boardBox.getRow(), boardBox.getColumn(), false);
            sendMessageToServer.sendMoveWorker(objMove);
        }
        if(state==9){
            disableAllButtons();
            Box boardBox= getBoxIndex(pane);
            boxToBuild=boardBox;
            printPossibleBlocks(boxToBuild.getRow(), boxToBuild.getColumn());
        }
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives for the first time an AskWorkerToMoveEvent message.
     * It's used to load the scene with the game field updated
     * @param row1 is the row of the box occupied by the worker 1
     * @param column1 is the column of the box occupied by the worker 1
     * @param row2 is the row of the box occupied by the worker 2
     * @param column2 is the column of the box occupied by the worker 2
     * @param currentPlaying is the integer index of the player who is playing
     * @param clientIndex is the integer index associated to the client
     */
    @Override
    public void askWorker(int row1, int column1, int row2, int column2, int currentPlaying, int clientIndex) {
        indexClient=clientIndex;
        currentPlayer=currentPlaying;
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer,null, false,false,false,4 );
        });
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives not for the first time an AskWorkerToMoveEvent message.
     * It's used to load the scene with the game field updated with a new Pane that asks to the player if he is sure about the worker to move chosen
     * @param row1 is the row of the box occupied by the worker 1
     * @param column1 is the column of the box occupied by the worker 1
     * @param row2 is the row of the box occupied by the worker 2
     * @param column2 is the column of the box occupied by the worker 2
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param currentPlaying is the integer index of the player who is playing
     * @param indexClient is the integer index associated to the client
     */
    @Override
    public void areYouSure(int row1, int column1, int row2, int column2, int indexWorker, int currentPlaying, int indexClient) {
        this.indexClient=indexClient;
        currentPlayer=currentPlaying;
        if (indexWorker == 1) {
            workerToMove= board.getBox(row1,column1);
        } else {
            workerToMove= board.getBox(row2,column2);
        }
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer, workerToMove, false, false, false, 5 );
        });
    }

    /**
     * This method is associated with a button used to ask confirm to some choice of the player
     * If the state is 5, when the button is clicked means that  the player is sure about the worker to move chosen and to send an ObjWorkerToMove object to the server
     * If the state is 7, when the button is clicked means that the player chose to build before making a move
     * If the state is 8, when the button is clicked means that the player chose to move again
     * If the state is 10, when the button is clicked means that the player chose to build again
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void surePaneYes(ActionEvent actionEvent){
        if(state==5){
            ObjWorkerToMove objWorkerToMove = new ObjWorkerToMove(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), true);
            sendMessageToServer.sendWorkerToMove(objWorkerToMove);
            disableAllButtons();
        }
        if(state==7){
            ObjBlockBeforeMove objBlockBeforeMove =  new ObjBlockBeforeMove(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), true);
            sendMessageToServer.sendBlockBeforeMove(objBlockBeforeMove);
        }
        if(state==8){
            moveWorker( workerToMove.getRow(), workerToMove.getColumn(),workerToMove.getWorker().getWorkerId(),false,  firstTime,indexClient, currentPlayer);
        }
        if(state==10){
            buildMove(workerToMove.getRow(), workerToMove.getColumn(),workerToMove.getWorker().getWorkerId(),false,firstTime,done,indexClient,currentPlayer,specialTurn);
        }
    }

    /**
     * This method is associated with a button used to allowed the player to change some choice
     * If the state is 5, when the button is clicked means that the player wants to change the worker to move and it's used
     * to recall the method askWorker to allow the player to make another choice
     * If the state is 7, when the button is clicked means that the player chose to move without build
     * If the state is 8, when the button is clicked means that the player chose to not move again
     * If the state is 10, when the button is clicked means that the player chose to not build again
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void surePaneNo(ActionEvent actionEvent){
        if(state==5){
            askWorker(-1,-1,-1,-1,currentPlayer,indexClient);
        }
        if(state==7){
            ObjBlockBeforeMove objBlockBeforeMove =  new ObjBlockBeforeMove(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), false);
            sendMessageToServer.sendBlockBeforeMove(objBlockBeforeMove);
        }
        if(state==8){
            AckMove ackMove = new AckMove(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn());
            sendMessageToServer.sendAckMove(ackMove);
        }
        if(state==10){
            ObjBlock objBlock = new ObjBlock(true);
            surePane.setVisible(false);
            sendMessageToServer.sendBuildMove(objBlock);
        }
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskBeforeBuildMove message.
     * It's used to load the scene with the game field updated with a new Pane that asks to the player if he wants to build before move
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     */
    @Override
    public void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        workerToMove=board.getBox(rowWorker,columnWorker);
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove, false, false, false,7 );
        });
    }

    /**
     * Method called from the VisitorClient when the ClientHandler receives an AskMoveEvent message.
     * It's used to load the scene with the game field updated asking where the player wants to move the chosen worker
     * @param row is the starting row of the worker to move
     * @param column is the starting column of the worker to move
     * @param indexWorker is the integer index of the worker the player chose to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     */
    @Override
    public void moveWorker(int row, int column, int indexWorker, boolean isWrongBox, boolean firstTime, int clientIndex, int currentPlaying) {
        this.firstTime=firstTime;
        workerToMove= board.getBox(row, column);
        indexClient=clientIndex;
        currentPlayer=currentPlaying;
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove,firstTime , false,false, 6 );
        });
    }

    /**
     * This method is used by player with special gods that can move more than once to load the next scene where it is asked to the player if he wants to
     * @param row is the row of the box occupied by the worker
     * @param column is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    @Override
    public void anotherMove(int row, int column, int indexWorker, boolean isWrongBox, boolean firstTime, int clientIndex, int currentPlaying, boolean done) {
        indexClient=clientIndex;
        currentPlayer=currentPlaying;
        workerToMove=board.getBox(row, column);
        this.firstTime=firstTime;
        this.done = done;
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove,firstTime , done,false,8 );
        });
    }


    /**
     * Method used to load a new scene with the updated game field asking where the player wants to build
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param isFirstTime is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn is a boolean used to identify special moves
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    @Override
    public void buildMove(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done) {
        indexClient=clientIndex;
        currentPlayer=currentPlaying;
        workerToMove= board.getBox(rowWorker, columnWorker);
        firstTime=isFirstTime;
        this.done=done;
        this.specialTurn=isSpecialTurn;
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove, firstTime, specialTurn, done, 9);
        });
    }

    /**
     * Method used to show in a specific pane the blocks the player can build in the chosen position
     * @param row is the row where he wants to build
     * @param column is the column where he wants to build
     */
    @Override
    public void printPossibleBlocks(int row, int column) {
        possibleBlockPane.setVisible(true);
        block2.setVisible(false);
        int num=0;
        for(int size = 0; size < board.getBox(row, column).getPossibleBlock().size() ; size++ ){
            Image image= getBlock(board.getBox(row, column).getPossibleBlock().get(size).getBlockIdentifier()-1);
            if(num==0){
                firstBlock=size;
                firstBlockPossible.setImage(image);
            }
            else{
                secondBlock=size;
                block2.setVisible(true);
                secondBlockPossible.setImage(image);
            }
            num++;
        }
    }

    /**
     * Method used by the player to choose which block he wants to build
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    public void chooseBlock(ActionEvent actionEvent){
        Button cell = (Button) actionEvent.getSource();
        ObjBlock objBlock = new ObjBlock(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), firstTime, specialTurn);
        objBlock.setRowBlock(boxToBuild.getRow());
        objBlock.setColumnBlock(boxToBuild.getColumn());
        if("block1".equals(cell.getId())){
            objBlock.setPossibleBlock(0);
        }else{
            objBlock.setPossibleBlock(1);
        }
        playOrWaiting.setText("Wait, an opponent is playing");
        situationTurn.setText("Block built");
        sendMessageToServer.sendBuildMove(objBlock);
    }

    /**
     * This method is used by player with special gods that can move build than once to load the next scene where it is asked to the player if he wants to
     * @param rowWorker is the row of the box occupied by the worker
     * @param columnWorker is the column of the box occupied by the worker
     * @param indexWorker is the integer index of the worker the player wants to move
     * @param isWrongBox is a boolean that indicates if the move is wrong
     * @param isFirstTime is a boolean that indicates if this is the first move tried in this turn
     * @param isSpecialTurn is a boolean used to identify special moves
     * @param clientIndex is the integer index associated to the client
     * @param currentPlaying is the integer index of the player who is playing
     * @param done is a boolean used to indicates if the move turn is over
     */
    @Override
    public void anotherBuild(int rowWorker, int columnWorker, int indexWorker, boolean isWrongBox, boolean isFirstTime, boolean isSpecialTurn, int clientIndex, int currentPlaying, boolean done) {
        indexClient=clientIndex;
        currentPlayer=currentPlaying;
        workerToMove= board.getBox(rowWorker, columnWorker);
        firstTime=isFirstTime;
        this.done=done;
        this.specialTurn=isSpecialTurn;
        Platform.runLater(() -> {
            GUIMain.changBoardWithParameters("Scene/board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove, firstTime, specialTurn, done, 10);
        });
    }

    /**
     * This method is used to load the scene of losing game
     */
    @Override
    public void loserEvent(int indexClient) {
        Platform.runLater(() -> {
            GUIMain.changeFinal("Scene/youLose.fxml");
        });
    }

    /**
     *This method is used to load the scene of winning game
     */
    @Override
    public void winnerEvent(int indexClient) {
        Platform.runLater(() -> {
            GUIMain.changeFinal("Scene/youWin.fxml");
        });
    }

    /**
     * This method is used to load the scene of losing game because someone won
     */
    @Override
    public void someoneWon() {
        System.out.println("An opponent won. Game Over"); //todo da sistemare
        Platform.runLater(() -> {
            GUIMain.changeFinal("Scene/youLose.fxml");
        });
    }

    /**
     * This method is used to load the scene of winning game because someone lost
     */
    @Override
    public void whoHasLost() {
        System.out.println("An opponent lost"); //todo da sistemare
        Platform.runLater(() -> {
            GUIMain.changeFinal("Scene/youWin.fxml");
        });
    }

    /**
     * Method used to send, using the SendMessageToServer object, a Pong message to the server after received a ping
     * @param objHeartBeat is the message Ping received from the visitorClient
     */
    @Override
    public void printHeartBeat(ObjHeartBeat objHeartBeat){
        new Thread(() -> {
            sendMessageToServer.sendPong(objHeartBeat.getClientIndex());
        }).start();
    }

    /**
     * This method is called from every scene when the player clicks on the exit button
     * @param actionEvent is the object associated to the click event
     */
    @FXML
    private void closeButtonHandler(ActionEvent actionEvent){
        closingConnectionEvent(indexClient,false);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Method used to send to the server the request of closing the connection
     * @param indexClient is the index associated to the client
     * @param gameNotAvailable is a boolean used to indicate if the connection will be close because the game is already
     * started or because of a problem
     */
    @Override
    public void closingConnectionEvent(int indexClient, boolean gameNotAvailable) {
        new Thread(() -> {
            if(gameNotAvailable){
                System.out.println("A game has already started -> Try to connect later");
            }else {
                System.out.println("A client is not responding, the connection will be closed");
            }
            sendMessageToServer.sendAckClosingConnection(indexClient);
        }).start();
    }

    /**
     *  This method is used to disenable every button corresponding to the box of the game field that is unreachable
     */
    private void disableButtonsNotReachable(){
        int row, column;
        for(int index=0; index<25;index++){
            row = index / 5;
            column = index % 5;
            if(!board.getBox(row,column).isReachable()){
                AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
                pane.getChildren().get(4).setDisable(true);
            }
        }
    }

    /**
     * This method is used to enable every button corresponding to the box of the game field
     */
    private void enableAllButtons(){
        for(int index=0; index<25;index++){
            AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
            pane.getChildren().get(4).setDisable(false);
        }
    }

    /**
     * This method is used to disable every button corresponding to the box of the game field
     */
    private void disableAllButtons(){
        for(int index=0; index<25;index++){
            AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
            pane.getChildren().get(4).setDisable(true);
        }
    }

    /**
     * This method is used to print on the game field the corresponding images of what is contained in each box of the board
     */
    private void printBoard() {
        for(int row=0; row<5; row++){
            for(int col=0;col<5;col++){
                int index=row*5+col;
                if(board.getBox(row,col).getCounter()>0){
                    printBlock(board.getBox(row,col).getCounter(), (AnchorPane)gridBoard.getChildren().get(index), board.getBox(row,col));
                }
                if(board.getBox(row,col).getWorker()!=null){
                    printWorker((AnchorPane)gridBoard.getChildren().get(index), board.getBox(row,col).getWorker().getIndexPlayer());
                }
                AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
                pane.getChildren().get(4).toFront();
            }
        }
    }

    /**
     * This method returns the corresponding image of a building level
     * @param level is the index of the building level
     * @return the image of the level asked
     */
    public Image getBlock(int level){
        Image block = null;
        if(level==0){
            block= new Image("blocco 0.jpg");
        }else if(level==1) {
            block = new Image("blocco 1.jpg");
        }else if(level==2) {
            block = new Image("blocco 2.jpg");
        }else if(level==3) {
            block = new Image("/SenzaSfondo/cupola.png");
        }
        return block;
    }

    /**
     * This method is used to print the buildings in the game field
     * @param counter is the level of the building already built in the chosen position
     * @param actualPane is the AnchorPane associated to the box there's a building
     * @param actualBox is the Box where there's a building
     */
    private void printBlock(int counter, AnchorPane actualPane, Box actualBox){
        for(int level=0; level<counter;level++){
            if(actualBox.getBuilding().getBlockCounter(level).getBlockIdentifier()!=-1){
                Image block= getBlock(level);
                if(level==0){
                    ImageView img1=(ImageView)actualPane.getChildren().get(0);
                    img1.setImage(block);
                }else if(level==1){
                    ImageView img2=(ImageView)actualPane.getChildren().get(1);
                    img2.setImage(block);
                }else if(level==2){
                    ImageView img3=(ImageView)actualPane.getChildren().get(2);
                    img3.setImage(block);
                }else if(level==3){
                    ImageView img4=(ImageView)actualPane.getChildren().get(3);
                    img4.setImage(block);
                }
            }
        }
    }

    /**
     * This method is used to print a worker in a box, each player has his color of workers
     * @param actualPane is the AnchorPane associated to the box where there's a worker
     * @param indexP is the index of the player owner of the worker
     */
    private void printWorker( AnchorPane actualPane, int indexP){
        Image worker;
        if(indexP==0){
            worker=new Image("/SenzaSfondo/WorkerRed.png");
        }else if(indexP==1){
            worker= new Image("/SenzaSfondo/WorkerBlue.png");
        }else{
            worker= new Image("/SenzaSfondo/WorkerYellow.png");
        }
        ImageView img= (ImageView)actualPane.getChildren().get(3);
        img.setImage(worker);
    }

    /**
     * Method used to print the state of the player in his state pane
     */
    private void printYourState(){
        if(indexClient==currentPlayer){
            playOrWaiting.setText("It's your turn!");
        }else{
            playOrWaiting.setText("Wait, an opponent is playing");
        }
    }

    /**
     * This method is used to print the opponent players state in the right pane dedicated to the opponent players
     * @param userOpponent is the user whose state is to print
     * @return the state string
     */
    private String printOpponentState(User userOpponent){
        if(userOpponent.getClient()==currentPlayer){
            return "Is playing";
        }else{
            return "Is Waiting";
        }
    }

    /**
     * This method is used to set some graphic changes to the scenes loaded before shows them
     * State 1 prints the cards chosen by the previous player
     * State 3 prints the request to initialize workers
     * State 4 prints the request to chose the worker to move
     * State 5 prints the request to confirm the worker to move
     * State 6 prints the request to decide the position to reach
     * State 7 prints the request to decide if building before moving
     * State 8 prints the request to decide if moving again
     * State 9 prints the request to chose the position where build
     * State 10 prints the request to decide if building again
     * Each state after 1 also printed name, state and god's image of the opponent everytime they load a new scene from the GUIcontroller
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(state==1){
            if(nPlayers==2){
                if(img0!=null){
                    cardOneOfTwo.setImage(img0);
                }
                if(img1!=null){
                    cardTwoOfTwo.setImage(img1);
                }else{
                    buttonTwoOfTwo.setDisable(true);
                }
            }
            else if(nPlayers==3){
                if(img0!=null){
                    cardOneOfThree.setImage(img0);
                }
                if(img1!=null){
                    cardTwoOfThree.setImage(img1);
                }else{
                    buttonTwoOfThree.setDisable(true);
                }
                if(img2!=null){
                    cardThreeOfThree.setImage(img2);
                }else{
                    buttonThreeOfThree.setDisable(true);
                }
            }
        }
        if(state>1){
            myCard.setImage(new Image(usersArray.get(indexPlayer).getNameCard()+".jpg"));
            situationTurn.setText("");
            if(nPlayers==2){
                TwoOpponents.setVisible(false);
                for(int indexP=0;indexP<usersArray.size();indexP++){
                    if(indexPlayer!=indexP){
                        cardOpponent.setImage(new Image("/SenzaSfondo/"+usersArray.get(indexP).getNameCard()+".png"));
                        nameOpponent.setText(usersArray.get(indexP).getName());
                        stateOpponent.setText(printOpponentState(usersArray.get(indexP)));
                    }
                }
            }
            if(nPlayers==3){
                oneOpponent.setVisible(false);
                int find=0;
                for(int indexP=0;indexP<usersArray.size();indexP++){
                    if(indexPlayer!=indexP){
                        find++;
                        if(find==1){
                            cardFirstOpponent.setImage(new Image("/SenzaSfondo/"+usersArray.get(indexP).getNameCard()+".png"));
                            nameFirstOpponent.setText(usersArray.get(indexP).getName());
                            stateFirstOpponent.setText(printOpponentState(usersArray.get(indexP)));
                        }
                        else{
                            cardSecondOpponent.setImage(new Image("/SenzaSfondo/"+usersArray.get(indexP).getNameCard()+".png"));
                            nameSecondOpponent.setText(usersArray.get(indexP).getName());
                            stateSecondOpponent.setText(printOpponentState(usersArray.get(indexP)));
                        }
                    }
                }
            }
            printBoard();
        }
        if(state==3){
            printYourState();
            situationTurn.setText("Inizialize your workers!");
        }
        if(state==4){
            printYourState();
            enableAllButtons();
            situationTurn.setText("Choose the worker to move");
        }
        if(state==5){
            printYourState();
            surePane.setVisible(true);
            infoText1.setText("Confirm if you want to move");
            infoText2.setText("this worker");
            disableButtonsNotReachable();
        }
        if(state==6){
            printYourState();
            disableButtonsNotReachable();
            situationTurn.setText("You can move the worker");
            infoText1.setVisible(true);
            infoText1.setText("What position you wanna reach?");
        }
        if(state==7){
            printYourState();
            situationTurn.setText("You can build before move");
            infoText1.setVisible(true);
            infoText1.setText("If you decide to do so, You'll not be");
            infoText2.setVisible(true);
            infoText2.setText("able to move up a building");
            surePane.setVisible(true);
            helpText.setText("Do you want to?");
        }
        if(state==8){
            printYourState();
            disableAllButtons();
            situationTurn.setText("Move your worker again");
            infoText1.setVisible(true);
            infoText1.setText("You Have the possibility to make");
            infoText2.setVisible(true);
            infoText2.setText("another move");
            surePane.setVisible(true);
            helpText.setText("Do you want to?");
        }
        if(state==9){
            printYourState();
            disableButtonsNotReachable();
            surePane.setVisible(false);
            situationTurn.setText("Build a block");
        }
        if(state==10){
            printYourState();
            disableAllButtons();
            playOrWaiting.setText("It's your turn!");
            situationTurn.setText("You can build again");
            surePane.setVisible(true);
            helpText.setText("Do you want to?");
        }
    }
}

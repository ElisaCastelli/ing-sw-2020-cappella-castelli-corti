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

public class ViewGUIController  implements Initializable,View {
    private static SendMessageToServer sendMessageToServer;
    private static int indexPlayer = -1;
    private static int nPlayers;

    private static int indexClient;
    private static ArrayList<String> cards= new ArrayList<String>(Arrays.asList("Apollo.jpg","Artemis.jpg","Athena.jpg","Atlas.jpg",
            "Demeter.jpg","Hephaestus.jpg","Minotaur.jpg","Pan.jpg","Prometheus.jpg","Charon.jpg",
            "Cronus.jpg","Hestia.jpg","Triton.jpg","Zeus.jpg"));
    private static ArrayList<User> usersArray;
    private static ArrayList<Integer> cardsChoose= new ArrayList<>();
    private static ArrayList<String> cardsTemp = new ArrayList<>();
    private static Board board;
    private static int indexCard;
    private static int state;
    //è currentclient
    private static int currentPlayer;
    private static ArrayList<Box> boxesChoose= new ArrayList<>();
    private static Box workerToMove;
    private static Box boxToBuild;
    private static boolean firstTime;
    private static boolean done;
    private static boolean specialTurn;
    private static int firstBlock;
    private static int secondBlock;
    @FXML
    private AnchorPane allCards;
    @FXML
    private AnchorPane cardPane;
    @FXML
    private GridPane gridBoard;
    @FXML
    private AnchorPane TwoOpponents;
    @FXML
    private AnchorPane oneOpponent;
    @FXML
    private AnchorPane surePane;
    @FXML
    private AnchorPane possibleBlockPane;

    //ask n player attributes
    @FXML
    private TextField textNumPlayer;
    @FXML
    private Text nPlayerMessage;
    @FXML
    private Button buttonNplayer;

    //ask data player attributes
    @FXML
    private TextField playerName;
    @FXML
    private TextField playerAge;
    @FXML
    private Text messageNameError;
    @FXML
    private Text messageAgeError;
    @FXML
    private Button buttonDataPlayer;

    //ask card attributes
    @FXML
    private Button cardPressed;
    @FXML
    private Button buttonOneOfTwo;
    @FXML
    private Button buttonTwoOfTwo;
    @FXML
    private Button buttonOneOfThree;
    @FXML
    private Button buttonTwoOfThree;
    @FXML
    private Button buttonThreeOfThree;
    @FXML
    private Button block2;

    private Image img0, img1, img2;

    @FXML
    private ImageView cardShow;
    @FXML
    private ImageView cardOneOfTwo;
    @FXML
    private ImageView cardTwoOfTwo;
    @FXML
    private ImageView cardOneOfThree;
    @FXML
    private ImageView cardTwoOfThree;
    @FXML
    private ImageView cardThreeOfThree;
    @FXML
    private ImageView cardFirstOpponent;
    @FXML
    private ImageView cardSecondOpponent;
    @FXML
    private ImageView cardOpponent;
    @FXML
    private ImageView myCard;
    @FXML
    private ImageView firstBlockPossible;
    @FXML
    private ImageView secondBlockPossible;


    @FXML
    private Text nameOpponent;
    @FXML
    private Text stateOpponent;
    @FXML
    private Text nameFirstOpponent;
    @FXML
    private Text stateFirstOpponent;
    @FXML
    private Text nameSecondOpponent;
    @FXML
    private Text stateSecondOpponent;
    @FXML
    private Text playOrWaiting;
    @FXML
    private Text situationTurn;
    @FXML
    private Text helpText;
    @FXML
    private Text infoText1;
    @FXML
    private Text infoText2;
    @FXML
    private javafx.scene.control.Button closeButton;

    @FXML
    private void closeButtonHandler(ActionEvent actionEvent){
        closingConnectionEvent(indexClient,false);
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();
        // do what you have to do
        stage.close();
    }
    public ViewGUIController(){

    }
    public ViewGUIController(Image image1, Image image2, Image image3, int nPlayers) {
        this.nPlayers=nPlayers;
        img0=image1;
        img1=image2;
        img2=image3;
        state=1;
    }
    public ViewGUIController(ArrayList<User> usersArray, int indexClient, Board board, int indexPlayer, int currentPlayer){
        this.usersArray=usersArray;
        this.board=board;
        this.indexPlayer=indexPlayer;
        this.currentPlayer=currentPlayer;
        this.indexClient=indexClient;
        state=2;
    }
    public ViewGUIController(ArrayList<User> usersArray, int indexClient, int currentPlayer, Board board, int indexPlayer, Box workerToMove,int state){
        this.usersArray=usersArray;
        this.board=board;
        this.indexPlayer=indexPlayer;
        this.workerToMove=workerToMove;
        this.indexClient=indexClient;
        this.currentPlayer=currentPlayer;
        this.state=state;
    }
    public ViewGUIController(ArrayList<User> usersArray,int indexClient, int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, int state){
        this.usersArray=usersArray;
        this.indexClient=indexClient;
        this.currentPlayer=currentPlayer;
        this.board=board;
        this.indexPlayer=indexPlayer;
        this.workerToMove=workerToMove;
        this.firstTime=firstTime;
        this.state=state;
    }
    public ViewGUIController(ArrayList<User> usersArray,int indexClient, int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, boolean done, int state){
        this.usersArray=usersArray;
        this.indexClient=indexClient;
        this.currentPlayer=currentPlayer;
        this.board=board;
        this.indexPlayer=indexPlayer;
        this.workerToMove=workerToMove;
        this.firstTime=firstTime;
        this.done=done;
        this.state=state;
    }
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

    public void setCardIndex(String id){
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
    public Box getBoxIndex(AnchorPane pane) {
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
    public void setSendMessageToServer(SendMessageToServer sendMessageToServer){
        this.sendMessageToServer=sendMessageToServer;

    }
    public void setBoard(Board board){
        this.board=board;
    }

    @Override
    public void youCanPlay() {
        System.out.println("Tocca a te");
    }
    @Override
    public void youHaveToWait() {
        System.out.println("Aspetta");
    }


    @Override
    public void askWantToPlay(AskWantToPlay askWantToPlay) {
        System.out.println("Vuoi giocare?");
        indexClient=askWantToPlay.getIndexClient();
        new Thread(() -> Application.launch(Main.class)).start();
    }
    @FXML
    public void sendWantToPlay(ActionEvent actionEvent){
        sendMessageToServer.sendAskWantToPlay(new AskWantToPlay(indexClient));
    }
    @Override
    public void updateBoard(UpdateBoardEvent updateBoardEvent) {
        usersArray=updateBoardEvent.getUserArray();
        board=updateBoardEvent.getBoard();
        currentPlayer=updateBoardEvent.getCurrentClientPlaying();
        indexClient=updateBoardEvent.getClientIndex();
        Platform.runLater(() -> {
            Main.changeBoard("board.fxml", usersArray, indexClient, currentPlayer, board, indexPlayer, currentPlayer);
        });
    }

    @Override
    public void askNPlayer(){
        System.out.println("Ask n player");
        Platform.runLater(() -> {
                Main.changeScene("firstPage.fxml");
        });
    }
    @FXML
    public void loadNPlayer(ActionEvent actionEvent) throws Exception {
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
    @Override
    public void setNPlayer(int nPlayers){
        System.out.println("Start");
        this.nPlayers = nPlayers;
        sendMessageToServer.sendAckStartGame();
    }

    @Override
    public void askPlayer(int clientIndex){
        System.out.println("Ask player data");
        Platform.runLater(() -> {
                Main.changeScene("SecondPage.fxml");
        });
    }
    @Override
    public void setIndexPlayer(ObjState objState) {
        indexPlayer = objState.getIndexPlayer();
        if(indexPlayer == 0) {
            System.out.println("I have to play");
            sendMessageToServer.sendAckPlayer();
        }else{
            System.out.println("I've to wait my turn");
        }
    }
    @Override
    public int getIndexPlayer() {
        return indexPlayer;
    }
    @FXML
    public void loadData(ActionEvent actionEvent) throws IOException {
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

    @Override
    public void ask3Card(ArrayList<String> cards){
        System.out.println("Ask cards");
        Platform.runLater(() -> {
                Main.changeScene("allCardsPage.fxml");
        });
    }
    @FXML
    public void addCard(ActionEvent actionEvent) throws IOException {
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
    @FXML
    public void back(ActionEvent actionEvent) throws IOException{
        cardPane.setVisible(false);
    }
    @FXML
    public void selectCard(ActionEvent actionEvent) throws IOException {
        cardPressed=(Button)actionEvent.getSource();
        setCardIndex(cardPressed.getId());
        cardShow.setImage(new Image(cards.get(indexCard)));
        cardPane.setVisible(true);
    }
    @Override
    public void askCard(ArrayList<String> cards){
        System.out.println("Ask single card");
        cardsTemp=cards;
        if(nPlayers==2){
            Platform.runLater(() -> {
                    Main.changeCard("TwoCards.fxml", cardsTemp, nPlayers);
            });
        }else{
            Platform.runLater(() -> {
                Main.changeCard("ThreeCards.fxml", cardsTemp, nPlayers);
            });
        }
    }
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
        sendMessageToServer.sendCard(indexCard,indexPlayer);
    }

    @Override
    public void initializeWorker() {
        Platform.runLater(() -> {
            Main.changeInitialize("board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer, 3);
        });
    }
    @FXML
    public void select(ActionEvent actionEvent) {
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
                    sendMessageToServer.sendWorker(boxesChoose, indexPlayer);
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
            situationTurn.setText("Worker moved");
            sendMessageToServer.sendMoveWorker(objMove);
        }
        if(state==9){
            disableAllButtons();
            Box boardBox= getBoxIndex(pane);
            boxToBuild=boardBox;
            printPossibleBlocks(boxToBuild.getRow(), boxToBuild.getColumn());
        }
    }

    @Override
    public void askWorker(AskWorkerToMoveEvent askMoveEvent) {
        indexClient=askMoveEvent.getClientIndex();
        currentPlayer=askMoveEvent.getCurrentClientPlaying();
        Platform.runLater(() -> {
            Main.changeInitialize("board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer,4 );
        });
    }
    @Override
    public void areYouSure(AskWorkerToMoveEvent askWorkerToMoveEvent) {
        int indexWorker = askWorkerToMoveEvent.getIndexWorker();
        indexClient=askWorkerToMoveEvent.getClientIndex();
        currentPlayer=askWorkerToMoveEvent.getCurrentClientPlaying();
        if (indexWorker == 1) {
            workerToMove= board.getBox(askWorkerToMoveEvent.getRow1(),askWorkerToMoveEvent.getColumn1());
        } else {
            workerToMove= board.getBox(askWorkerToMoveEvent.getRow2(),askWorkerToMoveEvent.getColumn2());
        }

        Platform.runLater(() -> {
            Main.changeWorkerMove("board.fxml", usersArray,indexClient,currentPlayer, board, indexPlayer, workerToMove,5 );
        });
    }
    @FXML
    public void sure(ActionEvent actionEvent){
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
            AskMoveEvent askMoveEvent= new AskMoveEvent(workerToMove.getWorker().getWorkerId(), workerToMove.getRow(), workerToMove.getColumn(), firstTime, done);
            moveWorker(askMoveEvent);
        }
        if(state==10){
            buildMove(new AskBuildEvent(workerToMove.getWorker().getWorkerId(),workerToMove.getRow(), workerToMove.getColumn(),firstTime,done,specialTurn));
        }
    }
    @FXML
    public void notSure(ActionEvent actionEvent){
        if(state==5){
            askWorker(new AskWorkerToMoveEvent());
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

    @Override
    public void askBuildBeforeMove(int indexWorker, int rowWorker, int columnWorker) {
        workerToMove=board.getBox(rowWorker,columnWorker);
        Platform.runLater(() -> {
            Main.changeWorkerMove("board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove,7 );
        });
    }

    @Override
    public void moveWorker(AskMoveEvent askMoveEvent) {
        firstTime=askMoveEvent.isFirstTime();
        workerToMove= board.getBox(askMoveEvent.getRow(), askMoveEvent.getColumn());
        indexClient=askMoveEvent.getClientIndex();
        currentPlayer=askMoveEvent.getCurrentClientPlaying();
        Platform.runLater(() -> {
            Main.changeMove("board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove,firstTime ,6 );
        });
    }

    @Override
    public void anotherMove(AskMoveEvent askMoveEvent) {
        indexClient=askMoveEvent.getClientIndex();
        currentPlayer=askMoveEvent.getCurrentClientPlaying();
        workerToMove=board.getBox(askMoveEvent.getRow(), askMoveEvent.getColumn());
        firstTime=askMoveEvent.isFirstTime();
        done = askMoveEvent.isDone();
        Platform.runLater(() -> {
            Main.changeDoubleMove("board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove,firstTime , done,8 );
        });
    }


    @Override
    public void wrongMove() {
        System.out.println("Wrong move");
    }

    @Override
    public void buildMove(AskBuildEvent askBuildEvent) {
        indexClient=askBuildEvent.getClientIndex();
        currentPlayer=askBuildEvent.getCurrentClientPlaying();
        workerToMove= board.getBox(askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker());
        firstTime=askBuildEvent.isFirstTime();
        done=askBuildEvent.isDone();
        specialTurn=askBuildEvent.isSpecialTurn();
        Platform.runLater(() -> {
            Main.changeBuild("board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove, firstTime, specialTurn, done, 9);
        });
    }

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

    @Override
    public void anotherBuild(AskBuildEvent askBuildEvent) {
        indexClient=askBuildEvent.getClientIndex();
        currentPlayer=askBuildEvent.getCurrentClientPlaying();
        workerToMove= board.getBox(askBuildEvent.getRowWorker(), askBuildEvent.getColumnWorker());
        firstTime=askBuildEvent.isFirstTime();
        done=askBuildEvent.isDone();
        specialTurn=askBuildEvent.isSpecialTurn();
        Platform.runLater(() -> {
            Main.changeBuild("board.fxml", usersArray,indexClient, currentPlayer, board, indexPlayer, workerToMove, firstTime, specialTurn, done, 10);
        });
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
        new Thread(() -> {
            System.out.println(objHeartBeat.getMessageHeartbeat());
            sendMessageToServer.sendPong(objHeartBeat.getClientIndex());
        }).start();
    }

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


    public void disableButtonsNotReachable(){
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
    public void enableAllButtons(){
        for(int index=0; index<25;index++){
            AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
            pane.getChildren().get(4).setDisable(false);
        }
    }
    public void disableAllButtons(){
        for(int index=0; index<25;index++){
            AnchorPane pane= (AnchorPane)gridBoard.getChildren().get(index);
            pane.getChildren().get(4).setDisable(true);
        }
    }
    public void printBoard() {
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
    public void printBlock(int counter, AnchorPane actualPane, Box actualBox){
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
    public void printWorker( AnchorPane actualPane, int indexP){
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
    public void printYourState(){
        if(indexClient==currentPlayer){
            playOrWaiting.setText("It's your turn!");
        }else{
            playOrWaiting.setText("Wait, an opponent is playing");
        }
    }

    public String printOpponentState(User userOpponent){
        if(userOpponent.getClient()==currentPlayer){
            return "Is playing";
        }else{
            return "Is Waiting";
        }
    }
//state 1 card -- state 2 update Board -- 3 inizializza worker -- 4 scelta worker to move -- 5 sure? --6 move -- 7 build before move -- 8 move again
    // 9 build -- 10 build again
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


    /*Parent tableViewParent = FXMLLoader.load(getClass().getResource("allCardsPage.fxml"));
    Scene tableViewScene = new Scene(tableViewParent);
    Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
    window.setScene(tableViewScene);
    window.show();*/
    /*public void close(){
         Stage stage = (Stage)pane.getScene().getWindow();
         stage.close();
     }*/
}

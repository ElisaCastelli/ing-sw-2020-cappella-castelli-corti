package it.polimi.ingsw.client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the application class to show the gui
 */
public class GUIMain extends Application{

    /**
     * Main stage
     */
    private static  Stage primaryStage;
    /**
     * Main pain loaded on stage
     */
    private static Pane mainPane;

    /**
     *  First method call
     * @param primaryStage is the main stage to set
     */
    @Override
    public void start(Stage primaryStage){
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Santorini");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        showMainView();
    }

    /**
     * Method to load the FXML scene and set it on the stage
     */
    public void showMainView(){
        FXMLLoader loader = new FXMLLoader();
        //mainPane = loader.load(getClass().getResource("/emptyPage.fxml"));
        try {
            mainPane = loader.load(GUIMain.class.getClassLoader().getResource("Scene/emptyPage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setScene(new Scene(mainPane, 1280, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * Method to load a scene passed by parameters
     * @param sceneName is the name of the scene path to load
     */
    public static void changeScene(String sceneName) {
        FXMLLoader loader = new FXMLLoader(
                GUIMain.class.getClassLoader().getResource(
                        sceneName
                )
        );
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load a scene and set the gui controller with some parameters
     * @param sceneName is the name of the scene path to load
     * @param cardsTemp is the array of cards to print in the next scene
     * @param nPlayer is the number of player to set on the controller
     */
    public static void changeCard(String sceneName, ArrayList<String> cardsTemp, int nPlayer) {
        FXMLLoader loader = new FXMLLoader(
                GUIMain.class.getClassLoader().getResource(
                        sceneName
                )
        );

        ViewGUIController controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"), null, null, nPlayer,1);
        if(cardsTemp.size()==2){
            controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"),new Image(cardsTemp.get(1)+".jpg"), null, nPlayer,1);
        }else if(cardsTemp.size()==3){
            controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"),new Image(cardsTemp.get(1)+".jpg"), new Image(cardsTemp.get(2)+".jpg"), nPlayer,1);
        }

        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load the first scene with the game field
     * @param sceneName is the name of the scene path to load
     * @param usersArray is the ArrayList of users taking part to the game
     * @param clientIndex is the index of the client associated with the player
     * @param currentPlayer is the integer index of the gamer playing in this turn
     * @param board is the object Board describe the game field
     * @param indexPlayer is the index of the player
     */
    public static void changeBoard(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer) {
        FXMLLoader loader = new FXMLLoader(
                GUIMain.class.getClassLoader().getResource(
                        sceneName
                )
        );
        ViewGUIController controller = new ViewGUIController(usersArray, clientIndex, board, indexPlayer, currentPlayer,2);
        loader.setController(controller);try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load the first scene with the game field
     * @param sceneName is the name of the scene path to load
     * @param usersArray is the ArrayList of users taking part to the game
     * @param clientIndex is the index of the client associated with the player
     * @param currentPlayer is the integer index of the gamer playing in this turn
     * @param board is the object Board describe the game field
     * @param indexPlayer is the index of the player
     * @param workerToMove the board box containing the worker to move
     * @param firstTime is a boolean that indicates if this is the first move tried in this turn
     * @param specialTurn is a boolean used to identify special moves
     * @param done is a boolean used to indicates if the move turn is over
     * @param state is the actual gui situation
     */
    public static void changBoardWithParameters(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, boolean done, boolean specialTurn, int state){
        FXMLLoader loader = new FXMLLoader(
                GUIMain.class.getClassLoader().getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(usersArray,clientIndex,currentPlayer,board,indexPlayer, workerToMove, firstTime, done,specialTurn, state);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to load the final scene in case of defeat or victory
     * @param sceneName is the name of the scene path to load
     */
    public static void changeFinal(String sceneName){
        FXMLLoader loader = new FXMLLoader(
                GUIMain.class.getClassLoader().getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(-1);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This is the launcher method for the gui
     * @param args
     */
    public static void main(String[] args) {
       launch(args);
    }

}
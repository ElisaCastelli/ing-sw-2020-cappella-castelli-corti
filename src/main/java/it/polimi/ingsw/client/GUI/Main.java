package it.polimi.ingsw.client.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.server.model.gameComponents.Board;
import it.polimi.ingsw.server.model.gameComponents.Box;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application{

    private static  Stage primaryStage;
    private static Pane mainPane;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage=primaryStage;
        primaryStage.setTitle("Santorini");
        primaryStage.initStyle(StageStyle.UNDECORATED);
        showMainView();
    }

    public void showMainView() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        mainPane = loader.load(getClass().getResource("/it/polimi/ingsw/client/GUI/emptyPage.fxml"));
        primaryStage.setScene(new Scene(mainPane, 1280, 800));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public static void changeScene(String sceneName) {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
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

    public static void changeCard(String sceneName, ArrayList<String> cardsTemp, int nPlayer) {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );

        ViewGUIController controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"), null, null, nPlayer);
        if(cardsTemp.size()==2){
            controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"),new Image(cardsTemp.get(1)+".jpg"), null, nPlayer);
        }else if(cardsTemp.size()==3){
            controller = new ViewGUIController(new Image(cardsTemp.get(0)+".jpg"),new Image(cardsTemp.get(1)+".jpg"), new Image(cardsTemp.get(2)+".jpg"), nPlayer);
        }

        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeBoard(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, int current) {
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );
        ViewGUIController controller = new ViewGUIController(usersArray, clientIndex, board, indexPlayer, current);
        loader.setController(controller);try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeInitialize(String sceneName,ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, int state){
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(usersArray, clientIndex,currentPlayer,board,indexPlayer,null, state);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changeWorkerMove(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, Box workerToMove, int state){
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(usersArray,clientIndex,currentPlayer,board,indexPlayer, workerToMove,state);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void changeMove(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, int state){
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(usersArray, clientIndex,currentPlayer,board,indexPlayer, workerToMove, firstTime, state);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changeDoubleMove(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, boolean done, int state){
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
                        sceneName
                )
        );
        ViewGUIController controller= new ViewGUIController(usersArray,clientIndex,currentPlayer,board,indexPlayer, workerToMove, firstTime, done, state);
        loader.setController(controller);
        try{
            Scene scene = new Scene((Pane) loader.load());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void changeBuild(String sceneName, ArrayList<User> usersArray, int clientIndex,int currentPlayer, Board board, int indexPlayer, Box workerToMove, boolean firstTime, boolean done, boolean specialTurn, int state){
        FXMLLoader loader = new FXMLLoader(
                Main.class.getResource(
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

    private void shutdown(Stage mainWindow) {
        // you could also use your logout window / whatever here instead
        Alert alert = new Alert(Alert.AlertType.NONE, "Really close the stage?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            // you may need to close other windows or replace this with Platform.exit();
            mainWindow.close();
        }
    }

    public static void main(String[] args) {
       launch(args);
    }



}

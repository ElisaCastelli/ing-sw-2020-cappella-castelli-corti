package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class GUIView extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Santorini");

        AnchorPane boardScreen = new AnchorPane();
        // new Image(url)
        Image image = new Image("/SantoriniBoard.png");
        // new BackgroundSize(width, height, widthAsPercentage, heightAsPercentage, contain, cover)
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        // new BackgroundImage(image, repeatX, repeatY, position, size)
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        // new Background(images...)
        Background background = new Background(backgroundImage);

        Scene scene = new Scene(boardScreen, 900, 600);

        boardScreen.setBackground(background);
        //boardScreen.setMinSize(stage.getWidth()/3, stage.getHeight()/2);

        Button button = new Button();

        boardScreen.getChildren().add(button);

        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}

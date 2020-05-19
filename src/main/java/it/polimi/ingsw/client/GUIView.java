package it.polimi.ingsw.client;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class GUIView extends Application {

    Button[][] matrix; //names the grid of buttons
        private static int SIZE = 5;
        private static int length = SIZE;
        private static int width = SIZE;
        //pane base
        private AnchorPane basePane = new AnchorPane();
        //pane board
        private GridPane boardPane = new GridPane();
        //pane card
        private StackPane cardPane=new StackPane();
        //label pane
        private FlowPane usersPane = new FlowPane(Orientation.VERTICAL, 10.0, 10.0);

        private Label lbl = new Label();


    public void background(){
            Image image = new Image("/SantoriniBoard.png");
            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            Background background = new Background(backgroundImage);
            basePane.setBackground(background);
        }

        public void board(){
            matrix = new Button[width][length]; // allocates the size of the matrix

            // runs a for loop and an embedded for loop to create buttons to fill the size of the matrix
            // these buttons are then added to the matrix
            for(int y = 0; y < length; y++)
            {
                for(int x = 0; x < width; x++)
                {

                    matrix[x][y] = new Button(); //creates new random binary button
                    matrix[x][y].setText(x + "," + y);
                    int finalX = x;
                    int finalY = y;
                    matrix[x][y].setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            lbl.setText("Button "+ finalX + "," + finalY);
                        }
                    });
                    matrix[x][y].setStyle("-fx-background-color: transparent; -fx-border-color: black; ");
                    matrix[x][y].setScaleX(0.8);
                    matrix[x][y].setScaleY(1.2);
                    boardPane.add(matrix[x][y],y,x);
                }
            }

            boardPane.setHgap(1);
            boardPane.setVgap(10);
            boardPane.setScaleX(3);
            boardPane.setScaleY(3);
            boardPane.setLayoutX(620);
            boardPane.setLayoutY(315);
            //basePane.setTopAnchor(boardPane,);
        }

        public void card(String nameCard){
            Image card = new Image(nameCard);
            ImageView imageCard= new ImageView(card);
            imageCard.setFitHeight(329.4375*1.3);
            imageCard.setFitWidth(233.25*1.3);
            cardPane.getChildren().add(imageCard);
            cardPane.setLayoutX(1124);
            cardPane.setLayoutY(364);
            cardPane.setStyle("-fx-border-color: black; -fx-border-width: 2");
        }

        public void menuPlayer(){

            for (int i = 0; i < 3; i++) {
                // add nodes to the flow pane
                Label l = new Label("Utente "
                        + (int)(i + 1));
                Image userImage=null;
                if(i==1) {
                    userImage = new Image("APOLLO_ALETTE E TORCICOLLO_min.jpg");
                }
                else if(i==2){
                    userImage = new Image("PAN_ E MARMELLATA_min.jpg");
                }

                ImageView userView= new ImageView(userImage);
                userView.setFitWidth(150);
                userView.setFitHeight(150);
                l.setStyle("-fx-border-color: black; -fx-border-width: 2");
                l.setGraphic(userView);
                //l.setAlignment(Pos.CENTER);
                usersPane.getChildren().add(l);
                usersPane.setLayoutX(50);
                usersPane.setLayoutY(50);
                usersPane.setMaxHeight(600);
            }
        }

        public void start(Stage stage) {
            stage.setTitle("Santorini");
            lbl.setLayoutX(100);
            lbl.setLayoutY(320);
            lbl.setStyle(" -fx-font-size: 20px;");

            background();
            board();
            card("/ZEUS_PAN DE DEUS.jpg");
            menuPlayer();

            basePane.getChildren().addAll(cardPane,boardPane,usersPane);
            Scene scene = new Scene(basePane, 3408,2480 );
            stage.setScene(scene);
            stage.setResizable(false);

            stage.show();
        }

}

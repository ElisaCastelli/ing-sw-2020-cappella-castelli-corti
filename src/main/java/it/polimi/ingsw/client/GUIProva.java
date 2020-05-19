package it.polimi.ingsw.client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class GUIProva{
    public Label helloWorld;


    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World!");
    }


}

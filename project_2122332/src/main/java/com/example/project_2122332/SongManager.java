package com.example.project_2122332;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class SongManager extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SongManager.class.getResource("SongManager-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Song Management Application");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            onCloseButtonClick(stage);
        });
    }

    //Code For Close Button.
    public void onCloseButtonClick(Stage stage)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You are about to Exit!!");
        alert.setContentText("Do you want to save before Exit?");

        if(alert.showAndWait().get() == ButtonType.OK)
        {
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
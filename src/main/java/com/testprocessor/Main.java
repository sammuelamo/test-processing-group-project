package com.testprocessor;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    
    /** 
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            VBox root = FXMLLoader.load(getClass().getResource("/ui/MainUI.fxml"));
            Scene scene = new Scene(root, 600, 400);
            primaryStage.setTitle("Text Processing Tool");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

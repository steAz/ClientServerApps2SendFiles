/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2gniazda;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 *
 * @author Kazanostra
 */
public class Main extends Application {
    
     @Override
    public void start(Stage primaryStage) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("gui.fxml"));
            Scene scene = new Scene(root, 300, 250);
            primaryStage.setTitle("The file is being sent");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.err.println("Loading GUI has failed: " + ex);
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @param args the command line arguments
     */
  
    
}

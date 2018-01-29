/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 *
 * @author Touch Me
 */
public class Main extends Application {
    
    public static Stage stage;
    
    public static void main(String[] args){
        Application.launch(Main.class, (java.lang.String[])null);
    }
    
    @Override 
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setTitle("Lamorak");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        Parent root = FXMLLoader.load(getClass().getResource("View1.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);               
        stage.setScene(scene);
        stage.show();
    }
    
    public void goto2() throws Exception{
        Parent pane = FXMLLoader.load(getClass().getResource("View2N.fxml"));
        stage.getScene().setRoot(pane);
    }
    public void goto1() throws Exception{
        Parent pane = FXMLLoader.load(getClass().getResource("View1.fxml"));
        stage.getScene().setRoot(pane);
    }
    public void goto3() throws Exception{
       Parent pane = FXMLLoader.load(getClass().getResource("View3.fxml"));
       stage.getScene().setRoot(pane);
    }
}
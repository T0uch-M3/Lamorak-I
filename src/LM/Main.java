/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LM;

import java.sql.SQLException;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 *
 * @author Touch Me
 */
public class Main extends Application
{

    public static Stage stage;
    public static String lastWindow;
    public static Boolean currentWindow = false;

    public static void main(String[] args)
    {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        stage = primaryStage;
        stage.setTitle("Managment Software");
        stage.setMinWidth(700);
        stage.setMinHeight(500);
        Parent root = FXMLLoader.load(getClass().getResource("View1.fxml"));
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(confirmClosing);
    }

    private EventHandler<WindowEvent> confirmClosing = we ->
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(stage);//to get the same incon from the running app
        alert.initStyle(StageStyle.UTILITY);// for the restore&minimise buttons

        Optional<ButtonType> result = alert.showAndWait();
        //alert.showAndWait() return the pressed button
        Button exitButton = (Button) alert.getDialogPane().lookupButton(ButtonType.OK);
        //.consume will cancel the canceling event, so no more canceling and will go back to the program
        if (ButtonType.CANCEL.equals(result.get()))
        {//we check is the the pressed button is the cancel button
            we.consume();
        }
        else
        {
            if (currentWindow)
            {
                try
                {
                    View2Controller.addingLog("Left");
                }
                catch (SQLException exe)
                {
                    exe.printStackTrace();
                }
            }
        }
    };

    //the login screen
    public void goto1() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View1.fxml"));
        stage.getScene().setRoot(pane);
    }

    //the main screen
    public void goto2() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View2N.fxml"));
        stage.getScene().setRoot(pane);
    }

    //adding users screen
    public void goto3() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View3.fxml"));
        stage.getScene().setRoot(pane);
    }

    //manager's login screen
    public void goto4() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View4.fxml"));
        stage.getScene().setRoot(pane);
    }

    //manager's screen
    public void goto5() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View5.fxml"));
        stage.getScene().setRoot(pane);
    }

    //password changing screen
    public void goto6() throws Exception
    {
        Parent pane = FXMLLoader.load(getClass().getResource("View6.fxml"));
        stage.getScene().setRoot(pane);
    }
}

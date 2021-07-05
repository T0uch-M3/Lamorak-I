/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LM;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Touch-Me
 */

public class View3Controller extends AnchorPane implements Initializable{
    @FXML
    private TextField nameFieldA, idFieldA;
    @FXML
    private PasswordField pwdFieldA;
    @FXML
    private Label nameWarning, idWarning, pwdWarning, addingDone, statUserType;
    @FXML
    private Pane ppane, ppane1, ppane2;
    //*****************************
    Connection con;
    PreparedStatement st = null;
    Main m = new Main();
    
    @FXML
    private void goBack(ActionEvent ae) throws Exception{
        Main m = new Main();
        if (Main.lastWindow=="normalUser")
            m.goto1();
        if(Main.lastWindow=="superUser")
            m.goto4();
    }
    @FXML
    private void addingUserAction(MouseEvent me){
        try{
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        String sql = "INSERT into USERS (ID, NAME, PWD, TYPE) values (?,?,?,?)";
        if (nameFieldA.getText().trim().isEmpty()){
            nameWarning.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> nameWarning.setText(" ")));
            timeline.play();;
        }
        else if (idFieldA.getText().trim().isEmpty()){
            idWarning.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> idWarning.setText(" ")));
            timeline.play();;
        }
        else if (pwdFieldA.getText().trim().isEmpty()){
            pwdWarning.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning.setText(" ")));
            timeline.play();;
        }
        else{
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idFieldA.getText());
            st.setString(2, nameFieldA.getText());
            st.setString(3, pwdFieldA.getText());
            //check if normal user or no
            if (Main.lastWindow=="normalUser")
                st.setString(4, "N");
            if(Main.lastWindow=="superUser")
                st.setString(4, "S");
            st.executeUpdate();
            addingDone.setText(" ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), actionevent -> {
                addingDone.setText(" ");
                try{
                    if (Main.lastWindow=="normalUser")
                        m.goto1();
                    if(Main.lastWindow=="superUser")
                        m.goto4();
                }catch(Exception ex){
                        
                }}));
            timeline.play();

        }
        }catch(SQLException exc){
            exc.printStackTrace();
        }
    }
    @FXML
    private void unfocusAll (MouseEvent me){
        ppane.requestFocus();
        ppane1.requestFocus();
        ppane2.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (Main.lastWindow=="normalUser")
            statUserType.setText("    Add New User");
        if(Main.lastWindow=="superUser")
            statUserType.setText("    Add a Manager");
    }
    
    
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Touch-Me
 */
public class View6Controller extends AnchorPane implements Initializable {
    @FXML
    private PasswordField oldPwd, newPwd, confirmPwd;
    @FXML
    private Label pwdWarning1, pwdWarning2, pwdWarning3, savingDone;
    @FXML
    private Pane ppane, ppane1, ppane2;
    @FXML
    private Tooltip toolT;
    //*****************
    Connection con;
    PreparedStatement st = null;
    Main m = new Main();
    View1Controller v1 = new View1Controller();
    @FXML
    private void goBack(ActionEvent ae) throws Exception{
        Main m = new Main();
            m.goto2();
    }
     @FXML
    private void saveAction(MouseEvent me){
        try{
        String sql = "UPDATE USERS set PWD=? where ID=?";
        if (oldPwd.getText().trim().isEmpty()){
            pwdWarning1.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning1.setText(" ")));
            timeline.play();
        }
        else if (newPwd.getText().trim().isEmpty()){
            pwdWarning2.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning2.setText(" ")));
            timeline.play();
        }
        else if (confirmPwd.getText().trim().isEmpty()){
            pwdWarning3.setText("Required field");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning3.setText(" ")));
            timeline.play();
        }
        else if(!newPwd.getText().equals(confirmPwd.getText())){
            pwdWarning3.setText("Not Match");
            pwdWarning3.setTooltip(toolT);
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae ->{
                pwdWarning3.setText(" ");
                    }));
            timeline.play();
        }
        else if(verifyId(oldPwd.getText(),View1Controller.currentId)==false){
            pwdWarning1.setText("incorrect");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning1.setText(" ")));
            timeline.play();
        }
        else{
            System.out.println("GOOD");
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, newPwd.getText());
            st.setString(2, View1Controller.currentId);
            st.executeUpdate();
            savingDone.setText("saved");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), actionevent -> {
                savingDone.setText(" ");
                try{
                    m.goto2();
                }catch(Exception ex){
                        
                }}));
            timeline.play();
        
        }
        }catch(SQLException exc){
            System.out.println("busted");
            exc.printStackTrace();
        }
    }
    
    @FXML
    private boolean verifyId (String pwd, String id){
        try{
            String sql2 = "select PWD from USERS where ID=?";
            PreparedStatement pst = con.prepareStatement(sql2);
            pst.setString(1, id);
            ResultSet result = pst.executeQuery();
            while(result.next()){
            System.out.println("old password"+result.getString("PWD"));
            System.out.println("new password"+pwd);
            if (pwd.equals(result.getString("PWD")))
                return true;
            else
                return false;
            }
        }catch(SQLException exc){
            exc.printStackTrace();
            System.out.println("something went wrong");
            
        }
        return false;
    }
    @FXML
    private void unfocusAll (MouseEvent me){
        ppane.requestFocus();
        ppane1.requestFocus();
        ppane2.requestFocus();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        }catch (SQLException ex){
            
        }
        
        
        
    }
    
}

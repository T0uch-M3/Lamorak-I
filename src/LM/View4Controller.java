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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 *
 * @author Touch-Me
 */
public class View4Controller extends AnchorPane implements Initializable{
    
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private Button confirmBut, newManBut;
    @FXML
    private Label confirmWarning;
    
    String sql1 = "SELECT * FROM USERS WHERE TYPE = 'S'";
    PreparedStatement st = null;
    Main m = new Main();
    @FXML
    private void goBack() throws Exception{
        m.goto1();
    }
    @FXML
    private void goBackA(ActionEvent ae) throws Exception{
        goBack();
    }
    @FXML
    private void goBackK(KeyEvent ke) throws Exception{
        if (ke.getCode()== ke.getCode().F1)
        goBack();
    }
    @FXML
    private void gotoNewUser(ActionEvent ae) throws Exception{
        m.goto3();
    }
        @FXML
    private void moveNext(KeyEvent ke) throws Exception{
         if (ke.getCode()== ke.getCode().ENTER){
            if (name.isFocused()){
                password.requestFocus();
                ke.consume();
            }
            else if (password.isFocused())
                confirmBut.requestFocus();
                confirmBut.setDefaultButton(true);
         }
         if(ke.getCode()== ke.getCode().F1)
             goBack();
    }
    @FXML
    private void move() throws Exception{
        Main m = new Main();
        m.goto5();
    }
    @FXML
    private void confirmAction(ActionEvent actionE) throws SQLException, Exception{
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            st = con.prepareStatement(sql1);
            ResultSet result = st.executeQuery();
            while(result.next()) {
                if (((result.getString("NAME").equals(name.getText()))||(result.getString("ID").equals(name.getText()))) && result.getString("PWD").equals(password.getText())){
                    move();
                }else{
                    confirmWarning.setText("Something went wrong!!!");
                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> confirmWarning.setText(" ")));
                    timeline.play();
                }
            }
        } catch (SQLException ex){
            ex.printStackTrace();
            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            st.close();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.lastWindow="superUser";
                try{
            String sql2 = "SELECT * from USERS where TYPE = 'S'";
        
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            st = con.prepareStatement(sql2);
            ResultSet result = st.executeQuery();
        
            if (!result.next()) {    
            newManBut.setVisible(true);
            newManBut.setDisable(false);
            } 
            else{
                newManBut.setVisible(false);
                newManBut.setDisable(true);
            }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
    }
    
    
    
    
    
    
    
}

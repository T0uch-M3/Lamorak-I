/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
/**
 *
 * @author Touch-Me
 */
public class View1Controller extends AnchorPane implements Initializable{
    
    PreparedStatement st = null;
    String sql1 = "SELECT * FROM USERS";
    ArrayList<String> store = new ArrayList<>();
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private AnchorPane ppane;
    @FXML
    private Button confirm, newUserBut;
    @FXML
    private Label confirmWarning;
    @FXML
    private CheckBox rememberBox;

    
    
    @FXML
    private void ConfirmAction (ActionEvent event) throws Exception{
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            st = con.prepareStatement(sql1);
            ResultSet result = st.executeQuery();
            while(result.next()) {
                if (((result.getString("NAME").equals(name.getText()))||(result.getString("ID").equals(name.getText()))) && result.getString("PWD").equals(password.getText())){
                    
                    if (rememberBox.isSelected())
                        checkRemember(result.getString("ID"), con, true);
                    if (!rememberBox.isSelected())
                        checkRemember(result.getString("ID"), con, false);
                    move();
                }else
                    confirmWarning.setText("Something went wrong!");
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> confirmWarning.setText(" ")));
            }
        } catch (SQLException ex){
            System.out.println("SQL fucked");
            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            st.close();
        }
    }
    @FXML
    private void checkRemember(String id, Connection cn, boolean boo) throws SQLException{
        String sql4 = "UPDATE USERS set REMEMBER = ? where ID = ?";
        PreparedStatement ps = cn.prepareStatement(sql4);
        ps.setString(2, id);
        ps.setBoolean(1, boo);
        int nbr = ps.executeUpdate();
        
    }
    private void move() throws Exception{
        Main m = new Main();
        m.goto2();
    }
    @FXML
    private void gotoNewUser(ActionEvent ae) throws Exception{
        Main m = new Main();
        m.goto3();
    }
    @FXML
    private void unfocusAll (MouseEvent me){
        ppane.requestFocus();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        try{
        String sql2 = "SELECT * from USERS";
        
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        st = con.prepareStatement(sql2);
        ResultSet result = st.executeQuery();
        
        if (!result.next()) {    
        newUserBut.setVisible(true);
        newUserBut.setDisable(false);
        } 
        else{
            newUserBut.setVisible(false);
            newUserBut.setDisable(true);
            if( result.getBoolean(4)==true){
                rememberBox.setSelected(true);
                name.setText(result.getString(2));
                password.setText(result.getString(3));
            }
                else
                rememberBox.setSelected(false);
            
        }
        
        
        
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    
}

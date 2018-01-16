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
import java.lang.Exception;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Touch-Me
 */
public class View1Controller extends AnchorPane implements Initializable{
    
    @FXML
    private TextField name;
    private PasswordField password;
    @FXML
    private void ConfirmAction (ActionEvent event) throws Exception {
        //if ("test".equals(name.getText())){
        if ("test".equals(name.getText())){
        Main m = new Main();
        m.goto2();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        String insertTableSQL = "INSERT INTO EMPLOYEE"
				+ "(ID, NAME, VIP, PWD) VALUES"
				+ "(?,?,?,?)";
        PreparedStatement st = null;
        try {
            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            System.out.println("Connected");
            st = con.prepareStatement(insertTableSQL);
            st.setString(1, "4");
            st.setString(2, "aaa");
            st.setBoolean(3, false);
            st.setString(4, null);
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("It got fucked");
            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                st.close();
            } catch (SQLException ex) {
                Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

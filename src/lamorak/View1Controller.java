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
/**
 *
 * @author Touch-Me
 */
public class View1Controller extends AnchorPane implements Initializable{
    
    PreparedStatement st = null;
    String sql2 = "SELECT * FROM EMPLOYEE";
    String sql3 = "UPDATE EMPLOYEE SET VIP = true WHERE ID = '4'";
    ArrayList<String> store = new ArrayList<>();
    @FXML
    private TextField name;
    private PasswordField password;
    
    @FXML
    private void ConfirmAction (ActionEvent event) throws Exception{
        try {
            
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            System.out.println("Connected");
            st = con.prepareStatement(sql2);
            ResultSet result = st.executeQuery();
                     //int count = st.executeUpdate();
                     //System.out.println(count);
            while(result.next()) {
             if ((result.getBoolean(3)==true)&&(result.getString(2).equals(name.getText()))) 
                 //store.add(result.getString(2));
                 this.move();
             else
                 System.out.println("wrong choice mate");
//             System.out.println(result.getString    (1));//id
//             System.out.println(result.getString    (2));//name
//             System.out.println(result.getBoolean   (3));//VIP or no
//             System.out.println(result.getString    (4));//password
            }
//            System.out.println(store);
//             if ("test".equals(name.getText())){
//             this.move();
//             }
        } catch (SQLException ex){
            System.out.println("It got fucked");
            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            st.close();
            System.out.println("Finally!!!");
        }
    }
    
    private void move() throws Exception{
        Main m = new Main();
        m.goto2();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)  {
        
        
        
        try {
            name.setText("Lakyus");
//                     String sql1 = "INSERT INTO EMPLOYEE"
//				+ "(ID, NAME, VIP, PWD) VALUES"
//				+ "(?,?,?,?)";
//        //String sql2 = "SELECT * FROM EMPLOYEE";
//        //PreparedStatement st = null;
//        try {
//            Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
//            System.out.println("Connected");
//            st = con.prepareStatement(sql2);
//            ResultSet result = st.executeQuery();
//            while(result.next()) {
//
//             System.out.println(result.getString    (1));
//             System.out.println(result.getString    (2));
//             System.out.println(result.getBoolean   (3));
//             System.out.println(result.getString    (4));
//            }
//
////            st.setString(1, "4");
////            st.setString(2, "aaa");
////            st.setBoolean(3, false);
////            st.setString(4, null);
////            st.executeUpdate();
//        } catch (SQLException ex) {
//            System.out.println("It got fucked");
//            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                st.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        } catch (Exception ex) {
            
            Logger.getLogger(View1Controller.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("NOt CONNECTED");
        }
    }
    
}

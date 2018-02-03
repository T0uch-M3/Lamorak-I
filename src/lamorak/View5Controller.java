/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 *
 * @author Touch-Me
 */
public class View5Controller extends TabPane implements Initializable {
    @FXML
    private Label warningSearch;
    @FXML
    private TextField fieldIDs, searchField, nameFieldA, idFieldA;
    @FXML
    private PasswordField pwdFieldA;
    @FXML
    private TableColumn<History, String>C1;
    @FXML
    private TableColumn<History, Integer>C2;
    @FXML
    private TableColumn<History, Date>C3;
    @FXML
    private TableColumn<History, String>C4;
    @FXML
    private TableColumn<User, Integer>C11;
    @FXML
    private TableColumn<User, String>C12;
    @FXML
    private TableView tt, tableV;
    @FXML
    private ListView<History>listV;
    //====RANDOMVARIABLES====
    ObservableList<History> historyList= FXCollections.observableArrayList();
    ObservableList<User> userList= FXCollections.observableArrayList();
//    ListView<History>listV = new ListView<>(historyList);
    @FXML
    private void addingUserAction(ActionEvent me){
        try{
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        String sql = "INSERT into USERS (ID, NAME, PWD, TYPE) values (?,?,?,?)";
        if (nameFieldA.getText().trim().isEmpty()){
//            nameWarning.setText("Required field");
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> nameWarning.setText(" ")));
//            timeline.play();;
        }
        else if (idFieldA.getText().trim().isEmpty()){
//            idWarning.setText("Required field");
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> idWarning.setText(" ")));
//            timeline.play();;
        }
        else if (pwdFieldA.getText().trim().isEmpty()){
//            pwdWarning.setText("Required field");
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), ae -> pwdWarning.setText(" ")));
//            timeline.play();;
        }
        else{
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, idFieldA.getText());
            st.setString(2, nameFieldA.getText());
            st.setString(3, pwdFieldA.getText());
            //check if normal user or no
                st.setString(4, "N");
            st.executeUpdate();
//            addingDone.setText(" ");
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), actionevent -> {
//                addingDone.setText(" ");
                }));
            timeline.play();

        }
        }catch(SQLException exc){
            exc.printStackTrace();
        }
    }
    @FXML
    private void getEm(KeyEvent ke) {
        if(ke.getCode().equals(ke.getCode().ENTER)){
            try{
                tableV.getItems().clear();
            Connection cn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
            String sqlS = "select * from USERS where TYPE = 'N'";
            PreparedStatement Pstat = cn.prepareStatement(sqlS);
            ResultSet result = Pstat.executeQuery();
            while(result.next()){
                if ((result.getString("ID").equals(searchField.getText()))||(result.getString("NAME").equals(searchField.getText()))){
                User u = new User();
                u.setId(result.getInt(1));
                u.setName(result.getString(2));
                userList.add(u);
                }
            }
            
            C12.setCellValueFactory(cellData -> cellData.getValue().getNameProp());
            C11.setCellValueFactory(cellData -> cellData.getValue().getIdProp().asObject());
            tableV.setItems(userList);
        
        }catch(SQLException ex){
//            warningSearch.setText("Somethig went wrong!");
            ex.printStackTrace();
//            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500),
//                    ActionEvent -> warningSearch.setText("  ")));
//            timeline.play();
        }
        }
    }
    @FXML
    public void searchAction(ActionEvent event){
        try{
            Connection cn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
//            historyList = FXCollections.observableArrayList();
            String sqlS = "select * from HISTORY where TYPE = 'U'";
            PreparedStatement Pstat = cn.prepareStatement(sqlS);
            ResultSet result = Pstat.executeQuery();
            while(result.next()){
                if ((result.getString("ID").equals(fieldIDs.getText()))||(result.getString("NAME").equals(fieldIDs.getText()))){
                History hs = new History();
                hs.setDate(result.getDate(1));
                hs.setId(result.getInt(5));
                hs.setName(result.getString(3));
                hs.setTime(result.getTime(2));
                hs.setReason(result.getString(4));
                historyList.add(hs);
                }
            }
            
            C1.setCellValueFactory(cellData -> cellData.getValue().getNameProp());
            C2.setCellValueFactory(cellData -> cellData.getValue().getIdProp().asObject());
            C3.setCellValueFactory(cellData -> cellData.getValue().getDateProp());
            C4.setCellValueFactory(cellData -> cellData.getValue().getReasonProp());
            tt.setItems(historyList);
        
        }catch(SQLException ex){
            warningSearch.setText("Somethig went wrong!");
            ex.printStackTrace();
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500),
                    ActionEvent -> warningSearch.setText("  ")));
            timeline.play();
        }
    }
    @FXML
    private void deleteAction(ActionEvent ae){
        try{
        String sqlD = "DELETE FROM USERS WHERE ID = ?";
        Connection con = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        PreparedStatement preS = con.prepareStatement(sqlD);
        User getItem =  (User) tableV.getSelectionModel().getSelectedItem();
        int id = getItem.getId();
        preS.setInt(1, id);
        preS.executeUpdate();
        }catch(SQLException exe){
            exe.printStackTrace();
        }
        
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}

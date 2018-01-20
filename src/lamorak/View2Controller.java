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
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAmount;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Spinner;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import java.time.*;
/**
 *
 * @author Touch-Me
 */
public class View2Controller extends TabPane implements Initializable{
    @FXML
    private Label label1, labPM, labDATE;
    @FXML
    private TableView tt;
    @FXML
    private Button sub,upd;
    @FXML
    private DatePicker fieldDATE;
    @FXML
    private TextField fieldID, fieldNAME, fieldPWD,fieldIDc,fieldNAMEc,fieldPWDc;
    @FXML
    private CheckBox checkVIP,matBox; 
    @FXML
    private TableColumn C1,C2,C3,C4;
    @FXML
    private TilePane tp6D,tp1;
    @FXML
    private ChoiceBox fm;
    @FXML
    private VBox matVBOX;
    @FXML
    private Tab t2,t1;
    

    //***********************DATABASE************************
    
    String sql = "insert into EMPLOYEE"
                +"(ID, NAME, VIP, PWD, INDATE, GENDER) values"
                +"(?,?,?,?,?,?)";
    PreparedStatement st = null; 
    Connection cn;
    //********************SPINNERS*****************************
    SpinnerValueFactory svf1;
    SpinnerValueFactory svf2;
    //************Radio Buttons*******************************
    ToggleGroup tg = new ToggleGroup();
    RadioButton rb2 = new RadioButton("2 Months");
    RadioButton rb4 = new RadioButton("4 Months");
    RadioButton rb6 = new RadioButton("6 Months");
    //*********************SECOND*TAB**************************
    @FXML
    public void backAction (ActionEvent event) throws Exception {
        Main m = new Main();
        m.goto1();
    }
    //**********************FIRST*TAB****************************
    @FXML
    public void submitAction (ActionEvent event){
        try {
            st = cn.prepareStatement(sql);
            st.setString(1,fieldID.getText());
            st.setString(2,fieldNAME.getText());
            st.setBoolean(3,checkVIP.isSelected());
            st.setString(4,fieldPWD.getText());
            st.setDate(5, Date.valueOf(fieldDATE.getValue()));
            st.setString(6, fm.getValue().toString());
//            st.setString(1,"5");
//            st.setString(2,"testNAME");
//            st.setBoolean(3,false);
//            st.setString(4,"4444");
                 st.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void CheckAction(ActionEvent event){
        if (fieldPWD.isDisable())
            fieldPWD.setDisable(false);
        else{
            fieldPWD.setDisable(true);
            fieldPWD.clear();
        }
    }
    //*************************************************************
    @FXML
    private void debugAction(MouseEvent me){
//        SpinnerValueFactory svf1 = sp1.getValueFactory();
//        System.out.println(svf.getValue());
//            System.out.println("Dbu(^O^)ugg");
//            System.out.println(svf.getValue());
            me.consume();
           //ObservableList<String> tab = new ObservableList<String>();
//           ObservableList<String> str = "aaa";
//           tt.setItems(value);
//           C1.setCellValueFactory(new PropertyValueFactory<Object,String>("TEST"));
    }
//    @FXML
//    private void t2Action(ActionEvent event){
//        //System.out.println(tt.getClass());
//        System.out.println("zzzzzz");
//    }
    //********************THIRD*TAB*************************************
    @FXML
    private void fetchAction(KeyEvent ke) throws SQLException{
        if (ke.isControlDown()){
                System.out.println("GIMME THOSE DATA");
        String sql = "select * from EMPLOYEE where ID = ? ";
//        Connection cn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        st = cn.prepareStatement(sql);
        st.setString(1, fieldIDc.getText());
        ResultSet result = st.executeQuery();
        result.next();
            fieldNAMEc.setText(result.getString(2));
            fieldPWDc.setText(result.getString(4));
            labDATE.setText(result.getDate(5).toString());
            svf1.setValue(result.getInt(8));
            svf2.setValue(result.getInt(10));
            if (result.getInt(6)!=2)
                matBox.setSelected(false);
            else
                matBox.setSelected(true);
            InitRadioButton(result.getInt(7));
            
        }
    }
    @FXML
    private void updateAction(ActionEvent event) throws SQLException{
        String sql2 = "update employee set NAME=?, PWD=?, MATER=?, PMATER=?, SPEC=?, MONVAC=? where ID = ?";
        st = cn.prepareStatement(sql2);
        st.setString(1, fieldNAMEc.getText());
        st.setString(2, fieldPWDc.getText());
        if (matBox.isSelected())
            st.setInt(3, 2);
        else
            st.setInt(3, 0);
        Integer x = new Integer(labPM.getText());
        st.setInt(4, x);
        st.setInt(5, (int) svf1.getValue());
        st.setInt(6, (int) svf2.getValue());
        st.setString(7, fieldIDc.getText());
        int exupdt = st.executeUpdate();
    }
    @FXML
    private void pwdOpen(MouseEvent me){
        int count = me.getClickCount();
        if (count==2)
            fieldPWDc.setEditable(true);
    }
    @FXML
    private void nameOpen(MouseEvent me){
        int count = me.getClickCount();
        if (count==2)
            fieldNAMEc.setEditable(true);
    }
    
//    fieldNAMEc.setOnMouseClicked((MouseEvent me) -> {
//        System.out.println("zzzz");
//        me.consume();
//    });
    //**************************************************************
    @FXML
    private void InitRadioButton(int x){
        rb2.setToggleGroup(tg);
        rb4.setToggleGroup(tg);
        rb6.setToggleGroup(tg);
        rb2.setOnMouseClicked((MouseEvent event)->{
            labPM.setText("2");
        });
        rb4.setOnMouseClicked((MouseEvent event)->{
            labPM.setText("4");
        });
        rb6.setOnMouseClicked((MouseEvent event)->{
            labPM.setText("6");
        });
        switch(x){
            case 2:
                rb2.setSelected(true);
                labPM.setText("2");
                break;
            case 4:
                rb4.setSelected(true);
                labPM.setText("4");
                break;
            case 6:
                rb6.setSelected(true);
                labPM.setText("6");
                break;
            default : 
                try{
                tg.getSelectedToggle().setSelected(false);
                }catch (Exception ex){
                    System.out.println("ROFL");
                }
        }
    }
    //*************************************************************
    @FXML
    private Spinner SetupSpinner1(int x, boolean y,int a, int b){
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
        svf1 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        Spinner sp = new Spinner();
        svf1.valueProperty().set(x);
        sp.setValueFactory(svf1);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        sp.setPrefWidth(80);
        sp.setEditable(y);
        return sp;
    }
    //**********************************************************
    private LocalDate diffBetween (LocalDate ld, LocalDate ld2){
       //*****************************ld-->old*******ld2-->new**
        int y = ld.getYear();
        int m = ld.getMonth().getValue();
        int d = ld.getDayOfMonth();
        //subtract year, month, day from new date (ld2) 
        LocalDate ld3 = ld2.minusYears(y).minusMonths(m).minusDays(d);
        return ld3;
    }
    private int countDays(LocalDate ld){
        int y = ld.getYear();
        int m = ld.getMonthValue();
        int d = ld.getDayOfMonth();
        int yd = 365*y;
        int md = 30*m;
        int dd = d;
        int cc = yd+md+dd;
        return cc;
    }
    @FXML
    private Spinner SetupSpinner2(int x, boolean y,int a, int b){
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
        svf2 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        Spinner sp = new Spinner();
        svf2.valueProperty().set(x);
        sp.setValueFactory(svf2);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        sp.setPrefWidth(80);
        sp.setEditable(y);
        return sp;
    }
    private void querryDate () throws SQLException{
        String sqlDATE = "select INDATE from EMPLOYEE where id = '4444'";
        st = cn.prepareStatement(sqlDATE);
        ResultSet result = st.executeQuery();
        result.next();
        System.out.println(result.getDate(1));
        
//        LocalDate ll = diffBetween(LocalDate.MIN, LocalDate.MIN)
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //********************Meat for dogs******************
        try {
            cn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        } catch (SQLException ex) {
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //***********************************************
         final ObservableList<String> data = FXCollections.observableArrayList("aaaa","bbbbbb","cccccc");

         TableColumn C1 = new TableColumn();
         C1.setText("C1");
         TableColumn C2 = new TableColumn();
         C2.setText("C2");
         TableColumn C3 = new TableColumn();
         C3.setText("C3");
         tt.setItems(data);
         tt.getColumns().addAll(C1,C2,C3);
         
        //****************************
        fm.getItems().addAll("F","M");
        fm.getSelectionModel().select("F");
        //***************************
        fieldDATE.setValue(LocalDate.now());//related to datepicker
        fieldPWD.setDisable(true);//PWD disabled on default
        //*****3rd tab text field*****
//        upd.setDisable(true);
        fieldNAMEc.setEditable(false);
        fieldPWDc.setEditable(false);
        matVBOX.setSpacing(5);//=================VBOX for rBUTTons========
        matVBOX.getChildren().addAll(rb2,rb4,rb6);
        InitRadioButton(0);//==================Putting them in UI====================
        //****3rd tab radio buttons***
//        rad2.setToggleGroup(tg);
//        rad4.setToggleGroup(tg);
//        rad6.setToggleGroup(tg);
//        if (rad2.isSelected())
//            labPM.setText("2");
//        if(rad4.isSelected())
//            labPM.setText("4");
//        if(rad6.isSelected())
//            labPM.setText("6");
//            String[] styles = {
//            "spinner",  // defaults to arrows on right stacked vertically
//            Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL,
//            Spinner.STYLE_CLASS_ARROWS_ON_LEFT_VERTICAL,
//            Spinner.STYLE_CLASS_ARROWS_ON_LEFT_HORIZONTAL,
//            Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL,
//            Spinner.STYLE_CLASS_SPLIT_ARROWS_HORIZONTAL
//        };
// 
        //TilePane tilePane = new TilePane();
//        tp.setPrefColumns(6);     //preferred columns
//        tp.setPrefRows(3);        //preferred rows
//        tp.setHgap(20);
//        tp.setVgap(30);
 
//        Pane root = new Pane();
//        root.setMinSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
//        root.setMaxSize(Pane.USE_PREF_SIZE, Pane.USE_PREF_SIZE);
 
//        for (int i = 0; i < styles.length; i++) {
//            /* Integer spinners */
//            SpinnerValueFactory svf =
//                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 99);
//            Spinner sp = new Spinner();
//            sp.setValueFactory(svf);
//            sp.getStyleClass().add(styles[i]);
//            sp.setPrefWidth(80);
//            tp.getChildren().add(sp);
//        }
//         SpinnerValueFactory svf =
//         new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 6);
//         
//         Spinner sp = new Spinner();
        //*********THEHOLYGROUND*******************************
//        try{
//        String sqlDATE = "select INDATE from EMPLOYEE where id = 44";
//        st = cn.prepareStatement(sqlDATE);
//        ResultSet result = st.executeQuery();
//        System.out.println(result.getDate(5));
////        result.next();
////        LocalDate ll = diffBetween(LocalDate.MIN, LocalDate.MIN)
//                
//        }catch(SQLException ex){
//                    
//        }

//        
//        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
////        format.setLenient(false);22-??
//////        Date d = Date.valueOf("2018/01/01");
//            Date dda = Date.valueOf("1996-11-16");
////            Date ddr = Date.valueOf(LocalDate.now());
////            Date ddz = Date.valueOf("1996-11-17");
//            Date dde = Date.valueOf("2018-01-20");
//            LocalDate lca = dda.toLocalDate();
//            LocalDate lce = dde.toLocalDate();
////            int x = dda.compareTo(ddz);
////            boolean tes = ddz.after(dda);
//            int y1 = lca.getYear();
////            int y2 = lce.getYear();
//            int m = lca.getMonth().getValue();
//            int d = lca.getDayOfMonth();
//            LocalDate lc1 = lce.minusYears(y1).minusMonths(m).minusDays(d);
            
//            Date ddx = Date.valueOf(lca);//********************************THHHHIS IS THE ONE FOR GOING BACK*********************************************
//             Date ddf = ddr-dda;
//            Date dd1 = Date.valueOf("2018/01/01");
//            dd.setTime(2018-01-01);
//            java.sql.Date dda = new Date(2018-05-11);
//            countDays(lc1);
//            
//            System.out.println(countDays(lc1));
        try {
            querryDate();
        } catch (SQLException ex) {
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tp6D.getChildren().add(SetupSpinner1(0, false, 0, 6));
        tp1.getChildren().add(SetupSpinner2(0, true,0,999));
        //tpPM.getChildren().add(SetupSpinner(0, false, 0, 6));
        
    }
    
}

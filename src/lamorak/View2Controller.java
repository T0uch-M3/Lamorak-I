/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import com.mysql.fabric.xmlrpc.base.Value;
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
import java.lang.Math;
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
import java.util.Formatter;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ScrollEvent;
/**
 *
 * @author Touch-Me
 */
public class View2Controller extends TabPane implements Initializable{
    @FXML
    private Label label1, labPM, labDATE, labY, labM, labD;
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
    int D,Y,M;
    int i=0;
    PreparedStatement st = null; 
    Connection cn;
    //********************SPINNERS*****************************
    SpinnerValueFactory svf1, svf2, svf21, svf22;
    int svm, svM;
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
            String sql = "insert into EMPLOYEE"
            +"(ID, NAME, VIP, PWD, INDATE, GENDER, MONVAC, LASTUPDATE) values"
            +"(?,?,?,?,?,?,?,?)";
            st = cn.prepareStatement(sql);
            st.setString(1,fieldID.getText());
            st.setString(2,fieldNAME.getText());
            st.setBoolean(3,checkVIP.isSelected());
            st.setString(4,fieldPWD.getText());
            st.setDate(5, Date.valueOf(fieldDATE.getValue()));
            st.setString(6, fm.getValue().toString());
            int d = diffBetween(fieldDATE.getValue(), LocalDate.now());
            st.setInt(7, d);
            st.setDate(8, Date.valueOf(LocalDate.now()));
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
    private void debugAction(ScrollEvent me){
        
        System.out.println(svf2.getValue());
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
            monthSpin(result.getInt(10));
            svf2.setValue(0);
            svf21.setValue(0);
            svf22.setValue(0);
            if (result.getInt(6)!=2)
                matBox.setSelected(false);
            else
                matBox.setSelected(true);
            InitRadioButton(result.getInt(7));
            
            
        }
    }
    private void monthSpin(int i){
        int d=0,m=0,y=0,t1=6,t2=2,t;
            if (i > 24){
                d = i/24;//counter when u get float result
                if (d>30){
                    m = d/30;
                    d = d-m*30;
                    if (m>12){
                        y = m/12;
                        m = m-y*12;
                    }
                }
            }     
        System.out.println("d "+d+" m "+m+" y "+y);
        D=d;M=m;Y=y;
        labD.setText(String.valueOf(d));
        labM.setText(String.valueOf(m));
        labY.setText(String.valueOf(y));
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
    @FXML
    private SpinnerValueFactory spinnerVF (int a, int b){
        svf1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        return svf1;
    }
    @FXML
    private Spinner SetupSpinner2(int x, boolean y,int a, int b){
        
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
        svf2 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        Spinner sp = new Spinner();
        svf2.valueProperty().set(x);
        sp.setValueFactory(svf2);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        sp.setPrefWidth(32);
        
        sp.setOnMouseClicked((MouseEvent event)->{
            Integer inB = new Integer(labD.getText());
            Integer inSP = new Integer(String.valueOf(sp.getValue()));
            if (inB>0){
                labD.setText(String.valueOf(inSP+inB+i));
                inB++;
                i++;
                System.out.println("inB"+inB);
            }
            if(inB==0){
             svf2.setValue(inB);
//                sp.setValueFactory(svf1);
            }
        });
        sp.setEditable(y);
        return sp;
    }
    @FXML
    private Spinner SetupSpinner21(int x, boolean y,int a, int b){
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
        svf21 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        Spinner sp = new Spinner();
        svf21.valueProperty().set(x);
        sp.setValueFactory(svf21);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        sp.setPrefWidth(32);
        sp.setEditable(y);
        return sp;
    }
    @FXML
    private Spinner SetupSpinner22(int x, boolean y,int a, int b){
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
        svf22 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        Spinner sp = new Spinner();
        svf22.valueProperty().set(x);
        sp.setValueFactory(svf22);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_SPLIT_ARROWS_VERTICAL);
        sp.setPrefWidth(32);
        sp.setEditable(y);
        return sp;
    }
    //*************************************************************
    @FXML
    private Spinner SetupSpinner1(int x, boolean y,SpinnerValueFactory svf){
        //***x-->init value****y-->editable or NO****a-->MIN****b-->MAX****i-->ID**
//        svf1 =  new SpinnerValueFactory.IntegerSpinnerValueFactory(a,b);
        
        Spinner sp = new Spinner();
        svf.valueProperty().set(x);
        sp.setValueFactory(svf);
        sp.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        sp.setPrefWidth(80);
        Tooltip ttip = new Tooltip("THIS the 6 day one");
        sp.setTooltip(ttip);
        sp.setOnMouseClicked((MouseEvent event)->{
            System.out.println(svf.getValue());
        });
        sp.setEditable(y);
        return sp;
    }
    //**********************************************************
    private int diffBetween (LocalDate ld, LocalDate ld2){
    //NOTPERFECT****************ld-->old*******ld2-->new**
        int y = ld.getYear(), y2 = ld2.getYear();
        int Y = (y2-y)*365;
        
        int m = ld.getMonthValue(), m2 = ld2.getMonthValue();
        int M = (m2-m)*30;
        
        int d = ld.getDayOfMonth(), d2 = ld2.getDayOfMonth();
        int D = d2-d;
        
        int CC = Y+M+D;
        // return the difference in hours, 1 day = 2 hours
        return CC*2;
    }
    private void updateVac(String id, int mv) throws SQLException{
        String sqlUPDATE = "update EMPLOYEE set MONVAC = ? where ID = ?";
        st = cn.prepareStatement(sqlUPDATE);
        st.setInt(1, mv);
        st.setString(2, id);
        int exUpd = st.executeUpdate();
    }
    private void queryDate () throws SQLException{
        //fetch all rows and update MONVAC
        String sqlDATE = "select INDATE, ID from EMPLOYEE";
        st = cn.prepareStatement(sqlDATE);
        ResultSet result = st.executeQuery();
        while (result.next()){
            LocalDate ld = result.getDate(1).toLocalDate();
            int d = diffBetween(ld, LocalDate.now());
            updateVac(result.getString(2),d);
        }
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
       
        
        //================THEHOLYGROUND========================
//        try {
//            queryDate();
//        } catch (SQLException ex) {
//            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
//        }
        //====================================================
        tp6D.getChildren().add(SetupSpinner1(0, false, spinnerVF(0, 6)));
        tp1.getChildren().addAll(SetupSpinner2(0, true,-999,0),SetupSpinner21(0, true,-999,0),SetupSpinner22(0, true,-999,0));
        //tpPM.getChildren().add(SetupSpinner(0, false, 0, 6));
        
    }
    
}

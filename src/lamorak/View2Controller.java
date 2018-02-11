/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXRadioButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import static javafx.event.Event.ANY;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Spinner;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
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
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
/**
 *
 * @author Touch-Me
 */
public class View2Controller extends TabPane implements Initializable{
    @FXML
    private Label labMat,labPM,labDATE,labY,labM,labD,warningID,warningName,warningUpd,updDone,warningSearch,addDone;
    @FXML
    private TableView tt;
    @FXML
    private Button sub,upd, proceedB, testButton, noteField,arrowD;
    @FXML
    private DatePicker fieldDATE;
    @FXML
    private TextField fieldID,fieldNAME,fieldPWD,fieldIDc,fieldNAMEc,fieldPWDc,fieldIDs,fieldNames;
    @FXML
    private CheckBox checkVIP,matBox; 
    @FXML
    private TilePane tp6D,tp1;
    @FXML
    private ComboBox<String> fm;
    @FXML
    private VBox matVBOX;
    @FXML
    private Tab tabA,tabC,tabH;
    @FXML
    private TableColumn<History, String> C1;
    @FXML
    private TableColumn<History, Date> C2;
    @FXML
    private TableColumn<History, Time> C3;
    @FXML
    private TableColumn<History, String> C4;
    @FXML
    private JFXRadioButton rb2, rb4,rb6;
    @FXML
    public JFXDrawer drawPane,drawPane1,drawPane2;
    @FXML
    private AnchorPane anchorA, anchorC, anchorH;
    @FXML
    private ImageView outArrow;
    @FXML
    private Group firstGroup;

    //*******************RANDOMWIERDVARIALBLES**********
    
    Timeline timeL;
    boolean left = false, entered = false;
    Boolean vis = true;
    boolean checkL = false;
    Event iv = new Event(ANY);
    ObservableList<String> data;
    ObservableList<History> historyList;
    boolean checked = false;//for matbox listening
    boolean startFetch = false; //detect whether fetching results started or no
    ObservableList<String> boxList = FXCollections.observableArrayList("F","M");
    //***********************DATABASE************************
    int D,Y,M;
    PreparedStatement st = null; 
    Connection cn;
    //********************SPINNERS*****************************
    //svf1 should be for the 6 day one, the rest go respectively D/M/Y (i think, just too lazy to go through the code)
    SpinnerValueFactory svf1, svf2, svf21, svf22;
    int svm, svM;
    //************Radio Buttons*******************************
    
    ToggleGroup tg = new ToggleGroup();
//    JFXRadioButton rb2 = new JFXRadioButton("2 Months");
//    JFXRadioButton rb4 = new JFXRadioButton("4 Months");
//    JFXRadioButton rb6 = new JFXRadioButton("6 Months");
    //*********************THIRD*TAB**************************
    @FXML
    public void backAction (ActionEvent event) throws Exception {
        Main m = new Main();
        m.goto1();
    }
    @FXML
    private void searchActionM(MouseEvent ae){
        if (ae.getButton()==ae.getButton().PRIMARY){
            startSearch();
        }
    }
    @FXML 
    private void searchActionK(KeyEvent ke){
        if (ke.getCode()== ke.getCode().ENTER){
            startSearch();
        }
    }
    @FXML
    public void startSearch(){
        System.out.println("FUKKKKKKKKKKKK");
        try{
            historyList = FXCollections.observableArrayList();
            String sqlS = "select * from HISTORY where TYPE = 'E'";
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
            C2.setCellValueFactory(cellData -> cellData.getValue().getDateProp());
            C3.setCellValueFactory(cellData -> cellData.getValue().getTimeProp());
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
    //**********************FIRST*TAB****************************
    @FXML
    public void submitAction (ActionEvent event){
        try {
            String sql = "insert into EMPLOYEE"
            +"(ID, NAME, INDATE, GENDER, MONVAC, LASTUPDATE) values"
            +"(?,?,?,?,?,?)";
            st = cn.prepareStatement(sql);
            st.setString(1,fieldID.getText());
            st.setString(2,fieldNAME.getText());
            
            
            st.setDate(3, Date.valueOf(fieldDATE.getValue()));
            st.setString(4, fm.getValue().toString());
            int d = diffBetween(fieldDATE.getValue(), LocalDate.now());
            st.setInt(5, d);
            st.setDate(6, Date.valueOf(LocalDate.now()));
            st.executeUpdate();
            addDone.setText("Successfully added!");
        } catch (SQLException ex) {
            Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), AE -> addDone.setText("  ")));
            timeline.play();
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void registerHistory (String id, String reason, String name, Date date, Time time) throws SQLException{
        String sqlH = "insert into HISTORY"+
                "(ID, NAME, DATE, TIME, REASON)"+
                "values (?,?,?,?,?)";
        st = cn.prepareStatement(sqlH);
        st.setString(1, id);
        st.setString(2, name);
        st.setDate(3, date);
        st.setTime(4, time);
        st.setString(5, reason);
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
    private void debugAction(KeyEvent ke) throws SQLException{
            if(ke.getCode()==ke.getCode().ENTER)
                System.out.println(fieldIDs.getText());
//        SpinnerValueFactory svf1 = sp1.getValueFactory();
//        System.out.println(svf.getValue());
//            System.out.println("Dbu(^O^)ugg");
//            System.out.println(svf.getValue());
//            me.consume();
           //ObservableList<String> tab = new ObservableList<String>();
//           ObservableList<String> str = "aaa";
//           tt.setItems(value);
//           C1.setCellValueFactory(new PropertyValueFactory<Object,String>("TEST"));
    }

    //********************SECOND*TAB*************************************
    @FXML
    private void startFetch(){
        
        try{
            startFetch= true;
            String sql = "select * from EMPLOYEE where ID = ? or NAME = ? ";
            st = cn.prepareStatement(sql);
            st.setString(1, fieldIDc.getText());
            st.setString(2, fieldNAMEc.getText());
            ResultSet result = st.executeQuery();
            result.next();
            fieldIDc.setText(result.getString(1));
            fieldNAMEc.setText(result.getString(2));

            labDATE.setText(result.getDate(3).toString());
            labMat.setText(String.valueOf(result.getInt(4)));
            InitRadioButton(result.getInt(5));
            svf1.setValue(result.getInt(6));
            fromHourToDay(result.getInt(8));
            svf2.setValue(0);
            svf21.setValue(0);
            svf22.setValue(0);
            matBox.setSelected(false);
            
        }catch (SQLException ex){
            //display warning 
            if(fieldIDc.isFocused()){
                
                warningID.setText("doesn't exist!");
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500), 
                ActionEvent -> warningID.setText("  ")));
                timeline.play();
            }
            if(fieldNAMEc.isFocused()){
                warningName.setText("doesn't exist!");
                Timeline timeline = new Timeline(new KeyFrame(Duration.millis(2500),
                ae -> warningName.setText("  ")));
                timeline.play();
            }
            
        }
    }
    @FXML
    private void fetchActionK( KeyEvent ke ) {
        if (ke.getCode()== ke.getCode().ENTER){
            if(fieldNAMEc.isFocused())
                fieldIDc.setText("");
            else
                fieldNAMEc.setText("");
            startFetch();
        }
    }
    @FXML
    private void fetchActionM( MouseEvent me ) {
        if (me.getButton()==me.getButton().PRIMARY){
            if(fieldNAMEc.isFocused())
                fieldIDc.setText("");
            else
                fieldNAMEc.setText("");
            startFetch();
        }
    }
    private int fromDayToHour(){
        Integer d = new Integer(labD.getText());
        Integer m = new Integer(labM.getText());
        Integer y = new Integer(labY.getText());
        int dd = d*24;
        int mm = m*720;
        int yy = y*8640;
        return dd+mm+yy;
    }
    private void fromHourToDay(int i){
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
//        System.out.println("d "+d+" m "+m+" y "+y);
        D=d;M=m;Y=y;

        labD.setText(String.valueOf(d));
        labM.setText(String.valueOf(m));
        labY.setText(String.valueOf(y));
    }
    @FXML
    private void updateAction(ActionEvent event){
    try{
        String sql2 = "update employee set NAME=?, MATER=?, PMATER=?, SPEC=?, MONVAC=?, LASTUPDATE=? where ID = ?";
        st = cn.prepareStatement(sql2);
        //****************2/4/6 vac for PMAT
        Integer x = new Integer(labPM.getText());
        //========just before the update happe, a log get created============================
        makeHistory(fieldIDc.getText(),fieldNAMEc.getText(),checked, x, (int)svf1.getValue());
        
        st.setString(1, fieldNAMEc.getText());
        if (matBox.isSelected()){
            //add 2 months to the current flow 
            st.setInt(2, Integer.valueOf(labMat.getText())+2);
            checked=true;
        }
        else{
            st.setInt(2, Integer.valueOf(labMat.getText()));
            checked = false;
        }
        st.setInt(3, x);
        st.setInt(4, (int) svf1.getValue());//6 days vac
        st.setInt(5, fromDayToHour());
        st.setDate(6, Date.valueOf(LocalDate.now()));
        st.setString(7, fieldIDc.getText());
        
        int exupdt = st.executeUpdate();
        
        updDone.setText("Done");
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(2500), ActionEvent -> updDone.setText("  ")));
        timeline.play();
    }catch (NumberFormatException | SQLException exc){
        warningUpd.setText("Something went wrong!");
        warningUpd.setId("UpdLabel");
        Timeline timeline = new Timeline(new KeyFrame(
        Duration.millis(2500),
        ae -> warningUpd.setText("")));
        timeline.play();
        exc.printStackTrace();

    }
    }

    @FXML
    private void pwdOpen(MouseEvent me){
        int count = me.getClickCount();
        if (count==3)
            fieldPWDc.setEditable(true);
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
        sp.setOnMouseReleased((MouseEvent event)->{
            Integer inB = new Integer(labD.getText());
            Integer inBB = new Integer(labD.getText());
            Integer inSP = new Integer(String.valueOf(sp.getValue()));

            if (inB>0){
                labD.setText(String.valueOf(D+inSP));
//                System.out.println("inSP "+inSP);
//                System.out.println("inB "+inB);
//                System.out.println("DDDDD "+D);
            }
            if(inB==0 && inSP<(-D)){
             svf2.setValue(-D);
            }
            if(inB==0 && inSP==(-D+1))
                labD.setText(String.valueOf(D+inSP));
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
        
        sp.setOnMouseReleased((MouseEvent event)->{
            Integer inB = new Integer(labM.getText());
            Integer inSP = new Integer(String.valueOf(sp.getValue()));
            if (inB>0){
                labM.setText(String.valueOf(M+inSP));
//                System.out.println("inSP "+inSP);
//                System.out.println("inB "+inB);
//                System.out.println("DDDDD "+D);
            }
            if(inB==0 && inSP<(-M)){
             svf21.setValue(-M);
            }
            if(inB==0 && inSP==(-M+1))
                labM.setText(String.valueOf(M+inSP));
        });
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
        
        sp.setOnMouseReleased((MouseEvent event)->{
            Integer inB = new Integer(labY.getText());
            Integer inSP = new Integer(String.valueOf(sp.getValue()));
            if (inB>0){
                labY.setText(String.valueOf(Y+inSP));
//                System.out.println("inSP "+inSP);
//                System.out.println("inB "+inB);
//                System.out.println("DDDDD "+D);
            }
            if(inB==0 && inSP<(-Y)){
             svf22.setValue(-Y);
            }
            if(inB==0 && inSP==(-Y+1))
                labY.setText(String.valueOf(Y+inSP));
        });
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
    //NOTPERFECT*****ld2-ld*********ld-->old*******ld2-->new**
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
    private void updateVac(int dif, int cur, String id, int spec, Date indate) throws SQLException{
        String sqlUPDATE = "update EMPLOYEE set MONVAC = ?, LASTUPDATE = ?, SPEC = ? where ID = ?";
        st = cn.prepareStatement(sqlUPDATE);
        st.setInt(1, cur+dif);//(cur)rent amount + the (dif)ference between last update and currentDate
        st.setDate(2, Date.valueOf(LocalDate.now()));
        //updating 6 day vacation********
        Calendar cal = Calendar.getInstance();
        cal.setTime(indate);
        if (cal.get(Calendar.YEAR) < LocalDate.now().getYear()){
            st.setInt(3, 0);
        }if( cal.get(Calendar.YEAR) == LocalDate.now().getYear()) {
            st.setInt(3, spec);
                    }
        //**********************************
        st.setString(4, id);
        int exUpd = st.executeUpdate();
        
    }
    @FXML
    private void queryDate () throws SQLException{
        //fetch all rows and update MONVAC
        int dif,cur;
        String sqlDATE = "select LASTUPDATE, ID, MONVAC, SPEC, INDATE from EMPLOYEE";
        st = cn.prepareStatement(sqlDATE);
        ResultSet result = st.executeQuery();
        
        while (result.next()){
            LocalDate ldOld = result.getDate(1).toLocalDate();
            if (ldOld != LocalDate.now()){
                //to prevent adding hours more than once a day
                dif = diffBetween(ldOld, LocalDate.now());
                cur = result.getInt(3);
                updateVac(dif,cur,result.getString(2), result.getInt(4), result.getDate(5));
            }
        }
    }
    @FXML
    private boolean checkEmpty() throws SQLException{
        String sqlE = "select * from THINGS";
        st = cn.prepareStatement(sqlE);
        ResultSet result = st.executeQuery();
        if (result.next())
            return false;
        else
            return true;
    }
    private void fillHistory(History h) throws SQLException{
        String sqlH = "insert into History (ID, DATE, TIME, NAME, REASON, TYPE)"
                +"values (?,?,?,?,?,?)";
        PreparedStatement stf = cn.prepareStatement(sqlH);
        stf.setInt(1, h.getId());
        stf.setDate(2, h.getDate());
        stf.setTime(3, h.getTime());
        stf.setString(4, h.getName());
        stf.setString(5, h.getReason());
        stf.setString(6, "E");
        stf.executeUpdate();
    }
    private void makeHistory (String id, String name, boolean matV, int tfs, int svfInc ){
        try{
        Integer idd = new Integer(id);
        String sqlH1 = "select * from EMPLOYEE where ID = ?";
        PreparedStatement sth = cn.prepareStatement(sqlH1);
        //used "PreparedStatement sth" and not just st, cu'z some conflict apeared 
        //between variable, cu'z i have st as global (damn it, it took me a while to figure it out
        sth.setString(1, id);
        ResultSet result = sth.executeQuery();
        result.next();
        if (result.getString(2).compareToIgnoreCase(name)!=0){
            System.out.println("name "+name+" name from db "+result.getString("NAME") );
            History hs = new History();
            hs.setDate(Date.valueOf(LocalDate.now()));
            hs.setId(idd);
            hs.setName(name);
            hs.setTime(Time.valueOf(LocalTime.now()));
            hs.setReason("name chaged");
            fillHistory(hs);
        }
        if ((result.getInt(4)!=Integer.valueOf(labMat.getText()))){
//            System.out.println("old mat "+result.getInt(4)+""+matV);
            History hs = new History();
            hs.setDate(Date.valueOf(LocalDate.now()));
            hs.setId(idd);
            hs.setName(name);
            hs.setTime(Time.valueOf(LocalTime.now()));
            hs.setReason("Maternite selected");
            fillHistory(hs);
        }
        if ((result.getInt(5)!=Integer.valueOf(labPM.getText()))){
//            System.out.println("old mat "+result.getInt(4)+""+matV);
            History hs = new History();
            hs.setDate(Date.valueOf(LocalDate.now()));
            hs.setId(idd);
            hs.setName(name);
            hs.setTime(Time.valueOf(LocalTime.now()));
            hs.setReason("Post Maternite changed to "+labPM.getText());
            fillHistory(hs);
        }
        if ((result.getInt(6)!=Integer.valueOf(svf1.getValue().toString()))){
//            System.out.println("old mat "+result.getInt(4)+""+matV);
            History hs = new History();
            hs.setDate(Date.valueOf(LocalDate.now()));
            hs.setId(idd);
            hs.setName(name);
            hs.setTime(Time.valueOf(LocalTime.now()));
            hs.setReason("6 day Vacation current status "+svf1.getValue().toString());
            fillHistory(hs);
        }
        if ((result.getInt(8)!=fromDayToHour())){
//            System.out.println("old mat "+result.getInt(4)+""+matV);
            History hs = new History();
            hs.setDate(Date.valueOf(LocalDate.now()));
            hs.setId(idd);
            hs.setName(name);
            hs.setTime(Time.valueOf(LocalTime.now()));
            hs.setReason("Changed From"+"Y: "+Y+" M: "+M+" D: "+D+" TO Y: "+labY.getText()+" M: "+labM.getText()+" D: "+labD.getText());
            fillHistory(hs);
        }
        }catch(SQLException ex){
            ex.printStackTrace();
        }       
    }
    //************************************
    @FXML
    private void transitionMeth(Event ev){
        if(tabA.isSelected()){
                if(anchorA.getChildren().contains(noteField)==false)
                anchorA.getChildren().add(noteField);
                if(anchorA.getChildren().contains(firstGroup)==false)
                anchorA.getChildren().add(firstGroup);
                firstGroup.toBack();
        }
        else if (tabC.isSelected()){
                if(anchorC.getChildren().contains(noteField)==false)
                anchorC.getChildren().add(noteField);
                if(anchorC.getChildren().contains(firstGroup)==false)
                anchorC.getChildren().add(firstGroup);
                firstGroup.toBack();
        }
        else if (tabH.isSelected()){
                if(anchorH.getChildren().contains(noteField)==false)
                anchorH.getChildren().add(noteField);
                if(anchorH.getChildren().contains(firstGroup)==false)
                anchorH.getChildren().add(firstGroup);
                firstGroup.toBack();
        }
    }
    //the following 3 methods are for the hovering mechanic, this's crazy ikn
    @FXML
    private void checkIn(MouseEvent me){
        if (vis==true){
        entered = true;
        left = false;
        System.out.println("in");
        }
    }
    @FXML
    private void checkOut(MouseEvent me){
        if (vis == false){
        entered = false;
        timeL = new Timeline(new KeyFrame(Duration.millis(500), ae -> {
            if (entered==false){
                noteField.toFront();
                noteField.setVisible(true);
                vis=true;
                System.out.println("out");
                left = true;
                firstGroup.setDisable(true);
            }
        }));
        timeL.play();
        }
    }
    @FXML
    private void options (MouseEvent me){
        firstGroup.setDisable(false);
        noteField.toBack();
        noteField.setVisible(false);
        vis=false;
    }
    //***********************************************************************
    @FXML
    public static void addingLog(String state) throws SQLException{
        String sqlL = "insert into History (ID, DATE, NAME, REASON, TYPE, TIME)"
                +"values (?,?,?,?,?,?)";
        Connection connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
        PreparedStatement prs = connection.prepareStatement(sqlL);
        prs.setInt(1, Integer.valueOf(View1Controller.currentId));
        prs.setDate(2, Date.valueOf(LocalDate.now()));
        prs.setString(3, View1Controller.currentName);
        prs.setString(4, state);
        prs.setString(5, "U");
        prs.setTime(6, Time.valueOf(LocalTime.now()));
        prs.executeUpdate();
    }
    @FXML
    private void mainMenu(ActionEvent ae) throws Exception{
        Main m = new Main();
        addingLog("Left");
        m.goto1();
    }
    @FXML
    private void gotoPassword(ActionEvent ace) throws Exception{
        Main main = new Main();
        main.goto6();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //it appear the initialize is really slow that i had to delay
        //code execution so this methode can catch up on it :(
        Timeline tmLine = new Timeline(new KeyFrame(Duration.millis(500), ae -> {
            noteField.setText(View1Controller.currentName);
            try {
                addingLog("Entered");
            } catch (SQLException ex) {
                Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
                }));
        tmLine.play();
        
        //********************Meat for dogs******************
        try {
            cn = DriverManager.getConnection("jdbc:derby://localhost:1527/Project","Test","test");
//            addingLog();
        } catch (SQLException ex) {
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //***********************************************
        data = FXCollections.observableArrayList();

        //****************************
        fm.setItems(boxList);
//        fm.getSelectionModel().select("F");
        //***************************
        fieldDATE.setValue(LocalDate.now());//related to datepicker
        InitRadioButton(0);//==================Putting them in UI====================

        //================THEHOLYGROUND========================
        try {
            queryDate();
        } catch (SQLException ex) {
            Logger.getLogger(View2Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
        //====================================================
        tp6D.getChildren().add(SetupSpinner1(0, false, spinnerVF(0, 6)));
        tp1.getChildren().addAll(SetupSpinner2(0, true,-999,0),SetupSpinner21(0, true,-999,0),SetupSpinner22(0, true,-999,0));
        
    }
    
}

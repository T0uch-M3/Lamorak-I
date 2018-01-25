/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Touch-Me
 */
public class History {
    StringProperty name,reason;
    IntegerProperty id;
    SimpleObjectProperty<Date> date;
    SimpleObjectProperty<Time>time;
    
    
    public History(){
        this.id = new SimpleIntegerProperty();
        this.reason = new SimpleStringProperty();
        this.name = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.time = new SimpleObjectProperty<>();
    }
    
    public int getId(){
        return id.get();
    }
     public IntegerProperty getIdProp(){
        return id;
    }
    public void setId(int id){
        this.id.set(id);
    }
    //*********************************
    public StringProperty getNameProp(){
        return name;
    }
    public String getName(){
        return name.get();
    }
    public void setName(String name){
        this.name.set(name);
    }
    //***********************************
    public SimpleObjectProperty<Date> getDateProp(){
        return date;
    }
    public void setDate(Date date){
        this.date.set(date);
    }
    public Date getDate(){
        return date.get();
    }
    //************************************
    public SimpleObjectProperty<Time> getTimeProp(){
        return time;
    }
    public void setTime(Time time){
        this.time.set(time);
    }
    public Time getTime(){
        return time.get();
    }
    //**********************************
    public String getReason(){
        return reason.get();
    }
    public void setReason(String reason){
        this.reason.set(reason);
    }
    public StringProperty getReasonProp(){
        return reason;
    }
    //**********************************
}

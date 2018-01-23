/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.sql.Array;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Touch-Me
 */
public class Reasons {
    StringProperty tab[]= new StringProperty[30];
    
    IntegerProperty id;
    StringProperty st ;

    public Reasons() {
        //filling();
        this.id = new SimpleIntegerProperty();
        this.st = new SimpleStringProperty();
    }
//    public void filling(){
//        tab[1]= "TEST bitches";
//        tab[2]= "TEST fuckers";
//    }
    private String ToString(){
        return "id= "+id+"reason= "+st;
    }
    public int getID(){
        return id.get();
    }
     public IntegerProperty getIDProp(){
        return id;
    }
    public void setID(int id){
        this.id.set(id);
    }
   

    public StringProperty getSTProp(){
        return st;
    }
    public String getST(){
        return st.get();
    }
    public void setST(String st){
        this.st.set(st);
    }
}

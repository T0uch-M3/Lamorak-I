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
    String tab[]= new String[] {"test1","test2"};
    
    IntegerProperty id;
    StringProperty st ;

    public Reasons() {
        //filling();
        this.id = new SimpleIntegerProperty();
        this.st = new SimpleStringProperty();
    }
    
    
    public int getID(){
        return id.get();
    }
     public IntegerProperty getIDProp(){
        return id;
    }
    public void setID(int id){
            this.st.set(tab[id]);
        this.id.set(id);
    }
   

    public StringProperty getSTProp(){
        return st;
    }
    public String getST(){
        return st.get();
    }
    public void setST(String st){
//        if (st=="fuck")
        this.st.set(st);
//        this.st.set("DID U SAY FUCK?");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Touch-Me
 */
public class User {
    StringProperty name;
    IntegerProperty id;
    
    
    public User(){
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
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
}

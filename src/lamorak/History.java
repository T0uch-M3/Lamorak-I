/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Touch-Me
 */
public class History {
    private String id, reason, name;
    private Date date;
    private Time time;
    
    public History(String id, String reason, String name, Date date, Time time){
        this.id = id;
        this.reason = reason;
        this.name = name;
        this.date = date;
        this.time = time;
    }
    
    
}

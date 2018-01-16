/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lamorak;

import javafx.scene.layout.AnchorPane;
import javafx.event.ActionEvent;
/**
 *
 * @author Touch-Me
 */
public class View2Controller extends AnchorPane {
    
    
    public void ConfirmAction (ActionEvent event) throws Exception {
        Main m = new Main();
        m.goto2();
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;

/**
 *
 * @author AMore
 */
@Named(value = "adminManager")
@ConversationScoped
public class AdminManager implements Serializable {

    /**
     * Creates a new instance of AdminManager
     */
    public AdminManager() {
    }
    
}

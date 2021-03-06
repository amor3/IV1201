/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import se.kth.controller.AdminController;
import se.kth.integration.DuplicateEntryException;

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
    @EJB
    private AdminController adminController;
     private String email;
     
     
    public AdminManager() {
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * create a new recruiter by calling 
     * admin controller's creatRecruiter's method
     */
    public void creatRecruiter(){
        try {
            adminController.createRecriuter(email);
        } catch (DuplicateEntryException ex) {
            Logger.getLogger(AdminManager.class.getName()).log(Level.SEVERE, null, ex);
        }
}
    
}

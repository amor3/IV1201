/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
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

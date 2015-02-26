/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
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

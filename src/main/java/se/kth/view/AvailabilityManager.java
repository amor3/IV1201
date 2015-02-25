/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
 */
package se.kth.view;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;

/**
 *
 * @author AMore
 */
@Named(value = "availabilityManager")
@ConversationScoped
public class AvailabilityManager implements Serializable{

    private String fromDate;
    private String toDate;
    /**
     * Creates a new instance of AvailabilityManager
     */
    public AvailabilityManager() {
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
    
    
    
}

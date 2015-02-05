/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

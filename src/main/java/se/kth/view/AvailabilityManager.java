/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.Dependent;

/**
 *
 * @author AMore
 */
@Named(value = "availabilityManager")
@Dependent
public class AvailabilityManager {

    /**
     * Creates a new instance of AvailabilityManager
     */
    public AvailabilityManager() {
    }
    
}

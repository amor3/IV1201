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
@Named(value = "applicantManager")
@ConversationScoped
public class ApplicantManager implements Serializable {

    /**
     * Creates a new instance of ApplicantManager
     */
    public ApplicantManager() {
    }
    
}

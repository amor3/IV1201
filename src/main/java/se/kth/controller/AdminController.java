/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;

/**
 *
 * @author AMore
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class AdminController {
    
    @EJB
    private PersonDAO personDAO;
    
    /**
     * Creates Recruiter by giving email
     * as an ID for the recruiter
     * 
     * @param email 
     */
    
    public void createRecriuter(String email){
        if(email != null){
            personDAO.createRecriuter(new PersonDTO(email));
            
        }
        
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

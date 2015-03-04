/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;
import se.kth.model.PersonInterface;

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
     * @throws se.kth.integration.DuplicateEntryException 
     */
    
    public void createRecriuter(String email) throws DuplicateEntryException{
        if(email != null){
            try {
                personDAO.createRecriuter(new PersonDTO(email));
            } catch (NullArgumentException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public void removeRecruiter(String email) throws DoesNotExistException{
        if(email != null){
            try {
                personDAO.removePerson(email);
            } catch (NullArgumentException ex) {
                Logger.getLogger(AdminController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public List<PersonInterface> getAllRecruiters(){
        return personDAO.getAllRecruiters();
    }

}

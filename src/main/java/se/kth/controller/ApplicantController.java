/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;
import se.kth.model.Person;

/**
 *
 * @author AMore
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class ApplicantController {
    
    @EJB
    private PersonDAO personDAO;
    
    
    
    public Person getLoggedInPerson(){
        String email = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser();
        
        Person person = null;
        try {
            person = (Person)personDAO.getPerson(email);
        } catch (NullArgumentException | DoesNotExistException ex) {
            Logger.getLogger(ApplicantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return person;
    }
    
    
    public boolean saveUserProfile(String email, String firstname, String lastname, String ssn){
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        personDTO.setFirstname(firstname);
        personDTO.setSurname(lastname);
        personDTO.setSsn(ssn);
        boolean success = false;
        try {
            success = personDAO.updatePerson(personDTO);
        } catch (NullArgumentException ex) {
            // TODO: Add popup to xhtml
            Logger.getLogger(ApplicantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    public boolean saveUserCredentials(String email, String oldPassword, String newPassword, String newPasswordAgain){
        boolean success = false;
        try {
            success = personDAO.updatePersonCredentials(email, oldPassword, newPassword, newPasswordAgain);
        } catch (NullArgumentException ex) {
            // TODO: Add popup to xhtml
            Logger.getLogger(ApplicantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    
}

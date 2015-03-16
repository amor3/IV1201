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
    
    
    /**
     *  Gets a user who is logged in
     * 
     * @return Person 
     */
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
    
    /**
     * Save the user's profile after creating or
     * changing it.
     * 
     * @param email
     * @param firstname
     * @param lastname
     * @param ssn
     * @return ture if saving is succeeded, false if not
     * @throws DoesNotExistException 
     */
    public boolean saveUserProfile(String email, String firstname, String lastname, String ssn) throws DoesNotExistException {
        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        personDTO.setFirstname(firstname);
        personDTO.setSurname(lastname);
        personDTO.setSsn(ssn);
        boolean success = false;
        try {
            personDAO.updatePerson(personDTO);
            success = true;
        } catch (NullArgumentException ex) {
            Logger.getLogger(ApplicantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    /**
     * Save user's credentials when creating or 
     * changing.
     * 
     * @param email
     * @param oldPassword
     * @param newPassword
     * @param newPasswordAgain
     * @return true if succeed, false if not.
     * @throws DoesNotExistException 
     */
    public boolean saveUserCredentials(String email, String oldPassword, String newPassword, String newPasswordAgain) throws DoesNotExistException{
        boolean success = false;
        try {
            success = personDAO.updatePersonCredentials(email, oldPassword, newPassword, newPasswordAgain);
        } catch (NullArgumentException ex) {
            Logger.getLogger(ApplicantController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return success;
    }
    
    
}

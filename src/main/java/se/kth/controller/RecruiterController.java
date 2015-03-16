/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.integration.CompetenceDAO;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;
import se.kth.utility.logger.Log;
import se.kth.model.CompetenceLangInterface;
import se.kth.model.PersonInterface;

/**
 *
 * @author AMore e bäst
 */
@Log
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RecruiterController {

    @EJB
    private CompetenceDAO competenceDAO;
    
    @EJB
    private PersonDAO personDAO;
    
    
    /**
     *
     * @param nameEn Competence Name in English
     * @param nameSV Competence name in Swedish
     * 
     */
    public void creatCompetence(String nameEN, String nameSV) throws DuplicateEntryException, NullArgumentException {
        if (nameEN != null && nameSV != null) {
            competenceDAO.creatCompetenc(nameEN, nameSV);
            
        }
    }
    
    /**
     * removes a competence
     * 
     * @param comp the competence to be removed
     * @throws DoesNotExistException
     * @throws NullArgumentException 
     */
    public void removeCompetence(String comp) throws DoesNotExistException, NullArgumentException {
        competenceDAO.removeCompetence(comp);
    }
    /**
     * Get all competences registered
     * in the system.
     * 
     * @param lan  specify the which language
     * @return list of competences in specific language
     * 
     */
    public List<CompetenceLangInterface> getCompetences(String lan) {
        if (lan != null){
            return competenceDAO.getCompetences(lan);
        }
      return null;  
    }
    /**
     * Get the Registered Applicants 
     * in the system.
     * 
     * @return list of Applicants
     */
    public List<PersonInterface> getApplicants(){
        return personDAO.getAllPersons("APPLICANTS");
    }
    
    
    
    
    
}

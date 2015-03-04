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
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.utility.logger.Log;
import se.kth.model.CompetenceLangInterface;

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
     *
     * @param lan  specify the which language
     * @return competences in specific language
     * 
     */
    public List<CompetenceLangInterface> getComptences(String lan){
        if (lan != null){
            return competenceDAO.getCompetences(lan);
        }
      return null;  
    }

}

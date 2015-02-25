/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
 */
package se.kth.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.integration.CompetenceDAO;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore e bäst
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class RecruiterController {

    @EJB
    private CompetenceDAO competenceDAO;
 /**
     *  This Method is to create comptences in both 
     *  both languages (English & Swedish)
     * 
     * @param nameEn Competence Name in English
     * @param nameSV Competence name in Swedish
     * 
     */
    public void creatCompetence(String nameEN, String nameSV) {
        if (nameEN != null && nameSV != null) {
            competenceDAO.creatCompetenc(nameEN, nameSV);
        }
    }
    /**
     *  Returns a list of competences in the language (en, sv)
     *  stands for (English & Swedish)given in the parameter
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

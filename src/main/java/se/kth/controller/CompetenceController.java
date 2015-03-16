/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.integration.CompetenceDAO;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class CompetenceController {

    @EJB
    private CompetenceDAO competenceDAO;

    /**
     * Get competences in the specified language
     * (En or Sv)
     * 
     * @param lan specify which language
     * @return list of competences in the specified language
     *
     */
    public List<CompetenceLangInterface> getComptences(String lan) {
        if (lan != null) {
            return competenceDAO.getCompetences(lan);
        }
        return null;
    }
    
    
    

}

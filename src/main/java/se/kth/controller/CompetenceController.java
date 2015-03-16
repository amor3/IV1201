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
     *
     * @param lan specify the which language
     * @return competences in specific language
     *
     */
    public List<CompetenceLangInterface> getComptences(String lan) {
        if (lan != null) {
            return competenceDAO.getCompetences(lan);
        }
        return null;
    }
    
    
    

}

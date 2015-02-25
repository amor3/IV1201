/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.integration.CompetenceDAO;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore
 */
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

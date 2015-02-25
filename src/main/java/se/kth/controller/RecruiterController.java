/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.controller;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import se.kth.integration.CompetenceDAO;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore e bäst
 */

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
    public void creatCompetence(String nameEN, String nameSV) {
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

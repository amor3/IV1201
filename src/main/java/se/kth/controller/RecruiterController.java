/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.controller;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import se.kth.integration.CompetenceDAO;

/**
 *
 * @author AMore
 */
@Stateful
public class RecruiterController {

    @EJB
    private CompetenceDAO competenceDAO;

    public void creatCompetence(String nameEN, String nameSV) {
        if (nameEN != null && nameSV != null) {
            competenceDAO.creatCompetenc(nameEN, nameSV);
        }
    }

}

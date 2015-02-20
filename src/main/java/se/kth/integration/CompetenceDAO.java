/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import se.kth.model.Competence;
import se.kth.model.CompetenceEn;
import se.kth.model.CompetenceLangInterface;
import se.kth.model.CompetenceSv;

/**
 *
 * @author AMore
 */
@Stateless
public class CompetenceDAO {

    @PersistenceContext(unitName = "RDB_PU")
    private EntityManager em;

    /**
     * create competences in both languages
     * @param nameEn Competence Name in English
     * @param nameSV Competence name in Swedish
     * 
     */
    public void creatCompetenc(String nameEN, String nameSV) {
        if (nameEN != null && nameSV != null) {
            CompetenceSv sv= null;
            CompetenceEn en= null;
            try {
                sv = em.createNamedQuery("CompetenceSv.findByName", CompetenceSv.class).setParameter("name", nameSV).getSingleResult();
                en = em.createNamedQuery("CompetenceEn.findByName", CompetenceEn.class).setParameter("name", nameEN).getSingleResult();
            } catch (NoResultException e) {
               //e.printStackTrace();
            }
            if (sv == null && en == null) {
                Competence competence = new Competence();
                em.persist(competence);
                sv = new CompetenceSv(competence.getCompetenceId(), nameSV);
                en = new CompetenceEn(competence.getCompetenceId(), nameEN);
                em.persist(sv);
                em.persist(en);
            }

        }
    }
/**
     *
     * This Method for removing a competence
     * @param name name of the competence
     * 
     */
    public void removeCompetence(String name) {

    }

    public List<CompetenceLangInterface> getCompetences(String lang) {
        List<CompetenceLangInterface> lan = null;
        if( lang.equals("en")){
            lan =em.createNamedQuery("CompetenceEn.findAll", CompetenceLangInterface.class).getResultList();
            
        }
        else if( lang.equals("sv")){
            lan = em.createNamedQuery("CompetenceSv.findAll", CompetenceLangInterface.class).getResultList();
            
        }

        return lan;
    }
}

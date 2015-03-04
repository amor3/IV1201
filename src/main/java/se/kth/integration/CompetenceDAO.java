/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.integration;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import se.kth.utility.logger.Log;
import se.kth.model.Competence;
import se.kth.model.CompetenceEn;
import se.kth.model.CompetenceLangInterface;
import se.kth.model.CompetenceSv;

/**
 *
 * @author AMore
 */
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class CompetenceDAO {

    @PersistenceContext(unitName = "RDB_PU")
    private EntityManager em;

    /**
     * create competences in both languages
     * @param nameEn Competence Name in English
     * @param nameSV Competence name in Swedish
     * @throws DuplicateEntryException
     * @throws NullArgumentException 
     */
    public void creatCompetenc(String nameEN, String nameSV) throws DuplicateEntryException, NullArgumentException {
        if (nameEN != null && nameSV != null) {
            CompetenceSv sv = null;
            CompetenceEn en = null;
            try {
                sv = em.createNamedQuery("CompetenceSv.findByName", CompetenceSv.class).setParameter("name", nameSV).getSingleResult();
                en = em.createNamedQuery("CompetenceEn.findByName", CompetenceEn.class).setParameter("name", nameEN).getSingleResult();
            } catch (NoResultException e) {
                
            }
            if (sv == null && en == null) {
                Competence competence = new Competence();
                em.persist(competence);
                sv = new CompetenceSv(competence, nameSV);
                en = new CompetenceEn(competence, nameEN);
                em.persist(sv);
                em.persist(en);
            } else {
                throw new DuplicateEntryException("Competence alrady exist");
            }

        }else {
            throw new NullArgumentException("Null argument");
        }
    }


    /**
     * This Method for removing a competence
     * @param name of the competence
     * @throws DoesNotExistException
     * @throws NullArgumentException 
     */
    public void removeCompetence(String name) throws DoesNotExistException, NullArgumentException {
        CompetenceLangInterface comp = null;
        Competence competence;
        if (name != null) {
            try {
                comp = em.createNamedQuery("CompetenceEn.findByName", CompetenceLangInterface.class)
                        .setParameter("name", name)
                        .getSingleResult();
            } catch (NoResultException e) {
                
            }
            if (comp == null) {
                try {
                    comp = em.createNamedQuery("CompetenceSv.findByName", CompetenceLangInterface.class)
                            .setParameter("name", name)
                            .getSingleResult();
                } catch (NoResultException e) {

                }
                if (comp == null) {
                    throw new DoesNotExistException("Competence does not exist");
                } 
            }
            competence = comp.getCompetenceId();
            competence = em.find(Competence.class, competence.getCompetenceId());
            em.remove(competence);
        } else {
            throw new NullArgumentException("Null argument");
        }
    }

    /**
     * Gets all available competences for the specified language
     * @param lang language of the competence to fetched 
     * @return list of competenceLangInterface
     */
    public List<CompetenceLangInterface> getCompetences(String lang) {
        List<CompetenceLangInterface> lan = null;
        if (lang.equals("en")) {
            lan = em.createNamedQuery("CompetenceEn.findAll", CompetenceLangInterface.class).getResultList();

        } else if (lang.equals("sv")) {
            lan = em.createNamedQuery("CompetenceSv.findAll", CompetenceLangInterface.class).getResultList();

        }

        return lan;
    }
}

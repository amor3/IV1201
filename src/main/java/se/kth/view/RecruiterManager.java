/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
 */
package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import se.kth.controller.RecruiterController;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore
 */
@Named(value = "recruiterManager")
@ConversationScoped
public class RecruiterManager implements Serializable {

    @EJB
    private RecruiterController recruiter;
    
    @Inject
    private CompetenceManager competenceManager;

    @Inject
    private AvailabilityManager availabilityManager;
    
    @Inject
    private LanguageManager languageManager;
    
    
    private List<String> competence;

    public RecruiterManager() {
    }

    public RecruiterManager(CompetenceManager competenceManager) {
        this.competenceManager = competenceManager;
    }

    public RecruiterManager(AvailabilityManager availabilityManager) {
        this.availabilityManager = availabilityManager;
    }
    
    public CompetenceManager getCompetenceManager() {
        return competenceManager;
    }

    public AvailabilityManager getAvailabilityManager() {
        return availabilityManager;
    }

    public void setCompetenceManager(CompetenceManager competenceManager) {
        this.competenceManager = competenceManager;
    }

    public void setAvailabilityManager(AvailabilityManager availabilityManager) {
        this.availabilityManager = availabilityManager;
    }

    public String creatCompetence() {
        if (competenceManager.getCompetenceEN() != null && competenceManager.getCompetenceSV() != null) {
            recruiter.creatCompetence(competenceManager.getCompetenceEN(), competenceManager.getCompetenceSV());
        }
       

        return "";
    }
    
    public List<String> getCompetence(){
        competence = new ArrayList<>(); 
        for (CompetenceLangInterface c: recruiter.getComptences(languageManager.getLanguage())){
            competence.add(c.getName());
        }
       return competence;
    }

}

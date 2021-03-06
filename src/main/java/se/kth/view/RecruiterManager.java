/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */
package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import se.kth.controller.RecruiterController;
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.utility.logger.Log;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore
 */
@Log
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
    
    private String email;

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
    /**
     * Create a competence
     * 
     * @return true if creating competence succeeded, and false if not.
     */
    public String creatCompetence() {
        if (competenceManager.getCompetenceEN() != null && competenceManager.getCompetenceSV() != null) {
            try {
                recruiter.creatCompetence(competenceManager.getCompetenceEN(), competenceManager.getCompetenceSV());
                addMessage("Success", "Competence successfully added.");
            } catch (DuplicateEntryException ex) {
                addMessage("Error", "Competence already exists.");
            } catch (NullArgumentException ex) {
                Logger.getLogger(RecruiterManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return "";
    }

    public List<String> getCompetence() {
        competence = new ArrayList<>();
        for (CompetenceLangInterface c : recruiter.getCompetences(languageManager.getLanguage())) {
            competence.add(c.getName());
        }
        return competence;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
    
        public void addMessage(String subject, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(subject, message));
    }
    
}

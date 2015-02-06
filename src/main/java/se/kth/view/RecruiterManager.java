/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import javax.inject.Named;
import javax.enterprise.context.ConversationScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.ManagedProperty;
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

    @ManagedProperty(value = "#{competenceManager}")
    private CompetenceManager competenceManager;

    @ManagedProperty(value = "#{availabilityManager}")
    private AvailabilityManager availabilityManager;
    
    @ManagedProperty(value = "#{languageManager}")
    private LanguageManager languageManager;
    
    
    @NotNull(message="{se.kth.view.required}")
    private String competenceEN;
    @NotNull(message="{se.kth.view.required}")
    private String competenceSV;
    
    private List<String> competence;

    public RecruiterManager() {
    }

    public RecruiterManager(CompetenceManager competenceManager) {
        this.competenceManager = competenceManager;
    }

    public RecruiterManager(AvailabilityManager availabilityManager) {
        this.availabilityManager = availabilityManager;
    }

    public String getCompetenceEN() {
        return competenceEN;
    }

    public String getCompetenceSV() {
        return competenceSV;
    }

    public void setCompetenceEN(String competenceEN) {
        this.competenceEN = competenceEN;
    }

    public void setCompetenceSV(String competenceSV) {
        this.competenceSV = competenceSV;
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
        if (competenceEN != null && competenceSV != null) {
            recruiter.creatCompetence(competenceEN, competenceSV);
        }
       

        return "";
    }
    
    public List<String> getCompetence(){
        List <String> competence = new ArrayList<>(); 
        for (CompetenceLangInterface c: recruiter.getComptences("sv")){
            competence.add(c.getName());
        }
       return competence;
    }

}

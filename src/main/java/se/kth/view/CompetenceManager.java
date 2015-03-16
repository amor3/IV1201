/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import se.kth.controller.CompetenceController;
import se.kth.utility.logger.Log;
import se.kth.model.CompetenceLangInterface;


/**
 *
 * @author AMore
 */
@Log
@Named(value = "competenceManager")
@ConversationScoped
public class CompetenceManager implements Serializable{
    
    @NotNull(message="{se.kth.view.required}")
    private String competenceEN;
    @NotNull(message="{se.kth.view.required}")
    private String competenceSV;
    
    private List<String> competences;
    
    @EJB
    private CompetenceController competenceCtrl;
    
    @Inject
    private LanguageManager languageManager;
    
    
    public CompetenceManager() {
    }
    
    /**
     * constructor that take two parameters 
     * with competences in both English and 
     * Swedish language 
     * 
     * @param competenceSV
     * @param competenceEN 
     */
    public CompetenceManager(String competenceSV, String competenceEN) {
        this.competenceSV = competenceSV;
        this.competenceEN = competenceEN;
    }

    public String getCompetenceSV() {
        return competenceSV;
    }

    public String getCompetenceEN() {
        return competenceEN;
    }

    public void setCompetenceSV(String competenceSV) {
        this.competenceSV = competenceSV;
    }

    public void setCompetenceEN(String competenceEN) {
        this.competenceEN = competenceEN;
    }

    
    public List<String> getCompetences(){
        competences = new ArrayList<>(); 
        for (CompetenceLangInterface c: competenceCtrl.getComptences(languageManager.getLanguage())){
            competences.add(c.getName());
        }
       return competences;
    }
    
}

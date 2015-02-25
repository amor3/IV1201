/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
 */
package se.kth.view;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AMore
 */
@Named(value = "competenceManager")
@ConversationScoped
public class CompetenceManager implements Serializable{
    
    @NotNull(message="{se.kth.view.required}")
    private String competenceEN;
    @NotNull(message="{se.kth.view.required}")
    private String competenceSV;
    

    /**
     * Creates a new instance of CompetenceManager
     */
    public CompetenceManager() {
    }
      

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
    
    
}

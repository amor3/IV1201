/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import java.io.Serializable;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import se.kth.iv1201.utility.logger.Log;

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

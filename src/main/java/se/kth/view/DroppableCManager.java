/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.DragDropEvent;
import se.kth.integration.CompetenceProfileDTO;

/**
 *
 * @author AMore
 */
@Named(value = "droppableCompetences")
@ViewScoped
public class DroppableCManager implements Serializable {
  
    @Inject
    private CompetenceManager competenceManager;
 
    private List<CompetenceProfileDTO> droppedCompetences;     
    private String selectedCompetence;
     
    @PostConstruct
    public void init() {
        droppedCompetences = new ArrayList<>();
    }
     
    public void onCompetenceDrop(DragDropEvent ddEvent) {
        String competence = ((String) ddEvent.getData());
        droppedCompetences.add(new CompetenceProfileDTO(competence,BigDecimal.ONE));
        competenceManager.getCompetences().remove(competence);
    }
    
    public void removeDroppedCompetence(String comp){
        droppedCompetences.remove(new CompetenceProfileDTO(comp,BigDecimal.ONE));
    }
 
    public List<String> getCompetences() {
        return competenceManager.getCompetences();
    }
 
    public List<CompetenceProfileDTO> getDroppedCompetences() {
        return droppedCompetences;
    }   
 
    public String getSelectedCompetence() {
        return selectedCompetence;
    }
 
    public void setSelectedCompetence(String selIn) {
        this.selectedCompetence = selIn;
    }
}
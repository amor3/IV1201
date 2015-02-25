/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author AMore
 */
@Named(value = "droppableCompetences")
@ViewScoped
public class DroppableCManager implements Serializable {
  
    @Inject
    private CompetenceManager competenceManager;
 
    private List<String> droppedCompetences;     
    private String selectedCompetence;
     
    @PostConstruct
    public void init() {
        droppedCompetences = new ArrayList<>();
    }
     
    public void onCompetenceDrop(DragDropEvent ddEvent) {
        String competence = ((String) ddEvent.getData());
        droppedCompetences.add(competence);
        competenceManager.getCompetences().remove(competence);
    }
    
    public void removeDroppedCompetence(String comp){
        droppedCompetences.remove(comp);
    }
 
    public List<String> getCompetences() {
        return competenceManager.getCompetences();
    }
 
    public List<String> getDroppedCompetences() {
        return droppedCompetences;
    }   
 
    public String getSelectedCompetence() {
        return selectedCompetence;
    }
 
    public void setSelectedCompetence(String selIn) {
        this.selectedCompetence = selIn;
    }
}
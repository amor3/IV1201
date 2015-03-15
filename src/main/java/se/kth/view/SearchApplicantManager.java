package se.kth.view;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import se.kth.controller.RecruiterController;
import se.kth.model.PersonInterface;



/**
 *
 * @author AMore
 */
@Named(value = "searchApplicantManager")
@ViewScoped
public class SearchApplicantManager implements Serializable {
    @EJB
    private RecruiterController recruterCTRL;
    
    @Inject
    private CompetenceManager competenceMGR;
        
    private List<PersonInterface> applicants;
    private List<PersonInterface> filteredApplicants;
    private List<String> competences;
    

    @PostConstruct
    public void init() {
        applicants = recruterCTRL.getApplicants();
        competences = competenceMGR.getCompetences();
        filteredApplicants = applicants;
    }
    
    public SearchApplicantManager() {
    }
    
    
    public List<PersonInterface> getApplicants() {
        setApplicants(recruterCTRL.getApplicants());
        return applicants;
    }

    public void setApplicants(List<PersonInterface> applicants) {
        this.applicants = applicants;
    }

    public List<PersonInterface> getFilteredApplicants() {
        return filteredApplicants;
    }

    public void setFilteredApplicants(List<PersonInterface> filteredApplicants) {
        this.filteredApplicants = filteredApplicants;
    }

    public List<String> getCompetences() {
        setCompetences(competenceMGR.getCompetences());
        return competences;
    }

    public void setCompetences(List<String> competences) {
        this.competences = competences;
    }

    
    
    
    
    
    

    public void addMessage(String subject, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(subject, message));
    }
    
    
    
    
}

/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */
package se.kth.view;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import se.kth.controller.ApplicantController;
import se.kth.integration.DoesNotExistException;

/**
 *
 * @author AMore
 */
@ManagedBean(name = "applicantManager")
@ViewScoped
public class ApplicantManager implements Serializable {

    // Profile page
    private String firstname;
    private String lastname;
    private String ssn;

    // Profile page, change password
    private String email;
    private String oldPassword;
    private String newPassword;
    private String newPasswordAgain;

    // Passing messages to the view
    private String meddelande;

    @EJB
    private ApplicantController applicantCTRL;

    @PostConstruct
    public void init() {
    }
    
    /**
     * save new user profile 
     */
    public void saveUserProfile() {
        boolean status = false;
        try {
            status = applicantCTRL.saveUserProfile(email, firstname, lastname, ssn);
        } catch (DoesNotExistException ex) {
            addMessage("Error", "User does not exist");
        }
        if (status) {
            addMessage("Success", "Saved profile");
        } else {
            addMessage("Error", "Could not save profile");
        }
    }
   /**
    * Save user's credential 
    */
    public void saveUserCredentials() {
        boolean status = false;
        try {
            status = applicantCTRL.saveUserCredentials(email, oldPassword, newPassword, newPasswordAgain);
        } catch (DoesNotExistException ex) {
            addMessage("Error", "User does not exist");
        }

        if (status) {
            addMessage("Success", "Updated credentials");
        } else {
            addMessage("Error", "Could not update credentials, check your passwords.");
        }
    }
    /**
     * logout a user
     * 
     * @return true if logout is succeeded
     */
    public String loggout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            request.logout();
            // clear the session
            ((HttpSession) context.getExternalContext().getSession(false)).invalidate();

        } catch (ServletException ex) {
            ex.printStackTrace();
        } finally {
            return "/index?faces-redirect=true";
        }
    }
    /**
     * Add Messages 
     * 
     * @param subject
     * @param message 
     */
    public void addMessage(String subject, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(subject, message));
    }

    public String getMeddelande() {
        return meddelande;
    }

    public void setMeddelande(String meddelande) {
        this.meddelande = meddelande;
    }

    public String getFirstname() {
        this.firstname = applicantCTRL.getLoggedInPerson().getName();
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        this.lastname = applicantCTRL.getLoggedInPerson().getSurname();
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getSsn() {
        this.ssn = applicantCTRL.getLoggedInPerson().getSsn();
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        this.email = applicantCTRL.getLoggedInPerson().getEmail();
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordAgain() {
        return newPasswordAgain;
    }

    public void setNewPasswordAgain(String newPasswordAgain) {
        this.newPasswordAgain = newPasswordAgain;
    }

}

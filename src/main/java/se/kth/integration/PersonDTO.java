/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.integration;

import java.util.Date;
import java.util.List;

/**
 * contains several constructors of person DTOs
 * that can get several parameters, such as email, password
 * first and surname with social security number and availability dates
 * 
 */
public class PersonDTO {

    private String email;
    private String password;
    private String firstname;
    private String surname;
    private String ssn;
    private Date availableFrom;
    private Date availableTo;
    private List<CompetenceProfileDTO> competences;

    public PersonDTO() {
    }

    public PersonDTO(String email, String password, String firstname, String surname, String ssn, Date availableFrom, Date availableTo, List<CompetenceProfileDTO> competences) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.ssn = ssn;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.competences = competences;
    }
    
    public PersonDTO(String email){
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }

    public List<CompetenceProfileDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceProfileDTO> competences) {
        this.competences = competences;
    }

    @Override
    public String toString() {
        return "PersonDTO{" + "email=" + email + ", password=" + password
                + ", firstname=" + firstname + ", surname=" + surname
                + ", ssn=" + ssn + ", availableFrom=" + availableFrom
                + ", availableTo=" + availableTo
                + ", competences=" + competences + '}';
    }

}

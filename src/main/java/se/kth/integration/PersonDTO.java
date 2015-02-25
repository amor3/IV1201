package se.kth.integration;

import java.util.Date;
import java.util.List;

/**
 *
 * @author AMore
 */
public class PersonDTO {
    private String email;
    private String password;
    private String firstname;
    private String surname;
    private String ssn;
    private Date availableFrom;
    private Date availableTo;
    private List<String> competences;

    public PersonDTO(String email, String password, String firstname, String surname, String ssn, Date availableFrom, Date availableTo, List<String> competences) {
        this.email = email;
        this.password = password;
        this.firstname = firstname;
        this.surname = surname;
        this.ssn = ssn;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
        this.competences = competences;
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

    public List<String> getCompetences() {
        return competences;
    }

    public void setCompetences(List<String> competences) {
        this.competences = competences;
    }
    
    
    
}

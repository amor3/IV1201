package se.kth.view;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import org.primefaces.event.SelectEvent;
import se.kth.controller.OpenController;
import se.kth.utility.beanValidation.Email;
import se.kth.utility.logger.Log;


/**
 *
 * @author AMore
 */
@Log
@Named(value = "registrationManager")
@SessionScoped
public class RegistrationManager implements Serializable {

    @EJB
    private OpenController openController;
    
    @Inject
    private DroppableCManager droppableCManager;
    
    
    @NotNull(message="{se.kth.view.emailRequired}")
    @Email
    private String email;
    @NotNull(message="{se.kth.view.required}")
    private String password;
    @NotNull(message="{se.kth.view.required}")
    private String repeatPassword;
    
    
    @NotNull(message="{se.kth.view.required}")
    private String firstname;
    @NotNull(message="{se.kth.view.required}")
    private String surname;
    @NotNull(message="{se.kth.view.required}")
    private String ssn;
    
    
    @NotNull(message="{se.kth.view.required}")
    private Date availableFrom;
    @NotNull(message="{se.kth.view.required}")
    private Date availableTo;
    

    
    
    public RegistrationManager() {
    }

    public void onDateSelect(SelectEvent event) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
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

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
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

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        System.out.println("DateFrom: " + availableFrom);
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        System.out.println("DateTo: " + availableTo);
        this.availableTo = availableTo;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
    
    
    
    

    public String gotoPage1() {
        return "success";
    }

    public String gotoPage2() {
        return "success";
    }

    public String gotoPage3() {
        return "success";
    }
    
    public String gotoPageFinal(){
        System.out.println("email" + this.email);
        System.out.println("pass" + this.password);
        System.out.println("name" + this.firstname);
        System.out.println("name2" + this.surname);
        System.out.println("ssn" + this.ssn);
        System.out.println("from" + this.availableFrom);
        System.out.println("to" + this.availableTo);
        System.out.println(droppableCManager.getDroppedCompetences().get(0));
        openController.createApplicant(email, password, firstname, surname, ssn, availableFrom, availableTo, droppableCManager.getDroppedCompetences());   
        return "success";
    }
    
    

    
    
    
}

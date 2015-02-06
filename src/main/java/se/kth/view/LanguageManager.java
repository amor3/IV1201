/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import java.io.Serializable;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author work
 */
@Named(value = "languageManager")
@SessionScoped
public class LanguageManager implements Serializable {

    private Locale locale;

    public LanguageManager() {
        this.locale =new Locale("sv");
    }
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }


    public void changeLanguage() {
        this.locale = new Locale(getLanguageCode());
        FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
    }

    private String getLanguageCode() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("languageCode");
    }

}

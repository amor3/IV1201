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

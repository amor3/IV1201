/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.view;

import java.util.Locale;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author work
 */
@Named(value = "languageManager")
@ApplicationScoped
public class LanguageManager {

    public void changeLanguage() {
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(getLanguageCode()));
    }

    private String getLanguageCode() {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("languageCode");
    }
    
}

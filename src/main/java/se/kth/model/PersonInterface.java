/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.model;

import java.util.Collection;

/**
 *
 * @author AMore
 */
public interface PersonInterface {
    public Long getPersonId();
    public String getName();
    public String getSurname();
    public String getSsn();
    public String getEmail();
    public String getPassword();
    public Collection<Availability> getAvailabilityCollection();
    public Collection<CompetenceProfile> getCompetenceProfileCollection();

}

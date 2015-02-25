/*
 * Copyright 2015 The code Masters  <www.thecodemasters.se>.
 * All rights reserved.
 *
 * using this code is prohebitted and not allowed, only under some exceptions
 * by contacting the copyright holders ©The code Masters 2015
 * 
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

/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
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
    public String getCompetencesToString();

}

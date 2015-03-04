/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */

package se.kth.controller;

import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import se.kth.integration.CompetenceProfileDTO;
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;

/**
 *
 * @author AMore
 */
@Stateless
public class OpenController {

    @EJB
    private PersonDAO personDAO;

    public void createApplicant(
            String email,
            String password,
            String firstname,
            String surname,
            String ssn,
            Date availableFrom,
            Date availableTo,
            List<CompetenceProfileDTO> competences) throws DuplicateEntryException, NullArgumentException {

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);

        personDAO.createApplicant(personDTO);
    }

}

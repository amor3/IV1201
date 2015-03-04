/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */
package se.kth.integration;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import se.kth.model.Availability;
import se.kth.model.CompetenceProfile;
import se.kth.model.Person;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.model.AvailabilityInterface;
import se.kth.utility.logger.Log;
import se.kth.model.Competence;
import se.kth.model.CompetenceEn;
import se.kth.model.CompetenceInterface;
import se.kth.model.CompetenceSv;
import se.kth.model.PersonInterface;
import se.kth.model.Role;

/**
 *
 * @author AMore
 */
@Log
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class PersonDAO {

    @PersistenceContext(unitName = "RDB_PU")
    private EntityManager em;

    private Person createPerson(PersonDTO personDTO) {
        Person user = null;
        if (personDTO != null) {
            try {
                user = em.createNamedQuery("Person.findByEmail", Person.class)
                        .setParameter("email", personDTO.getEmail())
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("No result exception");
            }

            if (user == null) {
                Person person = new Person(personDTO.getEmail());
                person.setName(personDTO.getFirstname());
                person.setSurname(personDTO.getSurname());
                person.setSsn(personDTO.getSsn());
                //person.setPassword(getEncryptedPassword(personDTO.getPassword()));
                em.persist(person);
                return person;
            }
        }
        return user;
    }

    private void addPersonToRole(Person person, String roleName) {
        Role role = em.find(Role.class, roleName);
        role.addPerson(person);
        em.persist(role);
    }

    private void addAvailability(Person person, PersonDTO personDTO) {
        if (personDTO.getAvailableFrom().before(personDTO.getAvailableTo())) {
            Availability availability = new Availability(person);
            availability.setFromDate(personDTO.getAvailableFrom());
            availability.setToDate(personDTO.getAvailableTo());
            em.persist(availability);
        }
    }

    private void addCompetences(Person person, PersonDTO personDTO) {
        CompetenceEn compEn = null;
        CompetenceSv compSv = null;
        Competence competenceId;
        CompetenceProfile competenceProfile;

        for (CompetenceProfileDTO comp : personDTO.getCompetences()) {
            try {
                compEn = em.createNamedQuery("CompetenceEn.findByName", CompetenceEn.class)
                        .setParameter("name", comp.getName())
                        .getSingleResult();
            } catch (NoResultException e) {

            }
            if (compEn == null) {
                try {
                    compSv = em.createNamedQuery("CompetenceSv.findByName", CompetenceSv.class)
                            .setParameter("name", comp.getName())
                            .getSingleResult();
                } catch (NoResultException e) {

                }
                if (compSv == null) {
                    System.out.println("No such competence");
                    return;
                } else {
                    competenceId = compSv.getCompetenceId();
                }
            } else {
                competenceId = compEn.getCompetenceId();
            }

            competenceId = em.find(Competence.class, competenceId.getCompetenceId());
            em.refresh(person);
            competenceProfile = new CompetenceProfile(competenceId, person);
            if (!person.getCompetenceProfileCollection().contains(competenceProfile)) {

                competenceProfile.setYearsOfExperience(comp.getYearsOfExperience());
                em.persist(competenceProfile);
            }

        }
    }

    public void createApplicant(PersonDTO personDTO) {

        if (personDTO != null) {
            Person user = createPerson(personDTO);
            if (user != null) {
                addPersonToRole(user, "APPLICANTS");
                addAvailability(user, personDTO);
                addCompetences(user, personDTO);
            } else {
                System.out.println("Error in PersonDAO, user already exist");
            }
        }
    }

    public void createRecriuter(PersonDTO personDTO) {

        if (personDTO != null) {
            Person user = createPerson(personDTO);
            if (user != null) {
                addPersonToRole(user, "RECRUITERS");
            } else {
                System.out.println("Error in PersonDAO, user already exist");
            }
        }
    }

    public void removePerson(String email) {
        Person user = null;
        if (email != null) {
            try {
                user = em.createNamedQuery("Person.findByEmail", Person.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("User does not exist");
            }
            if (user != null) {
                em.remove(user);
            }
        }
    }

    public PersonInterface getPerson(String email) {
        PersonInterface person = null;
        if (email != null) {
            try {
                person = em.createNamedQuery("Person.findByEmail", PersonInterface.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("User does not exist");
            }
        }
        return person;
    }

    public PersonDTO getApplicant(String email) {
        PersonDTO personDTO = null;

        if (email != null) {
            Person user = null;

            try {
                user = em.createNamedQuery("Person.findByEmail", Person.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                System.out.println("No result exception");
            }

            if (user != null) {
                personDTO = new PersonDTO(email, "secret", user.getName(), user.getSurname(), user.getSsn(), null, null, null);
            } else {
                System.out.println("Error in PersonDAO, user already exist");
            }
            return personDTO;
        }
        return null;
    }

    public List<PersonInterface> getAllApplicantes() {
        List<PersonInterface> applicantes;
        applicantes
                = em.createNamedQuery("Person.findAll", PersonInterface.class
                ).getResultList();

        return applicantes;
    }

    private String getEncryptedPassword(String clearTextPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(clearTextPassword.getBytes());
            return bytesToHex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Failed to encrypt password.");
        }
        return "";
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes) {
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        }
        return result.toString();
    }

    private String newPassword(int len) {
        StringBuilder sb = new StringBuilder(len);
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random();

        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}

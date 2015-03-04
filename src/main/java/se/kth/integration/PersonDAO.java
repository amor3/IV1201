/*
 * Copyright 2015 The Code Masters <info@thecodemasters.se>.
 * All rights reserved.
 * 
 */
package se.kth.integration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import se.kth.model.Availability;
import se.kth.model.CompetenceProfile;
import se.kth.model.Person;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import se.kth.utility.logger.Log;
import se.kth.model.Competence;
import se.kth.model.CompetenceEn;
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

    private Person createPerson(PersonDTO personDTO) throws DuplicateEntryException, NullArgumentException {
        Person user = null;
        if (personDTO != null) {
            if (personDTO.getEmail() != null && personDTO.getPassword()!= null) {
                try {
                    user = em.createNamedQuery("Person.findByEmail", Person.class)
                            .setParameter("email", personDTO.getEmail())
                            .getSingleResult();
                } catch (NoResultException e) {

                }

                if (user == null) {
                    Person person = new Person(personDTO.getEmail());
                    person.setName(personDTO.getFirstname());
                    person.setSurname(personDTO.getSurname());
                    person.setSsn(personDTO.getSsn());
                    person.setPassword(getEncryptedPassword(personDTO.getPassword()));
                    em.persist(person);
                    return person;
                } else {
                    throw new DuplicateEntryException("Person alrady exist.");
                }
            } else {
                throw new NullArgumentException("Null argument personDTO.email/personDTO.password");
            }
        }
        return user;
    }

    private void addPersonToRole(Person person, String roleName) throws DoesNotExistException {
        Role role = em.find(Role.class, roleName);
        if (role != null) {
            role.addPerson(person);
            em.persist(role);
        } else {
            throw new DoesNotExistException("Role does not exist.");
        }
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

    /**
     * Creates a new applicant
     *
     * @param personDTO that contains all the needed information about the new
     * applicant
     * @throws DuplicateEntryException
     * @throws NullArgumentException
     */
    public void createApplicant(PersonDTO personDTO) throws DuplicateEntryException, NullArgumentException {

        if (personDTO != null) {
            Person user = createPerson(personDTO);
            if (user != null) {
                try {
                    addPersonToRole(user, "APPLICANTS");
                } catch (DoesNotExistException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
                addAvailability(user, personDTO);
                addCompetences(user, personDTO);
            }
        } else {
            throw new NullArgumentException("Null argument personDTO");
        }
    }

    /**
     * Creates a new recruiter
     *
     * @param personDTO that contains all the needed information about the new
     * recruiter
     * @throws DuplicateEntryException
     * @throws NullArgumentException
     */
    public void createRecriuter(PersonDTO personDTO) throws DuplicateEntryException, NullArgumentException {

        if (personDTO != null) {
            personDTO.setPassword(newPassword(6));
            //System.out.println(personDTO.getPassword());
            //TODO: send password via mail to the new recruiter. 
            Person user = createPerson(personDTO);
            if (user != null) {
                try {
                    addPersonToRole(user, "RECRUITERS");
                } catch (DoesNotExistException ex) {
                    Logger.getLogger(PersonDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            throw new NullArgumentException("Null argument personDTO");
        }
    }

    /**
     * Removes the person with the given e-mail
     *
     * @param email of the person to be removed
     * @throws DoesNotExistException
     * @throws NullArgumentException
     */
    public void removePerson(String email) throws DoesNotExistException, NullArgumentException {
        Person user = null;
        if (email != null) {
            try {
                user = em.createNamedQuery("Person.findByEmail", Person.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new DoesNotExistException("User does not exist.");
            }
            if (user != null) {
                em.remove(user);
            }
        } else {
            throw new NullArgumentException("Null argument email");
        }
    }

    /**
     * Gets the person with the given e-mail
     *
     * @param email the email of the person to be returned
     * @return a person interface
     * @throws DoesNotExistException
     * @throws NullArgumentException
     */
    public PersonInterface getPerson(String email) throws DoesNotExistException, NullArgumentException {
        PersonInterface person = null;
        if (email != null) {
            try {
                person = em.createNamedQuery("Person.findByEmail", PersonInterface.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new DoesNotExistException("User does not exist.");
            }
            return person;
        } else {
            throw new NullArgumentException("Null argument email");
        }

    }

    /**
     * Gets all applicants in the system.
     *
     * @return list of applicants or null if there are no applicants in the
     * system.
     */
    public List<PersonInterface> getAllApplicants() {
        List<PersonInterface> applicantes;
        applicantes /*= em.createNamedQuery("Person.findAll", PersonInterface.class
                ).getResultList();*/
                = em.createNamedQuery("Person.findAllByRole", PersonInterface.class
                ).setParameter("roleName", "APPLICANTS").getResultList();

        return applicantes;
    }
    
    /**
     * Gets all recruiters in the system.
     *
     * @return list of recruiters or null if there are no recruiters in the
     * system.
     */
    public List<PersonInterface> getAllRecruiters() {
        List<PersonInterface> recruiters;
        recruiters /*= em.createNamedQuery("Person.findAllByRole", PersonInterface.class
                ).setParameter("roleName", "RECRUITERS").getResultList();*/
                =em.createNamedQuery("Person.findAll", PersonInterface.class
                ).getResultList();
                

        return recruiters;
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

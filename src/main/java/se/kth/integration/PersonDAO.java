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
            if (personDTO.getEmail() != null && personDTO.getPassword() != null) {
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

    /**
     * This method updates an existing user
     *
     * @param personDTO containing the new information to be merged
     * @throws NullArgumentException
     * @throws DoesNotExistException
     */
    public void updatePerson(PersonDTO personDTO) throws NullArgumentException, DoesNotExistException {
        Person user = null;
        if (personDTO != null) {
            if (personDTO.getEmail() != null) {
                try {
                    user = em.createNamedQuery("Person.findByEmail", Person.class)
                            .setParameter("email", personDTO.getEmail())
                            .getSingleResult();
                } catch (NoResultException e) {
                    throw new DoesNotExistException("user does not exist.");
                }

                if (user != null && personDTO.getFirstname() != null && personDTO.getSurname() != null && personDTO.getSsn() != null) {
                    user.setName(personDTO.getFirstname());
                    user.setSurname(personDTO.getSurname());
                    user.setSsn(personDTO.getSsn());

                    em.merge(user);

                } else {
                    throw new NullArgumentException("Null argument Firstname/Surname/Ssn");
                }
            }
        } else {
            throw new NullArgumentException("Null argument personDTO");
        }
    }

    /**
     * updates users credentials
     *
     * @param email of the user whose credentials are to be updated
     * @param oldPassword
     * @param newPassword
     * @param newPasswordAgain
     * @return true if the operation is successful false otherwise
     * @throws NullArgumentException
     */
    public boolean updatePersonCredentials(String email, String oldPassword, String newPassword, String newPasswordAgain) throws NullArgumentException, DoesNotExistException {
        Person user = null;
        if (email != null && oldPassword != null && newPassword != null && newPasswordAgain != null) {
            try {
                user = em.createNamedQuery("Person.findByEmail", Person.class)
                        .setParameter("email", email)
                        .getSingleResult();
            } catch (NoResultException e) {
                throw new DoesNotExistException("user does not exist.");
            }

            // Test if old password in DB matches the input from user
            boolean oldpasswordCheck = getEncryptedPassword(oldPassword).equals(user.getPassword());

            // Test if the new passwords are the same
            boolean newPasswordMatches = newPassword.equals(newPasswordAgain);

            if (oldpasswordCheck && newPasswordMatches) {
                user.setPassword(getEncryptedPassword(newPassword));

                em.merge(user);

                return true;
            } else {
                return false;
            }
        } else {
            throw new NullArgumentException("Null argument email/oldPassword/newPassword/newPasswordAgain");
        }
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

    private void addAvailability(Person person, PersonDTO personDTO) throws NullArgumentException {
        if (personDTO.getAvailableFrom() != null && personDTO.getAvailableTo() != null) {
            if (personDTO.getAvailableFrom().before(personDTO.getAvailableTo())) {
                Availability availability = new Availability(person);
                availability.setFromDate(personDTO.getAvailableFrom());
                availability.setToDate(personDTO.getAvailableTo());
                em.persist(availability);
            } else {
                throw new IllegalArgumentException("From date not before to date");
            }
        } else {
            throw new NullArgumentException("Null argument AvailableFrom/AvailableTo");
        }
    }

    private void addCompetences(Person person, PersonDTO personDTO) {
        CompetenceEn compEn = null;
        CompetenceSv compSv = null;
        Competence competenceId;
        CompetenceProfile competenceProfile;

        if (personDTO.getCompetences() != null) {

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
    public List<PersonInterface> getAllPersons(String role) {
        List<PersonInterface> applicantes;
        applicantes = em.createNamedQuery("Person.findAllByRole", PersonInterface.class)
                .setParameter("roleName", role).getResultList();

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

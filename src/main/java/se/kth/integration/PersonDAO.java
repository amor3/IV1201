package se.kth.integration;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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

/**
 *
 * @author AMore
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class PersonDAO {

    @PersistenceContext(unitName = "RDB_PU")
    private EntityManager em;

    public void createApplicant(PersonDTO personDTO) {

        if (personDTO != null) {
            Person user = null;

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
                person.setPassword(getEncryptedPassword(personDTO.getPassword()));

                Availability availability = new Availability(personDTO.getAvailableFrom(), personDTO.getAvailableTo());
                ArrayList<Availability> avArr = new ArrayList<>();
                avArr.add(availability);
                person.setAvailabilityCollection(avArr);

                ArrayList<CompetenceProfile> compArray = new ArrayList<>();

                for (String comp : personDTO.getCompetences()) {
                    try {
                        //em.createNamedQuery("Competence.find)
                    } catch (Exception e) {
                        System.out.println("Couldnt find competence");
                    }
                    
                    
                    compArray.add(new CompetenceProfile(null, BigDecimal.ONE));
                }

                //person.setCompetenceProfileCollection(null);
                //em.persist(newProfile);

            } else {
                System.out.println("Error in PersonDAO, user not null");
            }
        }
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

/**
 *
 */
package se.kth.integration;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.ejb.EJBTransactionRolledbackException;
import javax.inject.Inject;
import javax.transaction.RollbackException;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import org.junit.runner.RunWith;
import se.kth.model.Availability;
import se.kth.model.AvailabilityInterface;
import se.kth.model.CompetenceLangInterface;
import se.kth.model.PersonInterface;

/**
 *
 * @author work
 */
@RunWith(Arquillian.class)
public class PersonDAOTest {

    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(PersonDAO.class)
                .addClass(CompetenceDAO.class)
                //.addClass(PersonDTO.class)
                .addPackage("se.kth.model")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                //.addAsManifestResource("META-INF/orm.xml", "orm.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    PersonDAO personInstance;
    @Inject
    CompetenceDAO competenceInstance;

    /**
     * Test of createApplicant method, of class PersonDAO.
     */
    @Test
    public void testCreateApplicant() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname1";
        String surname = "surname1";
        String ssn = "191900001111";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date availableFrom =  ft.parse("2015-02-02");
        Date availableTo = new Date();
        List<CompetenceProfileDTO> competences = new ArrayList<>();
        competences.add(new CompetenceProfileDTO("competence1", BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2", BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2", BigDecimal.ONE));

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        utx.begin();
        try {
            competenceInstance.creatCompetenc("competence1", "competence1");
            competenceInstance.creatCompetenc("competence2", "competence2");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Competence alrady exist"));
        }

        // Get the old size
        int oldSize = personInstance.getAllPersons("APPLICANTS").size();

        personInstance.createApplicant(personDTO);

        // Check if we get the right size
        Assert.assertEquals(oldSize + 1, personInstance.getAllPersons("APPLICANTS").size());
        utx.commit();
        utx.begin();
        try {
            personInstance.createApplicant(personDTO);
            Assert.fail("Should not add duplicate values ");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }

        try {
            personInstance.createApplicant(null);
            Assert.fail("Should not add null values ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO"));
        }

        try {
            personInstance.createApplicant(new PersonDTO());
            Assert.fail("Should not add a person with no email");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }

        personInstance.removePerson(email);
        utx.commit();
        
        utx.begin();
        personDTO.setEmail("e@kth.se");
        personDTO.setAvailableFrom(new Date());
        personDTO.setAvailableTo(new Date());
        try {
            personInstance.createApplicant(personDTO);
            Assert.fail("Should not add a person with wrong availability");
        } catch (IllegalArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("From date not before to date"));
        } catch (EJBTransactionRolledbackException e) {
            
        }
        
        personDTO.setAvailableFrom(null);
        personDTO.setAvailableTo(null);
        try {
            personInstance.createApplicant(personDTO);
            Assert.fail("Should not add a person with wrong availability");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument AvailableFrom/AvailableTo"));
        } catch (EJBTransactionRolledbackException e) {
            
        }
        utx.rollback();
    }

    @Test
    public void testCreateRecriuter() throws Exception {
        String email = "email@kth.se";

        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        utx.begin();

        // Get the old size
        int oldSize = personInstance.getAllPersons("RECRUITERS").size();

        personInstance.createRecriuter(personDTO);

        // Check if we get the right size
        Assert.assertEquals(oldSize + 1, personInstance.getAllPersons("RECRUITERS").size());
        utx.commit();
        utx.begin();
        try {
            personInstance.createRecriuter(personDTO);
            Assert.fail("Should not add duplicate values ");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }

        try {
            personInstance.createRecriuter(null);
            Assert.fail("Should not add null values ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO"));
        }

        try {
            personInstance.createRecriuter(new PersonDTO());
            Assert.fail("Should not add a person with no email");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }

        personInstance.removePerson(email);
        utx.commit();
    }

    @Test
    public void testRemovePerson() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date availableFrom =  ft.parse("2015-02-02");
        Date availableTo = new Date();
        List<CompetenceProfileDTO> competences = new ArrayList<>();
        competences.add(new CompetenceProfileDTO("competence1", BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2", BigDecimal.ONE));

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        utx.begin();

        try {
            competenceInstance.creatCompetenc("competence1", "competence1");
            competenceInstance.creatCompetenc("competence2", "competence2");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Competence alrady exist"));
        }
        personInstance.createApplicant(personDTO);
        // Get the old size
        int oldSize = personInstance.getAllPersons("APPLICANTS").size();

        personInstance.removePerson(email);

        // Check if we get the right size
        assertEquals(oldSize - 1, personInstance.getAllPersons("APPLICANTS").size());
        utx.commit();
        utx.begin();
        try {
            personInstance.removePerson("email@kth.se");
            Assert.fail("Should not remove non existing values ");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("User does not exist."));
        }

        try {
            personInstance.removePerson(null);
            Assert.fail("Should not remove null values ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email"));
        }

        utx.commit();
    }

    @Test
    public void testUpdatePerson() throws Exception {
        String email = "email@kth.se";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";

        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        utx.begin();

        personInstance.createRecriuter(personDTO);
        PersonInterface p = personInstance.getPerson(email);

        Assert.assertEquals(p.getName(), null);
        Assert.assertEquals(p.getSurname(), null);
        Assert.assertEquals(p.getSsn(), null);
        utx.commit();

        utx.begin();
        personDTO.setFirstname(firstname);
        personDTO.setSurname(surname);
        personDTO.setSsn(ssn);

        personInstance.updatePerson(personDTO);
        p = personInstance.getPerson(email);
        Assert.assertEquals(p.getName(), firstname);
        Assert.assertEquals(p.getSurname(), surname);
        Assert.assertEquals(p.getSsn(), ssn);

        personDTO.setFirstname(null);
        personDTO.setSurname(surname);
        personDTO.setSsn(ssn);
        try {
            personInstance.updatePerson(personDTO);
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument Firstname/Surname/Ssn"));
        }

        personDTO.setFirstname(firstname);
        personDTO.setSurname(null);
        personDTO.setSsn(ssn);
        try {
            personInstance.updatePerson(personDTO);
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument Firstname/Surname/Ssn"));
        }

        personDTO.setFirstname(firstname);
        personDTO.setSurname(surname);
        personDTO.setSsn(null);
        try {
            personInstance.updatePerson(personDTO);
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument Firstname/Surname/Ssn"));
        }

        try {
            personInstance.updatePerson(null);
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO"));
        }

        personInstance.removePerson(email);
        utx.commit();
    }

    @Test
    public void testUpdatePersonCredentials() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date availableFrom =  ft.parse("2015-02-02");
        Date availableTo = new Date();
        List<CompetenceProfileDTO> competences = new ArrayList<>();

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        utx.begin();

        personInstance.createApplicant(personDTO);
        boolean test = personInstance.updatePersonCredentials(email, password, "newPassword", "newPassword");
        
        Assert.assertTrue(test);
        
        //password not correct
        test = personInstance.updatePersonCredentials(email, password, "newPassword", "newPassword");
        Assert.assertFalse(test);
        
        //new password does not match
        test = personInstance.updatePersonCredentials(email, "newPassword", "Password", "newPassword");
        Assert.assertFalse(test);
        
        try {
            personInstance.updatePersonCredentials("ooo", password, "newPassword", "newPassword");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("user does not exist."));
        }
        
        try {
            personInstance.updatePersonCredentials(null, password, "newPassword", "newPassword");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email/oldPassword/newPassword/newPasswordAgain"));
        }
        
        try {
            personInstance.updatePersonCredentials(email, null, "newPassword", "newPassword");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email/oldPassword/newPassword/newPasswordAgain"));
        }
        
        try {
            personInstance.updatePersonCredentials(email, password, null, "newPassword");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email/oldPassword/newPassword/newPasswordAgain"));
        }
        
        try {
            personInstance.updatePersonCredentials(email, password, "newPassword", null);
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email/oldPassword/newPassword/newPasswordAgain"));
        }
        
        personInstance.removePerson(email);
        utx.commit();
    }

    @Test
    public void testGetAllPersons() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date availableFrom =  ft.parse("2015-02-02");
        Date availableTo = new Date();
        List<CompetenceProfileDTO> competences = new ArrayList<>();

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        PersonDTO recruiter = new PersonDTO("recruiters@kth.se");
        PersonDTO recruiter1 = new PersonDTO("recruiters1@kth.se");
        utx.begin();
        int oldSizeA = personInstance.getAllPersons("APPLICANTS").size();
        int oldSizeR = personInstance.getAllPersons("RECRUITERS").size();
        int oldSizeAdmin = personInstance.getAllPersons("ADMIN").size();
        personInstance.createApplicant(personDTO);

        personInstance.createRecriuter(recruiter);
        personInstance.createRecriuter(recruiter1);

        assertEquals(oldSizeA + 1, personInstance.getAllPersons("APPLICANTS").size());
        assertEquals(oldSizeR + 2, personInstance.getAllPersons("RECRUITERS").size());
        assertEquals(oldSizeAdmin, personInstance.getAllPersons("ADMIN").size());

        personInstance.removePerson(email);
        personInstance.removePerson("recruiters@kth.se");
        personInstance.removePerson("recruiters1@kth.se");
        utx.commit();
    }

    @Test
    public void testGetPersons() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "181900001111";
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date availableFrom =  ft.parse("2015-02-02");
        Date availableTo = new Date();

        PersonDTO applicant = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, null);
        PersonDTO recruiter = new PersonDTO("recruiters@kth.se");
        utx.begin();

        personInstance.createApplicant(applicant);
        personInstance.createRecriuter(recruiter);

        PersonInterface a = personInstance.getPerson(email);
        PersonInterface r = personInstance.getPerson("recruiters@kth.se");

        Assert.assertEquals(a.getName(), firstname);
        Assert.assertEquals(a.getSurname(), surname);
        Assert.assertEquals(a.getSsn(), ssn);
        for(AvailabilityInterface availabilityInterface : a.getAvailabilityCollection()){
            Assert.assertEquals(availabilityInterface.getFromDate(), availableFrom);
            Assert.assertEquals(availabilityInterface.getToDate(), availableTo);
        }

        Assert.assertTrue(a.getCompetenceProfileCollection().isEmpty());
        
        Assert.assertNull(r.getName());
        Assert.assertNull(r.getSurname());
        Assert.assertNull(r.getSsn());
        Assert.assertTrue(r.getAvailabilityCollection().isEmpty());
        Assert.assertTrue(r.getCompetenceProfileCollection().isEmpty());
        
        personInstance.removePerson(email);
        personInstance.removePerson("recruiters@kth.se");

        utx.commit();
    }
}

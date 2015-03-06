/*
 */
package se.kth.controller;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.containsString;
import org.junit.runner.RunWith;
import se.kth.integration.CompetenceProfileDTO;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;
import se.kth.model.PersonInterface;

/**
 *
 * @author work
 */
@RunWith(Arquillian.class)
public class ApplicantControllerTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ApplicantController.class)
                .addClass(PersonDAO.class)
                .addPackage("se.kth.integration")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }
    @Inject
    UserTransaction utx;
    
    @Inject
    ApplicantController instance;
    @Inject
    PersonDAO personDAOinstance;
    
    @Test
    public void testSaveUserProfile() throws Exception {
        String email = "email@kth.se";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111"; 
        PersonDTO personDTO = new PersonDTO(email);
        utx.begin();
        personDAOinstance.createRecriuter(personDTO);
        utx.commit();
        
        
        boolean test = instance.saveUserProfile(email, firstname, surname, ssn);
        Assert.assertTrue(test);
        
        utx.begin();
        PersonInterface p = personDAOinstance.getPerson(email);
        Assert.assertEquals(p.getName(), firstname);
        Assert.assertEquals(p.getSurname(), surname);
        Assert.assertEquals(p.getSsn(), ssn);

        
        test = instance.saveUserProfile(email, null, surname, ssn);
        Assert.assertFalse(test);
        test = instance.saveUserProfile(email, firstname, null, ssn);
        Assert.assertFalse(test);
        test = instance.saveUserProfile(email, firstname, surname, null);
        Assert.assertFalse(test);
        personDAOinstance.removePerson(email);
        utx.commit();
        
        try {
            test = instance.saveUserProfile(email, firstname, surname, ssn);
            Assert.assertFalse(test);
            Assert.fail("Should not update non existing user");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("user does not exist."));
        }


    }

    
    @Test
    public void testSaveUserCredentials() throws Exception {
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

        personDAOinstance.createApplicant(personDTO);
        utx.commit();
        
        utx.begin();
        boolean test = instance.saveUserCredentials(email, password, "newPassword", "newPassword");
        Assert.assertTrue(test);
        
        test = instance.saveUserCredentials(email, "newPassword", "newPassword", "newPassword");        
        Assert.assertTrue(test);
        
        //password not correct
        test = instance.saveUserCredentials(email, password, "newPassword", "newPassword");
        Assert.assertFalse(test);
        
        //new password does not match
        test = instance.saveUserCredentials(email, "newPassword", "Password", "newPassword");
        Assert.assertFalse(test);
        
        personDAOinstance.removePerson(email);
        utx.commit();
        
        try {
            instance.saveUserCredentials(email, password, "newPassword", "newPassword");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("user does not exist."));
        }
        

    }
    
}

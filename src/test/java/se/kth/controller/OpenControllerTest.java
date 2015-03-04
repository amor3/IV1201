/*
 */
package se.kth.controller;


import java.math.BigDecimal;
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
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;


/**
 *
 * @author work
 */
@RunWith(Arquillian.class)
public class OpenControllerTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(OpenController.class)
                .addClass(PersonDAO.class)
                .addPackage("se.kth.integration")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }
    @Inject
    UserTransaction utx;
    
    @Inject
    OpenController instance;
    @Inject
    PersonDAO personDAOinstance;
    
    @Test
    public void testCreateApplicant() throws Exception {
        String email = "email@kth.se";
        String password = "password";
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";
        Date availableFrom = new Date();
        Date availableTo = new Date();
        List<CompetenceProfileDTO> competences = new ArrayList<>();
        competences.add(new CompetenceProfileDTO("competence1", BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2", BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2", BigDecimal.ONE));
         utx.begin();
        int oldSize = personDAOinstance.getAllApplicants().size();

        instance.createApplicant(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        
        Assert.assertEquals(oldSize + 1, personDAOinstance.getAllApplicants().size());
        
        try{
            instance.createApplicant(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
            Assert.fail("Should not add duplicate values");
        }catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }
        
        try{
            instance.createApplicant(null, password, firstname, surname, ssn, availableFrom, availableTo, competences);
            Assert.fail("Should not add a person with no email");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }
        
        try{
            instance.createApplicant(email, null, firstname, surname, ssn, availableFrom, availableTo, competences);
            Assert.fail("Should not add a person with no password");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }
        
        personDAOinstance.removePerson(email);
         utx.commit();
    }
    
}

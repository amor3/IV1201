/**
 *
 */
package se.kth.integration;

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
import static org.junit.Assert.*;
import static org.junit.matchers.JUnitMatchers.containsString;
import org.junit.runner.RunWith;

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
        String firstname = "firstname";
        String surname = "surname";
        String ssn = "191900001111";
        Date availableFrom = new Date();
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
        int oldSize = personInstance.getAllApplicants().size();

        personInstance.createApplicant(personDTO);

        // Check if we get the right size
        Assert.assertEquals(oldSize + 1, personInstance.getAllApplicants().size());
        utx.commit();
        utx.begin();
        try{
           personInstance.createApplicant(personDTO);
           Assert.fail("Should not add duplicate values ");
        } catch(DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }
        
        try{
           personInstance.createApplicant(null);
           Assert.fail("Should not add null values ");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO"));
        }
        
        try{
           personInstance.createApplicant(new PersonDTO());
           Assert.fail("Should not add a person with no email");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }
        
        personInstance.removePerson("email@kth.se");
        utx.commit();
    }
    
    @Test
    public void testCreateRecriuter() throws Exception {
        String email = "email@kth.se";

        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        utx.begin();

        // Get the old size
        int oldSize = personInstance.getAllRecruiters().size();

        personInstance.createRecriuter(personDTO);

        // Check if we get the right size
        Assert.assertEquals(oldSize + 1, personInstance.getAllRecruiters().size());
        utx.commit();
        utx.begin();
        try{
           personInstance.createRecriuter(personDTO);
           Assert.fail("Should not add duplicate values ");
        } catch(DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }
        
        try{
           personInstance.createRecriuter(null);
           Assert.fail("Should not add null values ");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO"));
        }
        
        try{
           personInstance.createRecriuter(new PersonDTO());
           Assert.fail("Should not add a person with no email");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument personDTO.email/personDTO.password"));
        }
        
        personInstance.removePerson("email@kth.se");
        utx.commit();
    }


    @Test
    public void testRemoveApplicant() throws Exception {
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
        int oldSize = personInstance.getAllApplicants().size();

        personInstance.removePerson("email@kth.se");

        // Check if we get the right size
        assertEquals(oldSize - 1, personInstance.getAllApplicants().size());
        utx.commit();
        utx.begin();
        try{
           personInstance.removePerson("email@kth.se");
           Assert.fail("Should not remove non existing values ");
        } catch(DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("User does not exist."));
        }
        
        try{
           personInstance.removePerson(null);
           Assert.fail("Should not remove null values ");
        } catch(NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument email"));
        }
        
        utx.commit();
    }

}

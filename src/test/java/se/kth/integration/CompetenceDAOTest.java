package se.kth.integration;

import java.util.List;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import static org.junit.matchers.JUnitMatchers.containsString;
import org.junit.runner.RunWith;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author AMore
 */
@RunWith(Arquillian.class)
public class CompetenceDAOTest {

    //@Resource
    @Inject
    UserTransaction utx;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CompetenceDAO.class)
                .addPackage("se.kth.model")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                //.addAsManifestResource("META-INF/orm.xml", "orm.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    CompetenceDAO instance;

    /**
     * Test of getCompetences, check if the instance is working as it should
     */
    @org.junit.Test
    public void testCreateCompetence() throws Exception {
        String language = "sv";
        utx.begin();
        // Get the old size
        int oldSize = instance.getCompetences(language).size();

        // Add a new competence
        instance.creatCompetenc("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
        instance.creatCompetenc("ENGLISH", "SVENSK");
        
        // Check if we get the right size
        Assert.assertEquals(oldSize + 2, instance.getCompetences(language).size());
        
        //Check if both have the same competenceId 
        Assert.assertEquals(instance.getCompetences("sv").get(0).getCompetenceId(),
                            instance.getCompetences("en").get(0).getCompetenceId());
        
        //Check if the right exception is throwen 
        try{
           instance.creatCompetenc("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
            Assert.fail("Should not add duplicate values ");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Competence alrady exist"));
        }
        
        try{
           instance.creatCompetenc(null, null);
            Assert.fail("Should not add null values ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument"));
        }
        
        
        instance.removeCompetence("ENGLISHCOMPETENCE");
        instance.removeCompetence("ENGLISH");
        utx.commit();
    }

    @org.junit.Test
    public void testRemoveCompetence() throws Exception {
        String language = "sv";
        String nameEn = "ENGLISHCOMPETENCE";
        String nameSv = "SVENSK";
        utx.begin();
        instance.creatCompetenc("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
        instance.creatCompetenc("ENGLISH", "SVENSK");
        int oldSize = instance.getCompetences(language).size();
        instance.removeCompetence(nameEn);
        instance.removeCompetence(nameSv);
        Assert.assertEquals(oldSize - 2, instance.getCompetences(language).size());
        
        try{
           instance.removeCompetence(nameEn);
            Assert.fail("Should not remove a non existing value ");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Competence does not exist"));
        }
        
        try{
           instance.removeCompetence(null);
            Assert.fail("Should not remove a null value ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument"));
        }
        
        
        utx.commit();
    }

    @org.junit.Test
    public void testGetCompetences() throws Exception {
        utx.begin();

        int sizeSv = instance.getCompetences("sv").size();
        int sizeEn = instance.getCompetences("en").size();
        
        Assert.assertEquals(sizeSv, sizeEn);
        
        List<CompetenceLangInterface> list = instance.getCompetences(" ");
        Assert.assertNull(list);
        
        utx.commit();
    }

}

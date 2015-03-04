/*
 */
package se.kth.controller;

import java.util.List;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.matchers.JUnitMatchers.containsString;
import org.junit.runner.RunWith;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.NullArgumentException;
import se.kth.model.CompetenceLangInterface;

/**
 *
 * @author work
 */
@RunWith(Arquillian.class)
public class RecruiterControllerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(RecruiterController.class)
                .addPackage("se.kth.integration")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    RecruiterController instance;

    @Test
    public void testCreatCompetence() throws Exception {
        String language = "sv";
        String nameEn = "ENGLISHCOMPETENCE";
        String nameSv = "SVENSK";

        instance.creatCompetence("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
        instance.creatCompetence("ENGLISH", "SVENSK");
        int oldSize = instance.getCompetences(language).size();
        instance.removeCompetence(nameEn);
        instance.removeCompetence(nameSv);
        Assert.assertEquals(oldSize - 2, instance.getCompetences(language).size());

        try {
            instance.removeCompetence(nameEn);
            Assert.fail("Should not remove a non existing value ");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Competence does not exist"));
        }

        try {
            instance.removeCompetence(null);
            Assert.fail("Should not remove a null value ");
        } catch (NullArgumentException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Null argument"));
        }
    }
    
    @Test
    public void testRemoveCompetence() throws Exception {
        String language = "sv";
        String nameEn = "ENGLISHCOMPETENCE";
        String nameSv = "SVENSK";
        instance.creatCompetence("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
        instance.creatCompetence("ENGLISH", "SVENSK");
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
        
    }

    @Test
    public void testGetComptences() throws Exception {
        int sizeSV = instance.getCompetences("sv").size();
        int sizeEN = instance.getCompetences("en").size();

        Assert.assertEquals(sizeSV, sizeEN);

        List<CompetenceLangInterface> list = instance.getCompetences(" ");
        Assert.assertNull(list);
    }

}

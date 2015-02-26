package se.kth.integration;

import java.util.Random;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;

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

        //Random rand = new Random();
        //int randomNum = rand.nextInt(9999);

        // Add a new competence
        instance.creatCompetenc("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");
        instance.creatCompetenc("ENGLISH", "SVENSK");
        

        // Check if we get the right size
        assertEquals(oldSize + 2, instance.getCompetences(language).size());
        
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
        assertEquals(oldSize - 2, instance.getCompetences(language).size());
        utx.commit();
    }

    @org.junit.Test
    public void testGetCompetences() throws Exception {
        utx.begin();

        int sizeSv = instance.getCompetences("sv").size();
        int sizeEn = instance.getCompetences("en").size();
        
        assertEquals(sizeSv, sizeEn);
        utx.commit();
    }

}

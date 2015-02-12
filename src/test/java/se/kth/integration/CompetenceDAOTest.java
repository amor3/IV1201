package se.kth.integration;

import javax.inject.Inject;
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

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(CompetenceDAO.class)
                .addPackage("se.kth.model")
                .addAsManifestResource("META-INF/test-persistence.xml", "test-persistence.xml")
                //.addAsManifestResource("META-INF/orm.xml", "orm.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    CompetenceDAO instance;

    /**
     * Test of getCompetences, check if the instance is working as it should
     */
    @org.junit.Test
    public void testCreateCompetence() {
        String language = "sv";

        // Get the old size
        int oldSize = instance.getCompetences(language).size();

        // Add a new competence
        instance.creatCompetenc("ENGLISHCOMPETENCE", "SVENSKKOMPETENS");

        // Check if we get the right size
        assertEquals(oldSize + 1, instance.getCompetences(language).size());
    }

    /*
     @Test
     public void testCreatCompetenc() throws Exception {
     System.out.println("creatCompetenc");
     String nameEN = "";
     String nameSV = "";
     EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
     CompetenceDAO instance = (CompetenceDAO) container.getContext().lookup("java:global/classes/CompetenceDAO");
     instance.creatCompetenc(nameEN, nameSV);
     container.close();
     // TODO review the generated test code and remove the default call to fail.
     fail("The test case is a prototype.");
     }


     @Test
     public void testRemoveCompetence() throws Exception {
     System.out.println("removeCompetence");
     String name = "";
     EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
     CompetenceDAO instance = (CompetenceDAO) container.getContext().lookup("java:global/classes/CompetenceDAO");
     instance.removeCompetence(name);
     container.close();
     // TODO review the generated test code and remove the default call to fail.
     fail("The test case is a prototype.");
     }


     @Test
     public void testGetCompetences() throws Exception {
     System.out.println("getCompetences");
     String lang = "";
     EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
     CompetenceDAO instance = (CompetenceDAO) container.getContext().lookup("java:global/classes/CompetenceDAO");
     List<CompetenceLangInterface> expResult = null;
     List<CompetenceLangInterface> result = instance.getCompetences(lang);
     assertEquals(expResult, result);
     container.close();
     // TODO review the generated test code and remove the default call to fail.
     fail("The test case is a prototype.");
     }
     */
}

/*
 */
package se.kth.controller;

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
import se.kth.integration.DuplicateEntryException;

/**
 *
 * @author work
 */
@RunWith(Arquillian.class)
public class AdminControllerTest {

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(AdminController.class)
                .addPackage("se.kth.integration")
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

    }

    @Inject
    AdminController instance;

    @Test
    public void testCreateRecriuter() throws Exception {
        String email = "email@kth.se";

        // Get the old size
        int oldSize = instance.getAllRecruiters().size();

        instance.createRecriuter(email);

        // Check if we get the right size
        Assert.assertEquals(oldSize + 1, instance.getAllRecruiters().size());

        try {
            instance.createRecriuter(email);
            Assert.fail("Should not add duplicate values ");
        } catch (DuplicateEntryException ex) {
            Assert.assertThat(ex.getMessage(), containsString("Person alrady exist."));
        }

        //should not add recruiter with null email
        instance.createRecriuter(null);
        Assert.assertEquals(oldSize + 1, instance.getAllRecruiters().size());

        instance.removeRecruiter(email);

    }

    @Test
    public void testRemoveRecriuter() throws Exception {
        String email = "email@kth.se";

        instance.createRecriuter(email);
        // Get the old size
        int oldSize = instance.getAllRecruiters().size();

        instance.removeRecruiter(email);

        // Check if we get the right size
        Assert.assertEquals(oldSize - 1, instance.getAllRecruiters().size());

        try {
            instance.removeRecruiter(email);
            Assert.fail("Should not remove a non existing value");
        } catch (DoesNotExistException ex) {
            Assert.assertThat(ex.getMessage(), containsString("User does not exist."));
        }

        instance.removeRecruiter(null);
        Assert.assertEquals(oldSize - 1, instance.getAllRecruiters().size());
    }
    
    @Test
    public void testGetAllRecruiters() throws Exception {
        String email = "email@kth.se";
        int oldSize = instance.getAllRecruiters().size();

        instance.createRecriuter(email);

        Assert.assertEquals(oldSize + 1, instance.getAllRecruiters().size());
        
        instance.removeRecruiter(email);
        
        Assert.assertEquals(oldSize , instance.getAllRecruiters().size());
    }
    
    

}

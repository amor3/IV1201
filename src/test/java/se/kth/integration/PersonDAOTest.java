/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.integration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
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
        competences.add(new CompetenceProfileDTO("competence1",BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2",BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2",BigDecimal.ONE));

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        utx.begin();
        competenceInstance.creatCompetenc("competence1", "competence1");
        competenceInstance.creatCompetenc("competence2", "competence2");
        
        // Get the old size
        int oldSize = personInstance.getAllApplicantes().size();
        
        personInstance.createApplicant(personDTO);

        // Check if we get the right size
        assertEquals(oldSize + 1, personInstance.getAllApplicantes().size());
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
        competences.add(new CompetenceProfileDTO("competence1",BigDecimal.ONE));
        competences.add(new CompetenceProfileDTO("competence2",BigDecimal.ONE));

        PersonDTO personDTO = new PersonDTO(email, password, firstname, surname, ssn, availableFrom, availableTo, competences);
        utx.begin();
        competenceInstance.creatCompetenc("competence1", "competence1");
        competenceInstance.creatCompetenc("competence2", "competence2");
        personInstance.createApplicant(personDTO);
        // Get the old size
        int oldSize = personInstance.getAllApplicantes().size();
        
        
        
        personInstance.removePerson("email@kth.se");

        // Check if we get the right size
        assertEquals(oldSize - 1, personInstance.getAllApplicantes().size());
        utx.commit();
    }

}

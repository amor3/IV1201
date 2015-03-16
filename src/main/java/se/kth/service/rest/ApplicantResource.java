package se.kth.service.rest;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import se.kth.integration.DoesNotExistException;
import se.kth.integration.DuplicateEntryException;
import se.kth.integration.NullArgumentException;
import se.kth.integration.PersonDAO;
import se.kth.integration.PersonDTO;
import se.kth.model.PersonInterface;

/**
 * REST Web Service
 *
 * @author AMore
 */
@Path("applicant")
@Stateless
public class ApplicantResource {

    @Context
    private UriInfo context;

    @EJB
    private PersonDAO personDAO;

    public ApplicantResource() {
    }

    /**
     * getApplicants lets you retrieve a applicant by just passing a email
     *
     * @param email searchable on the email
     * @return a person
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{email}")
    public Response getApplicant(@PathParam("email") String email) {

        PersonInterface person = null;
        try {
            person = personDAO.getPerson(email);
        } catch (DoesNotExistException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullArgumentException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(200).entity(person).build();
    }

    /**
     * createApplicant lets you create a new applicant by sending json
     *
     * @param email
     * @param firstname
     * @param surname
     * @param ssn
     * @param personDTO
     * @return
     */
    @POST
    @Path("/create/{email}/{firstname}/{surname}/{ssn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createApplicant(@PathParam("email") String email,
            @PathParam("firstname") String firstname,
            @PathParam("surname") String surname,
            @PathParam("ssn") String ssn) {
        JsonObject value = null;

        PersonDTO personDTO = new PersonDTO();
        personDTO.setEmail(email);
        personDTO.setFirstname(firstname);
        personDTO.setSurname(surname);
        personDTO.setSsn(ssn);
        personDTO.setPassword("Standard");

        // Check if the user exist already
        PersonInterface addApplicant;
        try {
            addApplicant = personDAO.getPerson(email);
        } catch (DoesNotExistException ex) {

            try {
                personDAO.createApplicant(personDTO);
            } catch (DuplicateEntryException | NullArgumentException ex1) {
                Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex1);
            }

            value = Json.createObjectBuilder()
                    .add("added", "true")
                    .build();

        } catch (NullArgumentException ex) {
            value = Json.createObjectBuilder()
                    .add("added", "false, due to exception.")
                    .build();
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        return Response.status(200).entity(value).build();
    }

    @PUT
    @Path("/update/{email}/{firstname}/{surname}/{ssn}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateApplicant(@PathParam("email") String email,
            @PathParam("firstname") String firstname,
            @PathParam("surname") String surname,
            @PathParam("ssn") String ssn) {
        JsonObject value;

        PersonInterface updateApplicant = null;
        try {
            updateApplicant = personDAO.getPerson(email);

        } catch (DoesNotExistException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullArgumentException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (updateApplicant != null) {
            PersonDTO personDTO = new PersonDTO();

            personDTO.setEmail(updateApplicant.getEmail());
            personDTO.setFirstname(updateApplicant.getName());
            personDTO.setSurname(updateApplicant.getSurname());
            personDTO.setSsn(updateApplicant.getSsn());

            try {
                personDAO.updatePerson(personDTO);
            } catch (NullArgumentException ex) {
                Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DoesNotExistException ex) {
                Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
            }

            value = Json.createObjectBuilder()
                    .add("updated", "true")
                    .build();
        } else {
            value = Json.createObjectBuilder()
                    .add("updated", "false")
                    .add("reason", "User dont exist")
                    .build();
        }
        return Response.status(200).entity(value).build();
    }

    @DELETE
    @Path("/delete/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteAuth(@PathParam("email") String email) {
        JsonObject value;
        try {
            personDAO.removePerson(email);
        } catch (DoesNotExistException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullArgumentException ex) {
            Logger.getLogger(ApplicantResource.class.getName()).log(Level.SEVERE, null, ex);
        }

        value = Json.createObjectBuilder()
                .add("deleted", "true")
                .add("user", email)
                .build();
        return Response.status(200).entity(value).build();
    }

}

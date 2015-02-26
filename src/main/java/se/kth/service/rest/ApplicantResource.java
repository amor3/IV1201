/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.service.rest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author AMore
 */
@Path("applicant")
@RequestScoped
public class ApplicantResource {

    @Context
    private UriInfo context;

    public ApplicantResource() {
    }

    
    @GET
    @Produces("application/json")
    public Response getJson() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }


    @PUT
    @Consumes("application/json")
    public void putJson(Response content) {
    }
}

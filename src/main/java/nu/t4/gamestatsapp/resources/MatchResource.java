/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.gamestatsapp.beans.MatchBean;

/**
 *
 * @author Erik
 */
@Path("")
public class MatchResource {
    
    @EJB
    MatchBean meatchBean;
    
    @GET
    @Path("match/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMatch(@PathParam("id") int id){
        return Response.ok(id).build();
    }
}

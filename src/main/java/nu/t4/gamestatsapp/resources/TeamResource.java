package nu.t4.gamestatsapp.resources;

import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.gamestatsapp.beans.TeamBean;
import nu.t4.gamestatsapp.entities.Team;

/**
 *
 * @author Erik
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TeamResource {
    
    @EJB
    TeamBean teamBean;
    
    @GET
    @Path("teams")
    public Response getTeams(){
        List<Team> teams = teamBean.getTeams();
        if(!teams.isEmpty()){
            return Response.ok(teams).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("team/{id}")
    public Response getTeam(@PathParam("id") int id){
        Team team = teamBean.getTeam(id);
        if(team != null){
            return Response.ok(team).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @POST
    @Path("team")
    public Response postTeam(Team team) {
        if(teamBean.addTeam(team) == 1){
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @PUT
    @Path("team")
    public Response updateTeam(Team team) {
        if(teamBean.updateTeam(team) == 1){
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @DELETE
    @Path("team/{id}")
    public Response deleteTeam(@PathParam("id") int id){
        if(teamBean.deleteTeam(id) == 1){
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

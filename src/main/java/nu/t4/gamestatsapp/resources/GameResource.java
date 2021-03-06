package nu.t4.gamestatsapp.resources;

import java.util.ArrayList;
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
import nu.t4.gamestatsapp.beans.GameBean;
import nu.t4.gamestatsapp.entities.Game;

/**
 *
 * @author Erik
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GameResource {

    @EJB
    GameBean gameBean;
    
    @GET
    @Path("match/{id}")
    public Response getGame(@PathParam("id") int id) {
        Game game = gameBean.getGame(id);
        if (game != null) {
            return Response.ok(game).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
    
    @GET
    @Path("teamMatches/{id}")
    public Response getTeamGames(@PathParam("id") int id){
        List<Game> games = gameBean.getTeamGames(id);
        if(!games.isEmpty()){
            return Response.ok(games).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @Path("matches")
    public Response getGames() {
        List<Game> games = new ArrayList();
        games = gameBean.getGames();
        if (!games.isEmpty()) {
            return Response.ok(games).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Path("match")
    public Response postGame(Game game) {
        if (gameBean.addGame(game) == 1) {
            return Response.status(Response.Status.CREATED).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Path("match")
    public Response updateGame(Game game) {
        if (gameBean.updateGame(game) == 1) {
            return Response.status(Response.Status.OK).entity(game).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Path("match/{id}")
    public Response deleteGame(@PathParam("id") int id) {
        if (gameBean.deleteGame(id) == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

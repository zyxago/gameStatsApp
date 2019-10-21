/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.resources;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
public class MatchResource {
    
    @EJB
    GameBean gameBean;
    
    @GET
    @Path("game/{id}")
    public Response getMatch(@PathParam("id") int id){
        Game game = gameBean.getGame(id);
        if(game != null){
            return Response.ok(game).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @GET
    @Path("games")
    public Response getGames(){
        List<Game> games = new ArrayList();
        games = gameBean.getGames();
        if(!games.isEmpty()){
            return Response.ok(games).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
    
    @POST
    @Path("game")
    public Response postGame(Game game){
        boolean result = gameBean.addGame(game);
        if(result){
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

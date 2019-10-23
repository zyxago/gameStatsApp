/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import nu.t4.gamestatsapp.beans.ScoreBoardBean;
import nu.t4.gamestatsapp.entities.ScoreBoard;

/**
 *
 * @author erikh
 */
@Path("")
@Produces(MediaType.APPLICATION_JSON)
public class BoardResources {
    
    @EJB
    ScoreBoardBean scoreBoardBean;
    
        @GET
    @Path("board")
    public Response getScoreBoard(){
        ScoreBoard scoreBoard = scoreBoardBean.getScoreBoard();
        if(scoreBoard != null && !scoreBoard.getTeamsTotalPoints().isEmpty()){
            return Response.ok(scoreBoard).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}

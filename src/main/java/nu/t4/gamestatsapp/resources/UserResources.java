package nu.t4.gamestatsapp.resources;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import nu.t4.gamestatsapp.beans.CredentialsBean;
import nu.t4.gamestatsapp.entities.Credentials;

/**
 *
 * @author Erik
 */
@Path("authenticate")
public class UserResources {

    @EJB
    CredentialsBean credentialsBean;

    @Path("/token")
    @GET
    public Response verifyUser(@HeaderParam("authorization") String token) {
        if(credentialsBean.verifyToken(token)){
            return Response.status(Response.Status.OK).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    public Response checkUser(@HeaderParam("authorization") String basicAuth) {
        Credentials credentials = credentialsBean.createCredentials(basicAuth);
        String token = credentialsBean.checkCredentials(credentials);
        if (!token.equals("")) {
            return Response.ok(token).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    @Path("/create")
    @GET
    public Response createUser(@HeaderParam("authorization") String basicAuth) {
        Credentials credentials = credentialsBean.createCredentials(basicAuth);
        if (credentialsBean.saveCredentials(credentials) == 1) {
            return Response.status(Response.Status.CREATED).build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}

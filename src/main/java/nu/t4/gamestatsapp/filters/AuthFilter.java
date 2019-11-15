package nu.t4.gamestatsapp.filters;

import java.io.IOException;
import javax.ejb.EJB;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import nu.t4.gamestatsapp.beans.CredentialsBean;
import nu.t4.gamestatsapp.entities.Credentials;

/**
 *
 * @author Erik
 */
@Provider
public class AuthFilter implements ContainerRequestFilter {

    @EJB
    CredentialsBean credentialsBean;
    
    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if(request.getMethod().equals("GET")){
            return;
        }
        try {
            String token = request.getHeaderString("Authorization");
            if(credentialsBean.verifyToken(token)) {
                return;
            }
        } catch (Exception e) {
            System.out.println("Error in AuthFilter.filter: " + e.getMessage());
        }
        Response unAuth = Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        request.abortWith(unAuth);
    }
    
}

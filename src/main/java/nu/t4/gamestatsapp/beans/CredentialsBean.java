package nu.t4.gamestatsapp.beans;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.util.Base64;
import javax.ejb.Stateless;
import nu.t4.gamestatsapp.ConnectionFactory;
import nu.t4.gamestatsapp.entities.Credentials;

/**
 *
 * @author Erik
 */
@Stateless
public class CredentialsBean {

    public Credentials createCredentials(String basicAuth) {
        basicAuth = basicAuth.substring(6).trim();//Removes "Basic " from string
        byte[] bytes = Base64.getDecoder().decode(basicAuth);
        basicAuth = new String(bytes);
        int colon = basicAuth.indexOf(":");
        String username = basicAuth.substring(0, colon);
        String password = basicAuth.substring(colon + 1);
        return new Credentials(username, password);
    }

    public boolean checkCredentials(Credentials credentials) {
        String hashedPassword = "a";//TEMP
        return (BCrypt.verifyer().verify(credentials.getPassword().toCharArray(), hashedPassword).verified);
    }
    
    public int addUser(Credentials credentials){
        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO user (name, hash, privilege) VALUES(?, ?, ?)");
            stmt.setString(1, credentials.getUsername());
            byte[] hashedPassword = BCrypt.withDefaults().hash(6, credentials.getPassword().toCharArray());
            stmt.setString(2, hashedPassword.toString());
            stmt.setInt(3, 0);//0 som default
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in CredentialsBean.addUser: " + e.getMessage());
        }
        BCrypt.withDefaults().hash(6, credentials.getPassword().toCharArray());
        return 0;
    }
}

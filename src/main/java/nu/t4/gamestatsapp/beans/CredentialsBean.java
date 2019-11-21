package nu.t4.gamestatsapp.beans;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mysql.jdbc.Connection;
import java.security.SecureRandom;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    public static String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }
    
    public boolean verifyToken(String token){;
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user WHERE token = ?");
            stmt.setString(1, token);
            ResultSet data = stmt.executeQuery();
            if(data.next()){
                return true;
            }
        }catch(Exception e){
            System.out.println("Error in CredentialsBean.verifyToken: " + e.getMessage());
        }
        return false;
    }
    
    private int addToken(Credentials credentials, String token){
        try (Connection connection = ConnectionFactory.getConnection()){
            PreparedStatement stmt = connection.prepareStatement("UPDATE user SET token = ? WHERE name = ?");
            stmt.setString(1, token);
            stmt.setString(2, credentials.getUsername());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in CredentialsBean.addToken: " + e.getMessage());
        }
        return 0;
    }

    public String checkCredentials(Credentials credentials) {
        String token = "";
        String hashedPassword = "";
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM user WHERE name = ?");
            stmt.setString(1, credentials.getUsername());
            ResultSet data = stmt.executeQuery();
            if (data.next()) {
                hashedPassword = data.getString("hash");
            }
        } catch (Exception e) {
            System.out.println("Error in CredentialsBean.checkCredentials: " + e.getMessage());
        }
        if(BCrypt.verifyer().verify(credentials.getPassword().toCharArray(), hashedPassword).verified){
            token = generateNewToken();
            addToken(credentials, token);
        }
        return token;
    }

    public int saveCredentials(Credentials credentials) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO user (name, hash) VALUES(?, ?)");
            stmt.setString(1, credentials.getUsername());
            String hashedPassword = BCrypt.withDefaults().hashToString(13, credentials.getPassword().toCharArray());
            stmt.setString(2, hashedPassword);
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in CredentialsBean.saveCredentials: " + e.getMessage());
        }
        return 0;
    }
}

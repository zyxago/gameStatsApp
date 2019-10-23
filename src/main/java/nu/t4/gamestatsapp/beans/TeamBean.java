package nu.t4.gamestatsapp.beans;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import nu.t4.gamestatsapp.ConnectionFactory;
import nu.t4.gamestatsapp.entities.Team;

/**
 *
 * @author Erik
 */
@Stateless
public class TeamBean {

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList();
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM team";
            ResultSet data = stmt.executeQuery(sql);
            while (data.next()) {
                teams.add(new Team(data.getString("name"), data.getInt("id")));
            }
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.getTeams: " + e.getMessage());
        }
        return teams;
    }

    public Team getTeam(int id) {
        Team team = null;
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM team WHERE id = %d", id);
            ResultSet data = stmt.executeQuery(sql);
            if (data.next()) {
                team = new Team(data.getString("name"), data.getInt("id"));
            }
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.getTeams: " + e.getMessage());
        }
        return team;
    }

    public int updateTeam(Team team) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = String.format("UPDATE team SET name VALUES(%s)", team.getName());
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.updateTeam: " + e.getMessage());
        }
        return 0;
    }

    public int deleteTeam(int id) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = String.format("DELETE team WHERE id = %d", id);
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.deleteTeam: " + e.getMessage());
        }
        return 0;
    }

    public int addTeam(Team team) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO team VALUES (NULL, %s)", team.getName());
            return stmt.executeUpdate(sql);
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.addTeam: " + e.getMessage());
        }
        return 0;
    }

}

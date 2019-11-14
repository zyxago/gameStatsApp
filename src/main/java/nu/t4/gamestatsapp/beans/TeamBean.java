package nu.t4.gamestatsapp.beans;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
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
            String sql = "SELECT * FROM team_stats";
            ResultSet data = stmt.executeQuery(sql);
            while (data.next()) {
                teams.add(new Team(data.getString("name"), data.getInt("teamId"), data.getInt("wins"), data.getInt("losses")));
            }
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.getTeams: " + e.getMessage());
        }
        return teams;
    }

    public Team getTeam(int id) {
        Team team = null;
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM team_stats WHERE id = ?");
            stmt.setInt(1, id);
            ResultSet data = stmt.executeQuery();
            if (data.next()) {
                team = new Team(data.getString("name"), data.getInt("teamId"), data.getInt("wins"), data.getInt("losses"));
            }
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.getTeams: " + e.getMessage());
        }
        return team;
    }

    public int updateTeam(Team team) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE team SET name = ? WHERE team.id = ?");
            stmt.setString(1, team.getName());
            stmt.setInt(2, team.getId());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.updateTeam: " + e.getMessage());
        }
        return 0;
    }

    public int deleteTeam(int id) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM team WHERE id = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.deleteTeam: " + e.getMessage());
        }
        return 0;
    }

    public int addTeam(Team team) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO team (name) VALUES (?)");
            stmt.setString(1, team.getName());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR in TeamBean.addTeam: " + e.getMessage());
        }
        return 0;
    }

}

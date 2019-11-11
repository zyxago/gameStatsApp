package nu.t4.gamestatsapp.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import nu.t4.gamestatsapp.ConnectionFactory;
import nu.t4.gamestatsapp.entities.ScoreBoard;
import nu.t4.gamestatsapp.entities.TeamTotalPoints;

/**
 *
 * @author erikh
 */
@Stateless
public class ScoreBoardBean {

    public ScoreBoard getScoreBoard() {
        ScoreBoard scoreBoard = null;
        List<TeamTotalPoints> teamTotalPoints = new ArrayList();
        try (Connection connection = ConnectionFactory.getConnection()){
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM team_total_points";
            ResultSet data = stmt.executeQuery(sql);
            while(data.next()){
                teamTotalPoints.add(new TeamTotalPoints(data.getString("team"), data.getInt("points")));
            }
            scoreBoard = new ScoreBoard(teamTotalPoints);
        } catch (Exception e) {
            System.out.println("ERROR in ScoreBoardBean.getScoreBoard: " + e.getMessage());
        }
        return scoreBoard;
    }
}

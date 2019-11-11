package nu.t4.gamestatsapp.beans;

import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import nu.t4.gamestatsapp.ConnectionFactory;
import nu.t4.gamestatsapp.entities.Game;

/**
 *
 * @author Erik
 */
@Stateless
public class GameBean {

    public Game getGame(int id) {
        Game game = null;
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM get_match WHERE gameId = ?");
            stmt.setInt(1, id);
            ResultSet data = stmt.executeQuery();
            if (data.next()) {
                game = new Game(
                        data.getString("home"),
                        data.getString("away"),
                        data.getInt("scoreHome"),
                        data.getInt("scoreAway"),
                        data.getInt("homeId"),
                        data.getInt("awayId"),
                        id);
            }
        } catch (Exception e) {
            System.out.println("Error in GameBean.getGame: " + e.getMessage());
        }
        return game;
    }

    public List<Game> getGames() {
        List<Game> games = new ArrayList();
        try ( Connection connection = ConnectionFactory.getConnection()) {
            Statement stmt = connection.createStatement();
            String sql = "SELECT * FROM get_match";
            ResultSet data = stmt.executeQuery(sql);
            while (data.next()) {
                games.add(new Game(
                        data.getString("home"),
                        data.getString("away"),
                        data.getInt("scoreHome"),
                        data.getInt("scoreAway"),
                        data.getInt("gameId"),
                        data.getInt("homeId"),
                        data.getInt("awayId")));
            }
        } catch (Exception e) {
            System.out.println("Error in GameBean.getGames:" + e.getMessage());
        }
        return games;
    }
    
        public List<Game> getTeamGames(int id) {
        List<Game> games = new ArrayList();
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM get_match WHERE homeId = ? OR awayId = ?");
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            ResultSet data = stmt.executeQuery();
            while (data.next()) {
                games.add(new Game(
                        data.getString("home"),
                        data.getString("away"),
                        data.getInt("scoreHome"),
                        data.getInt("scoreAway"),
                        data.getInt("gameId"),
                        data.getInt("homeId"),
                        data.getInt("awayId")));
            }
        } catch (Exception e) {
            System.out.println("Error in GameBean.getTeamGames: " + e.getMessage());
        }
        return games;
    }

    public int addGame(Game game) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO game_match (home_team_id, away_team_id, score_id) VALUES(?, ?, ?)");
            stmt.setInt(1, game.getHomeId());
            stmt.setInt(2, game.getAwayId());
            stmt.setInt(3, getScoreId(game.getHomeScore(), game.getAwayScore()));
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in GameBean.addGame: " + e.getMessage());
        }
        return 0;
    }

    public int updateGame(Game game) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("UPDATE game_match SET home_team_id = ?, away_team_id = ?, score_id = ? WHERE game_match.id = ?");
            stmt.setInt(1, game.getHomeId());
            stmt.setInt(2, game.getAwayId());
            stmt.setInt(3, getScoreId(game.getHomeScore(), game.getAwayScore()));
            stmt.setInt(4, game.getGameId());
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in GameBean.addGame: " + e.getMessage());
        }
        return 0;
    }

    public int deleteGame(int id) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM game_match WHERE game_match.id = ?");
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error in GameBean.deleteGame: " + e.getMessage());
        }
        return 0;
    }

    private int getScoreId(int scoreHome, int scoreAway) throws SQLException {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement("SELECT id FROM score WHERE home_score = ? AND away_score = ?");
            stmt.setInt(1, scoreHome);
            stmt.setInt(2, scoreAway);
            int scoreId = 0;
            ResultSet data = stmt.executeQuery();
            if (data.next()) {
                scoreId = data.getInt("id");
            } else {
                stmt = connection.prepareStatement("INSERT INTO score (home_score, away_score) VALUES(?, ?)");
                stmt.setInt(1, scoreHome);
                stmt.setInt(2, scoreAway);
                stmt.executeUpdate();

                stmt = connection.prepareStatement("SELECT id FROM score WHERE home_score = ? AND away_score = ?");
                stmt.setInt(1, scoreHome);
                stmt.setInt(2, scoreAway);
                data = stmt.executeQuery();
                if (data.next()) {
                    scoreId = data.getInt("id");
                }
            }
            return scoreId;
        } catch (Exception e) {
            System.out.println("Error in GameBean.getScoreId: " + e.getMessage());
            return 0;
        }
    }
}

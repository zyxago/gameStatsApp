/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.beans;

import com.mysql.jdbc.Connection;
import java.sql.ResultSet;
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
            Statement stmt = connection.createStatement();
            String sql = String.format("SELECT * FROM get_match WHERE gameId = %d", id);
            ResultSet data = stmt.executeQuery(sql);
            if (data.next()) {
                game = new Game(
                        data.getString("home"),
                        data.getString("away"),
                        data.getInt("scoreHome"),
                        data.getInt("scoreAway"),
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
                        data.getInt("gameId")));
            }
        } catch (Exception e) {
            System.out.println("Error in GameBean.getGames:" + e.getMessage());
        }
        return games;
    }

    public boolean addGame(Game game) {
        try ( Connection connection = ConnectionFactory.getConnection()) {
            int homeId = 0;
            int awayId = 0;
            int scoreId = 0;
            Statement stmt = connection.createStatement();
            String sql = String.format("INSERT INTO score (NULL, home_score, away_score) VALUES(%d, %d)", game.getHomeScore(), game.getAwayScore());
            String sql2 = String.format("INSERT INTO game_match (NULL, home_team_id, away_team_id, score_id) VALUES(%d, %d, %d)", homeId, awayId, scoreId);
        } catch (Exception e) {
            
        }
    }
}

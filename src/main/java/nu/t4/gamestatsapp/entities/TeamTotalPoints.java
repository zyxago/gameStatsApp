/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.entities;

/**
 *
 * @author erikh
 */
public class TeamTotalPoints {

    private String team;
    private int points;

    public TeamTotalPoints(String team, int points) {
        this.team = team;
        this.points = points;
    }

    public TeamTotalPoints() {
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}

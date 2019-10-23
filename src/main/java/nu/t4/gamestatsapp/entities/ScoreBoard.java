/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nu.t4.gamestatsapp.entities;

import java.util.List;

/**
 *
 * @author erikh
 */
public class ScoreBoard {

    private List<TeamTotalPoints> teamsTotalPoints;

    public ScoreBoard() {
    }

    public ScoreBoard(List<TeamTotalPoints> teamsTotalPoints) {
        this.teamsTotalPoints = teamsTotalPoints;
    }

    public List<TeamTotalPoints> getTeamsTotalPoints() {
        return teamsTotalPoints;
    }

    public void setTeamsTotalPoints(List<TeamTotalPoints> teamsTotalPoints) {
        this.teamsTotalPoints = teamsTotalPoints;
    }

}

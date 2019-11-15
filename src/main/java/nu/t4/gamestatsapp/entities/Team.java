package nu.t4.gamestatsapp.entities;

/**
 *
 * @author Erik
 */
public class Team {

    private String name;
    private int id;
    private int wins;
    private int losses;
    private int matchesPlayed;
    private int matchesWon;
    
    public Team() {
    }

    public Team(String name, int id, int wins, int losses, int matchesPlayed, int matchesWon) {
        this.name = name;
        this.id = id;
        this.wins = wins;
        this.losses = losses;
        this.matchesPlayed = matchesPlayed;
        this.matchesWon = matchesWon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    
    
    

}

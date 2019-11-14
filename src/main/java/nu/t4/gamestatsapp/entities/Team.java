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

    public Team() {
    }

    public Team(String name, int id, int wins, int losses) {
        this.name = name;
        this.id = id;
        this.wins = wins;
        this.losses = losses;
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
    
    

}

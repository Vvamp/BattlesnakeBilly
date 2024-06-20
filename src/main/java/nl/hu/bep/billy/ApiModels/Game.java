package nl.hu.bep.billy.ApiModels;

public class Game {
    public String id;
    public Ruleset ruleset;
    public String map;
    public int timeout; // ms
    public String source;

    public Game() {
    }

    public Game(Game other) {
        this.id = other.id;
        this.ruleset = (other.ruleset != null) ? new Ruleset(other.ruleset) : null;
        this.map = other.map;
        this.timeout = other.timeout;
        this.source = other.source;
    }
}

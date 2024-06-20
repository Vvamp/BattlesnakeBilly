package nl.hu.bep.billy.ApiModels;

public class Ruleset {
    public String name;
    public String version;
    public RulesetSettings settings;

    public Ruleset() {
    }

    public Ruleset(Ruleset other) {
        this.name = other.name;
        this.version = other.version;
        this.settings = (other.settings != null) ? new RulesetSettings(other.settings) : null;
    }
}

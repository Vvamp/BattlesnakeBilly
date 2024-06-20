package nl.hu.bep.billy.ApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RulesetSettings {
    public int foodSpawnChance;
    public int minimumFood;
    public int hazardDamagePerTurn;

    // Royale
    @JsonProperty("royale.shrinkEveryNTurns")
    public int shrinkEveryNTurns;

    // Squad
    @JsonProperty("squad.allowBodyCollisions")
    public boolean allowBodyCollisions;

    @JsonProperty("squad.sharedElimination")
    public boolean sharedElimination;

    @JsonProperty("squad.sharedHealth")
    public boolean sharedHealth;

    @JsonProperty("squad.sharedLength")
    public boolean sharedLength;

    public RulesetSettings() {
    }

    public RulesetSettings(RulesetSettings other) {
        this.foodSpawnChance = other.foodSpawnChance;
        this.minimumFood = other.minimumFood;
        this.hazardDamagePerTurn = other.hazardDamagePerTurn;
        this.shrinkEveryNTurns = other.shrinkEveryNTurns;
        this.allowBodyCollisions = other.allowBodyCollisions;
        this.sharedElimination = other.sharedElimination;
        this.sharedHealth = other.sharedHealth;
        this.sharedLength = other.sharedLength;
    }
}

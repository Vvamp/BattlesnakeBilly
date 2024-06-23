package nl.hu.bep.billy.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.ApiModels.Ruleset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battle {
    @JsonProperty
    private final String id;
    @JsonProperty
    private final List<Battlesnake> otherBattlesnakes;
    @JsonProperty
    private final List<GameRequest> turns;
    @JsonProperty
    private final Ruleset ruleset;
    @JsonProperty
    private Battlesnake myBattlesnake;
    @JsonProperty
    private boolean inProgress;


    public Battle(GameRequest gameRequest) {
        this.id = gameRequest.game.id;
        this.myBattlesnake = gameRequest.you;
        this.otherBattlesnakes = gameRequest.board.snakes;
        this.turns = new ArrayList<>();
        this.turns.add(gameRequest);
        this.ruleset = gameRequest.game.ruleset;
        this.inProgress = true;
    }

    @JsonCreator
    public Battle(@JsonProperty("id") String id, @JsonProperty("myBattlesnake") Battlesnake myBattlesnake, @JsonProperty("otherBattlesnakes") List<Battlesnake> otherBattlesnakes, @JsonProperty("turns") List<GameRequest> turns, @JsonProperty("ruleset") Ruleset ruleset, @JsonProperty("inProgress") boolean inProgress){
        this.id = id;
        this.myBattlesnake = myBattlesnake;
        this.otherBattlesnakes = otherBattlesnakes;
        this.turns = turns;
        this.ruleset = ruleset;
        this.inProgress = inProgress;
    }

    public void addTurn(GameRequest turn) {
        this.setMyBattlesnake(turn.you);
        this.turns.add(turn);
    }

    public void end() {
        this.inProgress = false;
        // calc end condition and stats
    }

    @JsonIgnore
    public String getMostUsedAlgorithm() {
        Map<String, Integer> algorithmsByCount = new HashMap<>();
        for(GameRequest turn : turns){
            if(!algorithmsByCount.containsKey(turn.getAlgorithm())){
                algorithmsByCount.put(turn.getAlgorithm(), 1);
            }else{
                int val = algorithmsByCount.get(turn.getAlgorithm());
                val +=1;
                algorithmsByCount.put(turn.getAlgorithm(), val);
            }
        }

        int biggestCount = -1000;
        String biggestAlgo = "none";
        for(Map.Entry<String, Integer> entry : algorithmsByCount.entrySet()){
            int count = algorithmsByCount.get(entry.getKey());
            String algo = entry.getKey();
            if(count > biggestCount){
                biggestCount = count;
                biggestAlgo=algo;
            }
        }

        return biggestAlgo;

    }

    public String getId() {
        return id;
    }

    @JsonIgnore
    public boolean isInProgress() {
        return inProgress;
    }

    @JsonIgnore
    public String getPlayerId() {
        return myBattlesnake.id;
    }
    @JsonIgnore
    public String getResult() {
        if (this.inProgress) {
            return "game still playing";
        }
        GameRequest lastRequest = turns.get(turns.size() - 1);
        if (lastRequest.board.snakes.contains(lastRequest.you)) {
            if (lastRequest.board.snakes.size() == 1) {
                return "Win";
            } else {
                return "Tie";
            }
        } else {
            return "Loss";
        }
    }

    @JsonIgnore
    public int getTurnCount() {
        return turns.size();
    }

    public Battlesnake getMyBattlesnake() {
        return myBattlesnake;
    }


    private void setMyBattlesnake(Battlesnake myBattlesnake) {
        this.myBattlesnake = myBattlesnake;
    }

    public List<Battlesnake> getOtherBattlesnakes() {
        return otherBattlesnakes;
    }

    public List<GameRequest> getTurns() {
        return turns;
    }


    public Ruleset getRuleset() {
        return ruleset;
    }

//    private EndCondition{ // WinCondition, LossCondition
//    private enum deathReason
//    private int death
//    }

}

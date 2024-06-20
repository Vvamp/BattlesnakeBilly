package nl.hu.bep.billy.models;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.ApiModels.Ruleset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Battle {
    private final String id;
    private final List<Battlesnake> otherBattlesnakes;
    private final List<GameRequest> turns;
    private final Ruleset ruleset;
    private Battlesnake myBattlesnake;
    private boolean isInProgress;


    public Battle(GameRequest gameRequest) {
        this.id = gameRequest.game.id;
        this.myBattlesnake = gameRequest.you;
        this.otherBattlesnakes = gameRequest.board.snakes;
        this.turns = new ArrayList<>();
        this.turns.add(gameRequest);
        this.ruleset = gameRequest.game.ruleset;
        this.isInProgress = true;
    }
//    public Battle(String id, Battlesnake myBattlesnake, List<Battlesnake> otherBattlesnakes, List<GameRequest> turns, Ruleset ruleset){
//        this.id = id;
//        this.myBattlesnake = myBattlesnake;
//        this.otherBattlesnakes = otherBattlesnakes;
//        this.turns = turns;
//        this.ruleset = ruleset;
//    }

    public void addTurn(GameRequest turn) {
        this.setMyBattlesnake(turn.you);
        this.turns.add(turn);
    }

    public void end() {
        this.isInProgress = false;
        // calc end condition and stats
    }

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


    public boolean isInProgress() {
        return isInProgress;
    }

    public String getPlayerId() {
        return myBattlesnake.id;
    }

    public String getResult() {
        if (this.isInProgress) {
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

package nl.hu.bep.billy.models;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.ApiModels.Ruleset;

import java.util.ArrayList;
import java.util.List;

public class Battle {
    private final String id;
    private final List<Battlesnake> otherBattlesnakes;
    private final List<GameRequest> turns;
    private final Ruleset ruleset;
    private Battlesnake myBattlesnake;
    private boolean isInProgress;

//    public Battle(String id, Battlesnake myBattlesnake, List<Battlesnake> otherBattlesnakes, List<GameRequest> turns, Ruleset ruleset){
//        this.id = id;
//        this.myBattlesnake = myBattlesnake;
//        this.otherBattlesnakes = otherBattlesnakes;
//        this.turns = turns;
//        this.ruleset = ruleset;
//    }

    public Battle(GameRequest gameRequest) {
        this.id = gameRequest.game.id;
        this.myBattlesnake = gameRequest.you;
        this.otherBattlesnakes = gameRequest.board.snakes;
        this.turns = new ArrayList<>();
        this.turns.add(gameRequest);
        this.ruleset = gameRequest.game.ruleset;
        this.isInProgress = true;
    }

    public void addTurn(GameRequest turn) {
        this.setMyBattlesnake(turn.you);
        this.turns.add(turn);
    }

    public void end() {
        this.isInProgress = false;
        // calc end condition and stats
    }

    public String getId() {
        return id;
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

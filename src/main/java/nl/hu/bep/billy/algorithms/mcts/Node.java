package nl.hu.bep.billy.algorithms.mcts;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.algorithms.Move;

import java.util.*;

public class Node {
    private final GameState gamestate;
    private final Node parent;
    private final List<Node> children;
    private double visits;
    private double value;
    private final Map<Battlesnake, Move> moves;

    public Node(GameState gamestate, Map<Battlesnake, Move> moves, Node parent) {
        this.gamestate = gamestate;
        this.moves = moves;
        this.parent = parent;
        this.children = new ArrayList<>();
        this.visits = 0;
        this.value = 0;
    }

    public Node findBestChild() {
        if (children.isEmpty()) {
            return null;
        }
        if (children.size() == 1) {
            return children.get(0);
        }
        return children.stream().max(Comparator.comparingDouble(c -> c.getValue() / c.getVisits() + Math.sqrt(2 * Math.log(c.getParent().getVisits()) / c.getVisits()))).orElse(children.get(0));
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public List<Move> getValidUnexploredMoves(Battlesnake snake) {
        return null;
    }

    public Node getParent() {
        return parent;
    }

    public double getVisits() {
        return visits;
    }

    public void setVisits(double visits) {
        this.visits = visits;
    }

    public List<Map<Battlesnake, Move>> getValidMoveCombinations() {
        List<Map<Battlesnake, Move>> combinations = new ArrayList<>();
        Battlesnake me = gamestate.getPlayer();
        Battlesnake opponent = gamestate.getOpponent();
        List<Move> myMoves = gamestate.getLegalMoves(me);
        List<Move> theirMoves = gamestate.getLegalMoves(opponent);
        for (Move myMove : myMoves) {
            Map<Battlesnake, Move> moveRound = new HashMap();
            for (Move theirMove : theirMoves) {
                moveRound.put(me, myMove);
                moveRound.put(opponent, theirMove);
            }
            combinations.add(moveRound);
        }
        return combinations;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public GameState getGamestate() {
        return gamestate;
    }

    public List<Node> getChildren() {
        return children;
    }

    public Map<Battlesnake, Move> getMoves() {
        return moves;
    }
}

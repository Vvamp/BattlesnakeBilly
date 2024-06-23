package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.algorithms.mcts.GameState;
import nl.hu.bep.billy.algorithms.mcts.Node;
import nl.hu.bep.billy.algorithms.mcts.Tree;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;


public class MctsAlgorithm implements IAlgorithm {

    private static Move lastMove = Move.NONE;

    @Override
    public String getName(){
        return "MCTS";
    }

    public static Move getInvertedMove(Move move) {
        if (move == null) return Move.NONE;
        return switch (move) {
            case UP -> Move.DOWN;
            case DOWN -> Move.UP;
            case LEFT -> Move.RIGHT;
            case RIGHT -> Move.LEFT;
            default -> Move.NONE;
        };
    }

    @Override
    public Move findBestMove(GameRequest turn) {
        GameState currentGamestate = new GameState(turn);
        Node root = new Node(currentGamestate, null, null);
        Tree tree = new Tree(root);
        Move invertedLastMove = getInvertedMove(lastMove);

        // get save moves(around me)
        // expand for each move a leaf node
        // simulate entire playthrough
        // loop until time (almost) up
        long c = 0;
        Instant instant = Instant.now();
        while (Instant.now().isBefore(instant.plusMillis(250))) { // we have at max 500ms. PC: 450ms limit. Azure: 250ms due to slownesss
            Node leafWithPossibleMoves = tree.findSpotToExpand(root, invertedLastMove); // find an unexplored move and return it as node
            Node newLeaf = tree.expand(leafWithPossibleMoves); // create a node for unexplored move
            double gameResult = tree.rollout(newLeaf); // Play the game until end
            tree.backupValue(newLeaf, gameResult); // store value
            c += 1;
        }

//        System.out.println("[Brain] Move took " + ((Instant.now().minusNanos(instant.getNano())).getNano() / 1000000) + " ms and " + c + " games were checked");
        List<Node> childrenToRemove = root.getChildren().stream().filter(child -> child.getMoves().get(root.getGamestate().getPlayer()) == invertedLastMove).collect(Collectors.toList());
        root.getChildren().removeAll(childrenToRemove);
        var bestChild = root.findBestChild();
        Move bestMove = bestChild.getMoves().get(root.getGamestate().getPlayer()); // get best move for player :)
//        System.out.println("[Brain] Best Move: " + bestMove.toString() + " with score: " + bestChild.getValue() + " and visits " + bestChild.getVisits());

        lastMove = bestMove;
        return bestMove;
    }
}

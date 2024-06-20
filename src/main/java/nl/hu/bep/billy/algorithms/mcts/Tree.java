package nl.hu.bep.billy.algorithms.mcts;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.algorithms.MctsAlgorithm;
import nl.hu.bep.billy.algorithms.Move;

import java.util.*;

public class Tree {
    private final Node root;

    public Tree(Node rootNode) {
        this.root = rootNode;
    }

    public Node findSpotToExpand(Node root, Move blacklist) {
        List<Map<Battlesnake, Move>> validMoves = root.getValidMoveCombinations();
        // check which moves are unexplored(not a child yet)
        // if multiple: check random(TODO: improvements here are possible)
        for (Node n : root.getChildren()) {
            Map<Battlesnake, Move> moveDone = n.getMoves();
            validMoves.remove(moveDone);
        }
        Map<Battlesnake, Move> blacklisted = new HashMap<>();
        blacklisted.put(root.getGamestate().getPlayer(), blacklist);
        validMoves.remove(blacklisted);

        if (validMoves.isEmpty()) {
            // Keep looking for leaf nodes
            Node newRoot = root.getChildren().get(new Random().nextInt(root.getChildren().size()));
            return findSpotToExpand(newRoot, MctsAlgorithm.getInvertedMove(newRoot.getMoves().get(newRoot.getGamestate().getPlayer())));
        } else {
            return root; // this node still has unexplored moves, so is a leaf node
        }
    }

    public Node expand(Node root) {
        Map<Battlesnake, Move> moves = new HashMap<>();
        List<Battlesnake> players = getPlayers(root.getGamestate());
        GameState newGameState = new GameState(root.getGamestate());
        for (Battlesnake player : players) {
            List<Move> validMoves = root.getValidUnexploredMoves(player);
            Move newMove = validMoves.get(new Random().nextInt(validMoves.size()));

            newGameState.applyMove(player, newMove);
            moves.put(player, newMove);
        }

        Node newMoveNode = new Node(newGameState, moves, root);
        root.addChild(newMoveNode);
        return newMoveNode;
    }

    private List<Battlesnake> getPlayers(GameState gameState) {
        Battlesnake me = gameState.getPlayer();
        Battlesnake other = gameState.getOpponent(); // there is only 1 when using this algo
        List<Battlesnake> players = new ArrayList<>();
        players.add(me);
        if (other != null) players.add(other);

        return players;
    }

    public double rollout(Node leaf) {
        GameState currentState = new GameState(leaf.getGamestate());
        Random random = new Random();
        int rounds = 0;
        while (!currentState.isTerminal()) {
            List<Battlesnake> players = getPlayers(currentState);
            Collections.reverse(players); // make sure I step last to decrease points
            Map<Battlesnake, Move> moves = new HashMap<>();
            for (Battlesnake snake : players) {
                List<Move> legalMoves = currentState.getLegalMoves(snake);
                if (legalMoves.isEmpty()) {
                    continue; // If no legal moves, skip this snake
                }
                Move newMove = legalMoves.get(random.nextInt(legalMoves.size())); // Pick a random move
                moves.put(snake, newMove);
            }

            for (Map.Entry<Battlesnake, Move> entry : moves.entrySet()) {
                boolean snakeDied = currentState.applyMove(entry.getKey(), entry.getValue()); // Apply moves for each snake
                if (snakeDied) {
                    return currentState.getScore();
                }
            }

            rounds += 1;
            if (rounds >= 1000) {
                break;
            }
        }

        return currentState.getScore();
    }


    public void backupValue(Node n, double result) {
        while (n != null) {
            n.setVisits((n.getVisits() + 1));
            n.setValue(n.getValue() + result);
            n = n.getParent();
        }
    }
}


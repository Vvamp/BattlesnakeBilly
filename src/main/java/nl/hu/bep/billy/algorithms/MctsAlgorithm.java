package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.algorithms.mcts.GameState;
import nl.hu.bep.billy.algorithms.mcts.Node;
import nl.hu.bep.billy.algorithms.mcts.Tree;

import java.time.Instant;
import java.util.List;


public class MctsAlgorithm implements IAlgorithm {


    @Override
    public Move findBestMove(GameRequest turn) {
        GameState currentGamestate = new GameState(turn);
        Node root = new Node(currentGamestate, null, null);
        Tree tree = new Tree(root);
        // get save moves(around me)
        // expand for each move a leaf node
        // simulate entire playthrough
        // loop until time (almost) up

        Instant instant = Instant.now();

        while(Instant.now().isBefore(instant.plusMillis(400))){ // max 400ms
            Node leafWithPossibleMoves = tree.findSpotToExpand(root); // find an unexplored move and return it as node
            Node newLeaf = tree.expand(leafWithPossibleMoves); // create a node for unexplored move
            double gameResult = tree.rollout(newLeaf); // Play the game until end
            tree.backupValue(newLeaf, gameResult); // store value
        }


        return root.findBestChild().getMoves().get(root.getGamestate().getPlayer()); // get best move for player :)
    }
}

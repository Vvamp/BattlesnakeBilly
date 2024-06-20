package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.Coordinate;
import nl.hu.bep.billy.ApiModels.GameRequest;

import java.util.ArrayList;
import java.util.List;

//TODO:
/*
 * playTurn returns enum: UP,DOWN,LEFT,RIGHT,NONE/UNKNOWN
 * algorithm:
 * Find nearest piece
 * Check if I am closer than the rest
 * If so: GO
 * if not, find second nearest piece
 * etc
 *
 * if none, try and kill/stay alive
 * */
public class VvampAlgorithm implements IAlgorithm {
    private final int maxFoodDistance = 4;
    private Move lastMove = Move.NONE;

    private int findStepsToCoordinate(Coordinate initial, Coordinate target) {
        return Math.abs(initial.x - target.x) + Math.abs(initial.y - target.y);
    }

    private Coordinate findRelativePosition(Coordinate initial, Coordinate target) {
        Coordinate relative = new Coordinate();
        relative.x = initial.x - target.x;
        relative.y = initial.y - target.y;
        return relative;
    }

    private Coordinate findBestFood(Coordinate head, List<Coordinate> foodCoords) {
        int shortestFoodDistance = Integer.MAX_VALUE;
        Coordinate bestFood = null;
        for (Coordinate food : foodCoords) {
            int distance = findStepsToCoordinate(head, food);
            if (distance == 1) {
                return food;
            }
            if (distance <= shortestFoodDistance) {
                bestFood = food;
                shortestFoodDistance = distance;
            }
        }
        return shortestFoodDistance > maxFoodDistance ? null : bestFood;
    }

    @Override
    public Move findBestMove(GameRequest turn) {
        Move bestMove;
        List<Move> bestMoves = new ArrayList<>();
        Coordinate head = turn.you.head;
        List<Coordinate> foodCoords = turn.board.food;
        List<Coordinate> danger = turn.board.hazards;

        for (Battlesnake s : turn.board.snakes) {
            danger.add(s.head);
            for (Coordinate b : s.body) {
                danger.add(b);
            }
            danger.remove(danger.get(danger.size() - 1));// tail won't be there next turn
        }


        Coordinate bestFood = findBestFood(head, foodCoords);
//        Coordinate dirToFood = findRelativePosition(head, bestFood);
        if (bestFood.x != head.x) bestMoves.add((bestFood.x > head.x ? Move.RIGHT : Move.LEFT));
        if (bestFood.y != head.y) bestMoves.add((bestFood.y > head.y ? Move.UP : Move.DOWN));


//
//        if(bestFood == null){
//            // no food to go for
//            return Move.UP;
//        }


        for (Move m : bestMoves) {
            Coordinate target = head;
            switch (m) {
                case RIGHT:
                    target.x++;
                    break;
                case LEFT:
                    target.x--;
                    break;
                case UP:
                    target.y++;
                    break;
                case DOWN:
                    target.y--;
                    break;
            }
            if (danger.stream().anyMatch(d -> d.x == target.x && d.y == target.y)) {
                bestMoves.remove(m);
            }
        }

        if (bestMoves.isEmpty()) {
            RandomAlgorithm ra = new RandomAlgorithm();
            System.out.println("Random move cuz no good moves");
            return ra.findBestMove(turn);
        }
        bestMove = bestMoves.get(0);
        lastMove = bestMove;
        return bestMove;
    }
}

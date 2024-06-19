package nl.hu.bep.billy.algorithms.mcts;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.Coordinate;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.algorithms.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GameState {
    private final GameRequest gamerequest;
    private Cell[][] board;
    private double score = 0;

    public GameState(GameRequest gamerequest) {
        this.gamerequest = gamerequest;
        board = new Cell[gamerequest.board.width][gamerequest.board.height];
        board = loadBoard();
    }

    public GameState(GameState gamestate) {
        this(new GameRequest(gamestate.getGamerequest()));
    }

    private Cell[][] loadBoard() {
        Cell[][] currentBoard = new Cell[board.length][board[0].length];
        for (int x = 0; x < currentBoard.length; x++) {
            for (int y = 0; y < currentBoard[0].length; y++) {
                Coordinate currentCellCoordinate = new Coordinate();
                currentCellCoordinate.x = x;
                currentCellCoordinate.y = y;

                if (gamerequest.board.food.contains(currentCellCoordinate)) {
                    currentBoard[x][y] = new Cell(CellStatus.Food);
                } else if (gamerequest.board.hazards.contains(currentCellCoordinate)) {
                    currentBoard[x][y] = new Cell(CellStatus.Danger);
                } else if (gamerequest.board.snakes.stream().map(Battlesnake::getBody).collect(Collectors.toList()).contains(currentCellCoordinate)) {
                    currentBoard[x][y] = new Cell(CellStatus.Danger);
                } else {
                    currentBoard[x][y] = new Cell(CellStatus.Empty);
                }
            }
        }
        return currentBoard;
    }


    // for each snake
    // // add new body (head) (pm)
    // // remove last body (tail) (pm)
    // // reduce health 1

    // for each foodFinder
    // // health max
    // // extra part at same pos as tail
    // // remove food

    // place foods (skip as its unknown)

    // eliminate snakes that are:
    // // health at or below 0
    // // oob
    // // colliding (head-to-head loser)
    public void applyMove(Battlesnake battlesnake, Move move) {
        // fix body
        Coordinate newHead = battlesnake.head;
        switch (move) {
            case UP -> {
                newHead.y += 1;
            }
            case DOWN -> {
                newHead.y -= 1;
            }
            case LEFT -> {
                newHead.x += 1;
            }
            case RIGHT -> {
                newHead.x -= 1;
            }
        }
        List<Coordinate> newBody = new ArrayList<>();
        newBody.add(newHead);
        newBody.addAll(battlesnake.body);
        newBody.remove(newBody.size() - 1);

        battlesnake.health -=1;

        if(board[newHead.x][newHead.y].getStatus() == CellStatus.Danger){
            battlesnake.health=0;
        }else if(board[newHead.x][newHead.y].getStatus() == CellStatus.Food){
            battlesnake.health=100;
        }

        if(battlesnake.health <= 0){
            // kill
            gamerequest.board.snakes.remove(battlesnake);

            score = battlesnake == getPlayer() ? -1 : 1; // if dead snake is player, we lose. Otherwise, we win!

            // Kill snake
            for(Coordinate c : battlesnake.body){
                board[c.x][c.y] = new Cell(CellStatus.Empty);
            }
            return;
        }
        battlesnake.body = newBody;
    }

    public List<Move> getLegalMoves(Battlesnake battlesnake) {
        List<Move> legalMoves = new ArrayList<>();
        legalMoves.add(Move.UP);
        legalMoves.add(Move.DOWN);
        legalMoves.add(Move.LEFT);
        legalMoves.add(Move.RIGHT);

        legalMoves.remove(battlesnake.getInvertedDirection()); // can't move backwards
        return legalMoves;
    }


    public Battlesnake getPlayer() {
        return getGamerequest().you;
    }

    public Battlesnake getOpponent() {
        return getGamerequest().board.snakes.stream().filter(s -> !s.id.equals(getPlayer().id)).findFirst().orElse(null);
    }

    public GameRequest getGamerequest() {
        return gamerequest;
    }

    public boolean isTerminal() {
        return gamerequest.board.snakes.size() < 2;
    }

    public double getScore() {
        return score;
    }
}

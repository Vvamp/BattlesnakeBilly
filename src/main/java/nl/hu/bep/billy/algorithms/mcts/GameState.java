package nl.hu.bep.billy.algorithms.mcts;

import nl.hu.bep.billy.ApiModels.Battlesnake;
import nl.hu.bep.billy.ApiModels.Coordinate;
import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.algorithms.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GameState {
    private final GameRequest gamerequest;
    public Cell[][] board;
    private double score = 0;

    public GameState(GameRequest gamerequest) {
        this.gamerequest = new GameRequest(gamerequest);
        this.board = new Cell[gamerequest.board.width][gamerequest.board.height];
        this.board = loadBoard();
    }

    public GameState(GameState gamestate) {
        this.gamerequest = new GameRequest(gamestate.getGamerequest());
        this.board = new Cell[gamerequest.board.width][gamerequest.board.height];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                this.board[x][y] = new Cell(gamestate.board[x][y]);
            }
        }
        this.score = gamestate.score;
    }

    private Cell[][] loadBoard() {
        Cell[][] currentBoard = new Cell[board.length][board[0].length];
        for (int y = 0; y < currentBoard[0].length; y++) {
            for (int x = 0; x < currentBoard.length; x++) {
                Coordinate currentCellCoordinate = new Coordinate(x, y);

                if (gamerequest.board.food.contains(currentCellCoordinate)) {
                    currentBoard[x][y] = new Cell(CellStatus.Food);
                } else if (gamerequest.board.hazards.contains(currentCellCoordinate)) {
                    currentBoard[x][y] = new Cell(CellStatus.Danger);
                } else if (gamerequest.board.snakes.stream().flatMap(s -> s.body.stream()).anyMatch(c -> c.equals(currentCellCoordinate))) {
                    currentBoard[x][y] = new Cell(CellStatus.Danger);
                } else {
                    currentBoard[x][y] = new Cell(CellStatus.Empty);
                }
            }
        }
        return currentBoard;
    }

    public boolean applyMove(Battlesnake battlesnake, Move move) {
        Coordinate newHead = new Coordinate(battlesnake.head);
        switch (move) {
            case UP -> newHead.y += 1;
            case DOWN -> newHead.y -= 1;
            case LEFT -> newHead.x -= 1;
            case RIGHT -> newHead.x += 1;
        }

        List<Coordinate> newBody = new ArrayList<>();
        newBody.add(newHead);
        newBody.addAll(battlesnake.body);
        newBody.remove(newBody.size() - 1);
        battlesnake.setDirection(move);
        battlesnake.health -= 1;

        if (newHead.x < 0 || newHead.x >= gamerequest.board.width || newHead.y < 0 || newHead.y >= gamerequest.board.height || board[newHead.x][newHead.y].getStatus() == CellStatus.Danger) {
            battlesnake.health = 0;
            score -= 100000;
            newBody.remove(newHead);
        } else if (board[newHead.x][newHead.y].getStatus() == CellStatus.Food) {
            battlesnake.health = 100;
            score += 3;
            // Extend the body (add the old tail position back)
            newBody.add(battlesnake.body.get(battlesnake.body.size() - 1));
        }

        if (battlesnake.health <= 0) {
            // kill
            gamerequest.board.snakes.remove(battlesnake);
            // if dead snake is player, we lose. Otherwise, we win!
            if (battlesnake.equals(getPlayer())) {
                score -= 30;
            } else {
                score += 2;
            }

            // Kill snake
            for (Coordinate c : battlesnake.body) {
                if (c.x >= 0 && c.x < board.length && c.y >= 0 && c.y < board[0].length) {
                    board[c.x][c.y] = new Cell(CellStatus.Empty);
                }
            }
            return true;
        }
        battlesnake.body = newBody;
        return false;
    }

    public void printBoard() {
        for (int y = board[0].length - 1; y >= 0; y--) { // Start from the top row to the bottom row
            for (int x = 0; x < board.length; x++) {
                Cell cell = board[x][y];
                switch (cell.getStatus()) {
                    case Food -> System.out.print("F ");
                    case Danger -> System.out.print("D ");
                    case Empty -> System.out.print(". ");
                }
            }
            System.out.println(); // Newline after each row
        }

    }

    public List<Move> getLegalMoves(Battlesnake battlesnake) {

        return getLegalMoves(battlesnake, false);
    }

    public List<Move> getLegalMoves(Battlesnake battlesnake, boolean x) {
        List<Move> allMoves = new ArrayList<>();
        List<Move> legalMoves = new ArrayList<>();
        allMoves.add(Move.UP);
        allMoves.add(Move.DOWN);
        allMoves.add(Move.LEFT);
        allMoves.add(Move.RIGHT);
        for (Move move : allMoves) {
            Coordinate newHead = new Coordinate(battlesnake.head);
            switch (move) {
                case UP -> newHead.y += 1;
                case DOWN -> newHead.y -= 1;
                case LEFT -> newHead.x -= 1;
                case RIGHT -> newHead.x += 1;
            }

            if (newHead.x < 0 || newHead.x >= gamerequest.board.width || newHead.y < 0 || newHead.y >= gamerequest.board.height || board[newHead.x][newHead.y].getStatus() == CellStatus.Danger) {
//                if (x) System.out.println("Move " + move + " would kill");
//                legalMoves.remove(move);
            } else {
                legalMoves.add(move);
            }
        }

        return legalMoves;
    }

    public Battlesnake getPlayer() {
        return getGamerequest().you;
    }

    public List<Battlesnake> getOpponents() {
        return getGamerequest().board.snakes.stream().filter(s -> !s.id.equals(getPlayer().id)).collect(Collectors.toList());
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

package nl.hu.bep.billy.ApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GameRequest {
    public Game game;
    public int turn;
    public Board board;
    public Battlesnake you;
    private String algorithm;

    public GameRequest() {
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
    public String getAlgorithm() {
        return algorithm;
    }

    public GameRequest(Game game, int turn, Board board, Battlesnake you) {
        this.game = new Game(game);
        this.turn = turn;
        this.board = new Board(board);
        this.you = new Battlesnake(you);
    }

    public GameRequest(GameRequest gamerequest) {
        this(gamerequest.game, gamerequest.turn, gamerequest.board, gamerequest.you);
    }
}

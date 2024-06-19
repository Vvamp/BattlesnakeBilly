package nl.hu.bep.billy.ApiModels;

public class GameRequest {
    public Game game;
    public int turn;
    public Board board;
    public Battlesnake you;

    public GameRequest(Game game, int turn, Board board, Battlesnake you) {
        this.game = game;
        this.turn = turn;
        this.board = board;
        this.you = you;
    }

    public GameRequest(GameRequest gamerequest) {
        this(gamerequest.game, gamerequest.turn, gamerequest.board, gamerequest.you);
    }
}

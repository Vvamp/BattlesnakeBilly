package nl.hu.bep.billy.ApiModels;

public class GameRequest {
    public Game game;
    public int turn;
    public Board board;
    public Battlesnake you;

    public GameRequest() {
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

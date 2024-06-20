package nl.hu.bep.billy.ApiModels;

public class GameResult {
    public String gameid;
    public String gameresult;
    public int turns;
    public boolean inProgress;
    public String algorithm;
    public String snakeid;


    public GameResult(String gameid, String gameresult, int turns, boolean inProgress, String algorithm, String snakeid) {
        this.gameid = gameid;
        this.gameresult = gameresult;
        this.turns = turns;
        this.inProgress = inProgress;
        this.algorithm = algorithm;
        this.snakeid = snakeid;
    }
}

package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.GameRequest;

public interface IAlgorithm {
    Move findBestMove(GameRequest turn);
    public String getName();
}

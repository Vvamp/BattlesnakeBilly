package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.GameRequest;

public interface IAlgorithm {
    public Move findBestMove(GameRequest turn);
}

package nl.hu.bep.billy.algorithms;

import nl.hu.bep.billy.ApiModels.GameRequest;

import java.util.Random;

public class RandomAlgorithm implements  IAlgorithm{
    @Override
    public Move findBestMove(GameRequest turn) {
        return Move.values()[new Random().nextInt(Move.values().length)];
    }
}

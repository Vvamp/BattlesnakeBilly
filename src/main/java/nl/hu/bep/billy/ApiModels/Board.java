package nl.hu.bep.billy.ApiModels;

import java.util.List;

public class Board {
    public int height;
    public int width;
    public List<Coordinate> food;
    public List<Coordinate> hazards;
    public List<Battlesnake> snakes;
}

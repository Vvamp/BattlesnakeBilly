package nl.hu.bep.billy.ApiModels;

import java.util.List;
import java.util.stream.Collectors;

public class Board {
    public int height;
    public int width;
    public List<Coordinate> food;
    public List<Coordinate> hazards;
    public List<Battlesnake> snakes;

    public Board() {
    }

    public Board(Board other) {
        this.width = other.width;
        this.height = other.height;
        this.food = other.food.stream().map(Coordinate::new).collect(Collectors.toList());
        this.hazards = other.hazards.stream().map(Coordinate::new).collect(Collectors.toList());
        this.snakes = other.snakes.stream().map(Battlesnake::new).collect(Collectors.toList());
    }
}

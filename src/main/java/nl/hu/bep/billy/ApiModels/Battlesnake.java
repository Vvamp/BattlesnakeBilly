package nl.hu.bep.billy.ApiModels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import nl.hu.bep.billy.algorithms.Move;

import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Battlesnake {
    public String id;
    public String name;
    public int health;
    public List<Coordinate> body;
    public String latency;
    public Coordinate head;
    public int length;
    public String shout;
    public String squad;
    public Customizations customizations;
    private boolean foundFood = false;
    private Move direction = Move.NONE;

    public Battlesnake(Battlesnake other) {
        this.id = other.id;
        this.name = other.name;
        this.health = other.health;
        this.body = other.body.stream().map(Coordinate::new).collect(Collectors.toList());
        this.latency = other.latency;
        this.head = new Coordinate(other.head);
        this.length = other.length;
        this.shout = other.shout;
        this.squad = other.squad;
        this.customizations = new Customizations(other.customizations);
        this.foundFood = other.foundFood;
    }

    public Battlesnake() {
    }

    public boolean hasFoundFood() {
        return foundFood;
    }

    public List<Coordinate> getBody() {
        return body;
    }

    public Move getDirection() {
        return direction;
    }

    public void setDirection(Move direction) {
        this.direction = direction;
    }

    public Move getInvertedDirection() {
        Move dir = getDirection();

        switch (dir) {
            case UP:
                return Move.DOWN;
            case DOWN:
                return Move.UP;
            case LEFT:
                return Move.RIGHT;
            case RIGHT:
                return Move.LEFT;
            default:
                return Move.NONE;
        }
    }

    public void setFoundFood(boolean foundFood) {
        this.foundFood = foundFood;
    }
}

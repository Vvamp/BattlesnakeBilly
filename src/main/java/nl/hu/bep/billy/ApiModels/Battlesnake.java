package nl.hu.bep.billy.ApiModels;

import nl.hu.bep.billy.algorithms.Move;

import java.util.List;

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

    public boolean hasFoundFood() {
        return foundFood;
    }

    public List<Coordinate> getBody() {
        return body;
    }

    public Move getDirection() {
        int ax = body.get(0).x;
        int ay = body.get(0).y;
        int bx = body.get(1).x;
        int by = body.get(1).y;

        if (ax > bx) {
            return Move.RIGHT;
        } else if (ax < bx) {
            return Move.LEFT;
        }
        if (ay > by) {
            return Move.UP;
        } else {
            return Move.DOWN;
        }

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

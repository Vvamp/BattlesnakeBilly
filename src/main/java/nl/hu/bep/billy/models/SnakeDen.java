package nl.hu.bep.billy.models;

import java.util.ArrayList;
import java.util.List;

public final class SnakeDen {
    public static List<Snake> snakes = new ArrayList<Snake>();
    private SnakeDen() {

    }

    public static void updateSnake(String color, String head, String tail){
        Snake snake = snakes.get(0); // TODO: update with id
        if(color.isEmpty() == false){
            snake.setColor(color);
        }
        if(head.isEmpty() == false){
            snake.setHead(head);
        }
        if(tail.isEmpty() == false){
            snake.setTail(tail);
        }
    }

    public static List<Snake> getSnakes() {
        return snakes;
    }

    public static void addSnake(Snake snake){
        snakes.add(snake);
    }


}

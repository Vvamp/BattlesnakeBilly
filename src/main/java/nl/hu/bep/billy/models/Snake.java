package nl.hu.bep.billy.models;

import nl.hu.bep.billy.ApiModels.GameRequest;
import nl.hu.bep.billy.Customizations;
import nl.hu.bep.billy.algorithms.IAlgorithm;
import nl.hu.bep.billy.algorithms.Move;
import nl.hu.bep.billy.algorithms.RandomAlgorithm;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private static int counter = 1;
    public final String apiversion = "1";
    public final String version = "0.1";
    private final IAlgorithm brain;
    private final String author = "Vvamp";
    private final int id;
    private final List<Battle> battles = new ArrayList<>();
    private String color;
    private String tail;
    private String head;

    public Snake() {
        this.id = counter++;
        this.color = "#0000ff";
        this.head = Customizations.getRandomHead();
        this.tail = Customizations.getRandomTail();
        this.brain = new RandomAlgorithm(); //TODO:Change
    }

    public void endBattle(GameRequest request) {
        registerTurn(request, true);
    }

    public void addBattle(Battle battle) {
        this.battles.add(battle);
    }

    public void updateBattle(Battle battle) {
        for (int battleIndex = 0; battleIndex < battles.size(); battleIndex++) {
            Battle currentBattle = battles.get(battleIndex);
            if (currentBattle.getId().equals(battle.getId())) {
                battles.set(battleIndex, battle);
                return;
            }
        }
    }

    public Move play(GameRequest gameRequest) {
        return brain.findBestMove(gameRequest);
    }

    public void registerTurn(GameRequest gameRequest) {
        registerTurn(gameRequest, false);
    }

    public void registerTurn(GameRequest gameRequest, boolean lastTurn) {
        String gameId = gameRequest.game.id;
        for (Battle currentBattle : battles) {
            if (currentBattle.getId().equals(gameId)) {
                currentBattle.addTurn(gameRequest);
                if (lastTurn) {
                    currentBattle.end();
                }
                return;
            }
        }
    }

    public void deleteBattle(String gameId) {
        Battle battleFound = null;
        for (Battle currentBattle : battles) {
            if (currentBattle.getId().equals(gameId)) {
                battleFound = currentBattle;
                break;
            }
        }
        if(battleFound != null) {
            battles.remove(battleFound);
        }
    }

    public List<Battle> getBattles() {
        return battles;
    }

    public String getTail() {
        return tail;
    }

    public void setTail(String tail) {
        if (!Customizations.isValidTail(tail)) {
            return;
        }
        this.tail = tail;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        if (!Customizations.isValidHead(head)) {
            return;
        }
        this.head = head;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void update(String color, String head, String tail){
        if(!color.isEmpty()){
            this.color = color;
        }
        if(!head.isEmpty() && Customizations.isValidHead(head)){
            this.head = head;
        }
        if(!tail.isEmpty() && Customizations.isValidTail(tail)){
            this.tail = tail;
        }
    }

    public String getAuthor() {
        return author;
    }

}

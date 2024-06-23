package nl.hu.bep.billy;


import java.util.Arrays;
import java.util.Random;

public class Customizations {
    private static final String[] heads = new String[]{"default", "smile", "pixel", "evil", "dead", "fang", "gamer", "sand-worm", "snowman", "beluga", "silly"};
    private static final String[] tails = new String[]{"default", "curled", "bolt", "coffee", "hook", "mouse", "replit-notmark", "tiger-tail", "round-bum", "small-rattle", "freckled"};
    private static final Random random = new Random();

    public Customizations() {
        random.setSeed(System.currentTimeMillis());
    }

    public static boolean isValidHead(String head) {
        return Arrays.asList(heads).contains(head);
    }

    public static boolean isValidTail(String tail) {
        return Arrays.asList(tails).contains(tail);
    }

    public static String getRandomHead() {
        return heads[random.nextInt(heads.length)];
    }

    public static String getRandomTail() {
        return tails[random.nextInt(tails.length)];
    }

    public static String[] getHeads(){
        return heads;
    }

    public static String[] getTails(){
        return tails;
    }

}

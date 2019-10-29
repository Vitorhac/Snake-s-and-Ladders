package org.underdogs.gloriagame;

public class Rule {

    private int newPosition;
    private String message;

    public Rule(int newPosition, String message) {
        this.message = message;
        this.newPosition = newPosition;
    }

    public int getNewPosition() {
        return newPosition;
    }

    public String getMessage() {
        return message;
    }
}

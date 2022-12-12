package com.example.dicegames;

public class Die {

    private final int sides;
    private final int cheating;

    //constructors
    public Die() {
        this.sides = 6;
        this.cheating = 0;
    }

    public Die(int sides, int cheating) {
        this.sides = sides;
        this.cheating = cheating;
    }

    //methods
    public int roll() {
        //the number
        if (cheating != 0) {
            int cheatRolledNum = (int) (Math.random() * 101);
            if (cheatRolledNum <= cheating) {
                return sides;
            } else {
                return (int) (Math.random() * sides - 1) + 1;
            }
        } else {
            return (int) (Math.random() * sides) + 1;
        }
    }
}

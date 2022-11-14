package com.example.dicegames;

public class Die {

    private int sides;
    private int cheating;

    //constructors
    public Die(){
        this.sides = 6;
        this.cheating = 0;
    }

    public Die(int sides, int cheating) {
        this.sides = sides;
        this.cheating = cheating;
    }

    //methods
    public int roll(){
        //the number
        int cheatRolledNum = (int)(Math.random()*101);
        if(cheatRolledNum <= cheating){
            return sides;
        }
        return (int)(Math.random()*sides) + 1;
    }

    //setters
    public void setSides(int sides) {
        this.sides = sides;
    }

    public void setCheating(int cheating) {
        this.cheating = cheating;
    }

    //getters
    public int getSides() {
        return sides;
    }

    public int getCheating() {
        return cheating;
    }
}
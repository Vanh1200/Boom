package com.vanh1200.models;

public class HighScore implements Comparable{
    private String name;
    private int score;

    public HighScore(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int compareTo(Object o) {
        HighScore highScore = (HighScore) o;
        if(this.score < highScore.getScore()){
            return 1;
        }
        else if(this.score > highScore.getScore())
            return -1;
        return 0;
    }
}

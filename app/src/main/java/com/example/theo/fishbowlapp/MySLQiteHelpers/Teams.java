package com.example.theo.fishbowlapp.MySLQiteHelpers;

public class Teams {

    private int id;
    private String name;
    private String color;
    private int points;
    private int mistakes_round;
    private int found_round;
    private int mistakes_overall;
    private int found_overall;

    public Teams(){}

    public Teams(String name, String color, int points, int mistakes_round, int found_round, int mistakes_overall, int found_overall) {
        super();
        this.name = name;
        this.color = color;
        this.points = points;
        this.mistakes_round = mistakes_round;
        this.found_round = found_round;
        this.mistakes_overall = mistakes_overall;
        this.found_overall = found_overall;
    }

    //getters & setters

    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", color=" + color + ", points=" + points +  ", points=" + points +
                ", mistakes_round=" + mistakes_round + ", found_round=" + found_round + ", mistakes_overall=" + mistakes_overall +  ", found_overall=" + found_overall +"]";
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getPoints() {
        return points;
    }

    public int getMistakesRound() {
        return mistakes_round;
    }

    public int getFoundRound() {
        return found_round;
    }

    public int getMistakesOverall() {
        return mistakes_overall;
    }

    public int getFoundOverall() {
        return found_overall;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setMistakesRound(int mistakesRound) {
        this.mistakes_round = mistakesRound;
    }

    public void setFoundRound(int foundRound) {
        this.found_round = foundRound;
    }

    public void setMistakesOverall(int mistakesOverall) {
        this.mistakes_overall = mistakesOverall;
    }

    public void setFoundOverall(int foundOverall) {
        this.found_overall = foundOverall;
    }
}

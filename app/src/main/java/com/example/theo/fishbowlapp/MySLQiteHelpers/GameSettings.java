package com.example.theo.fishbowlapp.MySLQiteHelpers;

public class GameSettings {

    private int id;
    private int teams_num;
    private int words_per_pl;
    private int seconds;

    public GameSettings(){}

    public GameSettings(int teams_num, int words_per_pl, int seconds) {
        super();
        this.teams_num = teams_num;
        this.words_per_pl = words_per_pl;
        this.seconds = seconds;
    }

    //getters & setters

    @Override
    public String toString() {
        return "GameSettings [id=" + id + ", teams_num=" + teams_num + ", words_per_pl=" + words_per_pl + ", seconds=" + seconds + "]";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public int getTeamsNum() {
        return teams_num;
    }

    public int getWordsPerPl() {
        return words_per_pl;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setTeamsNum(int teamsNum) {
        this.teams_num = teamsNum;
    }

    public void setWordsPerPl(int wordsPerPl) {
        this.words_per_pl = wordsPerPl;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }


}

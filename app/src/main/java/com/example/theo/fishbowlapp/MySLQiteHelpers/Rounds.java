package com.example.theo.fishbowlapp.MySLQiteHelpers;

public class Rounds {

    private int id;
    private int round_num;
    private int team_playing_id;

    public Rounds(){}

    public Rounds(int round_num, int team_playing_id) {
        super();
        this.round_num = round_num;
        this.team_playing_id = team_playing_id;   //none, mistaken, found
    }

    //getters & setters

    @Override
    public String toString() {
        return "Round [id=" + id + ", round_num=" + round_num + ", team_playing_id=" + team_playing_id + "]";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoundNum() {
        return round_num;
    }

    public void setRoundNum(int round_num) {
        this.round_num = round_num;
    }

    public int getTeamPlaying() {
        return team_playing_id;
    }

    public void setTeamPlaying(int team_playing_id) {
        this.team_playing_id = team_playing_id;
    }
}

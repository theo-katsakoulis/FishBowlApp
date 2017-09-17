package com.example.theo.fishbowlapp.MySLQiteHelpers;

public class Words {

    private int id;
    private String word;
    private String word_state;

    public Words(){}

    public Words(String word, String word_state) {
        super();
        this.word = word;
        this.word_state = word_state;   //none, mistaken, found
    }

    //getters & setters

    @Override
    public String toString() {
        return "Word [id=" + id + ", word=" + word + ", word_state=" + word_state + "]";
    }

    public String getWord() {
        return word;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordState() {
        return word_state;
    }

    public void setWordState(String wordState) {
        this.word_state = wordState;
    }
}

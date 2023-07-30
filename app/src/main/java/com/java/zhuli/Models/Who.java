package com.java.zhuli.Models;

import java.io.Serializable;

public class Who implements Serializable {
    double score;
    String word = "";

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString(){
        return
                "WhoItem{" +
                        "score = '" + score + '\'' +
                        ",word = '" + word + '\'' +
                        "}";
    }
}

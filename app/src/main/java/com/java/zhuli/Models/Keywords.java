package com.java.zhuli.Models;

import java.io.Serializable;

public class Keywords implements Serializable {
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
                "KeywordsItem{" +
                        "score = '" + score + '\'' +
                        ",word = '" + word + '\'' +
                        "}";
    }
}

package com.java.zhuli.Models;

import java.io.Serializable;

public class Persons implements Serializable {
    int count;
    String linkedURL = "";
    String mention = "";

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLinkedURL() {
        return linkedURL;
    }

    public void setLinkedURL(String linkedURL) {
        this.linkedURL = linkedURL;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    @Override
    public String toString(){
        return
                "PersonsItem{" +
                        "count = '" + count + '\'' +
                        ",linkedURL = '" + linkedURL + '\'' +
                        ",mention = '" + mention + '\'' +
                        "}";
    }
}

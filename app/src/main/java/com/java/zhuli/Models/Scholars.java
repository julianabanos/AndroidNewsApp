package com.java.zhuli.Models;

import java.io.Serializable;

public class Scholars implements Serializable {
    int count;
    String linkedURl = "";
    String mention = "";

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLinkedURl() {
        return linkedURl;
    }

    public void setLinkedURl(String linkedURl) {
        this.linkedURl = linkedURl;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }
}

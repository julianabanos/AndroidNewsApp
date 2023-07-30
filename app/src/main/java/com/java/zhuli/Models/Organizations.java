package com.java.zhuli.Models;

import java.io.Serializable;

public class Organizations implements Serializable {
    int count;
    String linkedURL = "";
    String mention = "";

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getLinkedURl() {
        return linkedURL;
    }

    public void setLinkedURl(String linkedURl) {
        this.linkedURL = linkedURl;
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
                "OrganizationsItem{" +
                        "count = '" + count + '\'' +
                        ",linkedURL = '" + linkedURL + '\'' +
                        ",mention = '" + mention + '\'' +
                        "}";
    }
}

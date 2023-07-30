package com.java.zhuli.Models;

import java.io.Serializable;

public class Locations implements Serializable {
    double lng;
    int count;
    String linkedURL = "";
    double lat;
    String mention = "";

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
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
                "LocationsItem{" +
                        "lng = '" + lng + '\'' +
                        ",count = '" + count + '\'' +
                        ",linkedURL = '" + linkedURL + '\'' +
                        ",lat = '" + lat + '\'' +
                        ",mention = '" + mention + '\'' +
                        "}";
    }
}

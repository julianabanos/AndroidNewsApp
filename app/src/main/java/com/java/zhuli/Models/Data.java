package com.java.zhuli.Models;

import java.io.Serializable;
import java.util.List;

public class Data implements Serializable {
    String image = "";
    String publishTime = "";
    List<Keywords> keywords;
    String language = "";
    String video = "";
    String title = "";
     List<When> when;
    String content = "";
    String url = "";
    List<Persons> persons;
    String newsID = "";
    String crawlTime = "";
    List<Organizations> organizations;
    String publisher = "";
    List<Locations> locations;
    List<Where> where;
    List<Scholars> scholars;
    String category = "";
    List<Who> who;
    Boolean saved = false;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public List<Keywords> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keywords> keywords) {
        this.keywords = keywords;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<When> getWhen() {
        return when;
    }

    public void setWhen(List<When> when) {
        this.when = when;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Persons> getPersons() {
        return persons;
    }

    public void setPersons(List<Persons> persons) {
        this.persons = persons;
    }

    public String getNewsID() {
        return newsID;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public String getCrawlTime() {
        return crawlTime;
    }

    public void setCrawlTime(String crawlTime) {
        this.crawlTime = crawlTime;
    }

    public List<Organizations> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organizations> organizations) {
        this.organizations = organizations;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public List<Locations> getLocations() {
        return locations;
    }

    public void setLocations(List<Locations> locations) {
        this.locations = locations;
    }

    public List<Where> getWhere() {
        return where;
    }

    public void setWhere(List<Where> where) {
        this.where = where;
    }

    public List<Scholars> getScholars() {
        return scholars;
    }

    public void setScholars(List<Scholars> scholars) {
        this.scholars = scholars;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Who> getWho() {
        return who;
    }

    public void setWho(List<Who> who) {
        this.who = who;
    }

    public void setSaved(){this.saved = true;}

    public Boolean getSaved(){return this.saved;}
    @Override
    public String toString(){
        return
                "DataItem{" +
                        "image = '" + image + '\'' +
                        ",publishTime = '" + publishTime + '\'' +
                        ",keywords = '" + keywords + '\'' +
                        ",language = '" + language + '\'' +
                        ",video = '" + video + '\'' +
                        ",title = '" + title + '\'' +
                        ",when = '" + when + '\'' +
                        ",content = '" + content + '\'' +
                        ",url = '" + url + '\'' +
                        ",persons = '" + persons + '\'' +
                        ",newsID = '" + newsID + '\'' +
                        ",crawlTime = '" + crawlTime + '\'' +
                        ",organizations = '" + organizations + '\'' +
                        ",publisher = '" + publisher + '\'' +
                        ",locations = '" + locations + '\'' +
                        ",where = '" + where + '\'' +
                        ",scholars = '" + scholars + '\'' +
                        ",category = '" + category + '\'' +
                        ",who = '" + who + '\'' +
                        "}";
    }
}

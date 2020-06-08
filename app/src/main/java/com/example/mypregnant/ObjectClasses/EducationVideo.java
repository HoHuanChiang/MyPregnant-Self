package com.example.mypregnant.ObjectClasses;

import java.io.Serializable;

public class EducationVideo implements Serializable {
    int videoID;
    String category;
    String content;
    int status;
    int pregnantWeek;
    String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(int pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }

    public int getVideoID() {
        return videoID;
    }

    public void setVideoID(int videoID) {
        this.videoID = videoID;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}

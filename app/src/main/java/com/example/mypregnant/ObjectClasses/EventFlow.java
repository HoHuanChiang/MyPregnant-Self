package com.example.mypregnant.ObjectClasses;

import java.io.Serializable;

public class EventFlow implements Serializable {
    public String content;
    public int order;
    public int pregnantWeek;
    public boolean isEducation;
    public int educationID;

    public boolean isEducation() {
        return isEducation;
    }

    public void setEducation(boolean education) {
        isEducation = education;
    }

    public int getEducationID() {
        return educationID;
    }

    public void setEducationID(int educationID) {
        this.educationID = educationID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(int pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }
}

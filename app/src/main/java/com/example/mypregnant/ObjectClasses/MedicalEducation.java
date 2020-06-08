package com.example.mypregnant.ObjectClasses;

import android.os.Parcelable;

import java.io.Serializable;

public class MedicalEducation implements Serializable {
    int educationID;
    String educationName;
    String Content;
    int isEducational;
    String category;
    String subContent;
    int parentID;
    int pregnantWeek;
    int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsEducational() {
        return isEducational;
    }

    public void setIsEducational(int isEducational) {
        this.isEducational = isEducational;
    }

    public int getPregnantWeek() {
        return pregnantWeek;
    }

    public void setPregnantWeek(int pregnantWeek) {
        this.pregnantWeek = pregnantWeek;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }


    public String getSubContent() {
        return subContent;
    }

    public void setSubContent(String subContent) {
        this.subContent = subContent;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getEducationID() {
        return educationID;
    }

    public void setEducationID(int educationID) {
        this.educationID = educationID;
    }
}

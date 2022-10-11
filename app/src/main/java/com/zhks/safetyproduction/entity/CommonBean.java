package com.zhks.safetyproduction.entity;

import java.io.Serializable;

public class CommonBean implements Serializable {
    private String id;
    private String name;
    private String type;
    private String date;
    private String resulet;
    private String address;
    private String situation;
    private String startDate;
    private String endDate;
    private String classHours;
    private String achievement;
    private String makeUpExam;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getResulet() {
        return resulet;
    }

    public void setResulet(String resulet) {
        this.resulet = resulet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSituation() {
        return situation;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getClassHours() {
        return classHours;
    }

    public void setClassHours(String classHours) {
        this.classHours = classHours;
    }

    public String getAchievement() {
        return achievement;
    }

    public void setAchievement(String achievement) {
        this.achievement = achievement;
    }

    public String getMakeUpExam() {
        return makeUpExam;
    }

    public void setMakeUpExam(String makeUpExam) {
        this.makeUpExam = makeUpExam;
    }
}


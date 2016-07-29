package com.langying.models;

import java.io.Serializable;

public class TQuestionOptionanswer implements Serializable {
    private String id;

    private String questionoptionid;

    private String questionid;

    private String answeroption;

    private String isrightoption;

    private String status;

    private String makerid;

    private String makedate;

    private String lastupdaterid;

    private String lastupdatedate;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionoptionid() {
        return questionoptionid;
    }

    public void setQuestionoptionid(String questionoptionid) {
        this.questionoptionid = questionoptionid;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getAnsweroption() {
        return answeroption;
    }

    public void setAnsweroption(String answeroption) {
        this.answeroption = answeroption;
    }

    public String getIsrightoption() {
        return isrightoption;
    }

    public void setIsrightoption(String isrightoption) {
        this.isrightoption = isrightoption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMakerid() {
        return makerid;
    }

    public void setMakerid(String makerid) {
        this.makerid = makerid;
    }

    public String getMakedate() {
        return makedate;
    }

    public void setMakedate(String makedate) {
        this.makedate = makedate;
    }

    public String getLastupdaterid() {
        return lastupdaterid;
    }

    public void setLastupdaterid(String lastupdaterid) {
        this.lastupdaterid = lastupdaterid;
    }

    public String getLastupdatedate() {
        return lastupdatedate;
    }

    public void setLastupdatedate(String lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }
}
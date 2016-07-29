package com.langying.models;

import java.io.Serializable;

public class TQuestionOption implements Serializable {
    private String id;

    private String questionid;

    private String questionoption;

    private String answer;

    private String answerpath;

    private String status;

    private String makerid;

    private String makedate;

    private String lastupdaterid;

    private String lastupdatedate;
    private String question;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionid() {
        return questionid;
    }

    public void setQuestionid(String questionid) {
        this.questionid = questionid;
    }

    public String getQuestionoption() {
        return questionoption;
    }

    public void setQuestionoption(String questionoption) {
        this.questionoption = questionoption;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerpath() {
        return answerpath;
    }

    public void setAnswerpath(String answerpath) {
        this.answerpath = answerpath;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
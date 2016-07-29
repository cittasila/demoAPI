package com.langying.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExercisesInfo implements Serializable {
    private Integer userDoquestionId;


    private Integer sort;


    private String question;
    private String questionId;
    private String doStatus;
    private String userIsRight;
    private String userSelectedOptionId;
    private List<ExercisesOptionInfo> exercisesOptionInfoL;

    private static final long serialVersionUID = 1L;

    public Integer getUserDoquestionId() {
        return userDoquestionId;
    }

    public void setUserDoquestionId(Integer userDoquestionId) {
        this.userDoquestionId = userDoquestionId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(String doStatus) {
        this.doStatus = doStatus;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserIsRight() {
        return userIsRight;
    }

    public void setUserIsRight(String userIsRight) {
        this.userIsRight = userIsRight;
    }

    public List<ExercisesOptionInfo> getExercisesOptionInfoL() {
        return exercisesOptionInfoL;
    }

    public void setExercisesOptionInfoL(List<ExercisesOptionInfo> exercisesOptionInfoL) {
        this.exercisesOptionInfoL = exercisesOptionInfoL;
    }

    public String getUserSelectedOptionId() {
        return userSelectedOptionId;
    }

    public void setUserSelectedOptionId(String userSelectedOptionId) {
        this.userSelectedOptionId = userSelectedOptionId;
    }
}
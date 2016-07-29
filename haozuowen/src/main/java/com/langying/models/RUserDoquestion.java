package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class RUserDoquestion implements Serializable {
    private Integer userDoquestionId;

    private Integer userId;

    private Integer userBookId;

    private String bookId;

    private String questionId;

    private Integer sort;

    private Integer userDotimes;

    private String createTime;

    private Date updateTime;

    private String doStatus;

    private String isActive;
    private String isright;

    private static final long serialVersionUID = 1L;

    public Integer getUserDoquestionId() {
        return userDoquestionId;
    }

    public void setUserDoquestionId(Integer userDoquestionId) {
        this.userDoquestionId = userDoquestionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserBookId() {
        return userBookId;
    }

    public void setUserBookId(Integer userBookId) {
        this.userBookId = userBookId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getUserDotimes() {
        return userDotimes;
    }

    public void setUserDotimes(Integer userDotimes) {
        this.userDotimes = userDotimes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getDoStatus() {
        return doStatus;
    }

    public void setDoStatus(String doStatus) {
        this.doStatus = doStatus;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getIsright() {
        return isright;
    }

    public void setIsright(String isright) {
        this.isright = isright;
    }
}
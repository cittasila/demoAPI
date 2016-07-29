package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class RReadingLog implements Serializable {
    private Integer readingLogId;

    private Integer userId;

    private String bookId;

    private String isFinish;

    private String evaluateId;

    private String createTime;

    private Date updateTime;

    private String isActive;

    private static final long serialVersionUID = 1L;

    public Integer getReadingLogId() {
        return readingLogId;
    }

    public void setReadingLogId(Integer readingLogId) {
        this.readingLogId = readingLogId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getIsFinish() {
        return isFinish;
    }

    public void setIsFinish(String isFinish) {
        this.isFinish = isFinish;
    }

    public String getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(String evaluateId) {
        this.evaluateId = evaluateId;
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

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class RUserQuestionOption implements Serializable {
    private Integer userDoquestionOptionId;

    private Integer userId;

    private Integer userDoquestionId;

    private String userOptionId;

    private String isright;

    private String createTime;

    private Date updateTime;

    private String isActive;

    private static final long serialVersionUID = 1L;

    public Integer getUserDoquestionOptionId() {
        return userDoquestionOptionId;
    }

    public void setUserDoquestionOptionId(Integer userDoquestionOptionId) {
        this.userDoquestionOptionId = userDoquestionOptionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserDoquestionId() {
        return userDoquestionId;
    }

    public void setUserDoquestionId(Integer userDoquestionId) {
        this.userDoquestionId = userDoquestionId;
    }

    public String getUserOptionId() {
        return userOptionId;
    }

    public void setUserOptionId(String userOptionId) {
        this.userOptionId = userOptionId;
    }

    public String getIsright() {
        return isright;
    }

    public void setIsright(String isright) {
        this.isright = isright;
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
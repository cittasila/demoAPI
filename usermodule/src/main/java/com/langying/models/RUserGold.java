package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class RUserGold implements Serializable {
    private Integer userGoldId;

    private Integer userId;

    private Integer goldNum;

    private Date createTime;

    private Date updateTime;

    private String isActive;

    private static final long serialVersionUID = 1L;

    public Integer getUserGoldId() {
        return userGoldId;
    }

    public void setUserGoldId(Integer userGoldId) {
        this.userGoldId = userGoldId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoldNum() {
        return goldNum;
    }

    public void setGoldNum(Integer goldNum) {
        this.goldNum = goldNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
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
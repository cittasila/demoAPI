package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class RUserGoldDetail implements Serializable {
    private Integer userGoldDetailId;

    private Integer userId;

    private Integer goldChange;

    private String detailType;

    private Date createTime;

    private String isActive;

    private Integer basicGold;

    private Integer goldOrderId;

    private String bookId;

    private static final long serialVersionUID = 1L;

    public Integer getUserGoldDetailId() {
        return userGoldDetailId;
    }

    public void setUserGoldDetailId(Integer userGoldDetailId) {
        this.userGoldDetailId = userGoldDetailId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoldChange() {
        return goldChange;
    }

    public void setGoldChange(Integer goldChange) {
        this.goldChange = goldChange;
    }

    public String getDetailType() {
        return detailType;
    }

    public void setDetailType(String detailType) {
        this.detailType = detailType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Integer getBasicGold() {
        return basicGold;
    }

    public void setBasicGold(Integer basicGold) {
        this.basicGold = basicGold;
    }

    public Integer getGoldOrderId() {
        return goldOrderId;
    }

    public void setGoldOrderId(Integer goldOrderId) {
        this.goldOrderId = goldOrderId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
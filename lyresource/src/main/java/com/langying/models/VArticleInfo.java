package com.langying.models;

import java.util.Date;

/**
 * Created by thtanghao on 2016/3/16.
 */
public class VArticleInfo implements Comparable<VArticleInfo>{
    private String id;
    private String number;
    private String booktitle;
    private String author;
    private String showlevel;
    private String imgurl;
    private String readStatus;
    private Integer lexile;
    private String tip;
    private Integer goldcoin;
    private Integer purchaseStatus;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBooktitle() {
        return booktitle;
    }

    public void setBooktitle(String booktitle) {
        this.booktitle = booktitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getShowlevel() {
        return showlevel;
    }

    public void setShowlevel(String showlevel) {
        this.showlevel = showlevel;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }

    public Integer getLexile() {
        return lexile;
    }

    public void setLexile(Integer lexile) {
        this.lexile = lexile;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getGoldcoin() {
        return goldcoin;
    }

    public void setGoldcoin(Integer goldcoin) {
        this.goldcoin = goldcoin;
    }

    public Integer getPurchaseStatus() {
        return purchaseStatus;
    }

    public void setPurchaseStatus(Integer purchaseStatus) {
        this.purchaseStatus = purchaseStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public int compareTo(VArticleInfo vArticleInfo) {
        if(this.updateTime.getTime() < vArticleInfo.getUpdateTime().getTime()){
            return 1;
        }else if(this.updateTime.getTime() > vArticleInfo.getUpdateTime().getTime()){
            return -1;
        }else{
            return 0;
        }
    }
}

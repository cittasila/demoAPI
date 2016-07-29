package com.langying.models;

import java.io.Serializable;

public class TArticleSentence implements Serializable {
    private String id;

    private String articleid;

    private Integer pagenum;

    private String pic;

    private Integer picpostion;

    private Integer sort;

    private String content;

    private String recordingpath;

    private Integer part;

    private String isrecording;

    private String makerid;

    private String makedate;

    private String lastupdaterid;

    private String lastupdatedate;

    private String picwidthandheight;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }

    public Integer getPagenum() {
        return pagenum;
    }

    public void setPagenum(Integer pagenum) {
        this.pagenum = pagenum;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getPicpostion() {
        return picpostion;
    }

    public void setPicpostion(Integer picpostion) {
        this.picpostion = picpostion;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRecordingpath() {
        return recordingpath;
    }

    public void setRecordingpath(String recordingpath) {
        this.recordingpath = recordingpath;
    }

    public Integer getPart() {
        return part;
    }

    public void setPart(Integer part) {
        this.part = part;
    }

    public String getIsrecording() {
        return isrecording;
    }

    public void setIsrecording(String isrecording) {
        this.isrecording = isrecording;
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

    public String getPicwidthandheight() {
        return picwidthandheight;
    }

    public void setPicwidthandheight(String picwidthandheight) {
        this.picwidthandheight = picwidthandheight;
    }
}
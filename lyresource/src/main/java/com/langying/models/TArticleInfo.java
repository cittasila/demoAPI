package com.langying.models;

import java.io.Serializable;
import java.util.List;

public class TArticleInfo implements Serializable {
    private String id;

    private String number;

    private String genreid;

    private String booktitle;

    private String author;

    private String packisbn;

    private String resourcesource;

    private Integer wordcount;

    private Integer pagecount;

    private Integer level;

    private String showlevel;

    private Integer sort;

    private Integer lexile;

    private Integer lesson;

    private String previewcoachpath;

    private String imgurl;

    private String status;

    private String makerid;

    private String makedate;

    private String lastupdaterid;

    private String lastupdatedate;

    private  List<List<TArticleSentence>> content;

    private long price=10;
    private static final long serialVersionUID = 1L;

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

    public String getGenreid() {
        return genreid;
    }

    public void setGenreid(String genreid) {
        this.genreid = genreid;
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

    public String getPackisbn() {
        return packisbn;
    }

    public void setPackisbn(String packisbn) {
        this.packisbn = packisbn;
    }

    public String getResourcesource() {
        return resourcesource;
    }

    public void setResourcesource(String resourcesource) {
        this.resourcesource = resourcesource;
    }

    public Integer getWordcount() {
        return wordcount;
    }

    public void setWordcount(Integer wordcount) {
        this.wordcount = wordcount;
    }

    public Integer getPagecount() {
        return pagecount;
    }

    public void setPagecount(Integer pagecount) {
        this.pagecount = pagecount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getShowlevel() {
        return showlevel;
    }

    public void setShowlevel(String showlevel) {
        this.showlevel = showlevel;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLexile() {
        return lexile;
    }

    public void setLexile(Integer lexile) {
        this.lexile = lexile;
    }

    public Integer getLesson() {
        return lesson;
    }

    public void setLesson(Integer lesson) {
        this.lesson = lesson;
    }

    public String getPreviewcoachpath() {
        return previewcoachpath;
    }

    public void setPreviewcoachpath(String previewcoachpath) {
        this.previewcoachpath = previewcoachpath;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
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

    long getPrice() {
        return price;
    }

    void setPrice(long price) {
        this.price = price;
    }

    public List<List<TArticleSentence>> getContent() {
        return content;
    }

    public void setContent(List<List<TArticleSentence>> content) {
        this.content = content;
    }
}
package com.langying.models;

import java.io.Serializable;
import java.util.Date;


public class USemester implements Serializable {
    private Integer semesterId;

    private String semesterName;

    private String semesterLevel;

    private Date semesterBeginDate;

    private Date semesterEndDate;

    private static final long serialVersionUID = 1L;

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getSemesterLevel() {
        return semesterLevel;
    }

    public void setSemesterLevel(String semesterLevel) {
        this.semesterLevel = semesterLevel;
    }

    public Date getSemesterBeginDate() {
        return semesterBeginDate;
    }

    public void setSemesterBeginDate(Date semesterBeginDate) {
        this.semesterBeginDate = semesterBeginDate;
    }

    public Date getSemesterEndDate() {
        return semesterEndDate;
    }

    public void setSemesterEndDate(Date semesterEndDate) {
        this.semesterEndDate = semesterEndDate;
    }
}
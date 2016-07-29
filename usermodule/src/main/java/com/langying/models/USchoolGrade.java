package com.langying.models;

import java.io.Serializable;

public class USchoolGrade implements Serializable {
    private Integer schoolGradeId;

    private String gradeId;

    private Integer schoolId;

    private String scoreStandardZh;

    private String scoreStandardEn;

    private static final long serialVersionUID = 1L;

    public Integer getSchoolGradeId() {
        return schoolGradeId;
    }

    public void setSchoolGradeId(Integer schoolGradeId) {
        this.schoolGradeId = schoolGradeId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

    public String getScoreStandardZh() {
        return scoreStandardZh;
    }

    public void setScoreStandardZh(String scoreStandardZh) {
        this.scoreStandardZh = scoreStandardZh;
    }

    public String getScoreStandardEn() {
        return scoreStandardEn;
    }

    public void setScoreStandardEn(String scoreStandardEn) {
        this.scoreStandardEn = scoreStandardEn;
    }
}
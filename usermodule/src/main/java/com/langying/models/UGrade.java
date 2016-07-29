package com.langying.models;

import java.io.Serializable;
public class UGrade implements Serializable {
    private String gradeId;

    private String gradeName;

    private Integer gradeEnglish;

    private static final long serialVersionUID = 1L;

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getGradeEnglish() {
        return gradeEnglish;
    }

    public void setGradeEnglish(Integer gradeEnglish) {
        this.gradeEnglish = gradeEnglish;
    }
}
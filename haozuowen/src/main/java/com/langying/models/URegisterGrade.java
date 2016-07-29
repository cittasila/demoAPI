package com.langying.models;

import java.io.Serializable;

/**
 * Created by Liwei on 2016/5/23.
 */
public class URegisterGrade implements Serializable {
    private Integer id;

    private Integer userId;

    private String gradeId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId == null ? null : gradeId.trim();
    }
}
package com.langying.models;

import java.io.Serializable;

public class UClassesUserKey implements Serializable {
    private Integer classesId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
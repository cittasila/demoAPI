package com.langying.models;

import java.io.Serializable;

public class UUserRoleKey implements Serializable {
    public UUserRoleKey() {
    }

    public UUserRoleKey(Integer roleId, Integer userId) {
        this.roleId = roleId;
        this.userId = userId;
    }

    private Integer roleId;

    private Integer userId;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
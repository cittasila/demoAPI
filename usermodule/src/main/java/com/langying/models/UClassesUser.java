package com.langying.models;

import java.io.Serializable;
import java.util.Date;

public class UClassesUser extends UClassesUserKey implements Serializable {
    private Integer groupId;

    private Date updateDate;

    private static final long serialVersionUID = 1L;

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
package com.langying.models;

import java.io.Serializable;

public class UDistcd implements Serializable {
    private String distCd;

    private String pDistCd;

    private String provName;

    private String cityName;

    private String cntyName;

    private String statusCd;

    private Byte levCd;

    private String isGradeSixInPrimarySchool;

    private static final long serialVersionUID = 1L;

    public String getDistCd() {
        return distCd;
    }

    public void setDistCd(String distCd) {
        this.distCd = distCd;
    }

    public String getpDistCd() {
        return pDistCd;
    }

    public void setpDistCd(String pDistCd) {
        this.pDistCd = pDistCd;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCntyName() {
        return cntyName;
    }

    public void setCntyName(String cntyName) {
        this.cntyName = cntyName;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public Byte getLevCd() {
        return levCd;
    }

    public void setLevCd(Byte levCd) {
        this.levCd = levCd;
    }

    public String getIsGradeSixInPrimarySchool() {
        return isGradeSixInPrimarySchool;
    }

    public void setIsGradeSixInPrimarySchool(String isGradeSixInPrimarySchool) {
        this.isGradeSixInPrimarySchool = isGradeSixInPrimarySchool;
    }
}
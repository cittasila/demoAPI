package com.langying.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.langying.handler.ResponseHandler;

import java.io.Serializable;
import java.util.Date;

public class UClasses implements Serializable {
    public interface ClassesView extends ResponseHandler.ResponseWrapperView {}
    @JsonView(ClassesView.class)
    private Integer classesId;

    private Integer schoolGradeId;

    private Integer semesterId;
    @JsonView(ClassesView.class)
    private String classesName;

    private String classesDescription;

    private Date classesBuildDate;
    @JsonView(ClassesView.class)
    private Integer studentNum;
    @JsonView(ClassesView.class)
    private Integer teacherId;

    @JsonView(ClassesView.class)
    private String gradeId;


    private static final long serialVersionUID = 1L;

    public Integer getClassesId() {
        return classesId;
    }

    public void setClassesId(Integer classesId) {
        this.classesId = classesId;
    }

    public Integer getSchoolGradeId() {
        return schoolGradeId;
    }

    public void setSchoolGradeId(Integer schoolGradeId) {
        this.schoolGradeId = schoolGradeId;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getClassesDescription() {
        return classesDescription;
    }

    public void setClassesDescription(String classesDescription) {
        this.classesDescription = classesDescription;
    }

    public Date getClassesBuildDate() {
        return classesBuildDate;
    }

    public void setClassesBuildDate(Date classesBuildDate) {
        this.classesBuildDate = classesBuildDate;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getGradeId() {
        String gradeIdExternal = null;
        switch (gradeId){
            case "P1":
                gradeIdExternal = "1";
                break;
            case "P2":
                gradeIdExternal = "2";
                break;
            case "P3":
                gradeIdExternal = "3";
                break;
            case "P4":
                gradeIdExternal = "4";
                break;
            case "P5":
                gradeIdExternal = "5";
                break;
            case "P6":
                gradeIdExternal = "6";
                break;
            case "PS":
                gradeIdExternal = "6";
                break;
            case "M1":
                gradeIdExternal = "7";
                break;
            case "M2":
                gradeIdExternal = "8";
                break;
            case "M3":
                gradeIdExternal = "9";
                break;
            case "H1":
                gradeIdExternal = "10";
                break;
            case "H2":
                gradeIdExternal = "11";
                break;
            case "H3":
                gradeIdExternal = "12";
                break;
            default:
                gradeIdExternal = "无对应年级信息";
        }
        return gradeIdExternal;
    }

    public void setGradeId(String gradeId) {
        this.gradeId = gradeId;
    }
}
package com.langying.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Liwei on 2016/5/17.
 * 用户年级与蓝思值对应表
 *
 */
@JsonIgnoreProperties({"minLexile","maxLexile"})
public class TGradeBook {

    private Integer id;

    @JsonProperty("grade_id")
    private String gradeId;
    @JsonProperty("grade_name")
    private String gradeName;

    private Integer minLexile;
    private Integer maxLexile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGradeId() {
        return gradeId;
    }

    public void setGradeId(String gradeLabel) {
        this.gradeId = gradeLabel;
    }

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getMinLexile() {
        return minLexile;
    }

    public void setMinLexile(Integer minLexile) {
        this.minLexile = minLexile;
    }

    public Integer getMaxLexile() {
        return maxLexile;
    }

    public void setMaxLexile(Integer maxLexile) {
        this.maxLexile = maxLexile;
    }

    @Override
    public String toString() {
        return "TGradeBook{" +
                "id=" + id +
                ", gradeId='" + gradeId + '\'' +
                ", gradeName='" + gradeName + '\'' +
                ", minLexile=" + minLexile +
                ", maxLexile=" + maxLexile +
                '}';
    }
}

package com.langying.models;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/31.
 */
public class   ResponseDesc {


    @ApiModel(value = "报告")
    public class Report{
        @ApiModelProperty(value = "写作水平")
     public writingLevel writingLevel;
        @ApiModelProperty(value = "写作微技能评估")
        public List<Map> microSkillsAssessment;
        @ApiModelProperty(value = "作文能力评估数据")
        public  List<Map> competencyAssessment;
        @ApiModelProperty(value = "系统评语")
        public Reviews reviews;
        @ApiModelProperty(value = "写作详情")
        public  detail detail;
        @ApiModelProperty(value = "错误汇总")
        public List<error> errorList;

    }
    public class ThreeAssessment{
        public String type;
        public float language;
        public float content;
        public float constuction;
        public float total;
    }
    @ApiModel(value = "系统评语")
    public class Reviews{
        @ApiModelProperty(value = "中文")
        public String comment_ch;
        @ApiModelProperty(value = "英文")
        public String comment_en;
    }
    public class error{
        @ApiModelProperty(value = "类型")
        public String rule_id;
        @ApiModelProperty(value = "数量")
        public int count;
    }
    public class detail{
        @ApiModelProperty(value = "教师")
        public String teacher;
        @ApiModelProperty(value = "字数统计")
        public String wordCount;
        @ApiModelProperty(value = "写作时间")
        public  String timeDiff;
        @ApiModelProperty(value = "标题")
        public  String essay_set_title;
        @ApiModelProperty(value = "学生姓名")
        public String studentName;
        @ApiModelProperty(value = "班级")
        public  String classes;
        @ApiModelProperty(value = "要求")
        public  String essay_set_requirement;
        @ApiModelProperty(value = "内容")
        public  String assignmentContent;
    }
    class FiveAssessment{
        public float ss;
        public float dev;
        public float org;
        public float wc;
        public float mech;
    }
    class writingLevel{
        public float classpoint;
        public float mypoint;
    }
}

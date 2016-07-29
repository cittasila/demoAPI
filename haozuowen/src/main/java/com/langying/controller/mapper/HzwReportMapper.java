package com.langying.controller.mapper;

import com.langying.common.models.LangYingSource;
import com.langying.models.RcoreComment;
import com.langying.models.ScoreStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by chenxu on 2016/3/21.
 */
public interface HzwReportMapper extends LangYingSource {
    List<Map> essayList(@Param(value = "userId")Integer userId, @Param(value = "essayList")String essayList);
    List<Map> errorList(@Param(value = "assignmentId")String assignmentId);
    List<Map> errorList(@Param(value = "assignmentId")Integer assignmentId);

    /**
     * 根据作业id查询评语
     * @param assignmentId
     * @return
     */
    List<RcoreComment> getRcoreCommentByAssignmentId(@Param(value = "assignmentId")Integer assignmentId);
	/**
     * 根据作业id查询教师评分的总分
     * @param assignmentId
     * @return
     */
    Float  getAssignmentMarkSumByAssignmentId(@Param(value = "assignmentId")Integer assignmentId);

    /**
     * 根据作业id查询教师评分的班级平均总分
     * @param assignmentId
     * @param classid
     * @return
     */
    Float  getAVGAssignmentMarkSumByAssignmentId(@Param(value = "assignmentId")Integer assignmentId,@Param(value = "classid")Integer classid);

    /**
     * 根据班级id查询评分标准
     * @param classid
     * @return
     */
    List<ScoreStandard> getStandardById(@Param(value = "classid")Integer classid);
    List<Map<String,String>> getEssayTitleAndRequirement(@Param(value = "assignmentId")Integer assignmentId);
    List<Map<String,String>> getAssignmentInfoByAssignmentId(@Param(value = "assignmentId")Integer assignmentId);
    List<Map<String,String>> getTeacherNameByAssignmentId(@Param(value = "assignmentId")Integer assignmentId);
    List<Map<String,Object>> getAssignmentPoint(@Param(value = "assignmentId")String assignmentId);
    List<Map<String,Integer>> getAssignmentInfoBySameClass(@Param(value = "assignmentId")Integer assignmentId);
    List<Map<String,Integer>> getAssignmentInfoBySameGrade(@Param(value = "assignmentId")Integer assignmentId);
    List<Map<String,Object>> getSubmitCount(@Param(value = "assignmentId")Integer assignmentId);
}

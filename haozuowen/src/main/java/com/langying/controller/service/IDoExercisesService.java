package com.langying.controller.service;

import com.langying.models.BookStatusInfo;
import com.langying.models.RUserBook;
import com.langying.models.RUserDoquestion;

import java.util.Map;

/**
 * Created by chenxu on 2016/4/19.
 */
public interface IDoExercisesService extends  IBaseService<RUserDoquestion>{

    BookStatusInfo exercisesStatus(String bookId) throws Exception;

    Map questionList(String bookId,int questionType) throws Exception;

    Map<String,Object> getNewExercises(String bookId) throws Exception;

    Map<String,Object> getCurrentExercises(String bookId) throws Exception;

    Map<String,Object> getExercisesOptionById(String exercisesId) throws Exception;

    Map<String,Object> getFinishExercisesById(Integer userDoquestionId)throws Exception;

    Map<String,Object> saveExercisesAnswer(Integer userDoquestionId,String userOptionId)throws Exception;

    Map<String,Object> getDoExercisesResult(String bookId) throws Exception;

    Integer queryUserFinishedRightCount(Map<String,Object> params);
    
}
